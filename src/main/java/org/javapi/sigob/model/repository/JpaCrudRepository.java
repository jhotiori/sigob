package org.javapi.sigob.model.repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio base para todos os outros repositorios
 *
 * @param <T> - A entidade
 * @param <ID> - O tipo do ID
 */
public interface JpaCrudRepository<T, ID> {

    /**
     * Salva uma entidade.
     *
     * @param entity - Entidade para salvar
     */
    void save(T entity);

    /**
     * Atualiza uma entidade.
     *
     * @param entity - Entidade para atualizar
     * @return T - Entidade atualizada
     */
    T update(T entity);

    /**
     * Busca todas as entidades.
     *
     * @return List<T> - Entidades encontradas
     */
    List<T> findAll();

    /**
     * Busca uma entidade pelo ID.
     *
     * @param id - ID da entidade
     * @return Optional<T> - Entidade encontrada
     */
    Optional<T> findById(ID id);

    /**
     * Verifica se uma entidade existe pelo ID.
     *
     * @param id - ID da entidade
     * @return boolean - true se existir
     */
    boolean existsById(ID id);

    /**
     * Remove uma entidade pelo ID.
     *
     * @param id - ID da entidade
     */
    void deleteById(ID id);
}
