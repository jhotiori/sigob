package org.javapi.sigob.view.screens;

import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.windows.ApplicationWindow;

/**
 * Tela de login.
 */
public class LoginScreen extends BaseScreen {

    /**
     * Campo de usuário.
     *
     * @see {@link JTextField}
     */
    private final JTextField usernameField = UI.textField(field -> {
        field.setFont(Fonts.DEFAULT_ITALIC);
        field.setColumns(20);
    });

    /**
     * Campo de senha.
     *
     * @see {@link JPasswordField}
     */
    private final JPasswordField passwordField = UI.passwordField(field -> {
        field.setFont(Fonts.DEFAULT_ITALIC);
        field.setColumns(20);
    });

    /**
     * Botão de entrada.
     *
     * @see {@link JButton}
     */
    private final JButton enterButton = UI.button("Entrar", entrar -> {
        entrar.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Callback de login realizado.
     *
     * @see {@link Runnable}
     */
    private Runnable onLoginSuccess;

    /**
     * Cria tela de login.
     */
    public LoginScreen() {
        super("login");
        init();
        setup();
    }

    /**
     * Define callback de login realizado.
     *
     * @param onLoginSuccess - Callback executado
     */
    public void onLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    /**
     * Realiza setup de forma interna.
     */
    @Override
    protected void setup() {
        Events.mouse(enterButton, mouse -> {
            mouse.onClicked(() -> {
                enterButton.setEnabled(false);
                String usuario = usernameField.getText();
                String senha = new String(passwordField.getPassword());
                Optional<Funcionario> funcionario;

                try {
                    funcionario = ApplicationContext.getFuncionarioService().findByCodigo(senha);
                } catch (Exception e) {
                    Messages.error(e.getMessage());
                    enterButton.setEnabled(true);
                    return;
                }

                if (funcionario.isPresent() && funcionario.get().getNome().equalsIgnoreCase(usuario)) {
                    ApplicationContext.setFuncionarioLogado(funcionario.get());
                    System.out.println("Login efetuado com sucesso!");
                    if (onLoginSuccess != null) {
                        onLoginSuccess.run();
                    }
                } else {
                    Messages.warn("Login ou senha incorretos!");
                }
                enterButton.setEnabled(true);
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
                .add(buildTitle())
                .glue()
                .add(
                        buildUsernameField(),
                        buildPasswordField()
                )
                .glue()
                .add(enterButton)
                .build();
    }

    /**
     * Constrói título da tela.
     *
     * @return JPanel - Título construído
     */
    private JLabel buildTitle() {
        return UI.label(Settings.APP_WINDOW_LOGIN_TITLE, label -> {
            label.setFont(Fonts.TITLE_MEDIUM);
        });
    }

    /**
     * Constrói campo de usuário.
     *
     * @return JPanel - Campo construído
     */
    private JPanel buildUsernameField() {
        return UI.field(
                UI.label("Nome", label -> {
                    label.setFont(Fonts.MEDIUM_ITALIC);
                }),
                usernameField
        );
    }

    /**
     * Constrói campo de senha.
     *
     * @return JPanel - Campo construído
     */
    private JPanel buildPasswordField() {
        return UI.field(
                UI.label("Senha", label -> {
                    label.setFont(Fonts.MEDIUM_ITALIC);
                }),
                passwordField
        );
    }
}
