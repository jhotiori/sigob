package org.javapi.sigob.core.registry;

import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.screens.CaixaScreen;
import org.javapi.sigob.view.v2.screens.DashboardScreen;
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
 * Responsável pelo registro das telas da aplicação.
 */
public final class ScreenRegistry {

    /**
     * Registra todas as telas da aplicação.
     */
    public static void register() {
        ScreenContext.register(
            /**
             * Telas individuais
             */
            new DashboardScreen(),
            new CaixaScreen(),

            /**
             * Telas de Mercadoria
             */
            new MercadoriaCriacaoScreen(),
            new MercadoriaTransferenciaScreen(),

            /**
             * Telas de Cadastro
             */
            new CadastroAcessoScreen(),
            new CadastroCategoriaScreen(),
            new CadastroClienteScreen(),
            new CadastroFuncionarioScreen(),
            new CadastroDocumentoScreen(),
            new CadastroEstoqueScreen(),
            new CadastroProdutoScreen(),
            new CadastroMoedaScreen(),

            /**
             * Telas de Listagem
             */
            new ListagemAcessoScreen(),
            new ListagemCategoriaScreen(),
            new ListagemClienteScreen(),
            new ListagemDocumentoScreen(),
            new ListagemEstoqueScreen(),
            new ListagemFuncionarioScreen(),
            new ListagemProdutoScreen(),
            new ListagemMoedaScreen()
        );
    }

    /**
     * Construtor privado.
     */
    private ScreenRegistry() {

    }
}
