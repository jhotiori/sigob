package org.javapi.sigob.view.components;

import java.awt.Dimension;

import javax.swing.JTextArea;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Área estilizada de texto.
 */
public class TextAreaComponent extends JTextArea {

    /**
     * Cria área vazia.
     */
    public TextAreaComponent() {
        setup();
    }

    /**
     * Cria área com texto inicial.
     *
     * @param text - Texto inicial
     */
    public TextAreaComponent(String text) {
        super(text);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);

        setBackground(Palette.BG_SECONDARY);
        setForeground(Palette.FG_PRIMARY);
        setCaretColor(Palette.FG_PRIMARY);
        setSelectionColor(Palette.ACCENT_PRIMARY);
        setSelectedTextColor(Palette.FG_PRIMARY);

        setLineWrap(true);
        setWrapStyleWord(true);

        setAlignmentX(LEFT_ALIGNMENT);

        setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        getPreferredSize().height
                )
        );
    }

}
