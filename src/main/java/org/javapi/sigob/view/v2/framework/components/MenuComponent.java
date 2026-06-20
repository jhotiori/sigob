package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JMenu;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Menu estilizado.
 */
public class MenuComponent extends JMenu {

    /**
     * Cria menu.
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
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_SECONDARY);
        setForeground(Colors.FG_MUTED);
        setOpaque(true);
    }

}
