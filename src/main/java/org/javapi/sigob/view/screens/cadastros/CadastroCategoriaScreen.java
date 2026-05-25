package org.javapi.sigob.view.screens.cadastros;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.CategoriaTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.popups.PopupValues;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de cadastro de categorias.
 */
public final class CadastroCategoriaScreen extends BaseScreen {

    /**
     * Campo de nome.
     *
     * @see {@link JTextField}
     */
    private final JTextField nomeField
            = UI.textField();

    /**
     * Campo de pesquisa.
     *
     * @see {@link JTextField}
     */
    private final JTextField pesquisaField
            = UI.textField();

    /**
     * Modelo da tabela de categorias.
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
    private final JTable table
            = UI.table(tableModel);

    /**
     * Botão de cadastro.
     *
     * @see {@link JButton}
     */
    private final JButton cadastrarButton
            = UI.button("Cadastrar");

    /**
     * Botão de limpeza.
     *
     * @see {@link JButton}
     */
    private final JButton limparButton
            = UI.button("Limpar");

    /**
     * Botão de pesquisa.
     *
     * @see {@link JButton}
     */
    private final JButton pesquisarButton
            = UI.button("Pesquisar");

    /**
     * Cria tela de cadastro de categorias.
     */
    public CadastroCategoriaScreen() {
        super("cadastro-categoria");

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        listarTodos();

        registerEvents();
    }

    /**
     * Registra eventos da tela.
     */
    private void registerEvents() {
        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(this::cadastrar);
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
        });

        Events.mouse(pesquisarButton, mouse -> {
            mouse.onClicked(this::pesquisar);
        });

        Events.mouse(table, mouse -> {
            mouse.onClicked(event -> {
                if (event.getClickCount() == 2) {
                    editarSelecionado();
                }
            });
        });
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz da tela
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildContent())
                .padding(Spacing.XL)
                .build();
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel - Conteúdo construído
     */
    private JPanel buildContent() {
        return UI.column()
                .add(
                        UIScreen.title("Cadastro de Categorias"),
                        UIScreen.subtitle(
                                "Gerencia categorias utilizadas para classificação de produtos."
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Cadastro",
                                buildCadastroSection()
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Atualizar",
                                buildAtualizacaoSection()
                        )
                )
                .build();
    }

    /**
     * Constrói seção de cadastro.
     *
     * @return JPanel - Seção construída
     */
    private JPanel buildCadastroSection() {
        return UI.column()
                .add(
                        UIForm.field(
                                UIForm.fieldLabel("Nome [obrigatorio]"),
                                nomeField
                        )
                )
                .glue()
                .add(
                        UIScreen.actions(
                                cadastrarButton,
                                limparButton
                        )
                )
                .build();
    }

    /**
     * Constrói seção de atualização.
     *
     * @return JPanel - Seção construída
     */
    private JPanel buildAtualizacaoSection() {
        return UI.column()
                .add(
                        UI.row()
                                .add(
                                        pesquisaField
                                )
                                .glue()
                                .add(
                                        pesquisarButton
                                )
                                .build(),
                        UI.scroll(table)
                )
                .build();
    }

    /**
     * Cadastra uma nova categoria.
     */
    private void cadastrar() {
        try {
            Categoria categoria = new Categoria();

            categoria.setNome(nomeField.getText());

            ApplicationContext
                    .getCategoriaService()
                    .save(categoria);

            Popups.success("Categoria cadastrada com sucesso!");

            clearForm();

            listarTodos();
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Pesquisa categorias pelo nome informado.
     */
    private void pesquisar() {
        try {
            String nome = pesquisaField.getText();

            if (nome.isBlank()) {
                listarTodos();
                return;
            }

            List<Categoria> categorias = ApplicationContext
                    .getCategoriaService()
                    .findByNome(nome);

            setResultados(categorias);
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Lista todas as categorias.
     */
    private void listarTodos() {
        try {
            List<Categoria> categorias = ApplicationContext
                    .getCategoriaService()
                    .findAll();

            setResultados(categorias);
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Edita categoria selecionada.
     */
    private void editarSelecionado() {
        int row = table.getSelectedRow();

        if (row < 0) {
            Popups.warn("Selecione uma categoria.");

            return;
        }

        Categoria categoria = tableModel.getCategoria(row);

        boolean confirmacao = Popups.confirm(
                "Você deseja editar essa categoria?"
        );

        if (!confirmacao) {
            return;
        }

        try {
            String nome = Popups.input(
                    "Editar Categoria",
                    """
                    Novo nome
                    Atual: %s

                    [vazio = manter]
                    """
                            .formatted(categoria.getNome())
            );

            if (PopupValues.wasCancelled(nome)) {
                return;
            }

            if (!PopupValues.shouldKeep(nome)) {
                categoria.setNome(nome);
            }

            ApplicationContext
                    .getCategoriaService()
                    .update(categoria);

            Popups.success("Categoria atualizada com sucesso!");

            pesquisar();
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param categorias - Lista de categorias
     */
    private void setResultados(List<Categoria> categorias) {
        if (categorias.isEmpty()) {
            clearResults();

            Popups.warn("Nenhuma categoria encontrada.");

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

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UIForm.clearFields(
                nomeField
        );
    }

}
