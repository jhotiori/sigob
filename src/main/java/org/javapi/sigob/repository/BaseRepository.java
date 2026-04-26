package org.javapi.sigob.repository;

import java.util.Optional;

import jakarta.persistence.EntityManager;

/**
 * Repositorio base para todos os outros repositorios
 *
 * @param <T> A entidade
 * @param <ID> O tipo do ID
 */
public abstract class BaseRepository<T, ID> {

    /**
     * O EntityManager do repositorio. Vai ser utilizado para persistir,
     * atualizar, deletar e buscar entidades.
     *
     * @see {@link EntityManager}
     */
    protected final EntityManager em;

    /**
     * A classe da entidade. Usada apenas para identificar o tipo da entidade,
     * pelo fato do Java não armazenar a referência da classe da entidade em
     * argumentos de métodos.
     *
     * @see {@link Class}
     */
    private final Class<T> entityClass;

    /**
     * Cria um novo Repositorio Base
     *
     * @param em O EntityManager
     * @param entityClass A classe da entidade
     * @return BaseRepository - O repositorio
     */
    public BaseRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    /**
     * Persiste um novo objeto da entidade no EntityManager
     *
     * @param entity O objeto
     */
    public void save(T entity) {
        em.persist(entity);
    }

    /**
     * Atualiza um objeto da entidade no EntityManager
     *
     * @param entity O objeto
     * @return T - O objeto
     */
    public T update(T entity) {
        return em.merge(entity);
    }

    /**
     * Verifica se uma Entity com o ID providenciado esta no Banco de Dados
     *
     * @param <ID> O tipo do ID
     * @return boolean - true se existir, false caso contrário
     */
    public boolean contains(ID id) {
        return findById(id).isPresent();
    }

    /**
     * Procura pela entity com o ID informado
     *
     * @param id O ID da Entity
     * @return Optional<T> - O objeto encontrado
     */
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(this.getEntityClass(), id));
    }

    /**
     * Deleta uma entity com o ID informado
     *
     * @param id O ID da entity
     */
    public void deleteById(ID id) {
        findById(id).ifPresent(entity -> em.remove(entity));
    }

    /**
     * Retorna o `entityClass` do repositorio base
     *
     * @return Class<T> - A classe da entidade
     */
    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    /**
     * toString Implementation
     *
     * @return String - String formatada
     */
    @Override
    public String toString() {
        return "Repository(%s)".formatted(this.getEntityClass().getSimpleName());
    }
}
