package org.javapi.sigob.view.v2.framework.components;

import java.awt.Cursor;

import javax.swing.JMenuItem;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Item de menu estilizado.
 */
public class MenuItemComponent extends JMenuItem {

    /**
     * Cria item vazio.
     */
    public MenuItemComponent() {
        setup();
    }

    /**
     * Cria item com texto.
     *
     * @param text - Texto do item
     */
    public MenuItemComponent(String text) {
        super(text);
        setup();
    }

    /**
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_SECONDARY);
        setForeground(Colors.FG_MUTED);
        setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

}
