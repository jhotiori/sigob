package org.javapi.sigob.view.components;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;

/**
 * Componente estilizado de tabela.
 */
public class TableComponent extends JTable {

    /**
     * Cria tabela vazia.
     */
    public TableComponent() {
        setup();
    }

    /**
     * Cria tabela com modelo.
     *
     * @param model - Modelo da tabela
     */
    public TableComponent(TableModel model) {
        super(model);

        setup();
    }

    /**
     * Configura padrões visuais do componente.
     */
    private void setup() {
        setFont(Fonts.DEFAULT);

        setBackground(Palette.BG_SURFACE);
        setForeground(Palette.FG_PRIMARY);

        setSelectionBackground(Palette.ACCENT_PRIMARY);
        setSelectionForeground(Palette.FG_PRIMARY);

        setGridColor(Palette.BORDER_PRIMARY);

        setRowHeight(28);

        setFocusable(false);

        setFillsViewportHeight(true);

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setupHeader();
    }

    /**
     * Configura cabeçalho da tabela.
     */
    private void setupHeader() {
        JTableHeader header = getTableHeader();

        header.setFont(Fonts.DEFAULT_BOLD);

        header.setBackground(Palette.BG_SECONDARY);
        header.setForeground(Palette.FG_PRIMARY);

        header.setReorderingAllowed(false);
    }

}
