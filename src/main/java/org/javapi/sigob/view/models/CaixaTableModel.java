package org.javapi.sigob.view.models;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.javapi.sigob.entity.Caixa;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de caixas.
 */
public class CaixaTableModel extends BaseTableModel<Caixa> {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
            "ID",
            "Abertura",
            "Fechamento",
            "Inicial",
            "Atual / Final",
            "Resultado",
            "Status"
    };

    /**
     * Formatter de datas.
     */
    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Define caixas da tabela.
     *
     * @param caixas Lista de caixas
     */
    public void setCaixas(
            List<Caixa> caixas
    ) {
        setRows(caixas);
    }

    /**
     * Retorna caixa da linha.
     *
     * @param row Índice da linha
     * @return Caixa encontrado
     */
    public Caixa getCaixa(
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
            Caixa caixa,
            int columnIndex
    ) {
        return switch (columnIndex) {

            case 0 ->
                    caixa.getId();

            case 1 ->
                    caixa.getDataAbertura() != null
                            ? FORMATTER.format(caixa.getDataAbertura())
                            : "-";

            case 2 ->
                    caixa.getDataFecha() != null
                            ? FORMATTER.format(caixa.getDataFecha())
                            : "-";

            case 3 ->
                    caixa.getValorAbertura();

            case 4 ->
                    caixa.getValorSaldo();

            case 5 ->
                    caixa.getValorSaldo() != null
                            && caixa.getValorAbertura() != null
                            ? caixa.getValorSaldo()
                            .subtract(caixa.getValorAbertura())
                            : BigDecimal.ZERO;

            case 6 ->
                    caixa.getStatus();

            default ->
                    null;
        };
    }
}