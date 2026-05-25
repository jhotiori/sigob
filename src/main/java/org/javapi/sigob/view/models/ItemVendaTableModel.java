package org.javapi.sigob.view.models;

import java.util.List;

import org.javapi.sigob.entity.ItemVenda;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de itens da venda.
 */
public class ItemVendaTableModel extends BaseTableModel<ItemVenda> {

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
     * Define itens da tabela.
     *
     * @param itens - Lista de itens
     */
    public void setItens(
            List<ItemVenda> itens
    ) {
        setRows(itens);
    }

    /**
     * Retorna item da linha.
     *
     * @param row - Índice da linha
     * @return ItemVenda - Item encontrado ou null
     */
    public ItemVenda getItem(
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
            ItemVenda item,
            int columnIndex
    ) {
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

}
