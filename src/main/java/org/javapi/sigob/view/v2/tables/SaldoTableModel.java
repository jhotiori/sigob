package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Saldo;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Saldos.
 */
public final class SaldoTableModel extends BaseEntityTableModel<Saldo> {

    /**
     * Construtor.
     */
    public SaldoTableModel() {
        super(
                new EntityTableColumn<>(
                        "Valor",
                        Saldo::getValorSaldo
                ),
                new EntityTableColumn<>(
                        "Descrição",
                        Saldo::getDescricao
                ),
                new EntityTableColumn<>(
                        "Tipo",
                        Saldo::getTipo
                ),
                new EntityTableColumn<>(
                        "Data",
                        Saldo::getDataSaldo
                )
        );
    }
}
