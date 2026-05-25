package org.javapi.sigob.view.components;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.javapi.sigob.view.styles.Palette;

/**
 * ScrollPane estilizado.
 */
public class ScrollPaneComponent extends JScrollPane {

    /**
     * Cria um ScrollPane vazio.
     */
    public ScrollPaneComponent() {
        setup();
    }

    /**
     * Cria um ScrollPane com componente.
     *
     * @param component - Componente interno
     */
    public ScrollPaneComponent(JComponent component) {
        super(component);

        setup();
    }

    /**
     * Aplica estilos padrões do componente.
     */
    private void setup() {
        getViewport().setBackground(Palette.BG_PRIMARY);
        setBorder(null);
    }
}
