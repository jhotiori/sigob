package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Categorias.
 */
public final class CategoriaTableModel extends BaseEntityTableModel<Categoria> {

    /**
     * Construtor.
     */
    public CategoriaTableModel() {
        super(
                new EntityTableColumn<>(
                        "Nome",
                        Categoria::getNome
                )
        );
    }
}
