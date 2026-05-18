package org.javapi.sigob.view.models;

import org.javapi.sigob.entity.Venda;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VendaTableModel extends AbstractTableModel {

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
     * Lista de vendas.
     */
    private List<Venda> vendas = new ArrayList<>();

    @Override
    public int getRowCount() {
        return vendas.size();
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
        Venda venda = vendas.get(rowIndex);

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
                venda.getDataAbertura() == null
                ? "-"
                : FORMATTER.format(venda.getDataAbertura());
            case 5 ->
                venda.getDataFinalizada() == null
                ? "-"
                : FORMATTER.format(venda.getDataFinalizada());
            case 6 ->
                venda.getValorTotal();
            default ->
                "";
        };
    }

    /**
     * Define vendas da tabela.
     *
     * @param vendas Lista de vendas
     */
    public void setVendas(
            List<Venda> vendas
    ) {
        this.vendas = vendas == null
                ? new ArrayList<>()
                : vendas;

        fireTableDataChanged();
    }

    /**
     * Retorna venda por linha.
     *
     * @param row Linha
     * @return Venda - Venda encontrada
     */
    public Venda getVenda(
            int row
    ) {
        return vendas.get(row);
    }

}
