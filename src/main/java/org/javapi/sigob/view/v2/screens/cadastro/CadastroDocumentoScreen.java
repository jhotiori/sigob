package org.javapi.sigob.view.v2.screens.cadastro;

import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

/**
 * Tela de cadastro de documentos.
 */
public final class CadastroDocumentoScreen extends BaseCadastroScreen {

    /**
     * Construtor.
     */
    public CadastroDocumentoScreen() {
        super(
                "cadastro-documento",
                "Cadastro de Documentos");

        form().field(
                "Documento",
                "documento",
                UI.textField());

        form().field(
                "Tipo",
                "tipo",
                UI.textField());
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
