package org.javapi.sigob.view.components;

import java.awt.Dimension;

import javax.swing.JPasswordField;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Campo estilizado de senha.
 */
public class PasswordFieldComponent extends JPasswordField {

    /**
     * Cria campo vazio.
     */
    public PasswordFieldComponent() {
        setup();
    }

    /**
     * Cria campo com valor inicial.
     *
     * @param text - Texto inicial
     */
    public PasswordFieldComponent(String text) {
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
        setAlignmentX(LEFT_ALIGNMENT);
        setSelectionColor(Palette.ACCENT_PRIMARY);
        setSelectedTextColor(Palette.FG_PRIMARY);

        setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        getPreferredSize().height
                )
        );
    }
}
