package org.javapi.sigob.controller.cadastro;

import java.math.BigDecimal;

import org.javapi.sigob.controller.base.BaseCadastroController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.model.entity.Produto;
import org.javapi.sigob.model.service.CategoriaService;
import org.javapi.sigob.model.service.MoedaService;
import org.javapi.sigob.model.service.ProdutoService;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroProdutoScreen;

/**
 * Controller de cadastro de produtos.
 */
public final class CadastroProdutoController
        extends BaseCadastroController<Produto, CadastroProdutoScreen> {

    /**
     * Serviço de produtos.
     *
     * @see ProdutoService
     */
    private final ProdutoService produtoService;

    /**
     * Serviço de categorias.
     *
     * @see CategoriaService
     */
    private final CategoriaService categoriaService;

    /**
     * Serviço de moedas.
     *
     * @see MoedaService
     */
    private final MoedaService moedaService;

    /**
     * Construtor.
     *
     * @param screen - Tela de cadastro
     */
    public CadastroProdutoController(
            CadastroProdutoScreen screen) {
        super(screen);

        this.produtoService = ServiceFactory.produtos();
        this.categoriaService = ServiceFactory.categorias();
        this.moedaService = ServiceFactory.moedas();

        loadEntities(
                SCREEN.categoriaBox(),
                categoriaService::findAll
        );

        loadEntities(
                SCREEN.moedaBox(),
                moedaService::findAll
        );

        setup();
    }

    /**
     * Salva entidade.
     *
     * @param produto - Produto
     */
    @Override
    protected void save(Produto produto) {
        produtoService.save(produto);
    }

    /**
     * Cria entidade baseada na tela.
     *
     * @return Produto - Produto criado
     */
    @Override
    protected Produto entity() {
        Produto produto = new Produto();

        produto.setCodigo(
                SCREEN.value("codigo")
        );

        produto.setNome(
                SCREEN.value("nome")
        );

        produto.setValorCompra(
                new BigDecimal(
                        SCREEN.value("valorCompra"))
                );

        produto.setValorVenda(
                new BigDecimal(
                        SCREEN.value("valorVenda"))
                );

        produto.setCategoria(
                SCREEN.entity("categoria")
        );

        produto.setMoeda(
                SCREEN.entity("moeda")
        );

        return produto;
    }

    /**
     * Valida campos da tela.
     *
     * @return boolean - Se os campos são válidos
     */
    @Override
    protected boolean validate() {
        String codigo = SCREEN.value("codigo");
        String nome = SCREEN.value("nome");
        BigDecimal valorCompra = new BigDecimal(
                SCREEN.value("valorCompra")
        );

        BigDecimal valorVenda = new BigDecimal(
                SCREEN.value("valorVenda")
        );

        boolean codigoNotBlank = UIValidation.notBlank(
                codigo,
                "O código do produto deve ser preenchido!"
        );

        boolean nomeNotBlank = UIValidation.notBlank(
                nome,
                "O nome do produto deve ser preenchido!"
        );

        boolean valorCompraValido = UIValidation.positiveNumber(
                valorCompra,
                "O valor de compra deve ser maior que zero!"
        );

        boolean valorVendaValido = UIValidation.positiveNumber(
                valorVenda,
                "O valor de venda deve ser maior que zero!"
        );

        return codigoNotBlank
                && nomeNotBlank
                && valorCompraValido
                && valorVendaValido;
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String successMessage() {
        return "Produto registrado com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro
     * @return String - Mensagem
     */
    @Override
    protected String errorMessage(Throwable e) {
        return "Erro ao registrar Produto: " + e.getMessage();
    }
}
