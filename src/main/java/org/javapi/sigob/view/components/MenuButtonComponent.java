package org.javapi.sigob.view.components;

import java.awt.Cursor;

import javax.swing.JButton;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Botão estilizado para menus e barras superiores.
 */
public class MenuButtonComponent extends JButton {

    /**
     * Cria botão vazio.
     */
    public MenuButtonComponent() {
        setup();
    }

    /**
     * Cria botão com texto.
     *
     * @param text - Texto do botão
     */
    public MenuButtonComponent(String text) {
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

        setBorderPainted(false);
        setContentAreaFilled(false);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

}
