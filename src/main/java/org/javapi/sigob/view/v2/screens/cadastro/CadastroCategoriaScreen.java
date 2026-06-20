package org.javapi.sigob.view.v2.screens.cadastro;

import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

/**
 * Tela de cadastro de categoria.
 */
public final class CadastroCategoriaScreen extends BaseCadastroScreen {
    /**
     * Construtor.
     */
    public CadastroCategoriaScreen() {
        super("cadastro-categoria", "Cadastro de Categoria");

        form().field(
            "Nome",
            "nome",
            UI.textField()
        );
    }

    /**
     * Descrição da tela.
     */
    @Override
    protected String description() {
        return """
                Cadastre Categorias para os Produtos.
                Para melhor divisão de seções, as categorias devem ser criadas.
                """;
    }
}
