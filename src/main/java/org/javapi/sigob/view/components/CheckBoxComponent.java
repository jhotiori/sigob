package org.javapi.sigob.view.components;

import javax.swing.JCheckBox;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * CheckBox estilizado.
 */
public class CheckBoxComponent extends JCheckBox {

    /**
     * Cria CheckBox vazio.
     */
    public CheckBoxComponent() {
        setup();
    }

    /**
     * Cria CheckBox com texto.
     *
     * @param text - Texto do CheckBox
     */
    public CheckBoxComponent(String text) {
        super(text);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);

        setBackground(Palette.BG_PRIMARY);
        setForeground(Palette.FG_PRIMARY);

        setFocusPainted(false);
    }

}
