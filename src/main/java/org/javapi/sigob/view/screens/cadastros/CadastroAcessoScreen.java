package org.javapi.sigob.view.screens.cadastros;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.AcessoTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.popups.PopupValues;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de cadastro de acessos.
 */
public final class CadastroAcessoScreen extends BaseScreen {

    /**
     * Campo de nome.
     *
     * @see {@link JTextField}
     */
    private final JTextField nomeField = UI.textField(field -> {
        field.setColumns(20);
    });

    /**
     * Campo de descrição.
     *
     * @see {@link JTextArea}
     */
    private final JTextArea descricaoArea = UI.textArea(area -> {
        area.setRows(5);
        area.setColumns(20);
    });

    /**
     * Campo de pesquisa.
     *
     * @see {@link JTextField}
     */
    private final JTextField pesquisaField = UI.textField(field -> {
        field.setColumns(20);
    });

    /**
     * Modelo da tabela de acessos.
     *
     * @see {@link AcessoTableModel}
     */
    private final AcessoTableModel tableModel
            = new AcessoTableModel();

    /**
     * Tabela de acessos.
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
     * Cria tela de cadastro de acessos.
     */
    public CadastroAcessoScreen() {
        super("cadastro-acesso");

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
                        UIScreen.title("Cadastro de Acessos"),
                        UIScreen.subtitle(
                                "Gerencia os acessos e permissões disponíveis no sistema."
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
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Descrição [opcional]"),
                                descricaoArea
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
     * Cadastra um novo acesso.
     */
    private void cadastrar() {
        try {
            Acesso acesso = new Acesso();

            acesso.setNome(nomeField.getText());
            acesso.setDescricao(descricaoArea.getText());

            ApplicationContext
                    .getAcessoService()
                    .save(acesso);

            Popups.success("Acesso cadastrado com sucesso!");

            clearForm();

            listarTodos();
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Pesquisa acessos pelo nome informado.
     */
    private void pesquisar() {
        try {
            String nome = pesquisaField.getText();

            if (nome.isBlank()) {
                listarTodos();
                return;
            }

            List<Acesso> acessos = ApplicationContext
                    .getAcessoService()
                    .findByNome(nome);

            setResultados(acessos);
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Lista todos os acessos.
     */
    private void listarTodos() {
        try {
            List<Acesso> acessos = ApplicationContext
                    .getAcessoService()
                    .findAll();

            setResultados(acessos);
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Edita acesso selecionado.
     */
    private void editarSelecionado() {
        int row = table.getSelectedRow();

        if (row < 0) {
            Popups.warn("Selecione um acesso.");
            return;
        }

        Acesso acesso = tableModel.getAcesso(row);

        boolean confirmacao = Popups.confirm(
                "Você deseja editar esse acesso?"
        );

        if (!confirmacao) {
            return;
        }

        try {
            String nome = Popups.input(
                    "Editar Acesso",
                    """
                    Novo nome
                    Atual: %s

                    [vazio = manter]
                    """
                            .formatted(acesso.getNome())
            );

            if (PopupValues.wasCancelled(nome)) {
                return;
            }

            String descricao = Popups.input(
                    "Editar Descrição",
                    """
                    Nova descrição
                    Atual: %s

                    [vazio = manter]
                    [null = limpar]
                    """
                            .formatted(acesso.getDescricao())
            );

            if (PopupValues.wasCancelled(descricao)) {
                return;
            }

            if (!PopupValues.shouldKeep(nome)) {
                acesso.setNome(nome);
            }

            if (PopupValues.shouldClear(descricao)) {
                acesso.setDescricao(null);
            } else if (!PopupValues.shouldKeep(descricao)) {
                acesso.setDescricao(descricao);
            }

            ApplicationContext
                    .getAcessoService()
                    .update(acesso);

            Popups.success("Acesso atualizado com sucesso!");

            pesquisar();
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param acessos - Lista de acessos
     */
    private void setResultados(List<Acesso> acessos) {
        if (acessos.isEmpty()) {
            clearResults();

            Popups.warn("Nenhum acesso encontrado.");

            return;
        }

        tableModel.setAcessos(acessos);
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setAcessos(List.of());
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UIForm.clearFields(
                nomeField,
                descricaoArea
        );
    }

}

