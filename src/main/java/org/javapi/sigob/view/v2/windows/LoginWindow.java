package org.javapi.sigob.view.v2.windows;

import java.awt.Image;

import javax.swing.JFrame;

import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.v2.framework.base.BaseWindow;
import org.javapi.sigob.view.v2.framework.ui.UIResources;
import org.javapi.sigob.view.v2.framework.ui.UIWindows;
import org.javapi.sigob.view.v2.screens.LoginScreen;

public final class LoginWindow extends BaseWindow {
    /**
     * Icone da janela.
     */
    private final Image ICON = UIResources.image(Settings.APP_ICON_PATH);

    /**
     * Tela de login da janela.
     */
    private final LoginScreen SCREEN;

    /**
     * Construtor da janela de login.
     *
     * @param screen - Tela de login
     */
    public LoginWindow(LoginScreen screen) {
        this.SCREEN = screen;
    }

    @Override
    protected JFrame build() {
        return UIWindows.create()
            .title(Settings.APP_WINDOW_LOGIN_TITLE)
            .size(Settings.APP_MIN_WIDTH / 2, Settings.APP_MIN_HEIGHT / 2)
            .icon(ICON)
            .content(SCREEN.panel())
            .resizable(false)
            .center()
            .build();
    }
}
