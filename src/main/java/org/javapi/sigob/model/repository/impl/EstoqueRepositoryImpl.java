package org.javapi.sigob.model.repository.impl;

import java.util.List;

import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.repository.EstoqueRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação de repositório para Estoque.
 */
public final class EstoqueRepositoryImpl extends JpaCrudRepositoryImpl<Estoque, Integer> implements EstoqueRepository {

    /**
     * Cria um novo EstoqueRepository.
     *
     * @param entityManager - EntityManager do repositório
     */
    public EstoqueRepositoryImpl(EntityManager entityManager) {
        super(
                entityManager,
                Estoque.class
            );
    }

    /**
     * Busca estoques pelo código.
     *
     * @param codigo - Código informado para busca
     * @return List<Estoque> - Estoques encontrados
     */
    @Override
    public List<Estoque> findByCodigo(String codigo) {
        return query("""
                SELECT e
                FROM %s e
                WHERE LOWER(e.codigo) LIKE LOWER(:str)
                """)
                .param("str", like(codigo))
                .list();
    }

    /**
     * Busca estoques pelo nome.
     *
     * @param nome - Nome informado para busca
     * @return List<Estoque> - Estoques encontrados
     */
    @Override
    public List<Estoque> findByNome(String nome) {
        return query("""
                SELECT e
                FROM %s e
                WHERE LOWER(e.nome) LIKE LOWER(:str)
                """)
                .param("str", like(nome))
                .list();
    }
}
