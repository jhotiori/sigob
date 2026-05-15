package org.javapi.sigob.view.windows;

import java.awt.CardLayout;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.builders.MenuBarBuilder;
import org.javapi.sigob.view.screens.BaseScreen;

/**
 * Janela principal da aplicação.
 */
public final class ApplicationWindow extends BaseWindow {

    /**
     * Ícone da aplicação.
     *
     * @see {@link ImageIcon}
     */
    private final ImageIcon icon = new ImageIcon(Settings.APP_ICON_PATH);

    /**
     * Layout interno das telas.
     *
     * @see {@link CardLayout}
     */
    private final CardLayout layout = new CardLayout();

    /**
     * Painel de telas.
     *
     * @see {@link JPanel}
     */
    private final JPanel screens = new JPanel(layout);

    /**
     * Barra de menus.
     *
     * @see {@link JMenuBar}
     */
    private final JMenuBar bar = buildBar();

    /**
     * Cria janela principal.
     */
    public ApplicationWindow() {
        init();
    }

    /**
     * Registra tela.
     *
     * @param screen - Tela registrada
     */
    public void register(BaseScreen screen) {
        if (screen == null) {
            return;
        }

        screens.add(screen.root(), screen.id());
    }

    /**
     * Exibe tela.
     *
     * @param screenName - Nome da tela
     */
    public void showScreen(String screenName) {
        layout.show(screens, screenName);
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
                .size(Settings.APP_MIN_WIDTH, Settings.APP_MIN_HEIGHT)
                .minimumSize(Settings.APP_MIN_WIDTH, Settings.APP_MIN_HEIGHT)
                .icon(icon.getImage())
                .resizable(true)
                .menubar(bar)
                .content(screens)
                .center()
                .build();
    }

    /**
     * Constrói barra de menus.
     *
     * @return JMenuBar - Barra construída
     */
    private JMenuBar buildBar() {
        /*
        MenuBarBuilder builder = UI.menubar();
        builder.button("Dashboard", () -> showScreen("dashboard"));

        Set<Acesso> acessos = ApplicationContext.getFuncionarioLogado().getAcessos();

        for (Acesso acesso : acessos) {
            if (acesso.getNome().toLowerCase().equals("admin")) {
                builder.menu("Operações", menu -> {
                    menu.item("Vendas", () -> {
                    });

                    menu.item("Mercadorias", () -> {
                    });
                });
            }
        }*/

        return UI.menubar()
                .button("Dashboard", () -> showScreen("dashboard"))
                .menu("Operações", menu -> {
                    menu.item("Vendas", () -> {
                    });

                    menu.item("Mercadorias", () -> {
                    });
                })
                .menu("Cadastros", menu -> {
                    menu.item("Acesso", () -> {
                    });

                    menu.item("Categoria", () -> {
                    });

                    menu.item("Produtos", () -> {

                    });

                    menu.item("Clientes", () -> {
                    });

                    menu.item("Estoques", () -> {
                    });

                    menu.item("Funcionarios", () -> {
                    });
                })
                .menu("Relatórios", menu -> {
                    menu.item("Vendas", () -> {
                    });
                })
                .glue()
                .button("Sair", this::dispose)
                .build();
    }

}
