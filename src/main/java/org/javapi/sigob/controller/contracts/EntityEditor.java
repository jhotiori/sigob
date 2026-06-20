package org.javapi.sigob.controller.contracts;

/**
 * Contrato para controllers que suportam edição.
 *
 * @param <T> - Tipo da entidade
 */
public interface EntityEditor<T> {

    /**
     * Inicia edição.
     *
     * @param entity - Entidade
     */
    void edit(T entity);

}
