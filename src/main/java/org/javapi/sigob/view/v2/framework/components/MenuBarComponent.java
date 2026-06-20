package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JMenuBar;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Barra de menus estilizada.
 */
public class MenuBarComponent extends JMenuBar {

    /**
     * Cria barra de menus.
     */
    public MenuBarComponent() {
        setup();
    }

    /**
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_SECONDARY);
        setForeground(Colors.FG_PRIMARY);
        setBorderPainted(false);
    }

}
