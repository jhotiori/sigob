package org.javapi.sigob.view.models;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.javapi.sigob.entity.Saldo;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de saldos.
 */
public class SaldoTableModel extends BaseTableModel<Saldo> {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
            "ID",
            "Data",
            "Tipo",
            "Valor",
            "Descrição"
    };

    /**
     * Formatter de datas.
     */
    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Define saldos da tabela.
     *
     * @param saldos Lista de saldos
     */
    public void setSaldos(
            List<Saldo> saldos
    ) {
        setRows(saldos);
    }

    /**
     * Retorna saldo da linha.
     *
     * @param row Índice da linha
     * @return Saldo encontrado
     */
    public Saldo getSaldo(
            int row
    ) {
        return getRow(row);
    }

    @Override
    protected String[] columns() {
        return COLUMNS;
    }

    @Override
    protected Object getValueAt(
            Saldo saldo,
            int columnIndex
    ) {
        return switch (columnIndex) {

            case 0 ->
                    saldo.getId();

            case 1 ->
                    saldo.getDataSaldo() != null
                            ? FORMATTER.format(saldo.getDataSaldo())
                            : "-";

            case 2 ->
                    saldo.getTipo();

            case 3 ->
                    saldo.getValorSaldo();

            case 4 ->
                    saldo.getDescricao();

            default ->
                    null;
        };
    }
}