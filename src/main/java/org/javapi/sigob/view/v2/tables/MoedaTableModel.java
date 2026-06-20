package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Moedas.
 */
public final class MoedaTableModel extends BaseEntityTableModel<Moeda> {

    /**
     * Construtor.
     */
    public MoedaTableModel() {
        super(
                new EntityTableColumn<>(
                        "Nome",
                        Moeda::getNome
                ),
                new EntityTableColumn<>(
                        "Cifrão",
                        Moeda::getCifrao
                ),
                new EntityTableColumn<>(
                        "Sigla",
                        Moeda::getSigla
                )
        );
    }
}
