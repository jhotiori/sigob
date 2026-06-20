package org.javapi.sigob.view.v2.screens.cadastro;

import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

/**
 * Tela de cadastro de moedas.
 */
public final class CadastroMoedaScreen extends BaseCadastroScreen {

    /**
     * Construtor.
     */
    public CadastroMoedaScreen() {
        super(
                "cadastro-moeda",
                "Cadastro de Moedas");

        form().field(
                "Nome",
                "nome",
                UI.textField());

        form().field(
                "Cifrão",
                "cifrao",
                UI.textField());

        form().field(
                "Sigla",
                "sigla",
                UI.textField());
    }

    /**
     * Descrição da tela.
     */
    @Override
    protected String description() {
        return """
                Cadastre moedas utilizadas pelo sistema.
                As moedas são usadas para identificar valores monetários
                em produtos, saldos, caixas e vendas.
                """;
    }
}
