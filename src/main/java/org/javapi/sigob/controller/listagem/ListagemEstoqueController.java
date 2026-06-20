package org.javapi.sigob.controller.listagem;

import java.util.List;

import org.javapi.sigob.controller.listagem.base.BaseListagemController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.service.EstoqueService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroEstoqueScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemEstoqueScreen;

/**
 * Controller da listagem de estoques.
 */
public final class ListagemEstoqueController
        extends BaseListagemController<Estoque, ListagemEstoqueScreen> {

    /**
     * Serviço de estoques.
     *
     * @see EstoqueService
     */
    private final EstoqueService estoqueService;

    /**
     * Tela de cadastro.
     *
     * @see CadastroEstoqueScreen
     */
    private final CadastroEstoqueScreen cadastroScreen;

    /**
     * Construtor.
     *
     * @param screen         Tela
     * @param cadastroScreen Tela de cadastro
     */
    public ListagemEstoqueController(
            ListagemEstoqueScreen screen,
            CadastroEstoqueScreen cadastroScreen) {
        super(screen);

        this.estoqueService = ServiceFactory.estoques();
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

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O ID deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    Integer id = Integer.parseInt(text);

                    boolean isIdValid = UIValidation.condition(
                            id > 0,
                            "O ID deve ser maior que zero!");

                    if (!isIdValid) {
                        return List.of();
                    }

                    return estoqueService.findById(id)
                            .stream()
                            .toList();
                });

        bindSearch(
                "nome",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O nome do estoque deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return estoqueService.findByNome(text);
                });

        bindSearch(
                "codigo",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O código do estoque deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return estoqueService.findByCodigo(text);
                });
    }

    /**
     * Busca todos os estoques.
     *
     * @return List<Estoque> Estoques encontrados
     */
    @Override
    protected List<Estoque> findAll() {
        return estoqueService.findAll();
    }

    /**
     * Remove estoque.
     *
     * @param estoque Estoque
     */
    @Override
    protected void delete(
            Estoque estoque) {
        estoqueService.delete(estoque);
    }

    /**
     * Realiza edição.
     *
     * @param estoque Estoque
     */
    @Override
    protected void edit(
            Estoque estoque) {
        cadastroScreen.form().set(
                "codigo",
                estoque.getCodigo());

        cadastroScreen.form().set(
                "nome",
                estoque.getNome());

        ScreenContext.show(
                cadastroScreen.id());
    }

    /**
     * Mensagem de entidade não selecionada.
     *
     * @return String Mensagem
     */
    @Override
    protected String selectEntityMessage() {
        return "Selecione um estoque primeiro!";
    }

    /**
     * Mensagem de confirmação.
     *
     * @return String Mensagem
     */
    @Override
    protected String deleteConfirmationMessage() {
        return "Deseja realmente excluir este estoque?";
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String Mensagem
     */
    @Override
    protected String deleteSuccessMessage() {
        return "Estoque excluído com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e Erro ocorrido
     * @return String Mensagem
     */
    @Override
    protected String deleteErrorMessage(
            Throwable e) {
        return "Erro ao excluir estoque: " + e.getMessage();
    }
}
