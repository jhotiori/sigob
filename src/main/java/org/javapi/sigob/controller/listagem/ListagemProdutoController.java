package org.javapi.sigob.controller.listagem;

import java.util.List;

import org.javapi.sigob.controller.listagem.base.BaseListagemController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Produto;
import org.javapi.sigob.model.service.ProdutoService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroProdutoScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemProdutoScreen;

/**
 * Controller da listagem de produtos.
 */
public final class ListagemProdutoController extends BaseListagemController<Produto, ListagemProdutoScreen> {

    /**
     * Serviço de produtos.
     *
     * @see ProdutoService
     */
    private final ProdutoService produtoService;

    /**
     * Tela de cadastro.
     *
     * @see CadastroProdutoScreen
     */
    private final CadastroProdutoScreen cadastroScreen;

    /**
     * Construtor.
     *
     * @param screen         - Tela
     * @param cadastroScreen - Tela de cadastro
     */
    public ListagemProdutoController(
            ListagemProdutoScreen screen,
            CadastroProdutoScreen cadastroScreen) {
        super(screen);

        this.produtoService = ServiceFactory.produtos();
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

                    boolean valid = UIValidation.notBlank(
                            text,
                            "O ID deve ser preenchido!");

                    if (!valid) {
                        return List.of();
                    }

                    Integer id = Integer.parseInt(text);

                    boolean positive = UIValidation.condition(
                            id > 0,
                            "O ID deve ser maior que zero!");

                    if (!positive) {
                        return List.of();
                    }

                    return produtoService.findById(id)
                            .stream()
                            .toList();
                });

        bindSearch(
                "codigo",
                () -> {
                    String text = SCREEN.searchText();

                    boolean valid = UIValidation.notBlank(
                            text,
                            "O código do produto deve ser preenchido!");

                    if (!valid) {
                        return List.of();
                    }

                    return produtoService.findByCodigo(text)
                            .stream()
                            .toList();
                });

        bindSearch(
                "nome",
                () -> {
                    String text = SCREEN.searchText();

                    boolean valid = UIValidation.notBlank(
                            text,
                            "O nome do produto deve ser preenchido!");

                    if (!valid) {
                        return List.of();
                    }

                    return produtoService.findByNome(text);
                });

        bindSearch(
                "categoria",
                () -> {
                    String text = SCREEN.searchText();

                    boolean valid = UIValidation.notBlank(
                            text,
                            "A categoria deve ser preenchida!");

                    if (!valid) {
                        return List.of();
                    }

                    return produtoService.findByCategoria(text);
                });

        bindSearch(
                "moeda",
                () -> {
                    String text = SCREEN.searchText();

                    boolean valid = UIValidation.notBlank(
                            text,
                            "A moeda deve ser preenchida!");

                    if (!valid) {
                        return List.of();
                    }

                    return produtoService.findByMoeda(text);
                });
    }

    /**
     * Busca todos os produtos.
     *
     * @return List<Produto> - Produtos encontrados
     */
    @Override
    protected List<Produto> findAll() {
        return produtoService.findAll();
    }

    /**
     * Remove produto.
     *
     * @param produto - Produto
     */
    @Override
    protected void delete(Produto produto) {
        produtoService.delete(produto);
    }

    /**
     * Realiza edição.
     *
     * @param produto - Produto
     */
    @Override
    protected void edit(Produto produto) {
        cadastroScreen.form().set(
                "codigo",
                produto.getCodigo());

        cadastroScreen.form().set(
                "nome",
                produto.getNome());

        cadastroScreen.form().set(
                "valorCompra",
                produto.getValorCompra().toString());

        cadastroScreen.form().set(
                "valorVenda",
                produto.getValorVenda().toString());

        cadastroScreen.form().setEntity(
                "categoria",
                produto.getCategoria());

        cadastroScreen.form().setEntity(
                "moeda",
                produto.getMoeda());

        ScreenContext.show(
                cadastroScreen.id());
    }

    /**
     * Mensagem de entidade não selecionada.
     *
     * @return String - Mensagem
     */
    @Override
    protected String selectEntityMessage() {
        return "Selecione um produto primeiro!";
    }

    /**
     * Mensagem de confirmação.
     *
     * @return String - Mensagem
     */
    @Override
    protected String deleteConfirmationMessage() {
        return "Deseja realmente excluir este produto?";
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String deleteSuccessMessage() {
        return "Produto excluído com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro ocorrido
     * @return String - Mensagem
     */
    @Override
    protected String deleteErrorMessage(
            Throwable e) {
        return "Erro ao excluir produto: "
                + e.getMessage();
    }
}
