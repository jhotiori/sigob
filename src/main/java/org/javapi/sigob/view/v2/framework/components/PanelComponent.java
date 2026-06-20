package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.styles.Colors;

/**
 * Painel estilizado.
 */
public class PanelComponent extends JPanel {

    /**
     * Cria painel estilizado.
     */
    public PanelComponent() {
        setup();
    }

    /**
     * Configura estilos do componente.
     */
    private void setup() {
        setBackground(Colors.BG_PRIMARY);
        setForeground(Colors.FG_PRIMARY);

        setOpaque(true);
    }

}
