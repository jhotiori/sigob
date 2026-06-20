package org.javapi.sigob.view.v2.framework.layouts.menu;

import java.awt.Component;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.JMenuBar;

import org.javapi.sigob.view.v2.framework.components.MenuBarComponent;
import org.javapi.sigob.view.v2.framework.components.MenuButtonComponent;

/**
 * Construtor de barras de menu.
 */
public final class MenuBarBuilder {

    /**
     * Barra de menu interna.
     */
    private final MenuBarComponent menuBar;

    /**
     * Cria construtor.
     */
    public MenuBarBuilder() {
        this.menuBar = new MenuBarComponent();
    }

    /**
     * Adiciona menu.
     *
     * @param title - Título do menu
     * @param config - Configuração do menu
     * @return MenuBarBuilder - Própria instância
     */
    public MenuBarBuilder menu(
            String title,
            Consumer<MenuBuilder> config
    ) {
        MenuBuilder builder = new MenuBuilder(title);

        if (config != null) {
            config.accept(builder);
        }

        menuBar.add(builder.build());
        return this;
    }

    /**
     * Adiciona botão.
     *
     * @param title - Título do botão
     * @param action - Ação executada ao clicar
     * @return MenuBarBuilder - Própria instância
     */
    public MenuBarBuilder button(
            String title,
            Runnable action
    ) {
        MenuButtonComponent button = new MenuButtonComponent(title, action);
        menuBar.add(button);
        return this;
    }

    /**
     * Adiciona componente.
     *
     * @param component - Componente
     * @return MenuBarBuilder - Própria instância
     */
    public MenuBarBuilder add(Component component) {
        if (component != null) {
            menuBar.add(component);
        }

        return this;
    }

    /**
     * Adiciona espaço flexível.
     *
     * @return MenuBarBuilder - Própria instância
     */
    public MenuBarBuilder glue() {
        menuBar.add(
                Box.createHorizontalGlue()
        );

        return this;
    }

    /**
     * Retorna barra construída.
     *
     * @return JMenuBar - Barra construída
     */
    public JMenuBar build() {
        return menuBar;
    }

}
