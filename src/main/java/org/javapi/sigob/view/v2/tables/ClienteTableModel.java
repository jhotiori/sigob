package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Clientes.
 */
public final class ClienteTableModel extends BaseEntityTableModel<Cliente> {

    /**
     * Construtor.
     */
    public ClienteTableModel() {
        super(
                new EntityTableColumn<>(
                        "Nome",
                        Cliente::getNome
                ),
                new EntityTableColumn<>(
                        "Data de Nascimento",
                        Cliente::getDataNascimento
                ),
                new EntityTableColumn<>(
                        "Documento",
                        cliente -> {
                            if (cliente.getDocumento() == null) {
                                return "[?]";
                            }

                            return "(%s) %s".formatted(
                                    cliente.getDocumento().getTipo(),
                                    cliente.getDocumento().getDocumento()
                            );
                        }
                )
        );
    }
}
