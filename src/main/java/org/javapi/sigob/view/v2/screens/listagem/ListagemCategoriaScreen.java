package org.javapi.sigob.view.v2.screens.listagem;

import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;
import org.javapi.sigob.view.v2.tables.CategoriaTableModel;

/**
 * Tela de listagem de categorias.
 */
public final class ListagemCategoriaScreen
        extends BaseListagemScreen<Categoria> {

    /**
     * Construtor.
     */
    public ListagemCategoriaScreen() {
        super(
                "listagem-categoria",
                "Listagem de Categorias",
                new CategoriaTableModel());

        searchButton(
                "nome",
                "Buscar por Nome"
        );
    }
}
