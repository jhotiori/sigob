package org.javapi.sigob.view.screens.cadastros;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.EstoqueTableModel;
import org.javapi.sigob.view.popups.PopupValues;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de cadastro de estoques.
 */
public final class CadastroEstoqueScreen extends BaseScreen {

    /**
     * Serviço de estoques.
     */
    private final EstoqueService estoqueService
            = ApplicationContext.getEstoqueService();

    /**
     * Campo de código.
     */
    private final JTextField codigoField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * Campo de nome.
     */
    private final JTextField nomeField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * Campo de pesquisa.
     */
    private final JTextField pesquisaField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * ComboBox de modo de pesquisa.
     */
    private final JComboBox<String> pesquisaModoCombo = UI.comboBox(
            "Código",
            "Nome"
    );

    /**
     * Botão de cadastro.
     */
    private final JButton cadastrarButton = UI.button("Cadastrar");

    /**
     * Botão de limpeza.
     */
    private final JButton limparButton = UI.button("Limpar");

    /**
     * Botão de pesquisa.
     */
    private final JButton pesquisarButton = UI.button("Pesquisar");

    /**
     * Modelo da tabela.
     */
    private final EstoqueTableModel estoqueTableModel
            = new EstoqueTableModel();

    /**
     * Tabela de estoques.
     */
    private final JTable estoquesTable = UI.table(
            estoqueTableModel
    );

    /**
     * Cria tela de cadastro de estoques.
     */
    public CadastroEstoqueScreen() {
        super("cadastro-estoque");

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        loadEstoques();

        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(this::cadastrarEstoque);
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
        });

        Events.mouse(pesquisarButton, mouse -> {
            mouse.onClicked(this::pesquisarEstoques);
        });

        Events.mouse(estoquesTable, mouse -> {
            mouse.onClicked(event -> {
                if (event.getClickCount() == 2) {
                    editarEstoqueSelecionado();
                }
            });
        });
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildForm())
                .padding(Spacing.XL)
                .build();
    }

    /**
     * Constrói formulário principal.
     *
     * @return JPanel - Formulário construído
     */
    private JPanel buildForm() {
        return UI.column()
                .add(
                        UIScreen.title("Cadastro de Estoques"),
                        UIScreen.subtitle(
                                "Gerencia os estoques disponíveis para armazenamento de produtos."
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Cadastro",
                                buildCadastro()
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Atualizar",
                                buildAtualizar()
                        )
                )
                .build();
    }

    /**
     * Constrói seção de cadastro.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildCadastro() {
        return UI.column()
                .add(
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Código (ex: EST001) [obrigatorio]"
                                ),
                                codigoField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Nome [obrigatorio]"
                                ),
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
     * @return JPanel - Painel construído
     */
    private JPanel buildAtualizar() {
        return UI.column()
                .add(
                        buildSearchPanel(),
                        UI.scroll(estoquesTable)
                )
                .build();
    }

    /**
     * Constrói painel de pesquisa.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildSearchPanel() {
        return UI.row()
                .add(
                        pesquisaModoCombo,
                        pesquisaField
                )
                .glue()
                .add(
                        pesquisarButton
                )
                .build();
    }

    /**
     * Realiza cadastro de estoque.
     */
    private void cadastrarEstoque() {
        try {
            Estoque estoque = new Estoque();

            estoque.setCodigo(
                    codigoField.getText().trim()
            );

            estoque.setNome(
                    nomeField.getText().trim()
            );

            estoqueService.save(estoque);

            Popups.success(
                    "Estoque cadastrado com sucesso!"
            );

            clearForm();
            loadEstoques();

        } catch (Exception e) {
            String message = e.getMessage();

            if (message != null) {
                String lowerMessage = message.toLowerCase();

                if (lowerMessage.contains("unique")
                        || lowerMessage.contains("duplic")
                        || lowerMessage.contains("constraint")) {

                    Popups.error(
                            """
                            Já existe um estoque com esse código.
                            Utilize outro código único.
                            """
                    );

                    return;
                }
            }

            Popups.error(
                    message != null
                            ? message
                            : "Ocorreu um erro inesperado."
            );
        }
    }

    /**
     * Pesquisa estoques.
     */
    private void pesquisarEstoques() {
        try {
            String pesquisa = pesquisaField
                    .getText()
                    .trim();

            if (pesquisa.isBlank()) {
                loadEstoques();

                return;
            }

            String modo = (String) pesquisaModoCombo
                    .getSelectedItem();

            List<Estoque> estoques;

            if ("Nome".equalsIgnoreCase(modo)) {
                estoques = estoqueService
                        .findByNome(pesquisa);

            } else {
                estoques = estoqueService
                        .findByCodigo(pesquisa);
            }

            setResultados(estoques);

        } catch (Exception e) {
            Popups.error(
                    e.getMessage()
            );
        }
    }

    /**
     * Edita estoque selecionado.
     */
    private void editarEstoqueSelecionado() {
        int selectedRow = estoquesTable.getSelectedRow();

        if (selectedRow < 0) {
            Popups.warn(
                    "Selecione um estoque!"
            );

            return;
        }

        Estoque estoque = estoqueTableModel
                .getEstoque(selectedRow);

        if (estoque == null) {
            Popups.warn(
                    "Estoque inválido!"
            );

            return;
        }

        boolean confirmed = Popups.confirm(
                "Você deseja editar esse estoque?"
        );

        if (!confirmed) {
            return;
        }

        try {
            String novoCodigo = Popups.input(
                    "Editar Código",
                    """
                    Novo código [ENTER mantém atual]
                    Atual: %s
                    """
                            .formatted(
                                    estoque.getCodigo()
                            )
            );

            if (PopupValues.wasCancelled(novoCodigo)) {
                return;
            }

            String novoNome = Popups.input(
                    "Editar Nome",
                    """
                    Novo nome [ENTER mantém atual]
                    Atual: %s
                    """
                            .formatted(
                                    estoque.getNome()
                            )
            );

            if (PopupValues.wasCancelled(novoNome)) {
                return;
            }

            if (!PopupValues.shouldKeep(novoCodigo)) {
                estoque.setCodigo(
                        novoCodigo.trim()
                );
            }

            if (!PopupValues.shouldKeep(novoNome)) {
                estoque.setNome(
                        novoNome.trim()
                );
            }

            estoqueService.update(estoque);

            Popups.success(
                    "Estoque atualizado com sucesso!"
            );

            pesquisarEstoques();

        } catch (Exception e) {
            String message = e.getMessage();

            if (message != null) {
                String lowerMessage = message.toLowerCase();

                if (lowerMessage.contains("unique")
                        || lowerMessage.contains("duplic")
                        || lowerMessage.contains("constraint")) {

                    Popups.error(
                            """
                            Já existe um estoque com esse código.
                            Utilize outro código único.
                            """
                    );

                    return;
                }
            }

            Popups.error(
                    message != null
                            ? message
                            : "Ocorreu um erro inesperado."
            );
        }
    }

    /**
     * Carrega todos os estoques.
     */
    private void loadEstoques() {
        setResultados(
                estoqueService.findAll()
        );
    }

    /**
     * Define resultados da tabela.
     *
     * @param estoques - Estoques encontrados
     */
    private void setResultados(
            List<Estoque> estoques
    ) {
        if (estoques == null || estoques.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhum estoque encontrado!"
            );

            return;
        }

        estoqueTableModel.setEstoques(
                estoques
        );
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        estoqueTableModel.setEstoques(
                List.of()
        );
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UIForm.clearFields(
                codigoField,
                nomeField
        );
    }

}
