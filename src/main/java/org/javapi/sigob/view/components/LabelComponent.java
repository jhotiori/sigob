package org.javapi.sigob.view.components;

import javax.swing.JLabel;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Componente estilizado de texto.
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
     * @param text - Texto do label
     */
    public LabelComponent(String text) {
        super(text);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setForeground(Palette.FG_PRIMARY);
    }

}
