package org.javapi.sigob.controller.listagem;

import java.util.List;

import org.javapi.sigob.controller.listagem.base.BaseListagemController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.service.AcessoService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroAcessoScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemAcessoScreen;

/**
 * Controller da listagem de acessos.
 */
public final class ListagemAcessoController extends BaseListagemController<Acesso, ListagemAcessoScreen> {

    /**
     * Serviço de acessos.
     *
     * @see AcessoService
     */
    private final AcessoService acessoService;

    /**
     * Tela de cadastro.
     *
     * @see CadastroAcessoScreen
     */
    private final CadastroAcessoScreen cadastroScreen;

    /**
     * Construtor.
     *
     * @param screen - Tela
     * @param cadastroScreen - Tela de cadastro
     */
    public ListagemAcessoController(
            ListagemAcessoScreen screen,
            CadastroAcessoScreen cadastroScreen
    ) {
        super(screen);

        this.acessoService = ServiceFactory.acessos();
        this.cadastroScreen = cadastroScreen;

        setup();
        reload();
    }

    /**
     * Registra pesquisas.
     */
    @Override
    protected void bindSearches() {
        bindSearch(
            "id",
            () -> {
                String text = SCREEN.searchText();
                boolean isTextNotBlank = UIValidation.notBlank(text, "O ID deve ser preenchido!");

                if (!isTextNotBlank) {
                    return List.of();
                }

                Integer id = Integer.parseInt(text);
                boolean isIdValid = UIValidation.condition(id > 0, "O ID deve ser maior que zero!");

                if (!isIdValid) {
                    return List.of();
                }

                return acessoService.findById(id)
                    .stream()
                    .toList();
            }
        );

        bindSearch(
            "nome",
            () -> {
                String text = SCREEN.searchText();
                boolean isTextNotBlank = UIValidation.notBlank(text, "O nome do acesso deve ser preenchido!");

                if (!isTextNotBlank) {
                    return List.of();
                }

                return acessoService.findByNome(text)
                    .stream()
                    .toList();
            }
        );
    }

    /**
     * Busca todos os acessos.
     *
     * @return List<Acesso> - Acessos encontrados
     */
    @Override
    protected List<Acesso> findAll() {
        return acessoService.findAll();
    }

    /**
     * Remove acesso.
     *
     * @param acesso - Acesso
     */
    @Override
    protected void delete(Acesso acesso) {
        acessoService.delete(acesso);
    }

    /**
     * Realiza edição.
     *
     * @param acesso - Acesso
     */
    @Override
    protected void edit(Acesso acesso) {
        cadastroScreen.form().set(
                "nome",
                acesso.getNome()
        );

        cadastroScreen.form().set(
                "descricao",
                acesso.getDescricao()
        );

        ScreenContext.show(
                cadastroScreen.id()
        );
    }

    /**
     * Mensagem de entidade não selecionada.
     *
     * @return String - Mensagem
     */
    @Override
    protected String selectEntityMessage() {
        return "Selecione um acesso primeiro!";
    }

    /**
     * Mensagem de confirmação.
     *
     * @return String - Mensagem
     */
    @Override
    protected String deleteConfirmationMessage() {
        return "Deseja realmente excluir este acesso?";
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String deleteSuccessMessage() {
        return "Acesso excluído com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro ocorrido
     * @return String - Mensagem
     */
    @Override
    protected String deleteErrorMessage(
            Throwable e
    ) {
        return "Erro ao excluir acesso: "+ e.getMessage();
    }
}
