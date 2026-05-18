package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.CategoriaTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de categorias.
 */
public final class CategoriaRelatorioScreen extends BaseScreen {

    /**
     * Serviço de categorias.
     *
     * @see {@link CategoriaService}
     */
    private final CategoriaService categoriaService = ApplicationContext.getCategoriaService();

    /**
     * Modelo da tabela.
     *
     * @see {@link CategoriaTableModel}
     */
    private final CategoriaTableModel tableModel
            = new CategoriaTableModel();

    /**
     * Tabela de categorias.
     *
     * @see {@link JTable}
     */
    private final JTable table = UI.table(tableModel);

    /**
     * Botão de listagem por ID.
     *
     * @see {@link JButton}
     */
    private final JButton buscarIdButton
            = UI.button("Buscar por ID");

    /**
     * Botão de listagem por nome.
     *
     * @see {@link JButton}
     */
    private final JButton buscarNomeButton
            = UI.button("Buscar por Nome");

    /**
     * Botão de listagem geral.
     *
     * @see {@link JButton}
     */
    private final JButton listarTodosButton
            = UI.button("Listar Todos");

    /**
     * Botão de remoção.
     *
     * @see {@link JButton}
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
        Events.mouse(buscarIdButton, mouse -> {
            mouse.onClicked(this::buscarPorId);
        });

        Events.mouse(buscarNomeButton, mouse -> {
            mouse.onClicked(this::buscarPorNome);
        });

        Events.mouse(listarTodosButton, mouse -> {
            mouse.onClicked(this::listarTodos);
        });

        Events.mouse(removerButton, mouse -> {
            mouse.onClicked(this::removerSelecionado);
        });
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel - Conteúdo construído
     */
    private JPanel buildContent() {
        return UI.column()
                .add(
                        UIScreen.title("Relatório de Categorias"),
                        UIScreen.subtitle(
                                "Consulta e gerenciamento das categorias cadastradas no sistema."
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
        if (ApplicationContext.hasFuncionarioAcesso("admin")) {
            return UI.grid(2, 4)
                    .add(
                            buscarIdButton,
                            buscarNomeButton,
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        } else {
            return UI.grid(2, 3)
                    .add(
                            buscarIdButton,
                            buscarNomeButton,
                            listarTodosButton
                    )
                    .build();
        }

    }

    /**
     * Busca categoria por ID.
     */
    private void buscarPorId() {
        try {
            String input = Popups.input(
                    "ID da categoria:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            int id = Integer.parseInt(input);

            Optional<Categoria> categoria = categoriaService.findById(id);

            if (categoria.isEmpty()) {
                clearResults();

                Popups.warn("Categoria não encontrada!");

                return;
            }

            setResultados(
                    List.of(categoria.get())
            );
        } catch (NumberFormatException e) {
            Popups.error("ID inválido!");
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar categoria: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca categorias por nome.
     */
    private void buscarPorNome() {
        try {
            String nome = Popups.input(
                    "Nome da categoria:"
            );

            if (nome == null || nome.isBlank()) {
                return;
            }

            List<Categoria> categorias = categoriaService.findByNome(nome);

            setResultados(categorias);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar categorias: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Lista todas as categorias.
     */
    private void listarTodos() {
        try {
            List<Categoria> categorias = categoriaService.findAll();

            setResultados(categorias);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao listar categorias: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Remove categoria selecionada.
     */
    private void removerSelecionado() {
        try {
            int row = table.getSelectedRow();

            if (row < 0) {
                Popups.warn(
                        "Selecione uma categoria para remover!"
                );

                return;
            }

            Categoria categoria = tableModel.getCategoria(row);

            boolean confirmacao = Popups.confirm(
                    "Deseja remover a categoria '%s'?"
                            .formatted(categoria.getNome())
            );

            if (!confirmacao) {
                return;
            }


            categoriaService.delete(categoria);

            Popups.success(
                    "Categoria removida com sucesso!"
            );

            listarTodos();
        } catch (Exception e) {
            Popups.error(
                    "Erro ao remover categoria: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param categorias - Lista de categorias
     */
    private void setResultados(
            List<Categoria> categorias
    ) {
        if (categorias == null || categorias.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhuma categoria encontrada!"
            );

            return;
        }

        tableModel.setCategorias(categorias);
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setCategorias(List.of());
    }

}
