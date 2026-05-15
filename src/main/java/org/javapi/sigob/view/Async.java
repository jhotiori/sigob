package org.javapi.sigob.view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import org.javapi.sigob.view.async.AwaitAsyncBuilder;
import org.javapi.sigob.view.async.AwaitTask;
import org.javapi.sigob.view.async.TaskAsyncBuilder;

/**
 * Utilitário para operações assíncronas.
 */
public final class Async {

    /**
     * Executor interno de tarefas.
     *
     * @see {@link ExecutorService}
     */
    private static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * Impede instanciação.
     */
    private Async() {
    }

    /**
     * Cria tarefa assíncrona.
     *
     * @param task - Tarefa executada
     * @return TaskAsyncBuilder - Builder criado
     */
    public static TaskAsyncBuilder task(Runnable task) {
        return new TaskAsyncBuilder(task);
    }

    /**
     * Cria tarefa assíncrona com retorno.
     *
     * @param task - Tarefa executada
     * @param <T> - Tipo do retorno
     * @return AwaitAsyncBuilder<T> - Builder criado
     */
    public static <T> AwaitAsyncBuilder<T> await(AwaitTask<T> task) {
        return new AwaitAsyncBuilder<>(task);
    }

    /**
     * Executa tarefa na EDT.
     *
     * @param task - Tarefa executada
     */
    public static void ui(Runnable task) {
        if (task == null) {
            return;
        }

        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            SwingUtilities.invokeLater(task);
        }
    }

    /**
     * Executa tarefa no executor interno.
     *
     * @param task - Tarefa executada
     */
    public static void execute(Runnable task) {
        if (task == null) {
            return;
        }

        EXECUTOR.submit(task);
    }

    /**
     * Finaliza executor interno.
     */
    public static void shutdown() {
        EXECUTOR.shutdown();
    }

}
