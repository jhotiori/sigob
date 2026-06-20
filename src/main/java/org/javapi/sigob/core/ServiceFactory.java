package org.javapi.sigob.core;

import org.javapi.sigob.model.service.AcessoService;
import org.javapi.sigob.model.service.CaixaService;
import org.javapi.sigob.model.service.CategoriaService;
import org.javapi.sigob.model.service.ClienteService;
import org.javapi.sigob.model.service.DocumentoService;
import org.javapi.sigob.model.service.EstoqueService;
import org.javapi.sigob.model.service.FuncionarioService;
import org.javapi.sigob.model.service.ItemVendaService;
import org.javapi.sigob.model.service.MoedaService;
import org.javapi.sigob.model.service.ProdutoService;
import org.javapi.sigob.model.service.ProdutosEstoquesService;
import org.javapi.sigob.model.service.SaldoService;
import org.javapi.sigob.model.service.VendaService;
import org.javapi.sigob.model.service.impl.AcessoServiceImpl;
import org.javapi.sigob.model.service.impl.CaixaServiceImpl;
import org.javapi.sigob.model.service.impl.CategoriaServiceImpl;
import org.javapi.sigob.model.service.impl.ClienteServiceImpl;
import org.javapi.sigob.model.service.impl.DocumentoServiceImpl;
import org.javapi.sigob.model.service.impl.EstoqueServiceImpl;
import org.javapi.sigob.model.service.impl.FuncionarioServiceImpl;
import org.javapi.sigob.model.service.impl.ItemVendaServiceImpl;
import org.javapi.sigob.model.service.impl.MoedaServiceImpl;
import org.javapi.sigob.model.service.impl.ProdutoServiceImpl;
import org.javapi.sigob.model.service.impl.ProdutosEstoquesServiceImpl;
import org.javapi.sigob.model.service.impl.SaldoServiceImpl;
import org.javapi.sigob.model.service.impl.VendaServiceImpl;

/**
 * Fabrica centralizada para obtenção de serviços.
 */
public final class ServiceFactory {

    /**
     * Construtor privado para evitar instanciação.
     */
    private ServiceFactory() {
    }

    /**
     * Retorna o serviço de Acessos.
     *
     * @return AcessoService - Serviço de Acessos
     */
    public static AcessoService acessos() {
        return new AcessoServiceImpl();
    }

    /**
     * Retorna o serviço de Caixas.
     *
     * @return CaixaService - Serviço de Caixas
     */
    public static CaixaService caixas() {
        return new CaixaServiceImpl();
    }

    /**
     * Retorna o serviço de Categorias.
     *
     * @return CategoriaService - Serviço de Categorias
     */
    public static CategoriaService categorias() {
        return new CategoriaServiceImpl();
    }

    /**
     * Retorna o serviço de Clientes.
     *
     * @return ClienteService - Serviço de Clientes
     */
    public static ClienteService clientes() {
        return new ClienteServiceImpl();
    }

    /**
     * Retorna o serviço de Documentos.
     *
     * @return DocumentoService - Serviço de Documentos
     */
    public static DocumentoService documentos() {
        return new DocumentoServiceImpl();
    }

    /**
     * Retorna o serviço de Estoques.
     *
     * @return EstoqueService - Serviço de Estoques
     */
    public static EstoqueService estoques() {
        return new EstoqueServiceImpl();
    }

    /**
     * Retorna o serviço de Funcionários.
     *
     * @return FuncionarioService - Serviço de Funcionários
     */
    public static FuncionarioService funcionarios() {
        return new FuncionarioServiceImpl();
    }

    /**
     * Retorna o serviço de Itens de Venda.
     *
     * @return ItemVendaService - Serviço de Itens de Venda
     */
    public static ItemVendaService itensVenda() {
        return new ItemVendaServiceImpl();
    }

    /**
     * Retorna o serviço de Moedas.
     *
     * @return MoedaService - Serviço de Moedas
     */
    public static MoedaService moedas() {
        return new MoedaServiceImpl();
    }

    /**
     * Retorna o serviço de Produtos.
     *
     * @return ProdutoService - Serviço de Produtos
     */
    public static ProdutoService produtos() {
        return new ProdutoServiceImpl();
    }

    /**
     * Retorna o serviço de Produtos em Estoques.
     *
     * @return ProdutosEstoquesService - Serviço de Produtos em Estoques
     */
    public static ProdutosEstoquesService produtosEstoques() {
        return new ProdutosEstoquesServiceImpl();
    }

    /**
     * Retorna o serviço de Saldos.
     *
     * @return SaldoService - Serviço de Saldos
     */
    public static SaldoService saldos() {
        return new SaldoServiceImpl();
    }

    /**
     * Retorna o serviço de Vendas.
     *
     * @return VendaService - Serviço de Vendas
     */
    public static VendaService vendas() {
        return new VendaServiceImpl();
    }
}
