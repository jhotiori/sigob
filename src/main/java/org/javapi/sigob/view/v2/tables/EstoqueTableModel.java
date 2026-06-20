package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Estoques.
 */
public final class EstoqueTableModel extends BaseEntityTableModel<Estoque> {

    /**
     * Construtor.
     */
    public EstoqueTableModel() {
        super(
            new EntityTableColumn<>(
                "Código",
                Estoque::getCodigo
            ),

            new EntityTableColumn<>(
                "Nome",
                Estoque::getNome
            )
        );
    }
}
