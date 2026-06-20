package org.javapi.sigob.core.registry;

import org.javapi.sigob.controller.CaixaController;
import org.javapi.sigob.controller.cadastro.CadastroAcessoController;
import org.javapi.sigob.controller.cadastro.CadastroCategoriaController;
import org.javapi.sigob.controller.cadastro.CadastroClienteController;
import org.javapi.sigob.controller.cadastro.CadastroDocumentoController;
import org.javapi.sigob.controller.cadastro.CadastroEstoqueController;
import org.javapi.sigob.controller.cadastro.CadastroFuncionarioController;
import org.javapi.sigob.controller.cadastro.CadastroMoedaController;
import org.javapi.sigob.controller.cadastro.CadastroProdutoController;
import org.javapi.sigob.controller.listagem.ListagemAcessoController;
import org.javapi.sigob.controller.listagem.ListagemCategoriaController;
import org.javapi.sigob.controller.listagem.ListagemClienteController;
import org.javapi.sigob.controller.listagem.ListagemDocumentoController;
import org.javapi.sigob.controller.listagem.ListagemEstoqueController;
import org.javapi.sigob.controller.listagem.ListagemFuncionarioController;
import org.javapi.sigob.controller.listagem.ListagemMoedaController;
import org.javapi.sigob.controller.listagem.ListagemProdutoController;
import org.javapi.sigob.controller.mercadoria.MercadoriaCriacaoController;
import org.javapi.sigob.controller.mercadoria.MercadoriaTransferenciaController;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.screens.CaixaScreen;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroAcessoScreen;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroCategoriaScreen;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroClienteScreen;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroDocumentoScreen;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroEstoqueScreen;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroFuncionarioScreen;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroMoedaScreen;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroProdutoScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemAcessoScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemCategoriaScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemClienteScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemDocumentoScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemEstoqueScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemFuncionarioScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemMoedaScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemProdutoScreen;
import org.javapi.sigob.view.v2.screens.mercadoria.MercadoriaCriacaoScreen;
import org.javapi.sigob.view.v2.screens.mercadoria.MercadoriaTransferenciaScreen;

/**
 * Responsável pelo registro dos controllers da aplicação.
 */
public final class ControllerRegistry {

    /**
     * Registra todos os controllers da aplicação.
     */
    @SuppressWarnings("unused")
    public static void register() {
        /**
         * Controllers individuais.
         */
        CaixaController caixaController = new CaixaController(
            (CaixaScreen) ScreenContext.get("caixa")
        );

        /**
         * Controllers de mercadoria.
         */
        MercadoriaCriacaoController mercadoriaCriacaoController = new MercadoriaCriacaoController(
            (MercadoriaCriacaoScreen) ScreenContext.get("mercadoria-criacao")
        );

        MercadoriaTransferenciaController mercadoriaTransferenciaController = new MercadoriaTransferenciaController(
            (MercadoriaTransferenciaScreen) ScreenContext.get("mercadoria-transferencia")
        );

        /**
         * Controllers de Acesso.
         */
        CadastroAcessoController cadastroAcessoController = new CadastroAcessoController(
            (CadastroAcessoScreen) ScreenContext.get("cadastro-acesso")
        );

        CadastroCategoriaController cadastroCategoriaController = new CadastroCategoriaController(
            (CadastroCategoriaScreen) ScreenContext.get("cadastro-categoria")
        );

        CadastroClienteController cadastroClienteController = new CadastroClienteController(
            (CadastroClienteScreen) ScreenContext.get("cadastro-cliente")
        );

        CadastroFuncionarioController cadastroFuncionarioController = new CadastroFuncionarioController(
            (CadastroFuncionarioScreen) ScreenContext.get("cadastro-funcionario")
        );

        CadastroDocumentoController cadastroDocuumentoController = new CadastroDocumentoController(
            (CadastroDocumentoScreen) ScreenContext.get("cadastro-documento")
        );

        CadastroEstoqueController cadastroEstoqueController = new CadastroEstoqueController(
            (CadastroEstoqueScreen) ScreenContext.get("cadastro-estoque")
        );

        CadastroProdutoController cadastroProdutoController = new CadastroProdutoController(
            (CadastroProdutoScreen) ScreenContext.get("cadastro-produto")
        );

        CadastroMoedaController cadastroMoedaController = new CadastroMoedaController(
            (CadastroMoedaScreen) ScreenContext.get("cadastro-moeda")
        );

        /**
         * Controllers de Listagem.
         */
        ListagemAcessoController listagemAcessoController = new ListagemAcessoController(
            (ListagemAcessoScreen) ScreenContext.get("listagem-acesso"),
            (CadastroAcessoScreen) ScreenContext.get("cadastro-acesso")
        );

        ListagemCategoriaController listagemCategoriaController = new ListagemCategoriaController(
            (ListagemCategoriaScreen) ScreenContext.get("listagem-categoria"),
            (CadastroCategoriaScreen) ScreenContext.get("cadastro-categoria")
        );

        ListagemClienteController listagemClienteController = new ListagemClienteController(
            (ListagemClienteScreen) ScreenContext.get("listagem-cliente"),
            (CadastroClienteScreen) ScreenContext.get("cadastro-cliente")
        );

        ListagemDocumentoController listagemDocumentoController = new ListagemDocumentoController(
            (ListagemDocumentoScreen) ScreenContext.get("listagem-documento"),
            (CadastroDocumentoScreen) ScreenContext.get("cadastro-documento")
        );

        ListagemEstoqueController listagemEstoqueController = new ListagemEstoqueController(
            (ListagemEstoqueScreen) ScreenContext.get("listagem-estoque"),
            (CadastroEstoqueScreen) ScreenContext.get("cadastro-estoque")
        );

        ListagemFuncionarioController listagemFuncionarioController = new ListagemFuncionarioController(
            (ListagemFuncionarioScreen) ScreenContext.get("listagem-funcionario"),
            (CadastroFuncionarioScreen) ScreenContext.get("cadastro-funcionario")
        );

        ListagemProdutoController listagemProdutoController = new ListagemProdutoController(
            (ListagemProdutoScreen) ScreenContext.get("listagem-produto"),
            (CadastroProdutoScreen) ScreenContext.get("cadastro-produto")
        );

        ListagemMoedaController listagemMoedaController = new ListagemMoedaController(
            (ListagemMoedaScreen) ScreenContext.get("listagem-moeda"),
            (CadastroMoedaScreen) ScreenContext.get("cadastro-moeda")
        );
    }

    /**
     * Construtor privado.
     */
    private ControllerRegistry() {

    }
}
