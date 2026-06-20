package org.javapi.sigob.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Caixa;
import org.javapi.sigob.model.entity.ItemVenda;
import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.model.entity.Saldo;
import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.model.service.CaixaService;
import org.javapi.sigob.model.service.ItemVendaService;
import org.javapi.sigob.model.service.ProdutosEstoquesService;
import org.javapi.sigob.model.service.SaldoService;
import org.javapi.sigob.model.service.VendaService;
import org.javapi.sigob.view.v2.context.CaixaContext;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.venda.VendaScreen;

/**
 * Controller da venda.
 */
public final class VendaEditorController {

    /**
     * Tela controlada.
     *
     * @see VendaScreen
     */
    private final VendaScreen SCREEN;

    /**
     * Serviço de itens.
     *
     * @see ItemVendaService
     */
    private final ItemVendaService itemVendaService;

    /**
     * Serviço de estoque.
     *
     * @see ProdutosEstoquesService
     */
    private final ProdutosEstoquesService estoqueService;

    /**
     * Serviço de vendas.
     *
     * @see VendaService
     */
    private final VendaService vendaService;

    /**
     * Serviço de saldos.
     *
     * @see SaldoService
     */
    private final SaldoService saldoService;

    /**
     * Serviço de caixas.
     *
     * @see CaixaService
     */
    private final CaixaService caixaService;

    /**
     * Construtor.
     *
     * @param screen - Tela da venda
     */
    public VendaEditorController(
            VendaScreen screen
        ) {

        this.SCREEN = screen;
        this.itemVendaService = ServiceFactory.itensVenda();
        this.estoqueService = ServiceFactory.produtosEstoques();
        this.vendaService = ServiceFactory.vendas();
        this.saldoService = ServiceFactory.saldos();
        this.caixaService = ServiceFactory.caixas();
        setup();
        refresh();
    }

    /**
     * Configura eventos.
     */
    private void setup() {

        SCREEN.onAdicionar(
                this::adicionar);

        SCREEN.onEditar(
                this::editar);

        SCREEN.onFinalizar(
                this::finalizar);

        SCREEN.onVoltar(
                this::voltar);

        SCREEN.onRemover(
                this::remover
        );

        SCREEN.onSelecionarItem(
                        this::carregarQuantidade);
    }

    /**
     * Atualiza dados da tela.
     */
    private void refresh() {
        loadProdutos();

        loadItens();

        updateState();
    }

    /**
     * Carrega produtos disponíveis.
     */
    private void loadProdutos() {

        List<ProdutosEstoques> produtos = estoqueService.findAll()
                .stream()
                .filter(
                        produto -> produto.getQuantidade() > 0)
                .toList();

        SCREEN.setProdutos(
                produtos);
    }

    /**
     * Carrega itens da venda.
     */
    private void loadItens() {

        List<ItemVenda> itens = itemVendaService.findByVenda(
                SCREEN.venda().getId());

        SCREEN.setItens(
                itens);

        BigDecimal total = itens.stream()
                .map(ItemVenda::getValorSaldo)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);

        SCREEN.setTotal(
                total);
    }

    /**
     * Calcula total da venda.
     *
     * @return Total
     */
    private BigDecimal calcularTotal() {

            return itemVendaService.findByVenda(
                            SCREEN.venda().getId())
                            .stream()
                            .map(ItemVenda::getValorSaldo)
                            .reduce(
                                            BigDecimal.ZERO,
                                            BigDecimal::add);
    }

    /**
     * Atualiza valor total da venda.
     */
    private void atualizarTotalVenda() {

            BigDecimal total = calcularTotal();

            Venda venda = SCREEN.venda();

            venda.setValorTotal(total);

            vendaService.update(venda);

            SCREEN.setTotal(total);
    }

    private void carregarQuantidade() {

            ItemVenda item = SCREEN.itemSelecionado();

            if (item == null) {
                    return;
            }

            SCREEN.setQuantidade(
                            String.valueOf(
                                            item.getQuantidade()));
    }

    /**
     * Adiciona item na venda.
     */
    private void adicionar() {

        try {

            ProdutosEstoques produto = SCREEN.produtoSelecionado();

            if (produto == null) {

                UIDialogs.error(
                        "Selecione um produto!");

                return;
            }

            int quantidade = parseQuantidade();

            validarQuantidade(
                    produto,
                    quantidade);

            Optional<ItemVenda> existente = itemVendaService
                    .findByVendaAndProdutoEstoque(
                            SCREEN.venda().getId(),
                            produto.getId());

            if (existente.isPresent()) {

                atualizarQuantidade(
                        existente.get(),
                        quantidade);

            } else {

                criarItem(
                        produto,
                        quantidade);
            }

            atualizarTotalVenda();
            refresh();

        } catch (Exception e) {

            UIDialogs.error(
                    e.getMessage());
        }
    }

    /**
     * Edita item selecionado.
     */
    private void editar() {

        ItemVenda item = SCREEN.itemSelecionado();

        if (item == null) {

            UIDialogs.error(
                    "Selecione um item!");

            return;
        }

        try {

            int quantidade = parseQuantidade();

            validarQuantidade(
                    item.getProdutoEstoque(),
                    quantidade);

            item.setQuantidade(
                    quantidade);

            item.setValorSaldo(
                    item.getProdutoEstoque()
                            .getProduto()
                            .getValorVenda()
                            .multiply(
                                    BigDecimal.valueOf(
                                            quantidade)));

            itemVendaService.update(
                    item);

            atualizarTotalVenda();
            refresh();

        } catch (Exception e) {

            UIDialogs.error(
                    e.getMessage());
        }
    }

    /**
     * Cria item.
     */
    private void criarItem(
            ProdutosEstoques produto,
            int quantidade) {

        BigDecimal total = produto.getProduto()
                .getValorVenda()
                .multiply(
                        BigDecimal.valueOf(
                                quantidade));

        ItemVenda item = new ItemVenda();

        item.setQuantidade(
                quantidade);

        item.setValorSaldo(
                total);

        item.setProdutoEstoque(
                produto);

        item.setVenda(
                SCREEN.venda());

        itemVendaService.save(
                item);
    }

    /**
     * Atualiza quantidade existente.
     */
    private void atualizarQuantidade(
                    ItemVenda item,
                    int quantidade) {

            int novaQuantidade = item.getQuantidade()
                            +
                            quantidade;

            validarQuantidade(
                            item.getProdutoEstoque(),
                            novaQuantidade);

            BigDecimal total = item.getProdutoEstoque()
                            .getProduto()
                            .getValorVenda()
                            .multiply(
                                            BigDecimal.valueOf(novaQuantidade));

            item.setQuantidade(
                            novaQuantidade);

            item.setValorSaldo(
                            total);

            itemVendaService.update(
                            item);
    }

    /**
     * Finaliza venda.
     */
    private void finalizar() {

        List<ItemVenda> itens = itemVendaService.findByVenda(
                SCREEN.venda().getId());

        if (itens.isEmpty()) {

            UIDialogs.error(
                    "Venda sem itens!");

            return;
        }

        boolean confirmar = UIDialogs.confirm(
                "Deseja finalizar venda?");

        if (!confirmar) {
            return;
        }

        BigDecimal total = itens.stream()
                .map(ItemVenda::getValorSaldo)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);

        itens.forEach(item -> {

            ProdutosEstoques estoque = item.getProdutoEstoque();

            estoque.setQuantidade(
                    estoque.getQuantidade()
                            -
                            item.getQuantidade());

            estoqueService.update(
                    estoque);

        });

        Venda venda = SCREEN.venda();

        venda.setStatus(
                "finalizada");

        venda.setValorTotal(
                total);

        venda.setDataFinalizada(
                OffsetDateTime.now());

        Saldo novoSaldo = new Saldo();
        novoSaldo.setDataSaldo(OffsetDateTime.now());
        novoSaldo.setDescricao("Finalização da Venda #" + venda.getId());
        novoSaldo.setTipo("venda");
        novoSaldo.setCaixa(CaixaContext.getCurrentCaixa());
        novoSaldo.setValorSaldo(total);
        novoSaldo.setVenda(venda);

        Caixa caixaAtual = CaixaContext.getCurrentCaixa();
        caixaAtual.setValorSaldo(
                caixaAtual.getValorSaldo().add(
                        total
                )
        );

        vendaService.update(venda);
        caixaService.update(caixaAtual);
        saldoService.save(novoSaldo);

        SCREEN.setEditable(false);
        refresh();
    }

    /**
     * Remove item selecionado.
     */
    private void remover() {

            ItemVenda item = SCREEN.itemSelecionado();

            if (item == null) {

                    UIDialogs.error(
                                    "Selecione um item!");

                    return;
            }

            boolean confirmar = UIDialogs.confirm(
                            "Deseja remover este item?");

            if (!confirmar) {
                    return;
            }

            try {
                    itemVendaService.delete(item);
                    refresh();
                    atualizarTotalVenda();
            } catch (Exception e) {
                    UIDialogs.error(
                                    e.getMessage());
            }
    }

    /**
     * Volta para dashboard.
     */
    private void voltar() {

        ScreenContext.show(
                "dashboard");
    }

    /**
     * Obtém quantidade.
     */
    private int parseQuantidade() {

        String text = SCREEN.quantidade();

        boolean valid = UIValidation.notBlank(
                text,
                "Informe a quantidade!");

        if (!valid) {

            throw new IllegalArgumentException(
                    "Quantidade inválida!");
        }

        return Integer.parseInt(text);
    }

    /**
     * Valida estoque.
     */
    private void validarQuantidade(
            ProdutosEstoques produto,
            int quantidade) {

        if (quantidade <= 0) {

            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero!");
        }

        if (quantidade > produto.getQuantidade()) {

            throw new IllegalArgumentException(
                    "Quantidade excede estoque disponível!");
        }
    }

    /**
     * Atualiza estado da tela.
     */
    private void updateState() {

        SCREEN.setEditable(
                !"finalizada".equalsIgnoreCase(
                        SCREEN.venda().getStatus()));
    }
}
