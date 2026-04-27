package org.javapi.sigob.cli;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.ItemVenda;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.service.ItemVendaService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuVendas extends Menu {

    private final VendaService vendaService;
    private final ItemVendaService itemService;
    private final ProdutosEstoquesService estoqueService;
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    public MenuVendas(
            VendaService vendaService,
            ItemVendaService itemService,
            ProdutosEstoquesService estoqueService,
            ClienteService clienteService,
            FuncionarioService funcionarioService
    ) {
        super("Operações de Vendas");
        add("Listar (TODOS)", this::listar);
        add("Abrir Venda", this::abrir);
        add("Editar Carrinho", this::editarCarrinho);
        add("Finalizar Venda", this::finalizar);
        this.vendaService = vendaService;
        this.itemService = itemService;
        this.estoqueService = estoqueService;
        this.clienteService = clienteService;
        this.funcionarioService = funcionarioService;
    }

    private void listar() {
        try {
            List<Venda> vendas = vendaService.findAll();

            if (vendas.isEmpty()) {
                Logger.warn("Nenhuma venda encontrada!");
                return;
            }

            for (Venda v : vendas) {
                System.out.println(v);
            }

        } catch (Exception e) {
            Logger.error("Erro ao listar vendas: " + e.getMessage());
        }
    }

    private void abrir() {
        try {
            Cliente cliente = resolverCliente();
            if (cliente == null) {
                return;
            }

            Funcionario funcionario = resolverFuncionario();
            if (funcionario == null) {
                return;
            }

            Venda venda = new Venda();
            venda.setCliente(cliente);
            venda.setFuncionario(funcionario);
            venda.setStatus("ABERTA");
            venda.setDataAbertura(OffsetDateTime.now());
            venda.setValorTotal(BigDecimal.ZERO);

            vendaService.save(venda);
            Logger.success("Venda aberta com sucesso!");
            editarCarrinho(venda);
        } catch (Exception e) {
            Logger.error("Erro ao abrir venda: " + e.getMessage());
        }
    }

    private void editarCarrinho() {
        try {
            Venda venda = resolverVendaAberta();
            if (venda == null) {
                return;
            }

            editarCarrinho(venda);
        } catch (Exception e) {
            Logger.error("Erro ao editar carrinho: " + e.getMessage());
        }
    }

    private void editarCarrinho(Venda venda) {
        boolean loop = true;

        while (loop) {
            renderCarrinho(venda);

            int op = Inputter.readInt(
                    "[1] Adicionar | [2] Remover | [0] Voltar: "
            );

            switch (op) {
                case 1 ->
                    adicionarItem(venda);
                case 2 ->
                    removerItem(venda);
                case 0 ->
                    loop = false;
                default ->
                    Logger.warn("Opção inválida!");
            }
        }
    }

    private void adicionarItem(Venda venda) {
        List<ProdutosEstoques> lista = estoqueService.findAll()
                .stream()
                .filter(p -> p.getQuantidade() > 0)
                .toList();

        if (lista.isEmpty()) {
            Logger.warn("Nenhum produto disponível!");
            return;
        }

        for (ProdutosEstoques pe : lista) {
            System.out.printf("[%d] %s | Quantidade: %d%n",
                    pe.getId(),
                    pe.getProduto().getNome(),
                    pe.getQuantidade());
        }

        int id = Inputter.readInt("ID do ProdutoEstoque: ");

        Optional<ProdutosEstoques> opt = estoqueService.findById(id);
        if (opt.isEmpty()) {
            Logger.warn("Item não encontrado!");
            return;
        }

        ProdutosEstoques pe = opt.get();

        int qtd = Inputter.readInt("Quantidade: ");

        if (qtd <= 0 || qtd > pe.getQuantidade()) {
            Logger.warn("Quantidade inválida!");
            return;
        }

        try {
            Optional<ItemVenda> existing = itemService.findByVendaAndProdutoEstoque(
                    venda.getId(),
                    pe.getId()
            );

            if (existing.isPresent()) {
                ItemVenda i = existing.get();
                i.setQuantidade(i.getQuantidade() + qtd);

                BigDecimal novoTotal = pe.getProduto()
                        .getValorVenda()
                        .multiply(BigDecimal.valueOf(i.getQuantidade()));

                i.setValorUnitario(novoTotal);
                itemService.update(i);
                Logger.success("Quantidade atualizada!");

            } else {
                BigDecimal total = pe.getProduto()
                        .getValorVenda()
                        .multiply(BigDecimal.valueOf(qtd));

                ItemVenda item = new ItemVenda(0, qtd, total, pe, venda);

                itemService.save(item);

                Logger.success("Item adicionado!");
            }

        } catch (Exception e) {
            Logger.error("Erro ao adicionar item: " + e.getMessage());
        }
    }

    private void removerItem(Venda venda) {
        List<ItemVenda> itens = itemService.findByVenda(venda.getId());

        if (itens.isEmpty()) {
            Logger.warn("Carrinho vazio!");
            return;
        }

        for (ItemVenda i : itens) {
            System.out.printf("[%d] %s%n",
                    i.getId(),
                    i.getProdutoEstoque().getProduto().getNome());
        }

        int id = Inputter.readInt("ID do item: ");

        Optional<ItemVenda> opt = itemService.findById(id);

        if (opt.isEmpty() || opt.get().getVenda().getId() != venda.getId()) {
            Logger.warn("Item inválido!");
            return;
        }

        try {
            itemService.delete(opt.get());
            Logger.success("Item removido!");

        } catch (Exception e) {
            Logger.error("Erro ao remover item: " + e.getMessage());
        }
    }

    private void finalizar() {
        try {
            Venda venda = resolverVendaAberta();
            if (venda == null) {
                return;
            }

            List<ItemVenda> itens = itemService.findByVenda(venda.getId());

            if (itens.isEmpty()) {
                Logger.warn("Carrinho vazio!");
                return;
            }

            BigDecimal total = itens.stream()
                    .map(ItemVenda::getValorUnitario)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            boolean confirmar = Inputter.readBoolean("Confirmar pagamento? [S/N]: ");

            if (!confirmar) {
                Logger.warn("Venda cancelada!");
                return;
            }

            for (ItemVenda i : itens) {
                ProdutosEstoques pe = i.getProdutoEstoque();
                pe.setQuantidade(pe.getQuantidade() - i.getQuantidade());
                estoqueService.update(pe);
            }

            venda.setStatus("FINALIZADA");
            venda.setDataFinalizada(OffsetDateTime.now());
            venda.setValorTotal(total);

            vendaService.update(venda);

            Logger.success("Venda finalizada!");

        } catch (Exception e) {
            Logger.error("Erro ao finalizar venda: " + e.getMessage());
        }
    }

    private void renderCarrinho(Venda venda) {
        List<ItemVenda> itens = itemService.findByVenda(venda.getId());

        if (itens.isEmpty()) {
            Logger.warn("Carrinho vazio!");
            return;
        }

        for (ItemVenda i : itens) {
            System.out.printf("[%d] %s | Qtde: %d | Total: %s%n",
                    i.getId(),
                    i.getProdutoEstoque().getProduto().getNome(),
                    i.getQuantidade(),
                    i.getValorUnitario());
        }
    }

    private Venda resolverVendaAberta() {
        List<Venda> abertas = vendaService.findAbertas();

        if (abertas.isEmpty()) {
            Logger.warn("Nenhuma venda aberta!");
            return null;
        }

        for (Venda v : abertas) {
            System.out.printf("[%d] Cliente: %s%n",
                    v.getId(),
                    v.getCliente().getNome());
        }

        int id = Inputter.readInt("ID da venda: ");

        Optional<Venda> opt = vendaService.findById(id);

        if (opt.isEmpty() || !"ABERTA".equals(opt.get().getStatus())) {
            Logger.warn("Venda inválida!");
            return null;
        }

        return opt.get();
    }

    private Cliente resolverCliente() {
        String nome = Inputter.readString("Nome do cliente: ");

        List<Cliente> lista = clienteService.findByNome(nome);

        if (!lista.isEmpty()) {
            return lista.get(0);
        }

        Cliente c = new Cliente();
        c.setNome(nome);

        clienteService.save(c);

        return c;
    }

    private Funcionario resolverFuncionario() {
        List<Funcionario> lista = funcionarioService.findAll();

        if (lista.isEmpty()) {
            Logger.warn("Nenhum funcionário encontrado!");
            return null;
        }

        for (Funcionario f : lista) {
            System.out.printf("[%d] %s%n", f.getId(), f.getNome());
        }

        int id = Inputter.readInt("ID do funcionário: ");

        Optional<Funcionario> opt = funcionarioService.findById(id);

        if (opt.isEmpty()) {
            Logger.warn("Funcionário não encontrado!");
            return null;
        }

        return opt.get();
    }
}
