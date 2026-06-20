package org.javapi.sigob.view.v2.dialogs;

import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.view.v2.dialogs.base.BaseEntityDialog;

public class VendaDialog extends BaseEntityDialog<Venda> {
    public VendaDialog() {
        super("Seleção de Venda", venda -> {
            return "[%s] %s - %s".formatted(venda.getId(), venda.getFuncionario().getNome(), venda.getCliente().getNome());
        });
    }
}
