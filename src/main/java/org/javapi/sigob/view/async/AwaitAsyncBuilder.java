package org.javapi.sigob.view.async;

import java.util.function.Consumer;

import org.javapi.sigob.view.Async;

/**
 * Builder fluente para tarefas assíncronas com retorno.
 *
 * @param <T> - Tipo do retorno
 */
public class AwaitAsyncBuilder<T> {

    /**
     * Tarefa executada.
     *
     * @see {@link AwaitTask}
     */
    private final AwaitTask<T> task;

    /**
     * Callback de conclusão.
     *
     * @see {@link Consumer}
     */
    private Consumer<T> complete;

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
    public AwaitAsyncBuilder(AwaitTask<T> task) {
        this.task = task;
    }

    /**
     * Define callback de conclusão.
     *
     * @param complete - Callback de conclusão
     * @return AwaitAsyncBuilder<T> - Instância atual
     */
    public AwaitAsyncBuilder<T> onComplete(Consumer<T> complete) {
        this.complete = complete;

        return this;
    }

    /**
     * Define callback de erro.
     *
     * @param error - Callback de erro
     * @return AwaitAsyncBuilder<T> - Instância atual
     */
    public AwaitAsyncBuilder<T> onError(Consumer<Throwable> error) {
        this.error = error;

        return this;
    }

    /**
     * Define callback final.
     *
     * @param finallyCallback - Callback final
     * @return AwaitAsyncBuilder<T> - Instância atual
     */
    public AwaitAsyncBuilder<T> onFinally(Runnable finallyCallback) {
        this.finallyCallback = finallyCallback;

        return this;
    }

    /**
     * Executa tarefa assíncrona.
     */
    public void execute() {
        Async.execute(() -> {
            try {
                T result = task.execute();

                if (complete != null) {
                    Async.ui(() -> complete.accept(result));
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
