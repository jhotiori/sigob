package org.javapi.sigob.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javapi.sigob.entity.Estoque;

/**
 * Modelo de tabela para estoques.
 */
public final class EstoqueTableModel extends AbstractTableModel {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Código",
        "Nome"
    };

    /**
     * Lista de estoques.
     */
    private List<Estoque> estoques = new ArrayList<>();

    /**
     * Define estoques da tabela.
     *
     * @param estoques - Lista de estoques
     */
    public void setEstoques(
            List<Estoque> estoques
    ) {
        this.estoques = estoques == null
                ? new ArrayList<>()
                : new ArrayList<>(estoques);

        fireTableDataChanged();
    }

    /**
     * Retorna estoque pela linha.
     *
     * @param row - Linha
     * @return Estoque - Estoque encontrado
     */
    public Estoque getEstoque(
            int row
    ) {
        return estoques.get(row);
    }

    /**
     * Retorna quantidade de linhas.
     *
     * @return int - Quantidade de linhas
     */
    @Override
    public int getRowCount() {
        return estoques.size();
    }

    /**
     * Retorna quantidade de colunas.
     *
     * @return int - Quantidade de colunas
     */
    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    /**
     * Retorna nome da coluna.
     *
     * @param column - Índice da coluna
     * @return String - Nome da coluna
     */
    @Override
    public String getColumnName(
            int column
    ) {
        return COLUMNS[column];
    }

    /**
     * Retorna valor da célula.
     *
     * @param rowIndex - Linha
     * @param columnIndex - Coluna
     * @return Object - Valor da célula
     */
    @Override
    public Object getValueAt(
            int rowIndex,
            int columnIndex
    ) {
        Estoque estoque = estoques.get(rowIndex);

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
