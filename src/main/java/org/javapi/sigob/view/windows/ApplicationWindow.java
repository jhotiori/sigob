package org.javapi.sigob.view.windows;

import java.awt.CardLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.base.BaseWindow;
import org.javapi.sigob.view.builders.MenuBarBuilder;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIWindow;

/**
 * Janela principal da aplicação.
 */
public final class ApplicationWindow extends BaseWindow {

    /**
     * Ícone da aplicação.
     *
     * @see {@link ImageIcon}
     */
    private final ImageIcon icon = new ImageIcon(
        getClass().getResource(Settings.APP_ICON_PATH)
    );

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
     * Mapa de telas.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, BaseScreen> screensMap = new LinkedHashMap<>();

    /**
     * Botão de saída.
     *
     * @see {@link JButton}
     */
    private final JButton sairButton = UI.menuButton("Sair");

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
        initialize();
    }

    /**
     * Registra tela.
     *
     * @param screen - Tela registrada
     */
    public void registerScreen(BaseScreen screen) {
        if (screen == null || containsScreen(screen.id())) {
            return;
        }

        screens.add(screen.root(), screen.id());
        screensMap.put(screen.id(), screen);
    }

    /**
     * Registra telas.
     *
     * @param baseScreens - Telas registradas
     */
    public void registerScreen(BaseScreen ...baseScreens) {
        for (BaseScreen screen : baseScreens) {
            registerScreen(screen);
        }
    }

    /**
     * Exibe tela.
     *
     * @param screenName - Nome da tela
     */
    public void showScreen(String screenName) {
        BaseScreen screen = getScreen(screenName);
        if (screen == null) {
            return;
        }

        screen.refresh();
        layout.show(screens, screenName);
    }

    /**
     * Registra e exibe tela dinamicamente.
     *
     * @param screen - Tela exibida
     */
    public void showScreen(BaseScreen screen) {
        if (screen == null) {
            return;
        }

        if (!containsScreen(screen.id())) {
            registerScreen(screen);
        }

        screen.refresh();
        layout.show(screens, screen.id());
    }

    /**
     * Verifica se tela já foi registrada.
     *
     * @param screenId - ID da tela
     * @return boolean - true se existir
     */
    public boolean containsScreen(String screenId) {
        return screensMap.containsKey(screenId);
    }

    /**
     * Retorna tela registrada.
     *
     * @param screenId - ID da tela
     * @return BaseScreen - Tela encontrada
     */
    public BaseScreen getScreen(String screenId) {
        return screensMap.get(screenId);
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
        MenuBarBuilder builder = UI.menubar();
        boolean hasAdminAcesso = ApplicationContext.hasFuncionarioAcesso("admin");
        boolean hasVendedorAcesso = ApplicationContext.hasFuncionarioAcesso("vendedor");
        boolean hasFuncionarioAcesso = ApplicationContext.hasFuncionarioAcesso("funcionario");
        
        builder.button("Dashboard", () -> showScreen("dashboard"));

        if (hasAdminAcesso || hasVendedorAcesso) {
            builder.menu("Operações", menu -> {
                menu.item("Vendas", () -> showScreen("vendas"));
                menu.item("Mercadorias", () -> showScreen("mercadorias"));
            });

            builder.menu("Cadastros", menu -> {
                menu.item("Acesso", () -> showScreen("cadastro-acesso"));
                menu.item("Categoria", () -> showScreen("cadastro-categoria"));
                menu.item("Produtos", () -> showScreen("cadastro-produto"));
                menu.item("Clientes", () -> showScreen("cadastro-cliente"));
                menu.item("Estoques", () -> showScreen("cadastro-estoque"));
                menu.item("Funcionarios", () -> showScreen("cadastro-funcionario"));
                menu.item("Documentos", () -> showScreen("cadastro-documento"));
            });
        }

        return builder.menu("Relatórios", menu -> {
            menu.item("Acessos", () -> showScreen("relatorio-acesso"));
            menu.item("Categorias", () -> showScreen("relatorio-categoria"));
            menu.item("Documentos", () -> showScreen("relatorio-documento"));
            menu.item("Produtos", () -> showScreen("relatorio-produto"));
            menu.item("Clientes", () -> showScreen("relatorio-cliente"));
            menu.item("Estoques", () -> showScreen("relatorio-estoque"));
            menu.item("Funcionarios", () -> showScreen("relatorio-funcionario"));
            menu.item("Vendas", () -> showScreen("relatorio-venda"));
        })
            .glue()
            .add(buildHorarioRow())
            .button(sairButton, this::dispose)
            .build();

        /*return UI.menubar()
                .button("Dashboard", () -> showScreen("dashboard"))
                .menu("Operações", menu -> {
                    menu.item("Vendas", () -> showScreen("vendas"));
                    menu.item("Mercadorias", () -> showScreen("mercadorias"));
                })
                .menu("Cadastros", menu -> {
                    menu.item("Acesso", () -> showScreen("cadastro-acesso"));
                    menu.item("Categoria", () -> showScreen("cadastro-categoria"));
                    menu.item("Produtos", () -> showScreen("cadastro-produto"));
                    menu.item("Clientes", () -> showScreen("cadastro-cliente"));
                    menu.item("Estoques", () -> showScreen("cadastro-estoque"));
                    menu.item("Funcionarios", () -> showScreen("cadastro-funcionario"));
                    menu.item("Documentos", () -> showScreen("cadastro-documento"));
                })
                .menu("Relatórios", menu -> {
                    menu.item("Acessos", () -> showScreen("relatorio-acesso"));
                    menu.item("Categorias", () -> showScreen("relatorio-categoria"));
                    menu.item("Documentos", () -> showScreen("relatorio-documento"));
                    menu.item("Produtos", () -> showScreen("relatorio-produto"));
                    menu.item("Clientes", () -> showScreen("relatorio-cliente"));
                    menu.item("Estoques", () -> showScreen("relatorio-estoque"));
                    menu.item("Funcionarios", () -> showScreen("relatorio-funcionario"));
                    menu.item("Vendas", () -> showScreen("relatorio-venda"));
                })
                .glue()
                .add(buildHorarioRow())
                .button(sairButton, this::dispose)
                .build();*/
    }

    /**
     * Constrói label de horário.
     *
     * @return JPanel - Label construida
     */
    private JPanel buildHorarioRow() {
        JLabel dataLabel = UI.label("[dd/MM/yyyy]", label -> {
            label.setForeground(Palette.FG_DARK);
            label.setFont(Fonts.DEFAULT_ITALIC);
        });

        JLabel horarioLabel = UI.label("HH:mm:ss", label -> {
            label.setForeground(Palette.FG_DARK);
            label.setFont(Fonts.DEFAULT_ITALIC);
        });

        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHorario = DateTimeFormatter.ofPattern("HH:mm:ss");

        Timer timer = new Timer(1000, e -> {
            horarioLabel.setText(LocalTime.now().format(formatterHorario));
            dataLabel.setText("[%s]".formatted(LocalDate.now().format(formatterData)));
        });

        timer.setInitialDelay(0);
        timer.start();

        return UI.row()
            .add(horarioLabel)
            .gap(Spacing.XS)
            .add(dataLabel)
            .build();
    }

}
