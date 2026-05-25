package org.javapi.sigob.view.windows;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.base.BaseWindow;
import org.javapi.sigob.view.screens.DashboardScreen;
import org.javapi.sigob.view.screens.LoginScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroAcessoScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroCategoriaScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroClienteScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroDocumentoScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroEstoqueScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroFuncionarioScreen;
import org.javapi.sigob.view.screens.cadastros.CadastroProdutoScreen;
import org.javapi.sigob.view.screens.mercadorias.MercadoriasScreen;
import org.javapi.sigob.view.screens.relatorios.AcessoRelatorioScreen;
import org.javapi.sigob.view.screens.relatorios.CategoriaRelatorioScreen;
import org.javapi.sigob.view.screens.relatorios.ClienteRelatorioScreen;
import org.javapi.sigob.view.screens.relatorios.DocumentoRelatorioScreen;
import org.javapi.sigob.view.screens.relatorios.EstoqueRelatorioScreen;
import org.javapi.sigob.view.screens.relatorios.FuncionarioRelatorioScreen;
import org.javapi.sigob.view.screens.relatorios.ProdutoRelatorioScreen;
import org.javapi.sigob.view.screens.relatorios.VendaRelatorioScreen;
import org.javapi.sigob.view.screens.venda.VendaNovaScreen;
import org.javapi.sigob.view.screens.venda.VendasScreen;
import org.javapi.sigob.view.ui.UIWindow;

/**
 * Janela de login.
 */
public class LoginWindow extends BaseWindow {

    /**
     * Ícone da aplicação.
     *
     * @see {@link ImageIcon}
     */
    private final ImageIcon icon = new ImageIcon(
        getClass().getResource(Settings.APP_ICON_PATH)
    );

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
            app.registerScreen(
                new DashboardScreen(),
                new MercadoriasScreen(),
                new VendaNovaScreen(),
                new VendasScreen(),
                new CadastroProdutoScreen(),
                new CadastroAcessoScreen(),
                new CadastroCategoriaScreen(),
                new CadastroDocumentoScreen(),
                new CadastroEstoqueScreen(),
                new CadastroClienteScreen(),
                new CadastroFuncionarioScreen(),
                new AcessoRelatorioScreen(),
                new CategoriaRelatorioScreen(),
                new FuncionarioRelatorioScreen(),
                new ProdutoRelatorioScreen(),
                new ClienteRelatorioScreen(),
                new EstoqueRelatorioScreen(),
                new VendaRelatorioScreen(),
                new DocumentoRelatorioScreen()
            );
            app.showScreen("dashboard");
            app.show();
        });
        initialize();
    }

    /**
     * Constrói janela.
     *
     * @return JFrame - Janela construida
     */
    @Override
    protected JFrame build() {
        return UIWindow.create()
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
