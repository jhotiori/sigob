package org.javapi.sigob.view.v2.screens.cadastro;

import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

public final class CadastroClienteScreen extends BaseCadastroScreen {
    /**
     * Combobox de documentos.
     *
     * @see EntityComboBox
     */
    private final EntityComboBox<Documento> DOCUMENTO_BOX = UI.entityComboBox((documento) -> {
        return "(%s) %s".formatted(
            documento.getTipo(),
            documento.getDocumento()
        );
    });

    /**
     * Construtor.
     */
    public CadastroClienteScreen() {
        super(
            "cadastro-cliente",
            "Cadastro de Clientes"
        );

        form().field(
            "Nome",
            "nome",
            UI.textField()
        );

        form().field(
            "Data de Nascimento [dd/mm/yyyy]",
            "nascimento",
            UI.textField()
        );

        form().field(
            "Documento",
            "documento",
            DOCUMENTO_BOX
        );
    }

    /**
     * Retorna caixa de documentos.
     *
     * @return EntityComboBox<Documento> - Caixa de documentos
     */
    public EntityComboBox<Documento> box() {
        return DOCUMENTO_BOX;
    }

    /**
     * Descrição da tela.
     */
    @Override
    protected String description() {
        return """
                Cadastre Clientes para realizar compras e manter controle.
                Os clientes são usados para identificar compradores no sistema.
                """;
    }
}
