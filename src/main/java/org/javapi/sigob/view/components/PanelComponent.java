package org.javapi.sigob.view.components;

import javax.swing.JPanel;

import org.javapi.sigob.view.styles.Palette;

/**
 * Painel estilizado.
 */
public class PanelComponent extends JPanel {

    /**
     * Cria um painel padrão.
     */
    public PanelComponent() {
        style();
    }

    /**
     * Aplica estilos padrões do componente.
     */
    private void style() {
        setBackground(Palette.BG_PRIMARY);
    }
}
