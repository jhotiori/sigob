package org.javapi.sigob.view.v2.dialogs;

import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.view.v2.dialogs.base.BaseEntityDialog;

public class FuncionarioDialog extends BaseEntityDialog<Funcionario> {
    public FuncionarioDialog() {
        super("Seleção de Funcionario", funcionario -> {
            return funcionario.getNome();
        });
    }
}
