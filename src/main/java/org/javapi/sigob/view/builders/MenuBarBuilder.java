package org.javapi.sigob.view.builders;

import java.util.function.Consumer;

import javax.swing.Box;

import org.javapi.sigob.view.components.MenuBarComponent;
import org.javapi.sigob.view.components.MenuButtonComponent;
import org.javapi.sigob.view.components.MenuComponent;

/**
 * Builder fluente para barra de menus.
 */
public class MenuBarBuilder {

    /**
     * Barra interna do builder.
     *
     * @see {@link MenuBarComponent}
     */
    private final MenuBarComponent menuBar;

    /**
     * Cria builder com barra vazia.
     */
    public MenuBarBuilder() {
        this.menuBar = new MenuBarComponent();
    }

    /**
     * Cria builder usando barra existente.
     *
     * @param menuBar - Barra existente
     */
    public MenuBarBuilder(MenuBarComponent menuBar) {
        this.menuBar = menuBar != null
                ? menuBar
                : new MenuBarComponent();
    }

    /**
     * Adiciona menu na barra.
     *
     * @param text - Texto do menu
     * @param children - Composição do menu
     * @return MenuBarBuilder - Instância atual
     */
    public MenuBarBuilder menu(String text, Consumer<MenuBuilder> children) {
        MenuComponent menu = new MenuComponent(text);

        if (children != null) {
            children.accept(new MenuBuilder(menu));
        }

        menuBar.add(menu);

        return this;
    }

    /**
     * Adiciona botão na barra.
     *
     * @param text - Texto do botão
     * @param action - Ação do botão
     * @return MenuBarBuilder - Instância atual
     */
    public MenuBarBuilder button(String text, Runnable action) {
        MenuButtonComponent button = new MenuButtonComponent(text);

        if (action != null) {
            button.addActionListener(event -> action.run());
        }

        menuBar.add(button);

        return this;
    }

    /**
     * Adiciona espaçamento flexível na barra.
     *
     * @return MenuBarBuilder - Instância atual
     */
    public MenuBarBuilder glue() {
        menuBar.add(Box.createHorizontalGlue());

        return this;
    }

    /**
     * Retorna barra construída.
     *
     * @return MenuBarComponent - Barra construída
     */
    public MenuBarComponent build() {
        return menuBar;
    }

}
