package org.javapi.sigob.view.v2.screens;

import javax.swing.JPanel;

import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.components.ButtonComponent;
import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.components.PasswordFieldComponent;
import org.javapi.sigob.view.v2.framework.components.TextFieldComponent;
import org.javapi.sigob.view.v2.framework.styles.Spacing;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.framework.ui.UIForms;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIResources;
import org.javapi.sigob.view.v2.framework.ui.UIText;

public class LoginScreen extends BaseScreen {
    /**
     * Titulo da tela.
     */
    private final LabelComponent TITLE = UIText.header("SIGOB");

    /**
     * Logo da tela.
     */
    private final LabelComponent LOGO = UI.label(label -> {
        label.setIcon(
            UIResources.icon(
                Settings.APP_ICON_PATH,
                32
            )
        );
    });

    /**
     * Campo para o usuário.
     */
    private final TextFieldComponent USUARIO_FIELD = UI.textField();

    /**
     * Campo para o código.
     */
    private final PasswordFieldComponent CODIGO_FIELD = UI.passwordField();

    /**
     * Botão de login.
     */
    private final ButtonComponent LOGIN_BUTTON = UI.button("Login");

    /**
     * Botão de criar conta.
     */
    private final ButtonComponent REGISTRAR_BUTTON = UI.button("Registrar");

    /**
     * Botão de cancelar.
     */
    private final ButtonComponent SAIR_BUTTON = UI.button("Sair");

    /**
     * Construtor da tela.
     */
    public LoginScreen() {
        super("login");
    }

    /**
     * Obtem o usuário a partir do campo.
     *
     * @return String - Usuário
     */
    public String getUsuario() {
        return USUARIO_FIELD.getText();
    }

    /**
     * Obtem o código a partir do campo.
     *
     * @return String - Código
     */
    public String getCodigo() {
        return new String(CODIGO_FIELD.getPassword());
    }

    /**
     * Registra evento de login.
     *
     * @param action - Ação executada
     */
    public void onLogin(Runnable action) {
        UIEvents.bind(LOGIN_BUTTON).onClick(action);
    }

    /**
     * Registra evento de criar conta.
     *
     * @param action - Ação executada
     */
    public void onRegistrar(Runnable action) {
        UIEvents.bind(REGISTRAR_BUTTON).onClick(action);
    }

    /**
     * Registra evento de saída.
     *
     * @param action - Ação executada
     */
    public void onSair(Runnable action) {
        UIEvents.bind(SAIR_BUTTON).onClick(action);
    }

    /**
     * Realiza setup da tela de forma interna.
     */
    @Override
    protected void setup() {

    }

    /**
     * Constrói a tela.
     */
    @Override
    protected JPanel build() {
        return UILayouts.border()
            .center(buildCenter())
            .padding(Spacing.LG)
            .build();
    }

    /**
     * Constrói os elementos do centro da tela.
     */
    private JPanel buildCenter() {
        return UILayouts.column()
                .add(buildHeader())
                .glue()
                .add(buildForms())
                .glue()
                .add(buildButtons())
                .build();
    }

    /**
     * Constrói os formulários.
     */
    private JPanel buildForms() {
        return UIForms.create()
            .field("Usuário", "usuario", USUARIO_FIELD)
            .field("Código", "codigo", CODIGO_FIELD)
            .build();
    }

    /**
     * Constrói os botões da tela.
     */
    private JPanel buildButtons() {
        return UILayouts.row()
            .add(LOGIN_BUTTON)
            .gap(Spacing.XS)
            .add(REGISTRAR_BUTTON)
            .gap(Spacing.XS)
            .add(SAIR_BUTTON)
            .build();
    }

    /**
     * Constrói o cabeçalho da tela.
     */
    private JPanel buildHeader() {
        return UILayouts.row()
            .add(LOGO)
            .gap(Spacing.XS)
            .add(TITLE)
            .build();
    }
}
