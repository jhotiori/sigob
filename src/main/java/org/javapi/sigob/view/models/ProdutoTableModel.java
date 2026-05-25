package org.javapi.sigob.view.models;

import java.math.BigDecimal;
import java.util.List;

import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo tabular de produtos.
 */
public class ProdutoTableModel extends BaseTableModel<Produto> {

    /**
     * Índice da coluna de ID.
     */
    private static final int COLUMN_ID = 0;

    /**
     * Índice da coluna de código.
     */
    private static final int COLUMN_CODIGO = 1;

    /**
     * Índice da coluna de nome.
     */
    private static final int COLUMN_NOME = 2;

    /**
     * Índice da coluna de categoria.
     */
    private static final int COLUMN_CATEGORIA = 3;

    /**
     * Índice da coluna de valor de compra.
     */
    private static final int COLUMN_VALOR_COMPRA = 4;

    /**
     * Índice da coluna de valor de venda.
     */
    private static final int COLUMN_VALOR_VENDA = 5;

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Código",
        "Nome",
        "Categoria",
        "Valor Compra",
        "Valor Venda"
    };

    /**
     * Define produtos da tabela.
     *
     * @param produtos - Lista de produtos
     */
    public void setProdutos(
            List<Produto> produtos
    ) {
        setRows(produtos);
    }

    /**
     * Retorna produto da linha.
     *
     * @param row - Índice da linha
     * @return Produto - Produto encontrado ou null
     */
    public Produto getProduto(
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
            Produto produto,
            int columnIndex
    ) {
        return switch (columnIndex) {
            case COLUMN_ID ->
                produto.getId();

            case COLUMN_CODIGO ->
                produto.getCodigo();

            case COLUMN_NOME ->
                produto.getNome();

            case COLUMN_CATEGORIA ->
                produto.getCategoria() != null
                ? produto.getCategoria().getNome()
                : "-";

            case COLUMN_VALOR_COMPRA ->
                produto.getValorCompra() != null
                ? produto.getValorCompra()
                : BigDecimal.ZERO;

            case COLUMN_VALOR_VENDA ->
                produto.getValorVenda() != null
                ? produto.getValorVenda()
                : BigDecimal.ZERO;

            default ->
                null;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getColumnClass(
            int columnIndex
    ) {
        return switch (columnIndex) {
            case COLUMN_ID ->
                Integer.class;

            case COLUMN_VALOR_COMPRA, COLUMN_VALOR_VENDA ->
                BigDecimal.class;

            default ->
                String.class;
        };
    }

}
