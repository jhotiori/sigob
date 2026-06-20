package org.javapi.sigob.view.v2.framework.components;

import java.awt.Dimension;

import javax.swing.JComboBox;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

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
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_SECONDARY);
        setForeground(Colors.FG_PRIMARY);
        setFocusable(true);

        setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        getPreferredSize().height
                )
        );
    }

}
