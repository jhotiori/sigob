package org.javapi.sigob.view.components;

import java.awt.Dimension;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Componente estilizado de lista.
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
     * Cria lista com itens.
     *
     * @param items - Itens da lista
     */
    @SafeVarargs
    public ListComponent(T... items) {
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

        setSelectionBackground(Palette.ACCENT_PRIMARY);
        setSelectionForeground(Palette.FG_PRIMARY);

        setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        setLayoutOrientation(VERTICAL);

        setVisibleRowCount(8);

        setFixedCellHeight(28);

        DefaultListSelectionModel selectionModel = (DefaultListSelectionModel) getSelectionModel();
        selectionModel.setLeadAnchorNotificationEnabled(false);
        setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        getPreferredSize().height
                )
        );
    }

}
