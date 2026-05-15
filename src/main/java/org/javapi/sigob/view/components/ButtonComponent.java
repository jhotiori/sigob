package org.javapi.sigob.view.components;

import java.awt.Cursor;

import javax.swing.JButton;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Componente estilizado de botão.
 */
public class ButtonComponent extends JButton {

    /**
     * Cria botão vazio.
     */
    public ButtonComponent() {
        setup();
    }

    /**
     * Cria botão com texto.
     *
     * @param text - Texto do botão
     */
    public ButtonComponent(String text) {
        super(text);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);

        setBackground(Palette.ACCENT_PRIMARY);
        setForeground(Palette.FG_PRIMARY);

        setFocusPainted(false);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

}
