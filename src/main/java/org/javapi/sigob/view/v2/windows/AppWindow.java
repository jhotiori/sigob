package org.javapi.sigob.view.v2.windows;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.javapi.sigob.controller.SaldoController;
import org.javapi.sigob.controller.VendaController;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.v2.context.CaixaContext;
import org.javapi.sigob.view.v2.context.PermissionContext;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.base.BaseWindow;
import org.javapi.sigob.view.v2.framework.layouts.menu.MenuBarBuilder;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.framework.ui.UIResources;
import org.javapi.sigob.view.v2.framework.ui.UIWindows;

public final class AppWindow extends BaseWindow {
    /**
     * Icone da janela.
     */
    private final Image ICON = UIResources.image(Settings.APP_ICON_PATH);

    /**
     * Controller de Saldos.
     */
    private final SaldoController saldoController;

    /**
     * Controller de Vendas.
     */
    private final VendaController vendaController;

    /**
     * Construtor da janela.
     */
    public AppWindow(SaldoController saldoController, VendaController vendaController) {
        this.saldoController = saldoController;
        this.vendaController = vendaController;
    }

    /**
     * Constrói a janela.
     *
     * @return JFrame - Janela
     */
    @Override
    protected JFrame build() {
        return UIWindows.create()
            .title(Settings.APP_WINDOW_TITLE)
            .minimumSize(Settings.APP_MIN_WIDTH, Settings.APP_MIN_HEIGHT)
            .maximumSize(Settings.APP_MAX_WIDTH, Settings.APP_MAX_HEIGHT)
            .icon(ICON)
            .menuBar(buildMenuBar())
            .content(ScreenContext.panel())
            .center()
            .resizable(true)
            .build();
    }

    /**
     * Constrói barra de menu.
     *
     * @return JMenuBar - Barra de menu
     */
    private JMenuBar buildMenuBar() {

        MenuBarBuilder menu = UI.menubar();

        menu.button(
                "Dashboard",
                () -> ScreenContext.show("dashboard"));

        if (hasAccess("CAIXA")) {

            menu.button(
                    "Caixa",
                    () -> ScreenContext.show("caixa"));
        }

        if (hasAccess("CAIXA")) {

            menu.menu(
                    "Saldos",
                    saldo -> {

                        saldo.item(
                                "Depositar",
                                () -> {

                                    if (requireCaixaAberto()) {
                                        saldoController.depositar();
                                    }
                                });

                        saldo.item(
                                "Sacar",
                                () -> {

                                    if (requireCaixaAberto()) {
                                        saldoController.sacar();
                                    }
                                });
                    });
        }

        if (hasAccess("CAIXA")) {

            menu.menu(
                    "Vendas",
                    venda -> {

                        venda.item(
                                "Iniciar",
                                () -> {

                                    if (requireCaixaAberto()) {
                                        vendaController.iniciar();
                                    }
                                });

                        venda.item(
                                "Continuar",
                                () -> {

                                    if (requireCaixaAberto()) {
                                        vendaController.continuar();
                                    }
                                });
                    });
        }

        if (hasAccess("CAIXA")) {

            menu.menu(
                    "Cadastros",
                    cadastro -> {

                        cadastro.item(
                                "Acessos",
                                () -> ScreenContext.show("cadastro-acesso"));

                        cadastro.item(
                                "Categorias",
                                () -> ScreenContext.show("cadastro-categoria"));

                        cadastro.item(
                                "Clientes",
                                () -> ScreenContext.show("cadastro-cliente"));

                        cadastro.item(
                                "Funcionarios",
                                () -> ScreenContext.show("cadastro-funcionario"));

                        cadastro.item(
                                "Documentos",
                                () -> ScreenContext.show("cadastro-documento"));

                        cadastro.item(
                                "Estoques",
                                () -> ScreenContext.show("cadastro-estoque"));

                        cadastro.item(
                                "Produtos",
                                () -> ScreenContext.show("cadastro-produto"));

                        cadastro.item(
                                "Moedas",
                                () -> ScreenContext.show("cadastro-moeda"));
                    });
        }

        if (hasAccess("FUNCIONARIO")) {

            menu.menu(
                    "Listagens",
                    listagem -> {

                        listagem.item(
                                "Acessos",
                                () -> ScreenContext.show("listagem-acesso"));

                        listagem.item(
                                "Categorias",
                                () -> ScreenContext.show("listagem-categoria"));

                        listagem.item(
                                "Clientes",
                                () -> ScreenContext.show("listagem-cliente"));

                        listagem.item(
                                "Funcionarios",
                                () -> ScreenContext.show("listagem-funcionario"));

                        listagem.item(
                                "Documentos",
                                () -> ScreenContext.show("listagem-documento"));

                        listagem.item(
                                "Estoques",
                                () -> ScreenContext.show("listagem-estoque"));

                        listagem.item(
                                "Produtos",
                                () -> ScreenContext.show("listagem-produto"));

                        listagem.item(
                                "Moedas",
                                () -> ScreenContext.show("listagem-moeda"));

                        listagem.item(
                                "Vendas",
                                () -> ScreenContext.show("listagem-venda"));

                        listagem.item(
                                "Saldos",
                                () -> ScreenContext.show("listagem-saldo"));
                    });
        }

        if (hasAccess("ESTOQUE")) {

            menu.menu(
                    "Mercadorias",
                    mercadoria -> {

                        mercadoria.item(
                                "Criação",
                                () -> ScreenContext.show("mercadoria-criacao"));

                        mercadoria.item(
                                "Transferência",
                                () -> ScreenContext.show("mercadoria-transferencia"));
                    });
        }

        return menu.build();
    }

    /**
     * Verifica se o usuário tem acesso ao recurso.
     *
     * @param acesso - O recurso
     * @return boolean - Se o usuário tem acesso
     */
    private boolean hasAccess(String acesso) {
        return PermissionContext.has(acesso);
    }

    /**
     * Verifica se o caixa está aberto.
     *
     * @return boolean - Se o caixa está aberto
     */
    private boolean requireCaixaAberto() {

        if (!CaixaContext.isCaixaAberto()) {

            UIDialogs.warn(
                    "É necessário possuir um caixa aberto!"
            );

            return false;
        }

        return true;
    }
}
