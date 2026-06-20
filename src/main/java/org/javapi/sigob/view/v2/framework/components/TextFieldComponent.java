package org.javapi.sigob.view.v2.framework.components;

import java.awt.Dimension;

import javax.swing.JTextField;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Campo de texto estilizado.
 */
public class TextFieldComponent extends JTextField {

    /**
     * Cria campo vazio.
     */
    public TextFieldComponent() {
        setup();
    }

    /**
     * Cria campo com tamanho.
     *
     * @param columns - Quantidade de colunas
     */
    public TextFieldComponent(int columns) {
        super(columns);
        setup();
    }

    /**
     * Cria campo com placeholder.
     *
     * @param placeholder - Placeholder
     */
    public TextFieldComponent(String placeholder) {
        super();
        setup();
        putClientProperty(
            "JTextField.placeholderText",
            placeholder
        );
    }

    /**
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_SURFACE);
        setForeground(Colors.FG_PRIMARY);
        setCaretColor(Colors.FG_MUTED);
        setSelectionColor(Colors.ACCENT_SECONDARY);

        setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        getPreferredSize().height
                )
        );
    }

}
