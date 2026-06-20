package org.javapi.sigob.view.v2.framework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SwingUtilities;

/**
 * Utilitário para execução assíncrona.
 */
public final class Async {

    /**
     * Executor virtual compartilhado.
     */
    private static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * Construtor privado para evitar instanciação.
     */
    private Async() {
    }

    /**
     * Executa tarefa assíncrona.
     *
     * @param task - Tarefa a executar
     */
    public static void run(Runnable task) {
        EXECUTOR.execute(task);
    }

    /**
     * Executa tarefa assíncrona.
     *
     * @param task - Tarefa a executar
     * @param onSuccess - Callback de sucesso
     * @param <T> - Tipo de retorno
     */
    public static <T> void run(
            Supplier<T> task,
            Consumer<T> onSuccess
    ) {
        run(
                task,
                onSuccess,
                null
        );
    }

    /**
     * Executa tarefa assíncrona.
     *
     * @param task - Tarefa a executar
     * @param onSuccess - Callback de sucesso
     * @param onError - Callback de erro
     * @param <T> - Tipo de retorno
     */
    public static <T> void run(
            Supplier<T> task,
            Consumer<T> onSuccess,
            Consumer<Throwable> onError
    ) {
        EXECUTOR.execute(() -> {
            try {
                T result = task.get();

                if (onSuccess != null) {
                    onSuccess.accept(result);
                }
            } catch (Throwable error) {
                if (onError != null) {
                    onError.accept(error);
                }
            }
        });
    }

    /**
     * Executa tarefa assíncrona com callbacks na EDT.
     *
     * @param task - Tarefa a executar
     */
    public static void ui(Runnable task) {
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                task.run();
            } else {
                SwingUtilities.invokeLater(task);
            }
        } catch (Throwable ignored) {

        }
    }

    /**
     * Executa tarefa assíncrona com callback de sucesso na EDT.
     *
     * @param task - Tarefa a executar
     * @param onSuccess - Callback de sucesso
     * @param <T> - Tipo de retorno
     */
    public static <T> void ui(
            Supplier<T> task,
            Consumer<T> onSuccess
    ) {
        ui(
                task,
                onSuccess,
                null
        );
    }

    /**
     * Executa tarefa assíncrona com callbacks na EDT.
     *
     * @param task - Tarefa a executar
     * @param onSuccess - Callback de sucesso
     * @param onError - Callback de erro
     * @param <T> - Tipo de retorno
     */
    public static <T> void ui(
            Supplier<T> task,
            Consumer<T> onSuccess,
            Consumer<Throwable> onError
    ) {
        EXECUTOR.execute(() -> {
            try {
                T result = task.get();

                if (onSuccess != null) {
                    ui(() -> onSuccess.accept(result));
                }
            } catch (Throwable error) {
                if (onError != null) {
                    ui(() -> onError.accept(error));
                }
            }
        });
    }

}
