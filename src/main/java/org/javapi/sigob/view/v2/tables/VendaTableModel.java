package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Vendas.
 */
public final class VendaTableModel extends BaseEntityTableModel<Venda> {

    /**
     * Construtor.
     */
    public VendaTableModel() {
        super(
                new EntityTableColumn<>(
                        "Status",
                        Venda::getStatus
                ),
                new EntityTableColumn<>(
                        "Data de Abertura",
                        Venda::getDataAbertura
                ),
                new EntityTableColumn<>(
                        "Data Finalizada",
                        Venda::getDataFinalizada
                ),
                new EntityTableColumn<>(
                        "Valor Total",
                        Venda::getValorTotal
                ),
                new EntityTableColumn<>(
                        "Cliente",
                        venda -> venda.getCliente().getNome()
                ),
                new EntityTableColumn<>(
                        "Funcionário",
                        venda -> venda.getFuncionario().getNome()
                )
        );
    }
}
