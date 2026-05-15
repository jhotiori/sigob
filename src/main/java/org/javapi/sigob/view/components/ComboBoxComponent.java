package org.javapi.sigob.view.components;

import javax.swing.JComboBox;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * ComboBox estilizado.
 *
 * @param <T> - Tipo dos itens
 */
public class ComboBoxComponent<T> extends JComboBox<T> {

    /**
     * Cria ComboBox vazio.
     */
    public ComboBoxComponent() {
        setup();
    }

    /**
     * Cria ComboBox com itens.
     *
     * @param items - Itens do ComboBox
     */
    @SafeVarargs
    public ComboBoxComponent(T... items) {
        super(items);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);

        setBackground(Palette.BG_SECONDARY);
        setForeground(Palette.FG_PRIMARY);

        setFocusable(false);
    }

}
