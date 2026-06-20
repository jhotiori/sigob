package org.javapi.sigob.controller.cadastro;

import org.javapi.sigob.controller.base.BaseCadastroController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.service.AcessoService;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroAcessoScreen;

/**
 * Controller de Cadastro de Acessos.
 */
public final class CadastroAcessoController extends BaseCadastroController<Acesso, CadastroAcessoScreen> {
    /**
     * Serviço de Acessos.
     *
     * @see AcessoService
     */
    private final AcessoService acessoService;

    /**
     * Construtor dp Controller de Acessos.
     * @param screen
     */
    public CadastroAcessoController(CadastroAcessoScreen screen) {
        super(screen);
        this.acessoService = ServiceFactory.acessos();
        setup();
    }

    /**
     * Salva entidade no banco de dados.
     */
    @Override
    protected void save(Acesso acesso) {
        acessoService.save(acesso);
    }

    /**
     * Cria entidade de acesso.
     *
     * @return Acesso - Novo acesso criado
     */
    @Override
    protected Acesso entity() {
        Acesso acesso = new Acesso();
        acesso.setNome(SCREEN.value("nome"));
        acesso.setDescricao(SCREEN.value("descricao"));
        return acesso;
    }

    /**
     * Valida os campos da tela.
     */
    @Override
    protected boolean validate() {
        String nome = SCREEN.value("nome");
        String descricao = SCREEN.value("descricao");

        boolean nomeNotBlank = UIValidation.notBlank(nome, "O nome do acesso deve ser preenchido!");
        boolean descricaoNotBlank = UIValidation.notBlank(descricao, "A descrição do acesso deve ser preenchida!");

        return nomeNotBlank && descricaoNotBlank;
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String successMessage() {
        return "Acesso criado com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @return String - Mensagem
     */
    @Override
    protected String errorMessage(Throwable e) {
        return "Erro ao criar Acesso: " + e.getMessage();
    }
}
