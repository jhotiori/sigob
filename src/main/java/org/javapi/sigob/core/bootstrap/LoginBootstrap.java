package org.javapi.sigob.core.bootstrap;

import org.javapi.sigob.controller.LoginController;
import org.javapi.sigob.view.v2.context.WindowContext;
import org.javapi.sigob.view.v2.screens.LoginScreen;
import org.javapi.sigob.view.v2.windows.LoginWindow;

/**
 * Responsável pela inicialização da tela de login.
 */
public final class LoginBootstrap {
    /**
     * Indica se o bootstrapping foi realizado.
     */
    private static boolean HAS_BOOTSTRAPPED = false;

    /**
     * Realiza boostrap da tela de login.
     */
    @SuppressWarnings("unused")
    public static void bootstrap() {
        if (HAS_BOOTSTRAPPED) {
            return;
        }

        HAS_BOOTSTRAPPED = true;
        LoginScreen loginScreen = new LoginScreen();
        LoginWindow loginWindow = new LoginWindow(loginScreen);
        LoginController loginController = new LoginController(loginScreen);

        WindowContext.setCurrentWindow(loginWindow);
        loginWindow.show();
    }
}
