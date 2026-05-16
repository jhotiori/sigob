package org.javapi.sigob.view.screens.cadastros;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.BaseScreen;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Tela de cadastro de estoques.
 */
public final class CadastroEstoqueScreen extends BaseScreen {

    /**
     * Campo de código.
     *
     * @see {@link JTextField}
     */
    private final JTextField codigoField = UI.textField();

    /**
     * Campo de nome.
     *
     * @see {@link JTextField}
     */
    private final JTextField nomeField = UI.textField();

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
     * Cria tela de cadastro de estoques.
     */
    public CadastroEstoqueScreen() {
        super("cadastro-estoque");
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
                    Estoque estoque = new Estoque();

                    estoque.setCodigo(codigoField.getText());
                    estoque.setNome(nomeField.getText());

                    ApplicationContext.getEstoqueService().save(estoque);

                    Messages.info("Estoque cadastrado com sucesso!");
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
                        UI.field(UI.fieldLabel("Nome"), nomeField)
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
        return UI.label("Cadastro de Estoques", label -> {
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
                "Gerencia os estoques disponíveis para armazenamento de produtos."
        );
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UI.clearFields(
                codigoField,
                nomeField
        );
    }

}
