package org.javapi.sigob.ui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SwingUtilities;

/**
 * Classe utilitaria para tarefas assíncronas em Swing. Adapta ao EDT,
 * garantindo que as tarefas sejam executadas da forma correta.
 */
public final class Async {

    /**
     * Pool de threads reutilizável para tarefas assíncronas. Se baseia no
     * número de processadores disponíveis da maquina.
     */
    private static final ExecutorService BACKGROUND_EXECUTOR = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("async-worker");
                thread.setDaemon(true);
                return thread;
            }
    );

    /**
     * Construtor privado para evitar instanciamento
     */
    private Async() {
    }

    /**
     * Executa uma tarefa em background utilizando um pool de threads
     * reutilizável. Note que a tarefa não será executada na EDT (Event Dispatch
     * Thread).
     *
     * @param task - Tarefa a ser executada
     */
    public static void execute(Runnable task, Consumer<Throwable> onError) {
        BACKGROUND_EXECUTOR.submit(() -> {
            try {
                task.run();
            } catch (Throwable throwable) {
                if (onError != null) {
                    onError.accept(throwable);
                } else {
                    throwable.printStackTrace();
                }
            }
        });
    }

    /**
     * Executa uma tarefa em background utilizando um pool de threads
     * reutilizável. Note que a tarefa não será executada na EDT (Event Dispatch
     * Thread).
     *
     * @param task - Tarefa a ser executada
     */
    public static void execute(Runnable task) {
        execute(task, null);
    }

    /**
     * Garante que uma tarefa seja executada na Event Dispatch Thread.
     *
     * @param task - Tarefa a ser executada na EDT
     */
    public static void ui(Runnable task) {
        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            SwingUtilities.invokeLater(task);
        }
    }

    /**
     * Executa uma tarefa em background, assim que for completada, chama o
     * consumidor, no qual será executado na EDT. De preferência, consumer deve
     * atualizar os componentes da UI.
     *
     * @param task - Tarefa executada fora da EDT
     * @param onSuccess - Consumidor executado se a tarefa for bem-sucedida
     * @param onError - Consumidor executado se a tarefa falhar
     * @param <T> - Tipo do resultado produzido
     */
    public static <T> void compute(Supplier<T> task, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        execute(() -> {
            try {
                T result = task.get();
                ui(() -> onSuccess.accept(result));
            } catch (Throwable throwable) {
                if (onError != null) {
                    ui(() -> onError.accept(throwable));
                } else {
                    throwable.printStackTrace();
                }
            }
        });
    }

    /**
     * Executa uma tarefa em background, assim que for completada, chama o
     * consumidor, no qual será executado na EDT. De preferência, consumer deve
     * atualizar os componentes da UI.
     *
     * @param task - Tarefa executada fora da EDT
     * @param onSuccess - Consumidor executado se a tarefa for bem-sucedida
     * @param <T> - Tipo do resultado produzido
     */
    public static <T> void compute(Supplier<T> task, Consumer<T> onSuccess) {
        compute(task, onSuccess, null);
    }

    /**
     * Encerra o executor de background.
     */
    public static void shutdown() {
        BACKGROUND_EXECUTOR.shutdownNow();
    }
}
