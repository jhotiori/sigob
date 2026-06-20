package org.javapi.sigob.view.v2.screens.venda;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.javapi.sigob.model.entity.ItemVenda;
import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.view.v2.framework.components.ButtonComponent;
import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.components.TableComponent;
import org.javapi.sigob.view.v2.framework.components.TextFieldComponent;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIUtils;
import org.javapi.sigob.view.v2.framework.ui.UIText;
import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.tables.ItemVendaTableModel;
import org.javapi.sigob.view.v2.framework.styles.Spacing;

/**
 * Tela de edição de venda.
 */
public final class VendaScreen extends BaseScreen {

    /**
     * Formatter de data.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Venda atual.
     *
     * @see Venda
     */
    private final Venda VENDA;

    /**
     * ComboBox de produtos em estoque.
     *
     * @see EntityComboBox
     */
    private final EntityComboBox<ProdutosEstoques> PRODUTO_BOX = UI.entityComboBox(produto -> {

        return "%s - %s (%d disponíveis)"
                .formatted(
                        produto.getProduto().getNome(),
                        produto.getEstoque().getNome(),
                        produto.getQuantidade());
    });

    /**
     * Campo de quantidade.
     *
     * @see TextFieldComponent
     */
    private final TextFieldComponent QUANTIDADE_FIELD = UI.textField();

    /**
     * Modelo do carrinho.
     *
     * @see ItemVendaTableModel
     */
    private final ItemVendaTableModel MODEL = new ItemVendaTableModel();

    /**
     * Tabela do carrinho.
     *
     * @see TableComponent
     */
    private final TableComponent TABLE = UI.table(MODEL);

    /**
     * Scroll da tabela.
     *
     * @see JScrollPane
     */
    private final JScrollPane TABLE_SCROLL = UI.scroll(TABLE);

    /**
     * Label do total.
     *
     * @see LabelComponent
     */
    private final LabelComponent TOTAL_LABEL = UI.label("Total: R$0,00");

    /**
     * Botão adicionar.
     */
    private final ButtonComponent ADICIONAR_BUTTON = UI.button("Adicionar");

    /**
     * Botão remover.
     */
    private final ButtonComponent REMOVER_BUTTON = UI.button("Remover");

    /**
     * Botão editar.
     */
    private final ButtonComponent EDITAR_BUTTON = UI.button("Editar");

    /**
     * Botão finalizar.
     */
    private final ButtonComponent FINALIZAR_BUTTON = UI.button("Finalizar");

    /**
     * Botão voltar.
     */
    private final ButtonComponent VOLTAR_BUTTON = UI.button("Voltar");

    /**
     * Construtor.
     *
     * @param venda - Venda atual
     */
    public VendaScreen(Venda venda) {
        super("venda-" + venda.getId());

        this.VENDA = venda;

        setupTable();
    }

    /**
     * Configura tabela.
     */
    private void setupTable() {

        TABLE.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Registra evento de adicionar.
     *
     * @param action - Ação
     */
    public void onAdicionar(
            Runnable action) {
        UIEvents.bind(
                ADICIONAR_BUTTON).onClick(action);
    }

    public void onRemover(
            Runnable action) {
        UIEvents.bind(
                REMOVER_BUTTON).onClick(action);
    }

    /**
     * Registra evento de editar.
     *
     * @param action - Ação
     */
    public void onEditar(
            Runnable action) {
        UIEvents.bind(
                EDITAR_BUTTON).onClick(action);
    }

    /**
     * Registra evento de finalizar.
     *
     * @param action - Ação
     */
    public void onFinalizar(
            Runnable action) {
        UIEvents.bind(
                FINALIZAR_BUTTON).onClick(action);
    }

    /**
     * Registra evento de voltar.
     *
     * @param action - Ação
     */
    public void onVoltar(
            Runnable action) {
        UIEvents.bind(
                VOLTAR_BUTTON).onClick(action);
    }

    /**
     * Registra evento de selecionar item.
     *
     * @param action - Ação
     */
    public void onSelecionarItem(Runnable action) {
            TABLE.getSelectionModel()
                            .addListSelectionListener(event -> {

                                    if (!event.getValueIsAdjusting()) {
                                            action.run();
                                    }
                            });
    }

    /**
     * Retorna produto selecionado.
     *
     * @return ProdutosEstoques - Produto
     */
    public ProdutosEstoques produtoSelecionado() {

        return PRODUTO_BOX.getSelectedEntity();
    }

    /**
     * Retorna quantidade digitada.
     *
     * @return String - Quantidade
     */
    public String quantidade() {

        return QUANTIDADE_FIELD.getText();
    }

    /**
     * Limpa quantidade.
     */
    public void clearQuantidade() {
            UIUtils.clearField(
                            QUANTIDADE_FIELD);
    }

    /**
     * Retorna item selecionado.
     *
     * @return ItemVenda - Item
     */
    public ItemVenda itemSelecionado() {

        int row = TABLE.getSelectedRow();

        if (row < 0) {
            return null;
        }

        return MODEL.entity(
                TABLE.convertRowIndexToModel(row));
    }

    /**
     * Define produtos disponíveis.
     *
     * @param produtos - Produtos
     */
    public void setProdutos(
            Iterable<ProdutosEstoques> produtos) {

        PRODUTO_BOX.setEntities(produtos);
    }

    /**
     * Define itens da venda.
     *
     * @param itens - Itens
     */
    public void setItens(
            Iterable<ItemVenda> itens) {

        MODEL.setEntities(itens);
    }

    /**
     * Define total da venda.
     *
     * @param total - Total
     */
    public void setTotal(
            BigDecimal total) {

        TOTAL_LABEL.setText(
                "Total: R$%s"
                        .formatted(total));
    }

    /**
     * Define quantidade.
     *
     * @param quantidade - Quantidade
     */
    public void setQuantidade(String quantidade) {
            QUANTIDADE_FIELD.setText(
                            quantidade);
    }

    /**
     * Define estado editável.
     *
     * @param editable - Estado
     */
    public void setEditable(
            boolean editable) {

        PRODUTO_BOX.setEnabled(editable);
        QUANTIDADE_FIELD.setEnabled(editable);

        UIUtils.enableButton(
                ADICIONAR_BUTTON,
                REMOVER_BUTTON,
                EDITAR_BUTTON,
                FINALIZAR_BUTTON);

        if (!editable) {

            UIUtils.disableButton(
                    ADICIONAR_BUTTON,
                    REMOVER_BUTTON,
                    EDITAR_BUTTON,
                    FINALIZAR_BUTTON);
        }
    }

    /**
     * Retorna venda atual.
     *
     * @return Venda - Venda
     */
    public Venda venda() {

        return VENDA;
    }

    /**
     * Constrói tela.
     *
     * @return JPanel - Painel
     */
    @Override
    protected JPanel build() {

        return UILayouts.border()
                .add(
                        UILayouts.column()

                                .add(
                                        UIText.header(
                                                "Venda #" + VENDA.getId()))

                                .gap(Spacing.MD)

                                .add(
                                        buildInfo())

                                .gap(Spacing.MD)

                                .add(
                                        buildAdicionar())

                                .gap(Spacing.MD)

                                .add(
                                        TABLE_SCROLL)

                                .gap(Spacing.SM)

                                .add(
                                        TOTAL_LABEL)

                                .gap(Spacing.MD)

                                .add(
                                        buildButtons())

                                .build())
                .padding(Spacing.LG)
                .build();
    }

    /**
     * Constrói informações da venda.
     *
     * @return JPanel - Painel
     */
    private JPanel buildInfo() {

        return UILayouts.grid(2, 2)
                .add(
                        UI.label(
                                "Funcionário: "
                                        + VENDA.getFuncionario().getNome()))

                .add(
                        UI.label(
                                "Cliente: "
                                        + VENDA.getCliente().getNome()))

                .add(
                        UI.label(
                                "Status: "
                                        + VENDA.getStatus()))

                .add(
                        UI.label(
                                "Abertura: "
                                        + VENDA.getDataAbertura()
                                                .format(FORMATTER)))

                .build();
    }

    /**
     * Constrói seção de adição.
     *
     * @return JPanel - Painel
     */
    private JPanel buildAdicionar() {

        return UILayouts.column()

                .add(
                        PRODUTO_BOX)

                .gap(Spacing.XS)

                .add(
                        QUANTIDADE_FIELD)

                .gap(Spacing.XS)

                .add(
                        ADICIONAR_BUTTON)

                .build();
    }

    /**
     * Constrói botões inferiores.
     *
     * @return JPanel - Painel
     */
    private JPanel buildButtons() {

        return UILayouts.row()

                .add(
                        EDITAR_BUTTON)

                .gap(Spacing.XS)

                .add(
                        REMOVER_BUTTON)
                .gap(Spacing.XS)

                .add(
                        FINALIZAR_BUTTON)

                .gap(Spacing.XS)

                .add(
                        VOLTAR_BUTTON)

                .build();
    }
}
