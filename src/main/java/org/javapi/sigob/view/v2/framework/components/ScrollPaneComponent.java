package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * ScrollPane estilizado.
 */
public class ScrollPaneComponent extends JScrollPane {

    /**
     * Cria ScrollPane vazio.
     */
    public ScrollPaneComponent() {
    }

    /**
     * Cria ScrollPane com conteúdo.
     *
     * @param component - Componente interno
     */
    public ScrollPaneComponent(JComponent component) {
        super(component);
    }

}
