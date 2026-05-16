package org.javapi.sigob.view.windows;

import java.awt.CardLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.BaseScreen;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

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
     * Mapa de telas.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, BaseScreen> screensMap = new LinkedHashMap<>();

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
        if (screen == null || screensMap.containsKey(screen.id())) {
            return;
        }

        screens.add(screen.root(), screen.id());
        screensMap.put(screen.id(), screen);
    }

    /**
     * Exibe tela.
     *
     * @param screenName - Nome da tela
     */
    public void showScreen(String screenName) {
        BaseScreen screen = screensMap.get(screenName);
        if (screen != null) {
            screen.update();
        }
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

        if (!screensMap.containsKey(screen.id())) {
            register(screen);
        }

        screen.update();

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
                        showScreen("vendas");
                    });

                    menu.item("Mercadorias", () -> {
                    });
                })
                .menu("Cadastros", menu -> {
                    menu.item("Acesso", () -> {
                        showScreen("cadastro-acesso");
                    });

                    menu.item("Categoria", () -> {
                        showScreen("cadastro-categoria");
                    });

                    menu.item("Produtos", () -> {
                        showScreen("cadastro-produto");
                    });

                    menu.item("Clientes", () -> {
                        showScreen("cadastro-cliente");
                    });

                    menu.item("Estoques", () -> {
                        showScreen("cadastro-estoque");
                    });

                    menu.item("Funcionarios", () -> {
                        showScreen("cadastro-funcionario");
                    });

                    menu.item("Documentos", () -> {
                        showScreen("cadastro-documento");
                    });
                })
                .menu("Relatórios", menu -> {
                    menu.item("Vendas", () -> {
                    });
                })
                .glue()
                .add(buildLogadoLabel())
                .glue()
                .add(buildHorarioLabel())
                .button("Sair", this::dispose)
                .build();
    }

    private JLabel buildLogadoLabel() {
        Funcionario funcionario = ApplicationContext.getFuncionarioLogado();
        List<Acesso> acessos = funcionario.getAcessos().stream().toList();
        String acessosString = new ArrayList<>(acessos).stream().map(Acesso::getNome).collect(Collectors.joining(", "));

        return UI.label("Logado(a) como %s (%s)".formatted(funcionario.getNome(), acessosString), label -> {
            label.setFont(Fonts.SMALL_BOLD);
            label.setForeground(Palette.FG_MUTED);
        });
    }

    private JLabel buildHorarioLabel() {
        JLabel horario = UI.label("", label -> {
            label.setForeground(Palette.FG_MUTED);
            label.setFont(Fonts.DEFAULT_BOLD);
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timer timer = new Timer(1000, e -> {
            horario.setText(LocalTime.now().format(formatter));
        });

        timer.setInitialDelay(0);
        timer.start();

        return horario;
    }

}
