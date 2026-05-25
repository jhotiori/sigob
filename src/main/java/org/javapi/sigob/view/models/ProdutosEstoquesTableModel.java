package org.javapi.sigob.view.models;

import java.util.List;

import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de produtos em estoque.
 */
public class ProdutosEstoquesTableModel extends BaseTableModel<ProdutosEstoques> {

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
     * Define itens da tabela.
     *
     * @param itens - Lista de itens
     */
    public void setItens(
            List<ProdutosEstoques> itens
    ) {
        setRows(itens);
    }

    /**
     * Retorna item da linha.
     *
     * @param row - Índice da linha
     * @return ProdutosEstoques - Item encontrado ou null
     */
    public ProdutosEstoques getItem(
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
            ProdutosEstoques item,
            int columnIndex
    ) {
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

}
