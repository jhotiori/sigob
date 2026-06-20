package org.javapi.sigob.view.v2.screens.listagem;

import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;
import org.javapi.sigob.view.v2.tables.ClienteTableModel;

/**
 * Tela de listagem de clientes.
 */
public final class ListagemClienteScreen
        extends BaseListagemScreen<Cliente> {

    /**
     * Construtor.
     */
    public ListagemClienteScreen() {
        super(
                "listagem-cliente",
                "Listagem de Clientes",
                new ClienteTableModel()
        );

        searchButton(
                "nome",
                "Buscar por Nome"
        );

        searchButton(
                "documento",
                "Buscar por Documento"
        );
    }
}
