package org.javapi.sigob.model.service;

import java.util.List;
import java.util.Optional;

public interface JpaCrudService<T, ID> {

    /**
     * Persiste uma entidade.
     *
     * @param entity - Entidade a ser persistida.
     */
    void save(T entity);

    /**
     * Atualiza uma entidade.
     *
     * @param entity - Entidade a ser atualizada.
     */
    void update(T entity);

    /**
     * Remove uma entidade.
     *
     * @param entity - Entidade a ser removida.
     */
    void delete(T entity);

    /**
     * Verifica se existe uma entidade com o identificador informado.
     *
     * @param id - Identificador da entidade.
     * @return boolean - Verdadeiro caso a entidade exista.
     */
    boolean existsById(ID id);

    /**
     * Busca todas as entidades.
     *
     * @return List<T> - Lista de entidades encontradas.
     */
    List<T> findAll();

    /**
     * Busca uma entidade pelo identificador.
     *
     * @param id - Identificador da entidade.
     * @return Optional<T> - Entidade encontrada, se existir.
     */
    Optional<T> findById(ID id);
}
