package org.javapi.sigob.model.repository.impl;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.model.repository.VendaRepository;

import jakarta.persistence.EntityManager;

public final class VendaRepositoryImpl extends JpaCrudRepositoryImpl<Venda, Integer> implements VendaRepository {

    /**
     * Cria um novo VendaRepository
     *
     * @param entityManager - O EntityManager
     */
    public VendaRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Venda.class);
    }

    /**
     * Busca todas as vendas.
     *
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findAll() {
        return query("""
                SELECT v
                FROM %s v
                JOIN FETCH v.cliente
                JOIN FETCH v.funcionario
                ORDER BY v.id DESC
                """)
                .list();
    }

    /**
     * Busca vendas pelo nome do cliente.
     *
     * @param nome - O nome do cliente
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findByClienteNome(String nome) {
        return query("""
                SELECT v
                FROM %s v
                JOIN FETCH v.cliente
                JOIN FETCH v.funcionario
                WHERE LOWER(v.cliente.nome) LIKE LOWER(:nome)
                ORDER BY v.id DESC
                """)
                .param("nome", like(nome))
                .list();
    }

    /**
     * Busca vendas pelo id do cliente.
     *
     * @param id - O id do cliente
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findByClienteId(int id) {
        return query("""
                SELECT v
                FROM %s v
                WHERE v.cliente.id = :id
                """)
                .param("id", id)
                .list();
    }

    /**
     * Busca vendas pelo nome do funcionario.
     *
     * @param nome - O nome do funcionario
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findByFuncionarioNome(String nome) {
        return query("""
                SELECT v
                FROM %s v
                JOIN FETCH v.cliente
                JOIN FETCH v.funcionario
                WHERE LOWER(v.funcionario.nome) LIKE LOWER(:nome)
                ORDER BY v.id DESC
                """)
                .param("nome", like(nome))
                .list();
    }

    /**
     * Busca vendas pelo id do funcionario.
     *
     * @param id - O id do funcionario
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findByFuncionarioId(int id) {
        return query("""
                SELECT v
                FROM %s v
                WHERE v.funcionario.id = :id
                """)
                .param("id", id)
                .list();
    }

    /**
     * Busca vendas pela data de abertura.
     *
     * @param data - A data de abertura
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findByDataAbertura(LocalDate data) {
        return query("""
                SELECT v
                FROM %s v
                JOIN FETCH v.cliente
                JOIN FETCH v.funcionario
                WHERE CAST(v.dataAbertura AS DATE) = :data
                ORDER BY v.dataAbertura DESC
                """)
                .param("data", data)
                .list();
    }

    /**
     * Busca vendas pela data de finalização.
     *
     * @param data - A data de finalização
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findByDataFinalizada(LocalDate data) {
        return query("""
                SELECT v
                FROM %s v
                JOIN FETCH v.cliente
                JOIN FETCH v.funcionario
                WHERE v.dataFinalizada IS NOT NULL
                  AND CAST(v.dataFinalizada AS DATE) = :data
                ORDER BY v.dataFinalizada DESC
                """)
                .param("data", data)
                .list();
    }

    /**
     * Busca vendas pelo período.
     *
     * @param inicio - A data de início
     * @param fim    - A data de fim
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findByPeriodo(
            LocalDate inicio,
            LocalDate fim) {
        return query("""
                SELECT v
                FROM %s v
                JOIN FETCH v.cliente
                JOIN FETCH v.funcionario
                WHERE CAST(v.dataAbertura AS DATE)
                BETWEEN :inicio AND :fim
                ORDER BY v.dataAbertura DESC
                """)
                .param("inicio", inicio)
                .param("fim", fim)
                .list();
    }

    /**
     * Busca vendas abertas.
     *
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findAbertas() {
        return query("""
                SELECT v
                FROM %s v
                JOIN FETCH v.cliente
                JOIN FETCH v.funcionario
                WHERE v.status = 'aberta'
                """)
                .list();
    }

    /**
     * Busca vendas finalizadas.
     *
     * @return List<Venda> - Lista de vendas
     */
    @Override
    public List<Venda> findFinalizadas() {
        return query("""
                SELECT v
                FROM %s v
                JOIN FETCH v.cliente
                JOIN FETCH v.funcionario
                WHERE v.status = 'finalizada'
                """)
                .list();
    }
}
