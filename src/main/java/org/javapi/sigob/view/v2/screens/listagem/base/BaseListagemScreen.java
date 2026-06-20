package org.javapi.sigob.view.v2.screens.listagem.base;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.components.ButtonComponent;
import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.components.TextFieldComponent;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.layouts.GridBuilder;
import org.javapi.sigob.view.v2.framework.styles.Spacing;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIText;

/**
 * Tela base de listagem de entidades.
 *
 * @param <T> - Tipo da entidade
 */
public abstract class BaseListagemScreen<T> extends BaseScreen {

    /**
     * Campo de pesquisa.
     *
     * @see TextFieldComponent
     */
    private final TextFieldComponent SEARCH_FIELD = UI.textField("Digite...");


    /**
     * Botão de pesquisa.
     *
     * @see ButtonComponent
     */
    private final ButtonComponent EDITAR_BUTTON = UI.button("Editar");

    /**
     * Botão de voltar.
     *
     * @see ButtonComponent
     */
    private final ButtonComponent VOLTAR_BUTTON = UI.button("Voltar");

    /**
     * Botão de criar.
     *
     * @see ButtonComponent
     */
    private final ButtonComponent EXCLUIR_BUTTON = UI.button("Excluir");

    /**
     * Label do título.
     *
     * @see LabelComponent
     */
    private final LabelComponent TITLE_LABEL;

    /**
     * Modelo da tabela.
     *
     * @see BaseEntityTableModel
     */
    private final BaseEntityTableModel<T> MODEL;

    /**
     * Tabela da tela.
     *
     * @see JTable
     */
    private final JTable TABLE;

    /**
     * Scroll da tabela.
     *
     * @see JScrollPane
     */
    private final JScrollPane TABLE_SCROLL;

    /**
     * Botões de pesquisa.
     */
    private final Map<String, ButtonComponent> SEARCH_BUTTONS = new LinkedHashMap<>();

    /**
     * Construtor.
     *
     * @param id - Identificador da tela
     * @param title - Título da tela
     * @param model - Modelo da tabela
     */
    protected BaseListagemScreen(
            String id,
            String title,
            BaseEntityTableModel<T> model
    ) {
        super(id);

        this.TITLE_LABEL = UIText.header(title);
        this.MODEL = model;
        this.TABLE = new JTable(model);
        this.TABLE.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        this.TABLE_SCROLL = UI.scroll(TABLE);

        searchButton(
            "id",
            "Buscar por ID"
        );

        searchButton(
            "todos",
            "Buscar Todos"
        );
    }

    /**
     * Registra botão de pesquisa.
     *
     * @param identifier - Identificador
     * @param label - Texto do botão
     */
    protected final void searchButton(
            String identifier,
            String label
    ) {
        SEARCH_BUTTONS.put(
                identifier,
                UI.button(label)
        );
    }

    /**
     * Registra evento de pesquisa.
     *
     * @param identifier - Identificador
     * @param action - Ação
     */
    public final void onSearch(
            String identifier,
            Runnable action
    ) {
        ButtonComponent button = SEARCH_BUTTONS.get(identifier);

        if (button != null) {
            UIEvents.bind(button)
                    .onClick(action);
        }
    }

    /**
     * Registra evento de voltar.
     *
     * @param action - Ação
     */
    public final void onVoltar(Runnable action) {
        UIEvents.bind(VOLTAR_BUTTON).onClick(action);
    }

    /**
     * Registra evento de editar.
     *
     * @param action - Ação
     */
    public final void onEditar(Runnable action) {
        UIEvents.bind(EDITAR_BUTTON).onClick(action);
    }

    /**
     * Registra evento de excluir.
     *
     * @param action - Ação
     */
    public final void onExcluir(Runnable action) {
        UIEvents.bind(EXCLUIR_BUTTON).onClick(action);
    }

    /**
     * Obtém texto pesquisado.
     *
     * @return String - Texto
     */
    public final String searchText() {
        return SEARCH_FIELD.getText();
    }

    /**
     * Define entidades.
     *
     * @param entities - Entidades
     */
    public void setEntities(
            Iterable<T> entities
    ) {
        MODEL.setEntities(entities);
    }

    /**
     * Limpa entidades.
     */
    public void clearEntities() {
        MODEL.clearEntities();
    }

    /**
     * Obtém entidade selecionada.
     *
     * @return T - Entidade
     */
    public final T getSelectedEntity() {
        int row = TABLE.getSelectedRow();

        if (row < 0) {
            return null;
        }

        int modelRow = TABLE.convertRowIndexToModel(row);
        return MODEL.entity(modelRow);
    }

    /**
     * Verifica se tem uma linha selecionada.
     *
     * @return boolean
     */
    public final boolean hasSelection() {
        return TABLE.getSelectedRow() >= 0;
    }

    /**
     * Limpa seleção.
     */
    public final void clearSelection() {
        TABLE.clearSelection();
    }

    /**
     * Obtém modelo da tabela.
     *
     * @return BaseEntityTableModel<T>
     */
    public final BaseEntityTableModel<T> model() {
        return MODEL;
    }

    /**
     * Obtém tabela.
     *
     * @return BaseEntityTable<T>
     */
    public final JTable table() {
        return TABLE;
    }

    /**
     * Constrói tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected final JPanel build() {
        return UILayouts.border()
                .add(buildCenter())
                .padding(Spacing.LG)
                .build();
    }

    /**
     * Constrói conteúdo central.
     *
     * @return JPanel - Painel
     */
    private JPanel buildCenter() {
        return UILayouts.column()
                .add(TITLE_LABEL)
                .gap(Spacing.SM)
                .add(SEARCH_FIELD)
                .gap(Spacing.MD)
                .add(buildSearchButtons())
                .gap(Spacing.MD)
                .add(TABLE_SCROLL)
                .gap(Spacing.MD)
                .add(buildFooter())
                .build();
    }

    /**
     * Constrói botões de pesquisa.
     *
     * @return JPanel - Painel
     */
    protected JPanel buildSearchButtons() {
        GridBuilder layout = UILayouts.grid(2, 3);
        SEARCH_BUTTONS.values().forEach(layout::add);

        return layout.build();
    }

    /**
     * Constrói rodapé.
     *
     * @return JPanel - Painel
     */
    protected JPanel buildFooter() {
        return UILayouts.row()
                .add(EDITAR_BUTTON)
                .gap(Spacing.XS)
                .add(EXCLUIR_BUTTON)
                .gap(Spacing.XS)
                .add(VOLTAR_BUTTON)
                .build();
    }
}
