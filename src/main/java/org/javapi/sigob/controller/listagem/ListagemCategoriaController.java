package org.javapi.sigob.controller.listagem;

import java.util.List;

import org.javapi.sigob.controller.listagem.base.BaseListagemController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.model.service.CategoriaService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroCategoriaScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemCategoriaScreen;

/**
 * Controller da listagem de categorias.
 */
public final class ListagemCategoriaController extends BaseListagemController<Categoria, ListagemCategoriaScreen> {

    /**
     * Serviço de categorias.
     */
    private final CategoriaService categoriaService;

    /**
     * Tela de cadastro.
     */
    private final CadastroCategoriaScreen cadastroScreen;

    /**
     * Construtor.
     *
     * @param screen         - Tela
     * @param cadastroScreen - Tela de cadastro
     */
    public ListagemCategoriaController(
            ListagemCategoriaScreen screen,
            CadastroCategoriaScreen cadastroScreen) {
        super(screen);

        this.categoriaService = ServiceFactory.categorias();
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

                    return categoriaService.findById(id)
                            .stream()
                            .toList();
                });

        bindSearch(
                "nome",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O nome da categoria deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return categoriaService.findByNome(text);
                });
    }

    /**
     * Busca todas as categorias.
     *
     * @return List<Categoria> - Categorias
     */
    @Override
    protected List<Categoria> findAll() {
        return categoriaService.findAll();
    }

    /**
     * Remove categoria.
     *
     * @param categoria - Categoria
     */
    @Override
    protected void delete(Categoria categoria) {
        categoriaService.delete(categoria);
    }

    /**
     * Realiza edição.
     *
     * @param categoria - Categoria
     */
    @Override
    protected void edit(Categoria categoria) {

        cadastroScreen.form().set(
                "nome",
                categoria.getNome());

        ScreenContext.show(
                cadastroScreen.id());
    }

    /**
     * Mensagem de seleção.
     *
     * @return String - Mensagem
     */
    @Override
    protected String selectEntityMessage() {
        return "Selecione uma categoria primeiro!";
    }

    /**
     * Mensagem de confirmação.
     *
     * @return String - Mensagem
     */
    @Override
    protected String deleteConfirmationMessage() {
        return "Deseja realmente excluir esta categoria?";
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String deleteSuccessMessage() {
        return "Categoria excluída com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro
     * @return String - Mensagem
     */
    @Override
    protected String deleteErrorMessage(
            Throwable e) {
        return "Erro ao excluir categoria: "
                + e.getMessage();
    }
}
