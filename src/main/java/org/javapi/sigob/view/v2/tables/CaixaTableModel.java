package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Caixa;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Caixas.
 */
public final class CaixaTableModel extends BaseEntityTableModel<Caixa> {

    /**
     * Construtor.
     */
    public CaixaTableModel() {
        super(
                new EntityTableColumn<>(
                        "Valor de Abertura",
                        Caixa::getValorAbertura
                ),
                new EntityTableColumn<>(
                        "Valor em Saldo",
                        Caixa::getValorSaldo
                ),
                new EntityTableColumn<>(
                        "Valor de Fechamento",
                        Caixa::getValorFechamento
                ),
                new EntityTableColumn<>(
                        "Status",
                        Caixa::getStatus
                ),
                new EntityTableColumn<>(
                        "Data de Abertura",
                        Caixa::getDataAbertura
                ),
                new EntityTableColumn<>(
                        "Data de Fechamento",
                        Caixa::getDataFechamento
                )
        );
    }
}
