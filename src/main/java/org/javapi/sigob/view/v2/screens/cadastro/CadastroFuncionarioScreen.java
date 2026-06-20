package org.javapi.sigob.view.v2.screens.cadastro;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.view.v2.framework.components.entity.EntityCheckList;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

/**
 * Tela de cadastro de funcionários.
 */
public final class CadastroFuncionarioScreen extends BaseCadastroScreen {

    /**
     * Caixa de documentos.
     *
     * @see EntityComboBox
     */
    private final EntityComboBox<Documento> DOCUMENTO_BOX = UI.entityComboBox(documento -> {
        return "(%s) %s".formatted(
                documento.getTipo(),
                documento.getDocumento()
            );
    });

    /**
     * Lista de acessos.
     *
     * @see EntityCheckList
     */
    private final EntityCheckList<Acesso> ACESSOS_LIST = UI.entityCheckList(acesso -> {
        return acesso.getNome();
    });

    /**
     * Construtor.
     */
    public CadastroFuncionarioScreen() {
        super(
                "cadastro-funcionario",
                "Cadastro de Funcionários"
            );

        form().field(
                "Nome",
                "nome",
                UI.textField()
            );

        form().field(
                "Código",
                "codigo",
                UI.passwordField()
            );

        form().field(
                "Documento",
                "documento",
                DOCUMENTO_BOX
            );

        form().field(
                "Acessos",
                "acessos",
                ACESSOS_LIST
            );
    }

    /**
     * Descrição da tela.
     *
     * @return String - Descrição
     */
    @Override
    protected String description() {
        return """
            Registre um novo Funcionário no sistema.
            Um funcionario deve obrigatoriamente ter um documento cadastrado.
            """;
    }

    /**
     * Retorna caixa de documentos.
     *
     * @return EntityComboBox<Documento> - Caixa de documentos
     */
    public EntityComboBox<Documento> documentoBox() {
        return DOCUMENTO_BOX;
    }

    /**
     * Retorna lista de acessos.
     *
     * @return EntityCheckList<Acesso> - Lista de acessos
     */
    public EntityCheckList<Acesso> acessosList() {
        return ACESSOS_LIST;
    }
}
