package org.javapi.sigob.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javapi.sigob.entity.ProdutosEstoques;

/**
 * Modelo de tabela de produtos em estoque.
 */
public class ProdutosEstoquesTableModel extends AbstractTableModel {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "Produto",
        "Estoque",
        "Quantidade",
        "Código Produto",
        "Código Estoque"
    };

    /**
     * Lista de itens.
     */
    private List<ProdutosEstoques> itens = new ArrayList<>();

    /**
     * Define itens da tabela.
     *
     * @param itens - Lista de itens
     */
    public void setItens(List<ProdutosEstoques> itens) {
        this.itens = itens != null
                ? itens
                : new ArrayList<>();

        fireTableDataChanged();
    }

    /**
     * Retorna item da linha.
     *
     * @param row - Índice da linha
     * @return ProdutosEstoques - Item encontrado
     */
    public ProdutosEstoques getItem(int row) {
        if (row < 0 || row >= itens.size()) {
            return null;
        }

        return itens.get(row);
    }

    @Override
    public int getRowCount() {
        return itens.size();
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProdutosEstoques item = itens.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                item.getProduto().getNome();

            case 1 ->
                item.getEstoque().getNome();

            case 2 ->
                item.getQuantidade();

            case 3 ->
                item.getProduto().getCodigo();

            case 4 ->
                item.getEstoque().getCodigo();

            default ->
                null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
