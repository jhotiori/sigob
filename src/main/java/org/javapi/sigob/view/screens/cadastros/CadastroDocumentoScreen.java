package org.javapi.sigob.view.screens.cadastros;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.BaseScreen;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Tela de cadastro de documentos.
 */
public final class CadastroDocumentoScreen extends BaseScreen {

    /**
     * Campo de documento.
     *
     * @see {@link JTextField}
     */
    private final JTextField documentoField = UI.textField();

    /**
     * Campo de tipo.
     *
     * @see {@link JTextField}
     */
    private final JTextField tipoField = UI.textField();

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
     * Cria tela de cadastro de documentos.
     */
    public CadastroDocumentoScreen() {
        super("cadastro-documento");
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
                    Documento documento = new Documento();

                    documento.setDocumento(documentoField.getText());
                    documento.setTipo(tipoField.getText());

                    ApplicationContext.getDocumentoService().save(documento);

                    Messages.info("Documento cadastrado com sucesso!");
                    clearForm();
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
                        UI.field(UI.fieldLabel("Documento"), documentoField),
                        UI.field(UI.fieldLabel("Tipo"), tipoField)
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
        return UI.label("Cadastro de Documentos", label -> {
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
                "Gerencia documentos e tipos utilizados no sistema."
        );
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UI.clearFields(
                documentoField,
                tipoField
        );
    }

}
