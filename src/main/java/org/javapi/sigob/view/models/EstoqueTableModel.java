package org.javapi.sigob.view.models;

import java.util.List;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela para estoques.
 */
public class EstoqueTableModel extends BaseTableModel<Estoque> {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Código",
        "Nome"
    };

    /**
     * Define estoques da tabela.
     *
     * @param estoques - Lista de estoques
     */
    public void setEstoques(
            List<Estoque> estoques
    ) {
        setRows(estoques);
    }

    /**
     * Retorna estoque da linha.
     *
     * @param row - Índice da linha
     * @return Estoque - Estoque encontrado ou null
     */
    public Estoque getEstoque(
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
            Estoque estoque,
            int columnIndex
    ) {
        return switch (columnIndex) {
            case 0 ->
                estoque.getId();

            case 1 ->
                estoque.getCodigo();

            case 2 ->
                estoque.getNome();

            default ->
                null;
        };
    }

}
