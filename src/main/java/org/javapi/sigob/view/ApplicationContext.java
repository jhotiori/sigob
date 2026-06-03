package org.javapi.sigob.view;

import java.util.List;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Caixa;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.service.*;
import org.javapi.sigob.view.windows.ApplicationWindow;

/**
 * Contexto central da aplicação.
 */
public final class ApplicationContext {

    /**
     * Funcionario atualmente logado.
     *
     * @see {@link Funcionario}
     */
    private static Funcionario funcionarioLogado;

    /**
     * Janela principal.
     *
     * @see {@link ApplicationWindow}
     */
    private static ApplicationWindow window;

    /**
     * Serviço de acesso.
     *
     * @see {@link AcessoService}
     */
    private static final AcessoService acessoService = new AcessoService();

    /**
     * Serviço de categorias.
     *
     * @see {@link CategoriaService}
     */
    private static final CategoriaService categoriaService = new CategoriaService();

    /**
     * Serviço de clientes.
     *
     * @see {@link ClienteService}
     */
    private static final ClienteService clienteService = new ClienteService();

    /**
     * Serviço de documentos.
     *
     * @see {@link DocumentoService}
     */
    private static final DocumentoService documentoService = new DocumentoService();

    /**
     * Serviço de estoque.
     *
     * @see {@link EstoqueService}
     */
    private static final EstoqueService estoqueService = new EstoqueService();

    /**
     * Serviço de funcionários.
     *
     * @see {@link FuncionarioService}
     */
    private static final FuncionarioService funcionarioService = new FuncionarioService();

    /**
     * Serviço de itens de venda.
     *
     * @see {@link ItemVendaService}
     */
    private static final ItemVendaService itemVendaService = new ItemVendaService();

    /**
     * Serviço de moedas.
     *
     * @see {@link MoedaService}
     */
    private static final MoedaService moedaService = new MoedaService();

    /**
     * Serviço de produtos.
     *
     * @see {@link ProdutoService}
     */
    private static final ProdutoService produtoService = new ProdutoService();

    /**
     * Serviço de produtos em estoque.
     *
     * @see {@link ProdutosEstoquesService}
     */
    private static final ProdutosEstoquesService produtosEstoquesService = new ProdutosEstoquesService();

    /**
     * Serviço de vendas.
     *
     * @see {@link VendaService}
     */
    private static final VendaService vendaService = new VendaService();

    /**
     * Serviço de Caixa.
     *
     * @see {@link CaixaService}
     */
    private static final CaixaService caixaService = new CaixaService();

    /**
     * Serviço de Saldo.
     *
     * @see {@link SaldoService}
     */
    private static final SaldoService saldoService = new SaldoService();

    /**
     * Impede instanciação.
     */
    private ApplicationContext() {
    }

    /**
     * Define funcionário logado.
     *
     * @param funcionarioLogado - Funcionario logado
     */
    public static void setFuncionarioLogado(Funcionario funcionarioLogado) {
        ApplicationContext.funcionarioLogado = funcionarioLogado;
    }

    /**
     * Retorna funcionário logado.
     *
     * @return Funcionario - Funcionario logado
     */
    public static Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    /**
     * Retorna acessos do funcionário logado.
     *
     * @return List<Acesso> - Acessos do funcionário logado
     */
    public static List<Acesso> getFuncionarioAcessos() {
        return funcionarioLogado != null ? funcionarioLogado.getAcessos().stream().toList() : null;
    }

    /**
     * Verifica se o funcionário logado possui acesso.
     *
     * @param acesso - Acesso
     * @return boolean - Se possui acesso
     */
    public static boolean hasFuncionarioAcesso(String acesso) {
        List<Acesso> acessos = getFuncionarioAcessos();
        if (acessos == null) {
            return false;
        }
        
        return acessos
            .stream()
            .anyMatch(a -> a.getNome().toLowerCase().equals(acesso.toLowerCase()));
    }

    /**
     * Define janela principal.
     *
     * @param window - Janela principal
     */
    public static void setWindow(ApplicationWindow window) {
        ApplicationContext.window = window;
    }

    /**
     * Retorna janela principal.
     *
     * @return BaseWindow - Janela principal
     */
    public static ApplicationWindow getWindow() {
        return window;
    }

    /**
     * Retorna serviço de acesso.
     *
     * @return AcessoService - Serviço de acesso
     */
    public static AcessoService getAcessoService() {
        return acessoService;
    }

    /**
     * Retorna serviço de categorias.
     *
     * @return CategoriaService - Serviço de categorias
     */
    public static CategoriaService getCategoriaService() {
        return categoriaService;
    }

    /**
     * Retorna serviço de clientes.
     *
     * @return ClienteService - Serviço de clientes
     */
    public static ClienteService getClienteService() {
        return clienteService;
    }

    /**
     * Retorna serviço de documentos.
     *
     * @return DocumentoService - Serviço de documentos
     */
    public static DocumentoService getDocumentoService() {
        return documentoService;
    }

    /**
     * Retorna serviço de estoque.
     *
     * @return EstoqueService - Serviço de estoque
     */
    public static EstoqueService getEstoqueService() {
        return estoqueService;
    }

    /**
     * Retorna serviço de funcionários.
     *
     * @return FuncionarioService - Serviço de funcionários
     */
    public static FuncionarioService getFuncionarioService() {
        return funcionarioService;
    }

    /**
     * Retorna serviço de itens de venda.
     *
     * @return ItemVendaService - Serviço de itens de venda
     */
    public static ItemVendaService getItemVendaService() {
        return itemVendaService;
    }

    /**
     * Retorna serviço de moedas.
     *
     * @return MoedaService - Serviço de moedas
     */
    public static MoedaService getMoedaService() {
        return moedaService;
    }

    /**
     * Retorna serviço de produtos.
     *
     * @return ProdutoService - Serviço de produtos
     */
    public static ProdutoService getProdutoService() {
        return produtoService;
    }

    /**
     * Retorna serviço de produtos em estoque.
     *
     * @return ProdutosEstoquesService - Serviço de produtos em estoque
     */
    public static ProdutosEstoquesService getProdutosEstoquesService() {
        return produtosEstoquesService;
    }

    /**
     * Retorna serviço de vendas.
     *
     * @return VendaService - Serviço de vendas
     */
    public static VendaService getVendaService() {
        return vendaService;
    }

    /**
     * Retorna serviço de caixa.
     *
     * @return CaixaService - Serviço de caixa
     */
    public static CaixaService getCaixaService() {
        return caixaService;
    }

    /**
     * Retorna serviço de caixa.
     *
     * @return CaixaService - Serviço de caixa
     */
    public static SaldoService getSaldoService() {
        return saldoService;
    }
}
