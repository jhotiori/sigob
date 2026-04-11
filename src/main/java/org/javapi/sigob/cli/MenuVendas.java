package org.javapi.sigob.cli;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.entity.ProdutosVendas;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.repository.ClienteRepository;
import org.javapi.sigob.repository.FuncionarioRepository;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;
import org.javapi.sigob.repository.ProdutosVendasRepository;
import org.javapi.sigob.repository.VendaRepository;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.service.ProdutosVendasService;
import org.javapi.sigob.service.VendaService;

public class MenuVendas extends Menu {

    public MenuVendas() {
        super("Operações de Venda");
        adicionarEntrada("Listar vendas", this::listarVendas);
        adicionarEntrada("Iniciar nova venda", this::iniciarVenda);
        adicionarEntrada("Editar carrinho", this::editarCarrinho);
        adicionarEntrada("Finalizar venda", this::fecharVenda);
    }

    private VendaService getVendaService(EntityManager em) {
        return new VendaService(new VendaRepository(em));
    }

    private ProdutosVendasService getPvService(EntityManager em) {
        return new ProdutosVendasService(new ProdutosVendasRepository(em));
    }

    private ProdutosEstoquesService getPeService(EntityManager em) {
        return new ProdutosEstoquesService(new ProdutosEstoquesRepository(em));
    }

    private void listarVendas() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            List<Venda> vendas = getVendaService(em).getAll();

            if (vendas.isEmpty()) {
                System.out.println("⚠  Nenhuma venda encontrada.");
                return;
            }

            System.out.println("\n── Vendas ──");
            vendas.forEach(v -> System.out.printf(
                    "[%d] Data: %s | Valor: %.2f | Pago: %s | Cliente: %s | Funcionário: %s%n",
                    v.getIdVenda(),
                    v.getDtVenda() != null ? v.getDtVenda().toLocalDate() : "Em aberto",
                    v.getVlVenda() != null ? v.getVlVenda() : BigDecimal.ZERO,
                    v.isFlPago() ? "Sim" : "Não",
                    v.getCliente().getNmCliente(),
                    v.getFuncionario().getNmFuncionario()));
        } finally {
            em.close();
        }
    }

    private void iniciarVenda() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            ClienteService clienteService = new ClienteService(new ClienteRepository(em));
            FuncionarioService funcService = new FuncionarioService(new FuncionarioRepository(em));

            Cliente cliente = resolverCliente(clienteService);
            if (cliente == null)
                return;

            System.out.println("\n── Funcionários disponíveis ──");
            funcService.getAll()
                    .forEach(f -> System.out.printf("[%d] %s%n", f.getIdFuncionario(), f.getNmFuncionario()));

            int idFuncionario = lerInt("\nID do funcionário: ");
            Funcionario funcionario = funcService.getById(idFuncionario);
            if (funcionario == null) {
                System.out.println("✗ Funcionário não encontrado.");
                return;
            }

            Venda venda = new Venda();
            venda.setCliente(cliente);
            venda.setFuncionario(funcionario);
            venda.setFlPago(false);

            getVendaService(em).save(venda);
            System.out.println("✔ Venda aberta! Use 'Editar carrinho' para adicionar produtos.");
        } finally {
            em.close();
        }
    }

    private void editarCarrinho() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            List<Venda> abertas = getVendaService(em).getAll()
                    .stream()
                    .filter(v -> !v.isFlPago())
                    .toList();

            if (abertas.isEmpty()) {
                System.out.println("⚠  Nenhuma venda em aberto.");
                return;
            }

            System.out.println("\n── Vendas em aberto ──");
            abertas.forEach(v -> System.out.printf(
                    "[%d] Cliente: %s | Funcionário: %s%n",
                    v.getIdVenda(),
                    v.getCliente().getNmCliente(),
                    v.getFuncionario().getNmFuncionario()));

            int idVenda = lerInt("\nID da venda: ");
            Venda venda = getVendaService(em).getById(idVenda);
            if (venda == null || venda.isFlPago()) {
                System.out.println("✗ Venda inválida.");
                return;
            }

            boolean continuar = true;
            while (continuar) {
                List<ProdutosVendas> itens = getPvService(em).findByVenda(venda);
                System.out.println("\n── Carrinho — Venda #" + idVenda + " ──");
                if (itens.isEmpty()) {
                    System.out.println("  (vazio)");
                } else {
                    itens.forEach(pv -> System.out.printf(
                            "[%d] %s | Qtde: %d | Valor: %.2f%n",
                            pv.getIdProdutoVenda(),
                            pv.getProduto().getNmProduto(),
                            pv.getNrQuantidade(),
                            pv.getVlSaldo()));
                }

                System.out.println("\n[1] Adicionar item");
                System.out.println("[2] Remover item");
                System.out.println("[0] Voltar");
                System.out.print("\nEscolha: ");

                try {
                    int op = Integer.parseInt(new java.util.Scanner(System.in).nextLine().trim());
                    switch (op) {
                        case 1 -> adicionarItemCarrinho(em, venda);
                        case 2 -> removerItemCarrinho(em, venda);
                        case 0 -> continuar = false;
                        default -> System.out.println("⚠  Opção inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠  Digite um número.");
                }
            }
        } finally {
            em.close();
        }
    }

    private void adicionarItemCarrinho(EntityManager em, Venda venda) {
        List<ProdutosEstoques> estoque = getPeService(em).findAll()
                .stream()
                .filter(pe -> pe.getNrQuantidade() > 0)
                .toList();

        if (estoque.isEmpty()) {
            System.out.println("⚠  Nenhum produto em estoque.");
            return;
        }

        System.out.println("\n── Produtos em estoque ──");
        estoque.forEach(pe -> System.out.printf(
                "[%d] %s | Estoque: %s | Qtde disponível: %d | Preço: %.2f%n",
                pe.getIdProdutosEstoque(),
                pe.getProduto().getNmProduto(),
                pe.getEstoque().getNmEstoque(),
                pe.getNrQuantidade(),
                pe.getProduto().getVlProduto()));

        int idPe = lerInt("\nID do item (ProdutoEstoque): ");
        ProdutosEstoques pe = getPeService(em).findById(idPe);
        if (pe == null) {
            System.out.println("✗ Item não encontrado.");
            return;
        }

        int qtde = lerInt("Quantidade: ");
        if (qtde <= 0 || qtde > pe.getNrQuantidade()) {
            System.out.printf("✗ Quantidade inválida. Disponível: %d%n", pe.getNrQuantidade());
            return;
        }

        BigDecimal vlTotal = pe.getProduto().getVlProduto().multiply(BigDecimal.valueOf(qtde));

        ProdutosVendas pv = new ProdutosVendas(0, qtde, vlTotal, pe.getProduto(), venda);
        getPvService(em).save(pv);

        System.out.printf("✔ %dx '%s' adicionado ao carrinho. Subtotal: %.2f%n",
                qtde, pe.getProduto().getNmProduto(), vlTotal);
    }

    private void removerItemCarrinho(EntityManager em, Venda venda) {
        List<ProdutosVendas> itens = getPvService(em).findByVenda(venda);
        if (itens.isEmpty()) {
            System.out.println("⚠  Carrinho vazio.");
            return;
        }

        System.out.println("\n── Itens do carrinho ──");
        itens.forEach(pv -> System.out.printf(
                "[%d] %s | Qtde: %d | Valor: %.2f%n",
                pv.getIdProdutoVenda(),
                pv.getProduto().getNmProduto(),
                pv.getNrQuantidade(),
                pv.getVlSaldo()));

        int idPv = lerInt("\nID do item a remover: ");
        ProdutosVendas pv = getPvService(em).findById(idPv);

        if (pv == null || pv.getVenda().getIdVenda() != venda.getIdVenda()) {
            System.out.println("✗ Item não pertence a esta venda.");
            return;
        }

        getPvService(em).delete(pv);
        System.out.println("✔ Item removido do carrinho.");
    }

    private void fecharVenda() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            List<Venda> abertas = getVendaService(em).getAll()
                    .stream()
                    .filter(v -> !v.isFlPago())
                    .toList();

            if (abertas.isEmpty()) {
                System.out.println("⚠  Nenhuma venda em aberto.");
                return;
            }

            System.out.println("\n── Vendas em aberto ──");
            abertas.forEach(v -> System.out.printf(
                    "[%d] Cliente: %s | Funcionário: %s%n",
                    v.getIdVenda(),
                    v.getCliente().getNmCliente(),
                    v.getFuncionario().getNmFuncionario()));

            int idVenda = lerInt("\nID da venda: ");
            Venda venda = getVendaService(em).getById(idVenda);
            if (venda == null || venda.isFlPago()) {
                System.out.println("✗ Venda inválida.");
                return;
            }

            List<ProdutosVendas> itens = getPvService(em).findByVenda(venda);
            if (itens.isEmpty()) {
                System.out.println("⚠  Carrinho vazio. Adicione itens antes de fechar.");
                return;
            }

            BigDecimal subtotal = itens.stream()
                    .map(ProdutosVendas::getVlSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            System.out.println("\n── Resumo da venda #" + idVenda + " ──");
            itens.forEach(pv -> System.out.printf(
                    "  %s | Qtde: %d | Valor: %.2f%n",
                    pv.getProduto().getNmProduto(),
                    pv.getNrQuantidade(),
                    pv.getVlSaldo()));
            System.out.printf("%n  TOTAL: R$ %.2f%n", subtotal);

            boolean confirmar = lerBoolean("\nConfirmar pagamento?");
            if (!confirmar) {
                System.out.println("✗ Venda cancelada.");

                itens.forEach(pv -> getPvService(em).delete(pv));
                getVendaService(em).Delete(venda);
                return;
            }

            ProdutosEstoquesService peService = getPeService(em);
            for (ProdutosVendas pv : itens) {
                peService.findAll().stream()
                        .filter(pe -> pe.getProduto().getIdProduto() == pv.getProduto().getIdProduto()
                                && pe.getNrQuantidade() >= pv.getNrQuantidade())
                        .findFirst()
                        .ifPresentOrElse(pe -> {
                            pe.setNrQuantidade(pe.getNrQuantidade() - pv.getNrQuantidade());
                            if (pe.getNrQuantidade() == 0) {
                                peService.delete(pe);
                            } else {
                                peService.update(pe);
                            }
                        }, () -> System.out.printf(
                                "⚠  Estoque insuficiente para '%s' — item não baixado.%n",
                                pv.getProduto().getNmProduto()));
            }

            venda.setVlVenda(subtotal);
            venda.setDtVenda(ZonedDateTime.now());
            venda.setFlPago(true);
            getVendaService(em).save(venda);

            System.out.printf("✔ Venda #%d finalizada! Total: R$ %.2f%n", idVenda, subtotal);
        } finally {
            em.close();
        }
    }

    private Cliente resolverCliente(ClienteService clienteService) {
        String nmCliente = lerString("\nNome do cliente: ");
        List<Cliente> lista = clienteService.getByNome(nmCliente);

        if (lista != null && !lista.isEmpty()) {
            if (lista.size() == 1) {
                Cliente c = lista.get(0);
                System.out.printf("✔ Cliente localizado: [%d] %s | %s%n",
                        c.getIdCliente(), c.getNmCliente(), c.getNrDocumento());
                return c;
            }

            System.out.println("\n── Clientes encontrados ──");
            lista.forEach(c -> System.out.printf("[%d] %s | %s%n",
                    c.getIdCliente(), c.getNmCliente(), c.getNrDocumento()));

            int id = lerInt("\nID do cliente: ");
            Cliente c = lista.stream().filter(x -> x.getIdCliente() == id).findFirst().orElse(null);
            if (c == null) {
                System.out.println("✗ Cliente inválido.");
                return null;
            }
            return c;
        }

        int opcao = lerInt("Cliente não cadastrado. Deseja cadastrar? (1-Sim / 0-Não): ");
        if (opcao == 1) {
            Cliente novo = new Cliente();
            novo.setNmCliente(lerString("Nome: "));

            while (true) {
                String doc = lerString("Documento: ");
                if (clienteService.getByDoc(doc) != null) {
                    System.out.println("⚠  Documento já cadastrado. Tente outro.");
                } else {
                    novo.setNrDocumento(doc);
                    break;
                }
            }

            clienteService.save(novo);
            System.out.println("✔ Cliente cadastrado!");
            return novo;
        }

        Cliente padrao = clienteService.getAll().stream()
                .filter(c -> c.getNmCliente().equalsIgnoreCase("Cliente não informado"))
                .findFirst()
                .orElse(null);

        if (padrao == null) {
            padrao = new Cliente();
            padrao.setNmCliente("Cliente não informado");
            padrao.setNrDocumento("000000000");
            clienteService.save(padrao);
            System.out.println("Cliente padrão criado automaticamente.");
        }

        return padrao;
    }
}
