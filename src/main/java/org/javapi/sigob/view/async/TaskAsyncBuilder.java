package org.javapi.sigob.view.async;

import java.util.function.Consumer;

import org.javapi.sigob.view.Async;

/**
 * Builder fluente para tarefas assíncronas.
 */
public class TaskAsyncBuilder {

    /**
     * Tarefa executada.
     *
     * @see {@link Runnable}
     */
    private final Runnable task;

    /**
     * Callback de sucesso.
     *
     * @see {@link Runnable}
     */
    private Runnable success;

    /**
     * Callback de erro.
     *
     * @see {@link Consumer}
     */
    private Consumer<Throwable> error;

    /**
     * Callback final.
     *
     * @see {@link Runnable}
     */
    private Runnable finallyCallback;

    /**
     * Cria builder de tarefa.
     *
     * @param task - Tarefa executada
     */
    public TaskAsyncBuilder(Runnable task) {
        this.task = task;
    }

    /**
     * Define callback de sucesso.
     *
     * @param success - Callback de sucesso
     * @return TaskAsyncBuilder - Instância atual
     */
    public TaskAsyncBuilder onSuccess(Runnable success) {
        this.success = success;

        return this;
    }

    /**
     * Define callback de erro.
     *
     * @param error - Callback de erro
     * @return TaskAsyncBuilder - Instância atual
     */
    public TaskAsyncBuilder onError(Consumer<Throwable> error) {
        this.error = error;

        return this;
    }

    /**
     * Define callback final.
     *
     * @param finallyCallback - Callback final
     * @return TaskAsyncBuilder - Instância atual
     */
    public TaskAsyncBuilder onFinally(Runnable finallyCallback) {
        this.finallyCallback = finallyCallback;

        return this;
    }

    /**
     * Executa tarefa assíncrona.
     */
    public void execute() {
        Async.execute(() -> {
            try {
                if (task != null) {
                    task.run();
                }

                if (success != null) {
                    Async.ui(success);
                }

            } catch (Throwable throwable) {
                if (error != null) {
                    Async.ui(() -> error.accept(throwable));
                }

            } finally {
                if (finallyCallback != null) {
                    Async.ui(finallyCallback);
                }
            }
        });
    }

}
