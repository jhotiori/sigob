package org.javapi.sigob.view.components;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Componente estilizado de texto.
 */
public class LabelComponent extends JLabel {

    /**
     * Cria label vazio.
     */
    public LabelComponent() {
        setup();
    }

    /**
     * Cria label com texto.
     *
     * @param text - Texto do label
     */
    public LabelComponent(String text) {
        super(text);

        setup();
    }

    /**
     * Cria label com ícone.
     *
     * @param icon - Ícone do label
     */
    public LabelComponent(Icon icon) {
        super(icon);

        setup();
    }

    /**
     * Cria label com texto e ícone.
     *
     * @param text - Texto do label
     * @param icon - Ícone do label
     * @param alignment - Alinhamento horizontal
     */
    public LabelComponent(
            String text,
            Icon icon,
            int alignment
    ) {
        super(text, icon, alignment);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setForeground(Palette.FG_PRIMARY);
    }

}
