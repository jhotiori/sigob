package org.javapi.sigob.core.bootstrap;

import org.javapi.sigob.controller.CaixaController;
import org.javapi.sigob.controller.SaldoController;
import org.javapi.sigob.controller.VendaController;
import org.javapi.sigob.core.registry.ControllerRegistry;
import org.javapi.sigob.core.registry.ScreenRegistry;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.view.v2.context.SessionContext;
import org.javapi.sigob.view.v2.context.WindowContext;
import org.javapi.sigob.view.v2.windows.AppWindow;

/**
 * Responsável pela inicialização da aplicação.
 */
public final class ApplicationBootstrap {
    /**
     * Indica se o bootstrapping foi realizado.
     */
    private static boolean HAS_BOOTSTRAPPED = false;

    /**
     * Inicializa aplicação.
     *
     * @param funcionario - Funcionário logado
     */
    public static void bootstrap(Funcionario funcionario) {
        if (HAS_BOOTSTRAPPED) {
            return;
        }

        HAS_BOOTSTRAPPED = true;
        SessionContext.setFuncionarioLogado(funcionario);
        WindowContext.disposeCurrentWindow();

        ScreenRegistry.register();
        ControllerRegistry.register();

        AppWindow window = new AppWindow(
            new SaldoController(),
            new VendaController()
        );
        WindowContext.setCurrentWindow(window);
        window.show();

        CaixaBootstrap.bootstrap();
    }
}
