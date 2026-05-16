package org.javapi.sigob.view.components;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.javapi.sigob.view.styles.Palette;

/**
 * Componente estilizado de rolagem.
 */
public class ScrollComponent extends JScrollPane {

    /**
     * Cria scroll vazio.
     */
    public ScrollComponent() {
        setup();
    }

    /**
     * Cria scroll com componente.
     *
     * @param component - Componente alvo
     */
    public ScrollComponent(JComponent component) {
        super(component);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setBorder(
                BorderFactory.createLineBorder(
                        Palette.BORDER_PRIMARY
                )
        );

        getViewport().setBackground(Palette.BG_SECONDARY);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        getVerticalScrollBar().setUnitIncrement(16);
    }

}
