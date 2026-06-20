package org.javapi.sigob.view.v2.framework.layouts.menu;

import java.util.function.Consumer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.javapi.sigob.view.v2.framework.components.MenuComponent;
import org.javapi.sigob.view.v2.framework.components.MenuItemComponent;

/**
 * Construtor de menus.
 */
public final class MenuBuilder {

    /**
     * Menu interno.
     */
    private final MenuComponent menu;

    /**
     * Cria construtor de menu.
     *
     * @param title - Título do menu
     */
    public MenuBuilder(String title) {
        this.menu = new MenuComponent(title);
    }

    /**
     * Adiciona item ao menu.
     *
     * @param title - Título do item
     * @param action - Ação executada ao clicar
     * @return MenuBuilder - Própria instância
     */
    public MenuBuilder item(
            String title,
            Runnable action
    ) {
        JMenuItem item = new MenuItemComponent(title);

        if (action != null) {
            item.addActionListener(
                    event -> action.run()
            );
        }

        menu.add(item);
        return this;
    }

    /**
     * Adiciona submenu.
     *
     * @param title - Título do submenu
     * @param config - Configuração do submenu
     * @return MenuBuilder - Própria instância
     */
    public MenuBuilder menu(
            String title,
            Consumer<MenuBuilder> config
    ) {
        MenuBuilder builder = new MenuBuilder(title);

        if (config != null) {
            config.accept(builder);
        }

        menu.add(builder.build());
        return this;
    }

    /**
     * Adiciona separador.
     *
     * @return MenuBuilder - Própria instância
     */
    public MenuBuilder separator() {
        menu.addSeparator();
        return this;
    }

    /**
     * Retorna menu construído.
     *
     * @return JMenu - Menu construído
     */
    public JMenu build() {
        return menu;
    }

}
