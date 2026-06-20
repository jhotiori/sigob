package org.javapi.sigob.model.repository.impl;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Caixa;
import org.javapi.sigob.model.repository.CaixaRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação do repositório de caixas.
 */
public final class CaixaRepositoryImpl extends JpaCrudRepositoryImpl<Caixa, Integer> implements CaixaRepository {

    /**
     * Cria um novo repositório de caixas.
     *
     * @param entityManager - EntityManager do repositório
     */
    public CaixaRepositoryImpl(EntityManager entityManager) {
        super(
                entityManager,
                Caixa.class);
    }

    /**
     * Busca caixas pelo status.
     *
     * @param status - Status desejado
     * @return List<Caixa> - Caixas encontrados
     */
    @Override
    public List<Caixa> findByStatus(String status) {
        return query("""
                SELECT c
                FROM %s c
                WHERE LOWER(c.status) = LOWER(:status)
                ORDER BY c.id DESC
                """)
                .param("status", status)
                .list();
    }

    /**
     * Busca caixas pela data de abertura.
     *
     * @param data - Data desejada
     * @return List<Caixa> - Caixas encontrados
     */
    @Override
    public List<Caixa> findByDataAbertura(LocalDate data) {
        return query("""
                SELECT c
                FROM %s c
                WHERE CAST(c.dataAbertura AS DATE) = :data
                ORDER BY c.dataAbertura DESC
                """)
                .param("data", data)
                .list();
    }

    /**
     * Busca caixas pela data de fechamento.
     *
     * @param data - Data desejada
     * @return List<Caixa> - Caixas encontrados
     */
    @Override
    public List<Caixa> findByDataFechamento(LocalDate data) {
        return query("""
                SELECT c
                FROM %s c
                WHERE CAST(c.dataFechamento AS DATE) = :data
                ORDER BY c.dataFechamento DESC
                """)
                .param("data", data)
                .list();
    }

    /**
     * Busca caixas abertos.
     *
     * @return List<Caixa> - Caixas encontrados
     */
    @Override
    public List<Caixa> findAbertos() {
        return query("""
                SELECT c
                FROM %s c
                WHERE LOWER(c.status) = 'aberto'
                ORDER BY c.id DESC
                """)
                .list();
    }

    /**
     * Busca caixas fechados.
     *
     * @return List<Caixa> - Caixas encontrados
     */
    @Override
    public List<Caixa> findFechados() {
        return query("""
                SELECT c
                FROM %s c
                WHERE LOWER(c.status) = 'fechado'
                ORDER BY c.id DESC
                """)
                .list();
    }
}
