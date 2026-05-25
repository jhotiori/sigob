package org.javapi.sigob.view;

import java.util.function.Supplier;

import org.javapi.sigob.view.errors.ErrorsDatabase;
import org.javapi.sigob.view.popups.Popups;

/**
 * Utilitário para execução segura de ações.
 */
public final class Actions {

    /**
     * Construtor privado.
     */
    private Actions() {
    }

    /**
     * Executa ação com tratamento automático.
     *
     * @param message - Mensagem base de erro
     * @param runnable - Ação executada
     */
    public static void safe(
            String message,
            Runnable runnable
    ) {
        try {
            runnable.run();
        } catch (Exception e) {
            Popups.error(
                    "%s: %s"
                            .formatted(
                                    message,
                                    ErrorsDatabase.message(e)
                            )
            );
        }
    }

    /**
     * Executa supplier com tratamento automático.
     *
     * @param <T> - Tipo retornado
     * @param message - Mensagem base de erro
     * @param supplier - Supplier executado
     * @return T - Resultado retornado
     */
    public static <T> T safe(
            String message,
            Supplier<T> supplier
    ) {
        return safe(
                message,
                supplier,
                null
        );
    }

    /**
     * Executa supplier com fallback.
     *
     * @param <T> - Tipo retornado
     * @param message - Mensagem base de erro
     * @param supplier - Supplier executado
     * @param fallback - Valor fallback
     * @return T - Resultado retornado
     */
    public static <T> T safe(
            String message,
            Supplier<T> supplier,
            T fallback
    ) {
        try {
            return supplier.get();
        } catch (Exception e) {
            Popups.error(
                    "%s: %s"
                            .formatted(
                                    message,
                                    ErrorsDatabase.message(e)
                            )
            );

            return fallback;
        }
    }

}
