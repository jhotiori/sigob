package org.javapi.sigob.view.v2.screens.listagem;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;
import org.javapi.sigob.view.v2.tables.AcessoTableModel;

/**
 * Tela de listagem de acessos.
 */
public final class ListagemAcessoScreen extends BaseListagemScreen<Acesso> {

    /**
     * Construtor.
     */
    public ListagemAcessoScreen() {
        super(
                "listagem-acesso",
                "Listagem de Acessos",
                new AcessoTableModel()
        );

        searchButton(
                "nome",
                "Buscar por Nome"
        );
    }
}
