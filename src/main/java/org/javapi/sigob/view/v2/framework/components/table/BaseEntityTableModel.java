package org.javapi.sigob.view.v2.framework.components.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Modelo base para entidades.
 *
 * @param <T> Tipo da entidade
 */
public abstract class BaseEntityTableModel<T> extends AbstractTableModel {

    /**
     * Entidades exibidas.
     */
    private final List<T> ENTITIES = new ArrayList<>();

    /**
     * Colunas da tabela.
     */
    private final EntityTableColumn<T>[] COLUMNS;

    /**
     * Construtor.
     *
     * @param columns - Colunas
     */
    @SafeVarargs
    protected BaseEntityTableModel(
            EntityTableColumn<T>... columns
    ) {
        this.COLUMNS = columns;
    }

    /**
     * Define entidades.
     *
     * @param entities - Entidades
     */
    public void setEntities(
            Iterable<T> entities
    ) {
        clearEntities();

        entities.forEach(
                ENTITIES::add
        );

        fireTableDataChanged();
    }

    /**
     * Limpa entidades.
     */
    public void clearEntities() {
        ENTITIES.clear();
        fireTableDataChanged();
    }

    /**
     * Retorna entidade.
     *
     * @param row - Linha
     * @return T - Entidade
     */
    public T entity(int row) {
        return ENTITIES.get(row);
    }

    /**
     * Retorna entidade da linha.
     *
     * @param row - Linha
     * @return T - Entidade
     */
    protected T row(int row) {
        return ENTITIES.get(row);
    }

    /**
     * Retorna coluna.
     *
     * @param column - Índice
     * @return TableColumn<T> - Coluna
     */
    protected EntityTableColumn<T> column(
            int column
    ) {
        return COLUMNS[column];
    }

    @Override
    public final int getRowCount() {
        return ENTITIES.size();
    }

    @Override
    public final int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public final String getColumnName(
            int column
    ) {
        return COLUMNS[column].name();
    }

    @Override
    public final Object getValueAt(
            int row,
            int column
    ) {
        return COLUMNS[column].value(
                ENTITIES.get(row)
        );
    }
}
