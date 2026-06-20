package org.javapi.sigob.view.v2.screens.listagem;

import org.javapi.sigob.model.entity.Produto;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;
import org.javapi.sigob.view.v2.tables.ProdutoTableModel;

public final class ListagemProdutoScreen extends BaseListagemScreen<Produto> {
    /**
     * Construtor.
     */
    public ListagemProdutoScreen() {
        super(
            "listagem-produto",
            "Listagem de Produtos",
            new ProdutoTableModel()
        );

        searchButton(
            "nome",
            "Buscar por Nome"
        );

        searchButton(
            "codigo",
            "Buscar por Código"
        );

        searchButton(
            "categoria",
            "Buscar por Categoria"
        );

        searchButton(
            "moeda",
            "Buscar por Moeda"
        );
    }

}
