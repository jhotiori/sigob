package org.javapi.sigob.view.windows;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.DashboardScreen;
import org.javapi.sigob.view.screens.LoginScreen;
import org.javapi.sigob.view.screens.NovaVendaScreen;
import org.javapi.sigob.view.screens.VendasScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroAcessoScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroCategoriaScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroClienteScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroDocumentoScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroEstoqueScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroFuncionarioScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroProdutoScreen;

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
            ApplicationContext.setWindow(app);
            app.register(new DashboardScreen());
            app.register(new VendasScreen());
            app.register(new NovaVendaScreen());
            app.register(new CadastroProdutoScreen());
            app.register(new CadastroAcessoScreen());
            app.register(new CadastroCategoriaScreen());
            app.register(new CadastroDocumentoScreen());
            app.register(new CadastroEstoqueScreen());
            app.register(new CadastroClienteScreen());
            app.register(new CadastroFuncionarioScreen());
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
