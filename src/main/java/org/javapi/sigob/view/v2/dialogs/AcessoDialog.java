package org.javapi.sigob.view.v2.dialogs;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.view.v2.dialogs.base.BaseEntityDialog;

public class AcessoDialog extends BaseEntityDialog<Acesso> {
    /**
     * Construtor do dialog de Documento.
     *
     * @return DocumentoDialog - Dialog de Documento
     */
    public AcessoDialog() {
        super("Seleção de Acesso", acesso -> {
            return acesso.getNome();
        });
    }
}
