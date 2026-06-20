package org.javapi.sigob.view.v2.screens.listagem;

import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;
import org.javapi.sigob.view.v2.tables.EstoqueTableModel;

/**
 * Tela de listagem de estoques.
 */
public final class ListagemEstoqueScreen extends BaseListagemScreen<Estoque> {

    /**
     * Construtor.
     */
    public ListagemEstoqueScreen() {
        super(
                "listagem-estoque",
                "Listagem de Estoques",
                new EstoqueTableModel());

        searchButton(
                "nome",
                "Buscar por Nome");

        searchButton(
                "codigo",
                "Buscar por Código");
    }
}
