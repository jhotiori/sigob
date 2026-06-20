package org.javapi.sigob.view.v2.screens.listagem;

import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;
import org.javapi.sigob.view.v2.tables.FuncionarioTableModel;

/**
 * Tela de listagem de funcionários.
 */
public final class ListagemFuncionarioScreen
        extends BaseListagemScreen<Funcionario> {

    /**
     * Construtor.
     */
    public ListagemFuncionarioScreen() {
        super(
                "listagem-funcionario",
                "Listagem de Funcionários",
                new FuncionarioTableModel());

        searchButton(
                "nome",
                "Buscar por Nome");

        searchButton(
                "documento",
                "Buscar por Documento"
        );

        searchButton(
                "acesso",
                "Buscar por Acesso");
    }
}
