package org.javapi.sigob.view.v2.framework.components;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.javapi.sigob.view.v2.framework.styles.Colors;

/**
 * Separador estilizado.
 */
public final class SeparatorComponent extends JPanel {
    /**
     * Construtor.
     */
    public SeparatorComponent() {
        setup();
    }

    /**
     * Realiza setup de forma interna.
     */
    private void setup() {
        setOpaque(false);
        setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        Colors.FG_DARK));

        setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        1));
    }
}
