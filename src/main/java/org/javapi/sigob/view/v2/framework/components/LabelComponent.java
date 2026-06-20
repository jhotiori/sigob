package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JLabel;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Label estilizado.
 */
public class LabelComponent extends JLabel {

    /**
     * Cria label vazio.
     */
    public LabelComponent() {
        setup();
    }

    /**
     * Cria label com texto.
     *
     * @param text - Texto da label
     */
    public LabelComponent(String text) {
        super(text);
        setup();
    }

    /**
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setForeground(Colors.FG_PRIMARY);
    }

}
