package org.javapi.sigob.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javapi.sigob.entity.Categoria;

/**
 * Modelo de tabela de categorias.
 */
public class CategoriaTableModel extends AbstractTableModel {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Nome"
    };

    /**
     * Lista de categorias.
     */
    private List<Categoria> categorias = new ArrayList<>();

    /**
     * Define categorias da tabela.
     *
     * @param categorias - Lista de categorias
     */
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias != null
                ? categorias
                : new ArrayList<>();

        fireTableDataChanged();
    }

    /**
     * Retorna categoria da linha.
     *
     * @param row - Índice da linha
     * @return Categoria - Categoria encontrada
     */
    public Categoria getCategoria(int row) {
        if (row < 0 || row >= categorias.size()) {
            return null;
        }

        return categorias.get(row);
    }

    @Override
    public int getRowCount() {
        return categorias.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(
            int rowIndex,
            int columnIndex
    ) {
        Categoria categoria = categorias.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                categoria.getId();

            case 1 ->
                categoria.getNome();

            default ->
                null;
        };
    }

    @Override
    public boolean isCellEditable(
            int rowIndex,
            int columnIndex
    ) {
        return false;
    }

}
