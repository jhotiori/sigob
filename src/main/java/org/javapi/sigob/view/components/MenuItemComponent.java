package org.javapi.sigob.view.components;

import javax.swing.JMenuItem;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Item estilizado de menu.
 */
public class MenuItemComponent extends JMenuItem {

    /**
     * Cria item vazio.
     */
    public MenuItemComponent() {
        setup();
    }

    /**
     * Cria item com texto.
     *
     * @param text - Texto do item
     */
    public MenuItemComponent(String text) {
        super(text);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);

        setBackground(Palette.BG_SECONDARY);
        setForeground(Palette.FG_PRIMARY);

        setFocusPainted(false);
    }

}
