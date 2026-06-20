package org.javapi.sigob;

import org.javapi.sigob.core.bootstrap.LoginBootstrap;
import org.javapi.sigob.core.bootstrap.SwingBootstrap;
import org.javapi.sigob.model.persistence.DatabaseMigrator;
import org.javapi.sigob.view.v2.context.WindowContext;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;

public class Main {
    /**
     * Ponto de entrada da aplicação.
     */
    public static void main(String[] args) {
        /**
         * Realiza migração do banco de dados. [PGSQL]
         */
        bootstrap(
            DatabaseMigrator::migrate,
            "Erro ao realizar Migração do Banco de Dados:\n%s"
        );

        /**
         * Aplica estilos globais.
         * Usado após o LAF para ter prioridade.
         */
        bootstrap(
            SwingBootstrap::bootstrap,
            "Erro ao realizar Bootstrap de Swing:\n%s"
        );

        /**
         * Realiza bootstrap de login.
         * Atualiza contexto da aplicação e exibe tela.
         */
        bootstrap(
            LoginBootstrap::bootstrap,
            "Erro ao realizar Bootstrap de Loin:\n%s"
        );
    }

    /**
     * Usado internamente para dar exit na aplicação.
     * Fecha a window (janela) atual e usa System.exit() para encerrar o processo.
     */
    private static void exit() {
        WindowContext.disposeCurrentWindow();
        System.exit(1);
    }

    /**
     * Realiza bootstrap de forma segura e sai da aplicação caso ocorra algum erro.
     *
     * @param runnable     - Função a ser executada
     * @param errorMessage - Mensagem de erro
     */
    private static void bootstrap(Runnable runnable, String errorMessage) {
        try {
            runnable.run();
        } catch (Exception error) {
            UIDialogs.error(errorMessage.formatted(error.getMessage()));
            exit();
        }
    }
}
