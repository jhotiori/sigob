package org.javapi.sigob.view.async;

/**
 * Representa tarefa assíncrona com retorno.
 *
 * @param <T> - Tipo do retorno
 */
@FunctionalInterface
public interface AwaitTask<T> {

    /**
     * Executa tarefa.
     *
     * @return T - Resultado da tarefa
     * @throws Exception - Erro durante execução
     */
    T execute() throws Exception;

}
