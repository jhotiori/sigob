package org.javapi.sigob.view.v2.tables;

import java.util.stream.Collectors;

import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Funcionários.
 */
public final class FuncionarioTableModel extends BaseEntityTableModel<Funcionario> {

    /**
     * Construtor.
     */
    public FuncionarioTableModel() {
        super(
                new EntityTableColumn<>(
                        "Nome",
                        Funcionario::getNome
                ),
                new EntityTableColumn<>(
                        "Documento",
                        funcionario -> "(%s) %s".formatted(
                                funcionario.getDocumento().getTipo(),
                                funcionario.getDocumento().getDocumento()
                        )
                ),
                new EntityTableColumn<>(
                        "Acessos",
                        funcionario -> funcionario
                                .getAcessos()
                                .stream()
                                .map(acesso -> acesso.getNome())
                                .collect(Collectors.joining(", ")
                                )
                )
        );
    }
}
