package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JCheckBox;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

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
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_PRIMARY);
        setForeground(Colors.FG_PRIMARY);

        setFocusPainted(false);
    }

}
