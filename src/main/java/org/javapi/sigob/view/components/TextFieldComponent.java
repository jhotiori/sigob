package org.javapi.sigob.view.components;

import java.awt.Dimension;

import javax.swing.JTextField;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Campo estilizado de texto.
 */
public class TextFieldComponent extends JTextField {

    /**
     * Cria campo vazio.
     */
    public TextFieldComponent() {
        setup();
    }

    /**
     * Cria campo com valor inicial.
     *
     * @param text - Texto inicial
     */
    public TextFieldComponent(String text) {
        super(text);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT_ITALIC);

        setBackground(Palette.BG_SECONDARY);
        setForeground(Palette.FG_PRIMARY);
        setCaretColor(Palette.FG_PRIMARY);
        setSelectionColor(Palette.ACCENT_PRIMARY);
        setSelectedTextColor(Palette.FG_PRIMARY);

        setAlignmentX(LEFT_ALIGNMENT);
        setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        getPreferredSize().height
                )
        );
    }

}
