package org.javapi.sigob.model.repository.impl;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Saldo;
import org.javapi.sigob.model.repository.SaldoRepository;

import jakarta.persistence.EntityManager;

public final class SaldoRepositoryImpl extends JpaCrudRepositoryImpl<Saldo, Integer> implements SaldoRepository {

    /**
     * Cria um novo SaldoRepository.
     *
     * @param entityManager - O EntityManager
     */
    public SaldoRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Saldo.class);
    }

    /**
     * Busca todos os Saldos.
     *
     * @return List<Saldo> - Os Saldos encontrados
     */
    @Override
    public List<Saldo> findAll() {
        return query("""
                SELECT s
                FROM %s s
                LEFT JOIN FETCH s.venda
                ORDER BY s.id DESC
                """)
                .list();
    }

    /**
     * Busca Saldos pela data.
     *
     * @param data - A data desejada
     * @return List<Saldo> - Os Saldos encontrados
     */
    @Override
    public List<Saldo> findByDataSaldo(LocalDate data) {
        return query("""
                SELECT s
                FROM %s s
                LEFT JOIN FETCH s.venda
                WHERE CAST(s.dataSaldo AS DATE) = :data
                ORDER BY s.dataSaldo DESC
                """)
                .param("data", data)
                .list();
    }

    /**
     * Busca Saldos pela descrição.
     *
     * @param descricao - A descrição desejada
     * @return List<Saldo> - Os Saldos encontrados
     */
    @Override
    public List<Saldo> findByDescricao(String descricao) {
        return query("""
                SELECT s
                FROM %s s
                LEFT JOIN FETCH s.venda
                WHERE LOWER(s.descricao) LIKE LOWER(:descricao)
                ORDER BY s.id DESC
                """)
                .param("descricao", like(descricao))
                .list();
    }

    /**
     * Busca Saldos pelo tipo.
     *
     * @param tipo - O tipo desejado
     * @return List<Saldo> - Os Saldos encontrados
     */
    @Override
    public List<Saldo> findByTipo(String tipo) {
        return query("""
                SELECT s
                FROM %s s
                LEFT JOIN FETCH s.venda
                WHERE LOWER(s.tipo) = LOWER(:tipo)
                ORDER BY s.id DESC
                """)
                .param("tipo", tipo)
                .list();
    }

    /**
     * Busca Saldos pela venda.
     *
     * @param idVenda - O ID da Venda
     * @return List<Saldo> - Os Saldos encontrados
     */
    @Override
    public List<Saldo> findByVendaId(int idVenda) {
        return query("""
                SELECT s
                FROM %s s
                LEFT JOIN FETCH s.venda
                WHERE s.venda.id = :id
                ORDER BY s.id DESC
                """)
                .param("id", idVenda)
                .list();
    }

    /**
     * Busca Saldos pelo ID do Caixa.
     *
     * @param idCaixa - O ID do Caixa
     * @return List<Saldo> - Os Saldos encontrados
     */
    @Override
    public List<Saldo> findByCaixaId(int idCaixa) {
        return query("""
                SELECT s
                FROM %s s
                LEFT JOIN FETCH s.venda
                WHERE s.caixa.id = :id
                ORDER BY s.id DESC
                """)
                .param("id", idCaixa)
                .list();
    }
}
