package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JTextArea;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Área de texto estilizada.
 */
public class TextAreaComponent extends JTextArea {

    /**
     * Cria área de texto.
     */
    public TextAreaComponent() {
        setup();
    }

    /**
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_SURFACE);
        setForeground(Colors.FG_PRIMARY);
        setCaretColor(Colors.FG_MUTED);
        setSelectionColor(Colors.ACCENT_PRIMARY);

        setLineWrap(true);
        setWrapStyleWord(true);
    }

}
