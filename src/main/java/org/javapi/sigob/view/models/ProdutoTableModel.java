package org.javapi.sigob.view.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javapi.sigob.entity.Produto;

/**
 * Modelo tabular de produtos.
 */
public final class ProdutoTableModel extends AbstractTableModel {

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
     * Lista de produtos exibidos.
     */
    private List<Produto> produtos
            = new ArrayList<>();

    /**
     * Define produtos da tabela.
     *
     * @param produtos - Lista de produtos
     */
    public void setProdutos(
            List<Produto> produtos
    ) {
        this.produtos = produtos != null
                ? new ArrayList<>(produtos)
                : new ArrayList<>();

        fireTableDataChanged();
    }

    /**
     * Retorna produto pelo índice da linha.
     *
     * @param row - Índice da linha
     * @return Produto - Produto encontrado
     */
    public Produto getProduto(
            int row
    ) {
        return produtos.get(row);
    }

    /**
     * Retorna quantidade de linhas.
     *
     * @return int - Quantidade de linhas
     */
    @Override
    public int getRowCount() {
        return produtos.size();
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
     * @param rowIndex - Índice da linha
     * @param columnIndex - Índice da coluna
     * @return Object - Valor da célula
     */
    @Override
    public Object getValueAt(
            int rowIndex,
            int columnIndex
    ) {
        Produto produto = produtos.get(rowIndex);

        return switch (columnIndex) {
            case COLUMN_ID ->
                produto.getId();

            case COLUMN_CODIGO ->
                produto.getCodigo();

            case COLUMN_NOME ->
                produto.getNome();

            case COLUMN_CATEGORIA -> {
                if (produto.getCategoria() == null) {
                    yield "-";
                }

                yield produto.getCategoria().getNome();
            }

            case COLUMN_VALOR_COMPRA -> {
                BigDecimal valorCompra = produto.getValorCompra();

                yield valorCompra != null
                ? valorCompra
                : BigDecimal.ZERO;
            }

            case COLUMN_VALOR_VENDA -> {
                BigDecimal valorVenda = produto.getValorVenda();

                yield valorVenda != null
                ? valorVenda
                : BigDecimal.ZERO;
            }

            default ->
                "";
        };
    }

    /**
     * Retorna tipo da coluna.
     *
     * @param columnIndex - Índice da coluna
     * @return Class<?> - Classe da coluna
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

    /**
     * Define se célula é editável.
     *
     * @param rowIndex - Índice da linha
     * @param columnIndex - Índice da coluna
     * @return boolean - true se editável
     */
    @Override
    public boolean isCellEditable(
            int rowIndex,
            int columnIndex
    ) {
        return false;
    }

}
