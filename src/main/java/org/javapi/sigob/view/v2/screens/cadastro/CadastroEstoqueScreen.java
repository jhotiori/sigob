package org.javapi.sigob.view.v2.screens.cadastro;

import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

/**
 * Tela de cadastro de estoques.
 */
public final class CadastroEstoqueScreen extends BaseCadastroScreen {

    /**
     * Construtor.
     */
    public CadastroEstoqueScreen() {
        super(
                "cadastro-estoque",
                "Cadastro de Estoques");

        form().field(
                "Código",
                "codigo",
                UI.textField());

        form().field(
                "Nome",
                "nome",
                UI.textField());
    }

    /**
     * Descrição da tela.
     *
     * @return String - Descrição
     */
    @Override
    public String description() {
        return """
                Cadastre Estoques para adicionar Produtos à eles.
                Um estoque é uma unidade fisíca para armazenamento de produtos.
                """;
    }
}
