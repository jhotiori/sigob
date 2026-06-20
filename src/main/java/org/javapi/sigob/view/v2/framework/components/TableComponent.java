package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.javapi.sigob.view.v2.framework.styles.Colors;
import org.javapi.sigob.view.v2.framework.styles.Fonts;

/**
 * Tabela estilizada.
 */
public class TableComponent extends JTable {

    /**
     * Cria tabela estilizada.
     */
    public TableComponent() {
        setup();
    }

    /**
     * Configura estilos do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);
        setBackground(Colors.BG_PRIMARY);
        setForeground(Colors.FG_PRIMARY);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setSelectionBackground(Colors.ACCENT_SECONDARY);
        setSelectionForeground(Colors.FG_PRIMARY);
        
        setRowHeight(28);
        setFillsViewportHeight(true);
        setFocusable(false);
    }

}
