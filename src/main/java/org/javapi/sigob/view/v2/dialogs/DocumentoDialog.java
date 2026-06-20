package org.javapi.sigob.view.v2.dialogs;


import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.view.v2.dialogs.base.BaseEntityDialog;

public class DocumentoDialog extends BaseEntityDialog<Documento> {
    /**
     * Construtor do dialog de Documento.
     *
     * @return DocumentoDialog - Dialog de Documento
     */
    public DocumentoDialog() {
        super("Seleção de Documento", documento -> {
            return "(%s) %s".formatted(
                    documento.getTipo(),
                    documento.getDocumento()
            );
        });
    }

}
