package org.javapi.sigob.view.windows;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.DashboardScreen;
import org.javapi.sigob.view.screens.LoginScreen;

/**
 * Janela de login.
 */
public class LoginWindow extends BaseWindow {

    /**
     * Ícone da aplicação.
     *
     * @see {@link ImageIcon}
     */
    private final ImageIcon icon = new ImageIcon(Settings.APP_ICON_PATH);

    /**
     * Tela de login.
     *
     * @see {@link LoginScreen}
     */
    private final LoginScreen screen = new LoginScreen();

    /**
     * Cria janela de login.
     */
    public LoginWindow() {
        screen.onLoginSuccess(() -> {
            dispose();
            ApplicationWindow app = new ApplicationWindow();
            app.register(new DashboardScreen());
            app.showScreen("dashboard");
            app.show();
        });
        init();
    }

    /**
     * Constrói janela.
     *
     * @return JFrame - Janela construida
     */
    @Override
    protected JFrame build() {
        return UI.frame()
                .title(Settings.APP_WINDOW_TITLE)
                .size(Settings.APP_MIN_WIDTH / 2, Settings.APP_MIN_HEIGHT / 2)
                .minimumSize(Settings.APP_MIN_WIDTH / 2, Settings.APP_MIN_HEIGHT / 2)
                .maximumSize(Settings.APP_MAX_WIDTH / 2, Settings.APP_MAX_HEIGHT / 2)
                .icon(icon.getImage())
                .resizable(true)
                .content(screen.root())
                .center()
                .build();
    }

}
