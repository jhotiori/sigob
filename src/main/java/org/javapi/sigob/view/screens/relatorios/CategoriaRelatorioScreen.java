package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.view.Actions;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.base.BaseRelatorioScreen;
import org.javapi.sigob.view.base.BaseTableModel;
import org.javapi.sigob.view.models.CategoriaTableModel;
import org.javapi.sigob.view.popups.PopupInputs;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIEvents;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de categorias.
 */
public final class CategoriaRelatorioScreen
        extends BaseRelatorioScreen<Categoria> {

    /**
     * Serviço de categorias.
     *
     * @see CategoriaService
     */
    private final CategoriaService categoriaService
            = ApplicationContext.getCategoriaService();

    /**
     * Modelo da tabela.
     *
     * @see CategoriaTableModel
     */
    private final CategoriaTableModel tableModel
            = new CategoriaTableModel();

    /**
     * Tabela de categorias.
     *
     * @see JTable
     */
    private final JTable table
            = UI.table(tableModel);

    /**
     * Botão de busca por ID.
     *
     * @see JButton
     */
    private final JButton buscarIdButton
            = UI.button("Buscar por ID");

    /**
     * Botão de busca por nome.
     *
     * @see JButton
     */
    private final JButton buscarNomeButton
            = UI.button("Buscar por Nome");

    /**
     * Botão de listagem geral.
     *
     * @see JButton
     */
    private final JButton listarTodosButton
            = UI.button("Listar Todos");

    /**
     * Botão de remoção.
     *
     * @see JButton
     */
    private final JButton removerButton
            = UI.button("Remover Selecionado");

    /**
     * Cria tela de relatório de categorias.
     */
    public CategoriaRelatorioScreen() {
        super("relatorio-categoria");

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        registerEvents();

        listarTodos();
    }

    /**
     * Atualiza tela.
     */
    @Override
    public void refresh() {
        listarTodos();
    }

    /**
     * Retorna tabela principal.
     *
     * @return JTable - Tabela principal
     */
    @Override
    protected JTable table() {
        return table;
    }

    /**
     * Retorna model da tabela.
     *
     * @return BaseTableModel<Categoria> - Model da tabela
     */
    @Override
    protected BaseTableModel<Categoria> tableModel() {
        return tableModel;
    }

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    @Override
    protected String entityNameSingular() {
        return "categoria";
    }

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    @Override
    protected String entityNamePlural() {
        return "categorias";
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildContent())
                .padding(Spacing.XL)
                .build();
    }

    /**
     * Registra eventos da tela.
     */
    private void registerEvents() {
        UIEvents.onClick(
                buscarIdButton,
                this::buscarPorId
        );

        UIEvents.onClick(
                buscarNomeButton,
                this::buscarPorNome
        );

        UIEvents.onClick(
                listarTodosButton,
                this::listarTodos
        );

        UIEvents.onClick(
                removerButton,
                this::removerSelecionado
        );
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel - Conteúdo construído
     */
    private JPanel buildContent() {
        return UI.column()
                .add(
                        UIScreen.title(
                                "Relatório de Categorias"
                        ),
                        UIScreen.subtitle(
                                """
                                Consulta e gerenciamento das categorias
                                cadastradas no sistema.
                                """
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Operações",
                                buildActions()
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Resultados",
                                UI.scroll(table)
                        )
                )
                .build();
    }

    /**
     * Constrói painel de ações.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildActions() {
        if (hasAdminAccess()) {
            return UI.grid(2, 4)
                    .add(
                            buscarIdButton,
                            buscarNomeButton,
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        }

        return UI.grid(2, 3)
                .add(
                        buscarIdButton,
                        buscarNomeButton,
                        listarTodosButton
                )
                .build();
    }

    /**
     * Busca categoria por ID.
     */
    private void buscarPorId() {
        Integer id = PopupInputs.integer(
                "Buscar Categoria",
                "ID da categoria:"
        );

        if (id == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar categoria!",
                () -> {
                    Optional<Categoria> categoria
                    = categoriaService.findById(id);

                    if (categoria.isEmpty()) {
                        clearResults();

                        Popups.warn(
                                "Categoria não encontrada!"
                        );

                        return;
                    }

                    setResultados(
                            List.of(categoria.get())
                    );
                }
        );
    }

    /**
     * Busca categorias por nome.
     */
    private void buscarPorNome() {
        String nome = PopupInputs.requiredText(
                "Buscar Categoria",
                "Nome da categoria:"
        );

        if (nome == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar categorias!",
                () -> {
                    setResultados(
                            categoriaService.findByNome(nome)
                    );
                }
        );
    }

    /**
     * Lista todas as categorias.
     */
    private void listarTodos() {
        Actions.safe(
                "Erro ao listar categorias!",
                () -> {
                    setResultados(
                            categoriaService.findAll()
                    );
                }
        );
    }

    /**
     * Remove categoria selecionada.
     */
    private void removerSelecionado() {
        Categoria categoria = selectedRow();

        if (categoria == null) {
            Popups.warn(
                    "Selecione uma categoria para remover!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                """
                Deseja remover a categoria '%s'?
                """
                        .formatted(
                                categoria.getNome()
                        )
        );

        if (!confirmacao) {
            return;
        }

        Actions.safe(
                "Erro ao remover categoria!",
                () -> {
                    categoriaService.delete(categoria);

                    Popups.success(
                            "Categoria removida com sucesso!"
                    );

                    listarTodos();
                }
        );
    }

}
