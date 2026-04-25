package org.javapi.sigob.cli;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.entity.ProdutosVendas;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.exception.ProdutosEstoquesException;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.service.ProdutosVendasService;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuVendas extends Menu {

    private final VendaService vendaService = new VendaService();
    private final ProdutosVendasService pvService = new ProdutosVendasService();
    private final ProdutosEstoquesService peService = new ProdutosEstoquesService();
    private final ClienteService clienteService = new ClienteService();
    private final FuncionarioService funcionarioService = new FuncionarioService();

    public MenuVendas() {
        super("Vendas");
        adicionarEntrada("Listar (TODOS)", this::listarVendas);
        adicionarEntrada("Cadastrar", this::iniciarVenda);
        adicionarEntrada("Atualizar", this::editarCarrinho);
        adicionarEntrada("Finalizar", this::fecharVenda);
    }

    private void listarVendas() {
        try {
            List<Venda> vendas = vendaService.findAll();

            if (vendas.isEmpty()) {
                Logger.warn("Nenhuma venda encontrada!");
                return;
            }

            for (Venda v : vendas) {
                System.out.printf(
                        "[%d] Data: %s | Valor: %.2f | Pago: %s | Cliente: %s | Funcionário: %s%n",
                        v.getIdVenda(),
                        v.getDtVenda() != null ? v.getDtVenda().toLocalDate() : "Em aberto",
                        v.getVlVenda() != null ? v.getVlVenda() : BigDecimal.ZERO,
                        v.isFlPago() ? "Sim" : "Não",
                        v.getCliente().getNmCliente(),
                        v.getFuncionario().getNmFuncionario());
            }
        } catch (Exception e) {
            Logger.error("Erro ao listar vendas: " + e.getMessage());
        }
    }

    private void iniciarVenda() {
        try {
            Cliente cliente = resolverCliente();
            if (cliente == null)
                return;

            List<Funcionario> funcionarios = funcionarioService.findAll();
            for (Funcionario f : funcionarios) {
                System.out.printf("[%d] %s%n", f.getIdFuncionario(), f.getNmFuncionario());
            }

            int idFuncionario = Inputter.lerInt("ID do funcionário: ");
            Funcionario funcionario = funcionarioService.findById(idFuncionario);

            if (funcionario == null) {
                Logger.warn("Funcionário não encontrado!");
                return;
            }

            Venda venda = new Venda();
            venda.setCliente(cliente);
            venda.setFuncionario(funcionario);
            venda.setFlPago(false);

            vendaService.save(venda);
            Logger.success("Venda aberta com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao iniciar venda: " + e.getMessage());
        }
    }

    private void editarCarrinho() {
        try {
            List<Venda> abertas = vendaService.findAll()
                    .stream()
                    .filter(v -> !v.isFlPago())
                    .toList();

            if (abertas.isEmpty()) {
                Logger.warn("Nenhuma venda em aberto!");
                return;
            }

            for (Venda v : abertas) {
                System.out.printf("[%d] Cliente: %s%n",
                        v.getIdVenda(),
                        v.getCliente().getNmCliente());
            }

            int idVenda = Inputter.lerInt("ID da venda: ");
            Venda venda = vendaService.findById(idVenda);

            if (venda == null || venda.isFlPago()) {
                Logger.warn("Venda inválida!");
                return;
            }

            boolean continuar = true;

            while (continuar) {
                List<ProdutosVendas> itens = pvService.findByVendaId(venda);

                if (itens.isEmpty()) {
                    Logger.warn("Carrinho vazio!");
                } else {
                    for (ProdutosVendas pv : itens) {
                        System.out.printf("[%d] %s | Qtde: %d | Valor: %.2f%n",
                                pv.getIdProdutoVenda(),
                                pv.getProduto().getNmProduto(),
                                pv.getNrQuantidade(),
                                pv.getVlSaldo());
                    }
                }

                int op = Inputter.lerInt("[1] Adicionar | [2] Remover | [0] Voltar: ");

                switch (op) {
                    case 1 -> adicionarItemCarrinho(venda);
                    case 2 -> removerItemCarrinho(venda);
                    case 0 -> continuar = false;
                    default -> Logger.warn("Opção inválida!");
                }
            }

        } catch (Exception e) {
            Logger.error("Erro ao editar carrinho: " + e.getMessage());
        }
    }

    private void adicionarItemCarrinho(Venda venda) {
        List<ProdutosEstoques> estoque = peService.findAll()
                .stream()
                .filter(pe -> pe.getNrQuantidade() > 0)
                .toList();

        if (estoque.isEmpty()) {
            Logger.warn("Nenhum produto em estoque!");
            return;
        }

        for (ProdutosEstoques pe : estoque) {
            System.out.printf("[%d] %s | Qtde: %d%n",
                    pe.getIdProdutosEstoque(),
                    pe.getProduto().getNmProduto(),
                    pe.getNrQuantidade());
        }

        int idPe = Inputter.lerInt("ID do item: ");
        ProdutosEstoques pe = peService.findById(idPe);

        if (pe == null) {
            Logger.warn("Item não encontrado!");
            return;
        }

        int qtde = Inputter.lerInt("Quantidade: ");

        if (qtde <= 0 || qtde > pe.getNrQuantidade()) {
            Logger.warn("Quantidade inválida!");
            return;
        }

        BigDecimal total = pe.getProduto().getVlProduto()
                .multiply(BigDecimal.valueOf(qtde));

        ProdutosVendas pv = new ProdutosVendas(0, qtde, total, pe.getProduto(), venda);
        pvService.save(pv);

        Logger.success("Item adicionado ao carrinho!");
    }

    private void removerItemCarrinho(Venda venda) {
        List<ProdutosVendas> itens = pvService.findByVendaId(venda);

        if (itens.isEmpty()) {
            Logger.warn("Carrinho vazio!");
            return;
        }

        for (ProdutosVendas pv : itens) {
            System.out.printf("[%d] %s%n",
                    pv.getIdProdutoVenda(),
                    pv.getProduto().getNmProduto());
        }

        int id = Inputter.lerInt("ID do item: ");
        ProdutosVendas pv = pvService.findById(id);

        if (pv == null || pv.getVenda().getIdVenda() != venda.getIdVenda()) {
            Logger.warn("Item inválido!");
            return;
        }

        pvService.delete(pv);
        Logger.success("Item removido!");
    }

    private void fecharVenda() {
        try {
            List<Venda> abertas = vendaService.findAll()
                    .stream()
                    .filter(v -> !v.isFlPago())
                    .toList();

            if (abertas.isEmpty()) {
                Logger.warn("Nenhuma venda em aberto!");
                return;
            }

            for (Venda v : abertas) {
                System.out.printf("[%d] %s%n",
                        v.getIdVenda(),
                        v.getCliente().getNmCliente());
            }

            int id = Inputter.lerInt("ID da venda: ");
            Venda venda = vendaService.findById(id);

            if (venda == null || venda.isFlPago()) {
                Logger.warn("Venda inválida!");
                return;
            }

            List<ProdutosVendas> itens = pvService.findByVendaId(venda);

            if (itens.isEmpty()) {
                Logger.warn("Carrinho vazio!");
                return;
            }

            BigDecimal total = itens.stream()
                    .map(ProdutosVendas::getVlSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            boolean confirmar = Inputter.lerBoolean("Confirmar pagamento?");

            if (!confirmar) {
                pvService.findByVendaId(venda).forEach(pvService::delete);
                vendaService.delete(venda);
                Logger.warn("Venda cancelada!");
                return;
            }

            for (ProdutosVendas pv : itens) {
                peService.findAll().stream()
                        .filter(pe -> pe.getProduto().getIdProduto() == pv.getProduto().getIdProduto())
                        .findFirst()
                        .ifPresent(pe -> {
                            pe.setNrQuantidade(pe.getNrQuantidade() - pv.getNrQuantidade());
                            if (pe.getNrQuantidade() == 0) {
                                peService.delete(pe);
                            } else {
                                try {
                                    peService.update(pe);
                                } catch (ProdutosEstoquesException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
            }

            venda.setVlVenda(total);
            venda.setDtVenda(ZonedDateTime.now());
            venda.setFlPago(true);

            vendaService.save(venda);
            Logger.success("Venda finalizada com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao finalizar venda: " + e.getMessage());
        }
    }

    private Cliente resolverCliente() {
        String nome = Inputter.lerString("Nome do cliente: ");
        List<Cliente> lista = clienteService.findByNome(nome);

        if (!lista.isEmpty()) {
            return lista.get(0);
        }

        Cliente c = new Cliente();
        c.setNmCliente(nome);
        c.setNrDocumento("000000000");

        clienteService.save(c);
        return c;
    }
}
