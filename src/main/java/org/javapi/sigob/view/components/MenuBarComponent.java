package org.javapi.sigob.view.components;

import javax.swing.JMenuBar;

import org.javapi.sigob.view.styles.Palette;

/**
 * Barra estilizada de menu.
 */
public class MenuBarComponent extends JMenuBar {

    /**
     * Cria barra de menu.
     */
    public MenuBarComponent() {
        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setBackground(Palette.BG_SECONDARY);
    }

}
