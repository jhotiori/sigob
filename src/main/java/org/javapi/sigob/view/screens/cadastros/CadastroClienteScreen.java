package org.javapi.sigob.view.screens.cadastros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.BaseScreen;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Tela de cadastro de clientes.
 */
public final class CadastroClienteScreen extends BaseScreen {

    /**
     * Formatador de data.
     *
     * @see {@link DateTimeFormatter}
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Campo de nome.
     *
     * @see {@link JTextField}
     */
    private final JTextField nomeField = UI.textField(field -> {
        field.setColumns(32);
    });

    /**
     * Campo de data de nascimento.
     *
     * @see {@link JTextField}
     */
    private final JTextField dataNascimentoField = UI.textField(field -> {
        field.setColumns(16);
    });

    /**
     * Campo de documento.
     *
     * @see {@link JTextField}
     */
    private final JTextField documentoField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * Campo de tipo do documento.
     *
     * @see {@link JTextField}
     */
    private final JTextField tipoDocumentoField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * Botão de cadastro.
     *
     * @see {@link JButton}
     */
    private final JButton cadastrarButton = UI.button("Cadastrar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Botão de limpeza.
     *
     * @see {@link JButton}
     */
    private final JButton limparButton = UI.button("Limpar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Cria tela de cadastro de clientes.
     */
    public CadastroClienteScreen() {
        super("cadastro-cliente");
        init();
        setup();
    }

    /**
     * Realiza setup da tela.
     */
    @Override
    protected void setup() {
        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(() -> {
                try {
                    Cliente cliente = new Cliente();

                    cliente.setNome(nomeField.getText());
                    cliente.setDataNascimento(parseDataNascimento());
                    cliente.setDocumento(createDocumento());

                    ApplicationContext.getClienteService().save(cliente);

                    Messages.info("Cliente cadastrado com sucesso!");
                    clearForm();
                } catch (DateTimeParseException e) {
                    Messages.error("Data inválida! Utilize o formato DD/MM/YYYY.");
                } catch (Exception e) {
                    Messages.error(e.getMessage());
                }
            });
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
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
                .add(buildTitle(), buildSubtitle())
                .glue()
                .add(
                        UI.field(
                                UI.fieldLabel("Nome"),
                                nomeField
                        ),
                        UI.field(
                                UI.fieldLabel("Data de Nascimento [DD/MM/YYYY]"),
                                dataNascimentoField
                        ),
                        UI.field(
                                UI.fieldLabel("Documento [Opcional]"),
                                documentoField
                        ),
                        UI.field(
                                UI.fieldLabel("Tipo do Documento [Opcional]"),
                                tipoDocumentoField
                        )
                )
                .glue()
                .add(UI.actions(cadastrarButton, limparButton))
                .build();
    }

    /**
     * Constrói título da tela.
     *
     * @return JLabel - Título construído
     */
    private JLabel buildTitle() {
        return UI.label("Cadastro de Clientes", label -> {
            label.setFont(Fonts.TITLE_MEDIUM);
        });
    }

    /**
     * Constrói subtítulo da tela.
     *
     * @return JLabel - Subtítulo construído
     */
    private JLabel buildSubtitle() {
        return UI.subtitle(
                "Gerencia clientes cadastrados e seus documentos vinculados."
        );
    }

    /**
     * Cria documento opcional do cliente.
     *
     * @return Documento - Documento criado ou null
     */
    private Documento createDocumento() {
        String documentoValor = documentoField.getText().trim();
        String tipoDocumento = tipoDocumentoField.getText().trim();

        if (documentoValor.isBlank() && tipoDocumento.isBlank()) {
            return null;
        }

        if (documentoValor.isBlank() || tipoDocumento.isBlank()) {
            Messages.error("Documento e Tipo do Documento devem ser preenchidos juntos!");
            return null;
        }

        Documento documento = new Documento();

        documento.setDocumento(documentoValor);
        documento.setTipo(tipoDocumento);

        ApplicationContext.getDocumentoService().save(documento);

        return documento;
    }

    /**
     * Realiza parse da data de nascimento.
     *
     * @return LocalDate - Data convertida ou null
     */
    private LocalDate parseDataNascimento() {
        String data = dataNascimentoField.getText().trim();

        if (data.isBlank()) {
            return null;
        }

        return LocalDate.parse(data, DATE_FORMATTER);
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UI.clearFields(
                nomeField,
                dataNascimentoField,
                documentoField,
                tipoDocumentoField
        );
    }

}
