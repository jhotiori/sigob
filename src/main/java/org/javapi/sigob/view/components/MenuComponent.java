package org.javapi.sigob.view.components;

import javax.swing.JMenu;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Menu estilizado.
 */
public class MenuComponent extends JMenu {

    /**
     * Cria menu vazio.
     */
    public MenuComponent() {
        setup();
    }

    /**
     * Cria menu com texto.
     *
     * @param text - Texto do menu
     */
    public MenuComponent(String text) {
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
