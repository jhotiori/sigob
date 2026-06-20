package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

public final class DocumentoTableModel extends BaseEntityTableModel<Documento> {
    /**
     * Construtor.
     */
    public DocumentoTableModel() {
        super(
                new EntityTableColumn<>(
                        "Tipo",
                        documento -> documento.getTipo()
                    ),
                new EntityTableColumn<>(
                        "Documento",
                        documento -> documento.getDocumento()
                    )
            );
    }
}
