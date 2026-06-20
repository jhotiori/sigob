package org.javapi.sigob.view.v2.framework.contracts;

/**
 * Contrato para telas que suportam carregamento de entidades.
 *
 * @param <T> - Tipo da entidade
 */
public interface EntityLoader<T> {

    /**
     * Carrega entidade na tela.
     *
     * @param entity - Entidade
     */
    void load(T entity);

}
