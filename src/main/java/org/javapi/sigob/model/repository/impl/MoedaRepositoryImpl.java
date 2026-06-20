package org.javapi.sigob.model.repository.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.model.repository.MoedaRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação de repositório para Moeda.
 */
public final class MoedaRepositoryImpl extends JpaCrudRepositoryImpl<Moeda, Integer> implements MoedaRepository {

    /**
     * Cria um novo MoedaRepository.
     *
     * @param entityManager - EntityManager do repositório
     */
    public MoedaRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Moeda.class);
    }

    /**
     * Busca Moedas pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Moeda> - As Moedas encontradas
     */
    @Override
    public List<Moeda> findByNome(String nome) {
        return query("""
                SELECT m
                FROM %s m
                WHERE LOWER(m.nome) LIKE LOWER(:str)
                """)
                .param("str", like(nome))
                .list();
    }

    /**
     * Busca uma Moeda pela sigla.
     *
     * @param sigla - Sigla para busca
     * @return Optional<Moeda> - A Moeda encontrada
     */
    @Override
    public Optional<Moeda> findBySigla(String sigla) {
        return query("""
                SELECT m
                FROM %s m
                WHERE LOWER(m.sigla) LIKE LOWER(:str)
                """)
                .param("str", like(sigla))
                .one();
    }

    /**
     * Busca Moedas pelo Cifrao.
     *
     * @param cifrao - Cifrao para busca
     * @return List<Moeda> - As Moedas encontradas
     */
    @Override
    public List<Moeda> findByCifrao(String cifrao) {
        return query("""
                SELECT m
                FROM %s m
                WHERE LOWER(m.cifrao) LIKE LOWER(:str)
                """)
                .param("str", like(cifrao))
                .list();
    }
}
