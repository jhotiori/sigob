package org.javapi.sigob.view.v2.dialogs;

import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.view.v2.dialogs.base.BaseEntityDialog;

public class ClienteDialog extends BaseEntityDialog<Cliente> {
    public ClienteDialog() {
        super("Seleção de Cliente", cliente -> {
            return cliente.getNome();
        });
    }
}
