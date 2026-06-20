package org.javapi.sigob.view.v2.screens.mercadoria;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.components.ButtonComponent;
import org.javapi.sigob.view.v2.framework.components.TableComponent;
import org.javapi.sigob.view.v2.framework.components.TextFieldComponent;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIText;
import org.javapi.sigob.view.v2.tables.ProdutosEstoquesTableModel;
import org.javapi.sigob.view.v2.framework.styles.Spacing;

/**
 * Tela de transferência de mercadorias.
 */
public final class MercadoriaTransferenciaScreen extends BaseScreen {

    /**
     * Combo de produtos origem.
     *
     * @see EntityComboBox
     */
    private final EntityComboBox<ProdutosEstoques> ORIGEM_BOX = UI.entityComboBox(produto -> {

        return "%s - %s (%d disponíveis)"
                .formatted(
                        produto.getProduto().getNome(),
                        produto.getEstoque().getNome(),
                        produto.getQuantidade());
    });

    /**
     * Combo de estoque destino.
     *
     * @see EntityComboBox
     */
    private final EntityComboBox<Estoque> DESTINO_BOX = UI.entityComboBox(estoque -> {

        return "%s (%s)"
                .formatted(
                        estoque.getNome(),
                        estoque.getCodigo());
    });

    /**
     * Campo de quantidade.
     */
    private final TextFieldComponent QUANTIDADE_FIELD = UI.textField();

    /**
     * Modelo da tabela.
     */
    private final ProdutosEstoquesTableModel MODEL = new ProdutosEstoquesTableModel();

    /**
     * Tabela.
     */
    private final TableComponent TABLE = UI.table(MODEL);

    /**
     * Scroll da tabela.
     */
    private final JScrollPane TABLE_SCROLL = UI.scroll(TABLE);

    /**
     * Botão transferir.
     */
    private final ButtonComponent TRANSFERIR_BUTTON = UI.button("Transferir");

    /**
     * Construtor.
     */
    public MercadoriaTransferenciaScreen() {

        super("mercadoria-transferencia");

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
     * Evento de transferência.
     *
     * @param action - Ação
     */
    public void onTransferir(
            Runnable action) {

        UIEvents.bind(
                TRANSFERIR_BUTTON).onClick(action);
    }

    /**
     * Retorna origem selecionada.
     *
     * @return ProdutosEstoques
     */
    public ProdutosEstoques origem() {

        return ORIGEM_BOX.getSelectedEntity();
    }

    /**
     * Retorna destino selecionado.
     *
     * @return Estoque
     */
    public Estoque destino() {

        return DESTINO_BOX.getSelectedEntity();
    }

    /**
     * Retorna quantidade.
     *
     * @return String
     */
    public String quantidade() {

        return QUANTIDADE_FIELD.getText();
    }

    /**
     * Define produtos disponíveis.
     *
     * @param produtos - Produtos
     */
    public void setProdutos(
            Iterable<ProdutosEstoques> produtos) {

        ORIGEM_BOX.setEntities(produtos);

        MODEL.setEntities(produtos);
    }

    /**
     * Define estoques destino.
     *
     * @param estoques - Estoques
     */
    public void setDestinos(
            Iterable<Estoque> estoques) {

        DESTINO_BOX.setEntities(estoques);
    }

    /**
     * Limpa quantidade.
     */
    public void clearQuantidade() {

        QUANTIDADE_FIELD.setText("");
    }

    /**
     * Constrói tela.
     *
     * @return JPanel
     */
    @Override
    protected JPanel build() {

        return UILayouts.border()

                .add(
                        UILayouts.column()

                                .add(
                                        UIText.header(
                                                "Transferência de Mercadorias"))

                                .gap(Spacing.MD)

                                .add(
                                        TABLE_SCROLL)

                                .gap(Spacing.MD)

                                .add(
                                        UIText.header(
                                                "Transferir"))

                                .add(
                                        buildTransferencia())

                                .build())

                .padding(
                        Spacing.LG)

                .build();
    }

    /**
     * Monta seção de transferência.
     *
     * @return JPanel
     */
    private JPanel buildTransferencia() {

        return UILayouts.column()

                .add(
                        ORIGEM_BOX)

                .gap(Spacing.XS)

                .add(
                        DESTINO_BOX)

                .gap(Spacing.XS)

                .add(
                        QUANTIDADE_FIELD)

                .gap(Spacing.XS)

                .add(
                        TRANSFERIR_BUTTON)

                .build();
    }
}
