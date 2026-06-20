package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Acessos.
 */
public final class AcessoTableModel extends BaseEntityTableModel<Acesso> {
    /**
     * Construtor.
     */
    public AcessoTableModel() {
        super(
            new EntityTableColumn<>(
                "Nome",
                Acesso::getNome
            ),

            new EntityTableColumn<>(
                "Descrição",
                Acesso::getDescricao
            )
        );
    }
}
