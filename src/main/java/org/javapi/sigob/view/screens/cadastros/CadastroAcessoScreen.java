package org.javapi.sigob.view.screens.cadastros;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.BaseScreen;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Tela de cadastro de acessos.
 */
public final class CadastroAcessoScreen extends BaseScreen {

    /**
     * Campo de código.
     *
     * @see {@link JTextField}
     */
    private final JTextField codigoField = UI.textField(field -> {
        field.setColumns(20);
    });

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
     * Cria tela de cadastro de acessos.
     */
    public CadastroAcessoScreen() {
        super("cadastro-acesso");
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
                    Acesso acesso = new Acesso();

                    acesso.setCodigo(codigoField.getText());
                    acesso.setNome(nomeField.getText());
                    acesso.setDescricao(descricaoArea.getText());

                    ApplicationContext.getAcessoService().save(acesso);

                    Messages.info("Acesso cadastrado com sucesso!");
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
                        UI.field(UI.fieldLabel("Código"), codigoField),
                        UI.field(UI.fieldLabel("Nome"), nomeField),
                        UI.field(UI.fieldLabel("Descrição"), descricaoArea)
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
        return UI.label("Cadastro de Acessos", label -> {
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
                "Gerencia os acessos e permissões disponíveis no sistema."
        );
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UI.clearFields(
                codigoField,
                nomeField,
                descricaoArea
        );
    }

}
