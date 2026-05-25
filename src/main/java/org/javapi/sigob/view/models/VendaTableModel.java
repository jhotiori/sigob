package org.javapi.sigob.view.models;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de vendas.
 */
public class VendaTableModel extends BaseTableModel<Venda> {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Status",
        "Cliente",
        "Funcionário",
        "Data Abertura",
        "Data Finalizada",
        "Valor Total"
    };

    /**
     * Formatter de datas.
     */
    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Define vendas da tabela.
     *
     * @param vendas - Lista de vendas
     */
    public void setVendas(
            List<Venda> vendas
    ) {
        setRows(vendas);
    }

    /**
     * Retorna venda da linha.
     *
     * @param row - Índice da linha
     * @return Venda - Venda encontrada ou null
     */
    public Venda getVenda(
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
            Venda venda,
            int columnIndex
    ) {
        return switch (columnIndex) {
            case 0 ->
                venda.getId();

            case 1 ->
                venda.getStatus();

            case 2 ->
                venda.getCliente().getNome();

            case 3 ->
                venda.getFuncionario().getNome();

            case 4 ->
                venda.getDataAbertura() != null
                ? FORMATTER.format(venda.getDataAbertura())
                : "-";

            case 5 ->
                venda.getDataFinalizada() != null
                ? FORMATTER.format(venda.getDataFinalizada())
                : "-";

            case 6 ->
                venda.getValorTotal();

            default ->
                null;
        };
    }

}
