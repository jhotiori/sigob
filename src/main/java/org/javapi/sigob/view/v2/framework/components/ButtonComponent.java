package org.javapi.sigob.view.v2.framework.components;

import java.awt.Cursor;

import javax.swing.JButton;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;

/**
 * Botão estilizado.
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
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT_BOLD);
        setBackground(Colors.ACCENT_PRIMARY);
        setForeground(Colors.FG_PRIMARY);
        setFocusPainted(false);
        setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        UIEvents.bind(this)
            .onHover(() -> {
                setBackground(Colors.ACCENT_MUTED);
                setForeground(Colors.FG_MUTED);
            })
            .onHoverExit(() -> {
                setBackground(Colors.ACCENT_PRIMARY);
                setForeground(Colors.FG_PRIMARY);
            });

    }

}
