package org.javapi.sigob.view.v2.framework.components;

import java.awt.Dimension;

import javax.swing.JPasswordField;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Campo de senha estilizado.
 */
public class PasswordFieldComponent extends JPasswordField {

    /**
     * Cria campo vazio.
     */
    public PasswordFieldComponent() {
        setup();
    }

    /**
     * Cria campo com tamanho.
     *
     * @param columns - Quantidade de colunas
     */
    public PasswordFieldComponent(int columns) {
        super(columns);
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
        setSelectionColor(Colors.ACCENT_SECONDARY);

        setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        getPreferredSize().height
                )
        );
    }

}
