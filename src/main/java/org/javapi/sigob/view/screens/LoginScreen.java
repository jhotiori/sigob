package org.javapi.sigob.view.screens;

import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

public final class LoginScreen extends BaseScreen {
    /**
     * Serviço de funcionários.
     *
     * @see FuncionarioService
     */
    private final FuncionarioService funcionarioService = ApplicationContext.getFuncionarioService();

    /**
     * Campo de usuário.
     *
     * @see JTextField
     */
    private final JTextField usernameField = UI.textField(field -> {
        field.setColumns(20);
    });

    /**
     * Campo de senha.
     *
     * @see JPasswordField
     */
    private final JPasswordField passwordField = UI.passwordField(field -> {
        field.setColumns(20);
    });

    /**
     * Botão de entrada.
     *
     * @see JButton
     */
    private final JButton enterButton = UI.button("Entrar");

    /**
     * Botão de saída.
     *
     * @see JButton
     */
    private final JButton exitButton = UI.button("Sair");

    /**
     * Callback de login realizado.
     *
     * @see Runnable
     */
    private Runnable onLoginSuccess;

    /**
     * Cria tela de login.
     */
    public LoginScreen() {
        super("login");

        initialize();
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
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        Events.mouse(enterButton, mouse -> {
            mouse.onClicked(() -> {
                UIForm.disableFields(enterButton, exitButton);

                String usuario = usernameField.getText();
                if (usuario.isBlank()) {
                    Popups.warn("Informe um nome de usuário!");
                    UIForm.enableFields(enterButton, exitButton);
                    return;
                }

                String senha = new String(passwordField.getPassword());
                if (senha.isBlank()) {
                    Popups.warn("Informe uma senha!");
                    UIForm.enableFields(enterButton, exitButton);
                    return;
                }

                Optional<Funcionario> funcionario;

                try {
                    funcionario = funcionarioService.findByCodigo(senha);
                } catch (Exception e) {
                    Popups.error("Erro ao logar: " + e.getMessage());
                    UIForm.enableFields(enterButton, exitButton);
                    return;
                }

                if (funcionario.isPresent() && funcionario.get().getNome().equalsIgnoreCase(usuario)) {
                    ApplicationContext.setFuncionarioLogado(funcionario.get());
                    if (onLoginSuccess != null) {
                        onLoginSuccess.run();
                    }
                } else {
                    Popups.warn("Nome ou senha incorretos!");
                }

                UIForm.enableFields(enterButton, exitButton);
            });
        });

        Events.mouse(exitButton, mouse -> {
            mouse.onClicked(() -> {
                System.exit(0);
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
                    buildTitle(),
                    buildSubtitle()
                )
                .glue()
                .add(
                        buildUsernameField(),
                        buildPasswordField()
                )
                .glue()
                .add(buildActions())
                .build();
    }

    private JPanel buildActions() {
        return UIScreen.actions(enterButton, exitButton);
    }

    /**
     * Constrói icone da tela.
     *
     * @return Image - Icone construido
     */
    private ImageIcon buildIcon() {
        return UI.icon(Settings.APP_ICON_PATH, 36);
    }

    /**
     * Constrói título da tela.
     *
     * @return JPanel - Título construído
     */
    private JPanel buildTitle() {
        JLabel icon = UI.label(buildIcon());
        JLabel title = UIScreen.title("SIGOB");

        return UI.row()
            .add(icon, title)
            .gap(Spacing.XL)
            .build();
    }

    /**
     * Constrói subtítulo da tela.
     *
     * @return JLabel - Subtítulo construido
     */
    private JLabel buildSubtitle() {
        JLabel subtitle = UIScreen.subtitle("Sistema Integrado de Gestão Comercial e Operacional para Distribuidoras de Bebidas");
        subtitle.setFont(Fonts.SMALL_ITALIC);
        return subtitle;
    }

    /**
     * Constrói campo de usuário.
     *
     * @return JPanel - Campo construído
     */
    private JPanel buildUsernameField() {
        return UIForm.field(
                UIForm.fieldLabel("Nome"),
                usernameField
        );
    }

    /**
     * Constrói campo de senha.
     *
     * @return JPanel - Campo construído
     */
    private JPanel buildPasswordField() {
        return UIForm.field(
                UIForm.fieldLabel("Senha"),
                passwordField
        );
    }

}
