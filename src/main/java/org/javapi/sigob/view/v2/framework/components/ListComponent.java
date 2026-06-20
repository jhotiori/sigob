package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Lista estilizada.
 *
 * @param <T> - Tipo dos itens
 */
public class ListComponent<T> extends JList<T> {

    /**
     * Cria lista vazia.
     */
    public ListComponent() {
        setup();
    }

    /**
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_SECONDARY);
        setForeground(Colors.FG_PRIMARY);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}
