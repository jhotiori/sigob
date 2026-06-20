package org.javapi.sigob.view.v2.screens.listagem;

import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;
import org.javapi.sigob.view.v2.tables.MoedaTableModel;

/**
 * Tela de listagem de moedas.
 */
public final class ListagemMoedaScreen extends BaseListagemScreen<Moeda> {

    /**
     * Construtor.
     */
    public ListagemMoedaScreen() {
        super(
                "listagem-moeda",
                "Listagem de Moedas",
                new MoedaTableModel());

        searchButton(
                "nome",
                "Buscar por Nome");

        searchButton(
                "sigla",
                "Buscar por Sigla");

        searchButton(
                "cifrao",
                "Buscar por Cifrão");
    }
}
