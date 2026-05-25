package org.javapi.sigob.view.screens.cadastros;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.service.DocumentoService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.DocumentoTableModel;
import org.javapi.sigob.view.popups.PopupValues;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de cadastro de documentos.
 */
public final class CadastroDocumentoScreen extends BaseScreen {

    /**
     * Serviço de documentos.
     *
     * @see {@link DocumentoService}
     */
    private final DocumentoService documentoService
            = ApplicationContext.getDocumentoService();

    /**
     * Campo de documento.
     *
     * @see {@link JTextField}
     */
    private final JTextField documentoField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * Campo de tipo.
     *
     * @see {@link JTextField}
     */
    private final JTextField tipoField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * Campo de pesquisa.
     *
     * @see {@link JTextField}
     */
    private final JTextField pesquisaField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * ComboBox de modo de pesquisa.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> pesquisaModoCombo = UI.comboBox(
            "Documento",
            "Tipo"
    );

    /**
     * Botão de cadastro.
     *
     * @see {@link JButton}
     */
    private final JButton cadastrarButton = UI.button("Cadastrar");

    /**
     * Botão de limpeza.
     *
     * @see {@link JButton}
     */
    private final JButton limparButton = UI.button("Limpar");

    /**
     * Botão de pesquisa.
     *
     * @see {@link JButton}
     */
    private final JButton pesquisarButton = UI.button("Pesquisar");

    /**
     * Modelo da tabela de documentos.
     *
     * @see {@link DocumentoTableModel}
     */
    private final DocumentoTableModel documentoTableModel
            = new DocumentoTableModel();

    /**
     * Tabela de documentos.
     *
     * @see {@link JTable}
     */
    private final JTable documentosTable = UI.table(
            documentoTableModel
    );

    /**
     * Cria tela de cadastro de documentos.
     */
    public CadastroDocumentoScreen() {
        super("cadastro-documento");

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        loadDocumentos();

        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(this::cadastrarDocumento);
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
        });

        Events.mouse(pesquisarButton, mouse -> {
            mouse.onClicked(this::pesquisarDocumentos);
        });

        Events.mouse(documentosTable, mouse -> {
            mouse.onClicked(event -> {
                if (event.getClickCount() == 2) {
                    editarDocumentoSelecionado();
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
                        UIScreen.title("Cadastro de Documentos"),
                        UIScreen.subtitle(
                                "Gerencia documentos e tipos utilizados no sistema."
                        )
                )
                .glue()
                .add(
                        UIForm.field(
                                UIForm.fieldLabel("Documento [obrigatorio]"),
                                documentoField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Tipo [obrigatorio]"),
                                tipoField
                        )
                )
                .glue()
                .add(
                        UIScreen.actions(
                                cadastrarButton,
                                limparButton
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Atualizar",
                                buildSearchPanel(),
                                UI.scroll(documentosTable)
                        )
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
     * Realiza cadastro de documento.
     */
    private void cadastrarDocumento() {
        try {
            Documento documento = new Documento();

            documento.setDocumento(
                    documentoField.getText()
            );

            documento.setTipo(
                    tipoField.getText()
            );

            documentoService.save(documento);

            Popups.success(
                    "Documento cadastrado com sucesso!"
            );

            clearForm();
            loadDocumentos();
        } catch (Exception e) {
            Popups.error(formatError(e));
        }
    }

    /**
     * Pesquisa documentos.
     */
    private void pesquisarDocumentos() {
        try {
            String pesquisa = pesquisaField
                    .getText()
                    .trim();

            if (pesquisa.isBlank()) {
                loadDocumentos();
                return;
            }

            String modo = (String) pesquisaModoCombo
                    .getSelectedItem();

            List<Documento> documentos;

            if ("Tipo".equalsIgnoreCase(modo)) {
                documentos = documentoService
                        .findByTipo(pesquisa);

            } else {
                documentos = documentoService
                        .findByDocumento(pesquisa);
            }

            documentoTableModel.setDocumentos(
                    deduplicateDocumentos(documentos)
            );

        } catch (Exception e) {
            Popups.error(formatError(e));
        }
    }

    /**
     * Edita documento selecionado.
     */
    private void editarDocumentoSelecionado() {
        int selectedRow = documentosTable.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        Documento documento = documentoTableModel
                .getDocumento(selectedRow);

        if (documento == null) {
            return;
        }

        boolean confirmed = Popups.confirm(
                "Você deseja editar esse documento?"
        );

        if (!confirmed) {
            return;
        }

        try {
            String novoDocumento = Popups.input(
                    "Editar Documento",
                    """
                    Novo documento [ENTER mantém atual]
                    Atual: %s
                    """
                            .formatted(
                                    documento.getDocumento()
                            )
            );

            if (PopupValues.wasCancelled(novoDocumento)) {
                return;
            }

            String novoTipo = Popups.input(
                    "Editar Tipo",
                    """
                    Novo tipo [ENTER mantém atual]
                    Atual: %s
                    """
                            .formatted(
                                    documento.getTipo()
                            )
            );

            if (PopupValues.wasCancelled(novoTipo)) {
                return;
            }

            if (!PopupValues.shouldKeep(novoDocumento)) {
                documento.setDocumento(
                        novoDocumento.trim()
                );
            }

            if (!PopupValues.shouldKeep(novoTipo)) {
                documento.setTipo(
                        novoTipo.trim()
                );
            }

            documentoService.update(documento);

            Popups.success(
                    "Documento atualizado com sucesso!"
            );

            pesquisarDocumentos();

        } catch (Exception e) {
            Popups.error(formatError(e));
        }
    }

    /**
     * Carrega todos os documentos.
     */
    private void loadDocumentos() {
        documentoTableModel.setDocumentos(
                documentoService.findAll()
        );
    }

    /**
     * Remove documentos duplicados.
     *
     * @param documentos - Lista original
     * @return List<Documento> - Lista sem duplicatas
     */
    private List<Documento> deduplicateDocumentos(
            List<Documento> documentos
    ) {
        Map<Integer, Documento> unique = new LinkedHashMap<>();

        for (Documento documento : documentos) {
            unique.put(
                    documento.getId(),
                    documento
            );
        }

        return new ArrayList<>(
                unique.values()
        );
    }

    /**
     * Formata erros amigáveis.
     *
     * @param exception - Exceção recebida
     * @return String - Mensagem formatada
     */
    private String formatError(Exception exception) {
        String message = exception.getMessage();

        if (message == null) {
            return "Ocorreu um erro inesperado.";
        }

        String lowerMessage = message.toLowerCase();

        if (lowerMessage.contains("unique")
                || lowerMessage.contains("duplic")
                || lowerMessage.contains("constraint")) {

            return """
                   Já existe um documento com esse valor.
                   Utilize outro documento único.
                   """;
        }

        return message;
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UIForm.clearFields(
                documentoField,
                tipoField
        );
    }

}
