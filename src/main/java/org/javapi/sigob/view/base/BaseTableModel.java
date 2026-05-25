package org.javapi.sigob.view.base;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Modelo base para tabelas da aplicação.
 *
 * @param <T> - Tipo da entidade da tabela
 */
public abstract class BaseTableModel<T> extends AbstractTableModel {

    /**
     * Linhas atuais da tabela.
     */
    private List<T> rows = List.of();

    /**
     * Define linhas da tabela.
     *
     * @param rows - Lista de linhas
     */
    public void setRows(
            List<T> rows
    ) {
        this.rows = rows == null
                ? List.of()
                : List.copyOf(rows);

        fireTableDataChanged();
    }

    /**
     * Retorna linhas atuais.
     *
     * @return List<T> - Linhas atuais
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * Retorna linha da tabela.
     *
     * @param row - Índice da linha
     * @return T - Entidade encontrada ou null
     */
    public T getRow(
            int row
    ) {
        if (row < 0 || row >= rows.size()) {
            return null;
        }

        return rows.get(row);
    }

    /**
     * Retorna colunas da tabela.
     *
     * @return String[] - Colunas da tabela
     */
    protected abstract String[] columns();

    /**
     * Retorna valor da célula.
     *
     * @param row - Linha atual
     * @param columnIndex - Índice da coluna
     * @return Object - Valor da célula
     */
    protected abstract Object getValueAt(
            T row,
            int columnIndex
    );

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        return rows.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnCount() {
        return columns().length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName(
            int column
    ) {
        return columns()[column];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValueAt(
            int rowIndex,
            int columnIndex
    ) {
        T row = getRow(rowIndex);

        if (row == null) {
            return null;
        }

        return getValueAt(
                row,
                columnIndex
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(
            int rowIndex,
            int columnIndex
    ) {
        return false;
    }

}
