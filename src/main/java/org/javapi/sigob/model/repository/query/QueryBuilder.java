package org.javapi.sigob.model.repository.query;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.TypedQuery;

/**
 * Encapsula uma consulta JPA tipada.
 *
 * @param <T> - Tipo da entidade retornada
 */
public final class QueryBuilder<T> {

    /**
     * Consulta JPA encapsulada.
     *
     * @see TypedQuery
     */
    private final TypedQuery<T> query;

    /**
     * Cria um novo QueryBuilder.
     *
     * @param query - Consulta JPA
     */
    public QueryBuilder(TypedQuery<T> query) {
        this.query = query;
    }

    /**
     * Define um parâmetro da consulta.
     *
     * @param name - Nome do parâmetro
     * @param value - Valor do parâmetro
     * @return QueryBuilder<T> - A própria instância
     */
    public QueryBuilder<T> param(
            String name,
            Object value
    ) {
        query.setParameter(name, value);
        return this;
    }

    /**
     * Define a quantidade máxima de resultados.
     *
     * @param maxResults - Quantidade máxima
     * @return QueryBuilder<T> - A própria instância
     */
    public QueryBuilder<T> max(
            int maxResults
    ) {
        query.setMaxResults(maxResults);
        return this;
    }

    /**
     * Executa a consulta e retorna todos os resultados.
     *
     * @return List<T> - Lista de resultados
     */
    public List<T> list() {
        return query.getResultList();
    }

    /**
     * Executa a consulta e retorna o primeiro resultado encontrado.
     *
     * @return Optional<T> - Primeiro resultado encontrado
     */
    public Optional<T> first() {
        return query
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }

    /**
     * Executa a consulta e retorna um único resultado.
     *
     * @return Optional<T> - Resultado encontrado
     */
    public Optional<T> one() {
        return Optional.ofNullable(
                query.getSingleResultOrNull()
        );
    }

    /**
     * Executa uma consulta de atualização ou remoção.
     *
     * @return int - Quantidade de registros afetados
     */
    public int execute() {
        return query.executeUpdate();
    }

    /**
     * Verifica se existe ao menos um resultado.
     *
     * @return boolean - true se existir resultado
     */
    public boolean exists() {
        return query
                .setMaxResults(1)
                .getResultStream()
                .findAny()
                .isPresent();
    }

    /**
     * Retorna a consulta JPA encapsulada.
     *
     * @return TypedQuery<T> - Consulta encapsulada
     */
    public TypedQuery<T> unwrap() {
        return query;
    }
}
