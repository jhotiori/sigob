package org.javapi.sigob.view.models;

import java.util.List;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de categorias.
 */
public class CategoriaTableModel extends BaseTableModel<Categoria> {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Nome"
    };

    /**
     * Define categorias da tabela.
     *
     * @param categorias - Lista de categorias
     */
    public void setCategorias(
            List<Categoria> categorias
    ) {
        setRows(categorias);
    }

    /**
     * Retorna categoria da linha.
     *
     * @param row - Índice da linha
     * @return Categoria - Categoria encontrada ou null
     */
    public Categoria getCategoria(
            int row
    ) {
        return getRow(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] columns() {
        return COLUMNS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getValueAt(
            Categoria categoria,
            int columnIndex
    ) {
        return switch (columnIndex) {
            case 0 ->
                categoria.getId();

            case 1 ->
                categoria.getNome();

            default ->
                null;
        };
    }

}
