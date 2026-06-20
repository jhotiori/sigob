package org.javapi.sigob.view.v2.screens.cadastro;

import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

/**
 * Tela de cadastro de acesso.
 */
public final class CadastroAcessoScreen extends BaseCadastroScreen {
    /**
     * Construtor.
     */
    public CadastroAcessoScreen() {
        super("cadastro-acesso", "Cadastro de Acessos");

        form().field(
            "Nome",
            "nome",
            UI.textField()
        );

        form().field(
            "Descrição",
            "descricao",
            UI.textField()
        );
    }

    /**
     * Descrição da tela.
     */
    @Override
    protected String description() {
        return """
        Cadastre acessos para os funcionários do sistema.
        Os acessos são usados para controlar as permissões perante funcionalidades.
        """;
    }
}
