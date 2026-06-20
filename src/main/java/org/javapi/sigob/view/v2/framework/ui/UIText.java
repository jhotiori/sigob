package org.javapi.sigob.view.v2.framework.ui;

import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

public final class UIText {
    /**
     * Cria um texto de cabeçalho H1.
     * @param text - Texto
     * @return JLabel - Label criada
     */
    public static LabelComponent header(String text) {
        return UI.label(text, label -> {
            label.setForeground(Colors.FG_PRIMARY);
            label.setFont(Fonts.HEADER_BOLD);
        });
    }

    /**
     * Cria um texto de cabeçalho H2.
     * @param text - Texto
     * @return JLabel - Label criada
     */
    public static LabelComponent title(String text) {
        return UI.label(text, label -> {
            label.setForeground(Colors.FG_PRIMARY);
            label.setFont(Fonts.TITLE);
        });
    }

    /**
     * Cria um texto de cabeçalho H3.
     * @param text - Texto
     * @return JLabel - Label criada
     */
    public static LabelComponent subtitle(String text) {
        return UI.label(text, label -> {
            label.setForeground(Colors.FG_PRIMARY);
            label.setFont(Fonts.SUBTITLE);
        });
    }

    /**
     * Cria um texto de parágrafo.
     * @param text - Texto
     * @return JLabel - Label criada
     */
    public static LabelComponent paragraph(String text) {
        return UI.label(text, label -> {
            label.setForeground(Colors.FG_SECONDARY);
            label.setFont(Fonts.DEFAULT);
        });
    }

    /**
     * Cria um texto pequeno.
     * @param text - Texto
     * @return JLabel - Label criada
     */
    public static LabelComponent subparagraph(String text) {
        return UI.label(text, label -> {
            label.setForeground(Colors.FG_MUTED);
            label.setFont(Fonts.SMALL);
        });
    }
}
