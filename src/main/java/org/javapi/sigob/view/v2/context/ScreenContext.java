package org.javapi.sigob.view.v2.context;

import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.ui.UI;

public final class ScreenContext {
    /**
     * Layout de cards.
     *
     * @see CardLayout
     */
    private static final CardLayout LAYOUT = new CardLayout();

    /**
     * Container de cards.
     *
     * @see JPanel
     */
    private static final JPanel CONTAINER = UI.panel();

    /**
     * Map de nome para tela.
     *
     * @see Map
     */
    private static final Map<String, BaseScreen> SCREENS = new LinkedHashMap<>();

    /**
     * Define o layout do container.
     */
    static {
        CONTAINER.setLayout(LAYOUT);
    }

    /**
     * Construtor de contexto de tela.
     *
     * @return ScreenContext - Contexto de tela
     */
    public ScreenContext() {

    }

    /**
     * Registra uma nova tela.
     *
     * @param screen - A tela para ser registrada
     */
    public static void register(BaseScreen screen) {
        if (screen == null) {
            return;
        }

        SCREENS.put(screen.id(), screen);
        CONTAINER.add(screen.panel(), screen.id());
    }

    /**
     * Registra novas telas.
     *
     * @param screens - As telas para serem registradas
     */
    public static void register(BaseScreen ...screens) {
        for (BaseScreen screen : screens) {
            register(screen);
        }
    }

    /**
     * Exibe uma tela.
     *
     * @param id - O ID da tela
     */
    public static void show(String id) {
        BaseScreen screen = SCREENS.get(id);

        if (screen == null) {
            return;
        }

        screen.update();
        LAYOUT.show(CONTAINER, id);
    }

    /**
     * Retorna uma tela.
     *
     * @param id - O ID da tela
     * @return BaseScreen - A tela
     */
    public static BaseScreen get(String id) {
        return SCREENS.get(id);
    }

    /**
     * Retorna o container de cards.
     *
     * @return JPanel - Container de cards
     */
    public static JPanel panel() {
        return CONTAINER;
    }
}
