package org.javapi.sigob.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javapi.sigob.entity.ItemVenda;

/**
 * Modelo de tabela de itens da venda.
 */
public class ItemVendaTableModel extends AbstractTableModel {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "Produto",
        "Estoque",
        "Quantidade",
        "Valor Unitário",
        "Valor Total"
    };

    /**
     * Lista de itens.
     */
    private List<ItemVenda> itens = new ArrayList<>();

    /**
     * Define itens da tabela.
     *
     * @param itens - Lista de itens
     */
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens != null ? itens : new ArrayList<>();

        fireTableDataChanged();
    }

    /**
     * Retorna item da linha.
     *
     * @param row - Índice da linha
     * @return ItemVenda - Item encontrado
     */
    public ItemVenda getItem(int row) {
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
        ItemVenda item = itens.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                item.getProdutoEstoque().getProduto().getNome();
            case 1 ->
                item.getProdutoEstoque().getEstoque().getNome();
            case 2 ->
                item.getQuantidade();
            case 3 ->
                item.getProdutoEstoque().getProduto().getValorVenda();
            case 4 ->
                item.getValorSaldo();
            default ->
                null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
