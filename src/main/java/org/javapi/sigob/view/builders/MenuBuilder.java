package org.javapi.sigob.view.builders;

import java.util.function.Consumer;

import org.javapi.sigob.view.components.MenuComponent;
import org.javapi.sigob.view.components.MenuItemComponent;

/**
 * Builder fluente para menus.
 */
public class MenuBuilder {

    /**
     * Menu interno do builder.
     *
     * @see {@link MenuComponent}
     */
    private final MenuComponent menu;

    /**
     * Cria builder para menu.
     *
     * @param menu - Menu alvo
     */
    public MenuBuilder(MenuComponent menu) {
        this.menu = menu;
    }

    /**
     * Adiciona item ao menu.
     *
     * @param text - Texto do item
     * @return MenuBuilder - Instância atual
     */
    public MenuBuilder item(String text) {
        return item(text, (Runnable) null);
    }

    /**
     * Adiciona item com ação.
     *
     * @param text - Texto do item
     * @param action - Ação do item
     * @return MenuBuilder - Instância atual
     */
    public MenuBuilder item(String text, Runnable action) {
        MenuItemComponent item = new MenuItemComponent(text);

        if (action != null) {
            item.addActionListener(event -> action.run());
        }

        menu.add(item);

        return this;
    }

    /**
     * Adiciona item configurável ao menu.
     *
     * @param text - Texto do item
     * @param config - Configuração do item
     * @return MenuBuilder - Instância atual
     */
    public MenuBuilder item(String text, Consumer<MenuItemComponent> config) {
        MenuItemComponent item = new MenuItemComponent(text);

        if (config != null) {
            config.accept(item);
        }

        menu.add(item);

        return this;
    }

    /**
     * Adiciona separador ao menu.
     *
     * @return MenuBuilder - Instância atual
     */
    public MenuBuilder separator() {
        menu.addSeparator();

        return this;
    }

    /**
     * Retorna menu construído.
     *
     * @return MenuComponent - Menu construído
     */
    public MenuComponent build() {
        return menu;
    }

}
