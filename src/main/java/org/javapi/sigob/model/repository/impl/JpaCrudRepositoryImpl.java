package org.javapi.sigob.model.repository.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.exception.NotFoundException;
import org.javapi.sigob.model.repository.JpaCrudRepository;
import org.javapi.sigob.model.repository.query.QueryBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

public abstract class JpaCrudRepositoryImpl<T, ID> implements JpaCrudRepository<T, ID> {

    /**
     * O EntityManager do repositorio. Vai ser utilizado para persistir,
     * atualizar, deletar e buscar entidades.
     *
     * @see {@link EntityManager}
     */
    private final EntityManager entityManager;

    /**
     * A classe da entidade. Usada apenas para identificar o tipo da entidade,
     * pelo fato do Java não armazenar a referência da classe da entidade em
     * argumentos de métodos.
     *
     * @see {@link Class}
     */
    private final Class<T> entityClass;

    /**
     * O nome da entidade. Armazenada pelo fato de não haver possibilidade de
     * adquirir ela, já que é atribuida por meio de notações do JPA.
     */
    private final String entityName;

    /**
     * Cria um novo Repositorio Base
     *
     * @param em O EntityManager
     * @param entityClass A classe da entidade
     * @return SigobRepository - O repositorio
     */
    public JpaCrudRepositoryImpl(
            EntityManager entityManager,
            Class<T> entityClass
    ) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.entityName = resolveEntityName(entityClass);
    }

    /**
     * Persiste um novo objeto da entidade no EntityManager
     *
     * @param entity O objeto
     */
    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    /**
     * Atualiza um objeto da entidade no EntityManager
     *
     * @param entity O objeto
     * @return T - O objeto
     */
    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    /**
     * Verifica se uma Entity com o ID providenciado esta no Banco de Dados
     *
     * @param <ID> O tipo do ID
     * @return boolean - true se existir, false caso contrário
     */
    @Override
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    /**
     * Busca todas as entidades
     *
     * @return List<T> - Todas as entidades
     */
    @Override
    public List<T> findAll() {
        return query("SELECT e FROM %s e").list();
    }

    /**
     * Procura pela entity com o ID informado
     *
     * @param id O ID da Entity
     * @return Optional<T> - O objeto encontrado
     */
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(this.getEntityClass(), id));
    }

    /**
     * Deleta uma entity com o ID informado
     *
     * @param id O ID da entity
     */
    @Override
    public void deleteById(ID id) {
        findById(id).ifPresent(entity -> entityManager.remove(entity));
    }

    /**
     * Cria uma consulta tipada.
     *
     * @param jpql - Consulta JPQL
     * @return QueryBuilder<T> - Consulta encapsulada
     */
    protected QueryBuilder<T> query(
            String jpql
    ) {
        return new QueryBuilder<>(
                entityManager.createQuery(
                        jpql.formatted(getEntityName()),
                        entityClass
                )
        );
    }

    /**
     * Formata um valor para busca utilizando LIKE.
     *
     * @param value - Valor informado
     * @return String - Valor formatado
     */
    protected String like(String value) {
        return "%" + value + "%";
    }

    /**
     * Retorna o entityClass do repositorio base
     *
     * @return Class<T> - A classe da entidade
     */
    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    /**
     * Retorna o entityName do repositorio base
     *
     * @return String - O nome da entidade
     */
    public String getEntityName() {
        return this.entityName;
    }

    /**
     * Resolve o nome da entidade.
     *
     * @return String - O nome da entidade
     */
    private String resolveEntityName(Class<T> entityClass) {
        Entity entity = entityClass.getAnnotation(Entity.class);

        if (entity == null) {
            throw new NotFoundException(
                "Entidade '%s' não possui anotação @Entity".formatted(
                    entityClass.getSimpleName()
                )
            );
        }

        String name = entity.name();
        if (!name.isBlank()) {
            return name;
        }

        return entityClass.getSimpleName();
    }

    /**
     * toString Implementation
     *
     * @return String - String formatada
     */
    @Override
    public String toString() {
        return "JpaCrudRepository(%s)".formatted(this.getEntityClass().getSimpleName());
    }
}
