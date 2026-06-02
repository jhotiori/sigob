package org.javapi.sigob.repository;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.entity.Saldo;

import jakarta.persistence.EntityManager;

public class SaldoRepository extends BaseRepository<Saldo, Integer> {

    /**
     * Cria um novo SaldoRepository.
     *
     * @param em O EntityManager
     */
    public SaldoRepository(EntityManager em) {
        super(em, Saldo.class);
    }

    /**
     * Busca todos os Saldos.
     *
     * @return List<Saldo> - Os Saldos encontrados
     */
    public List<Saldo> findAll() {
        return em.createQuery("""
                SELECT s FROM saldos s
                LEFT JOIN FETCH s.venda
                ORDER BY s.id DESC
                """, Saldo.class)
                .getResultList();
    }

    /**
     * Busca Saldos pelo tipo.
     *
     * @param tipo O tipo desejado
     * @return List<Saldo> - Os Saldos encontrados
     */
    public List<Saldo> findByTipo(String tipo) {
        return em.createQuery("""
                SELECT s FROM saldos s
                LEFT JOIN FETCH s.venda
                WHERE LOWER(s.tipo) = LOWER(:tipo)
                ORDER BY s.id DESC
                """, Saldo.class)
                .setParameter("tipo", tipo)
                .getResultList();
    }

    /**
     * Busca Saldos pela descrição.
     *
     * @param descricao A descrição desejada
     * @return List<Saldo> - Os Saldos encontrados
     */
    public List<Saldo> findByDescricao(String descricao) {
        return em.createQuery("""
                SELECT s FROM saldos s
                LEFT JOIN FETCH s.venda
                WHERE LOWER(s.descricao) LIKE LOWER(:descricao)
                ORDER BY s.id DESC
                """, Saldo.class)
                .setParameter("descricao", "%" + descricao + "%")
                .getResultList();
    }

    /**
     * Busca Saldos pela data.
     *
     * @param data A data desejada
     * @return List<Saldo> - Os Saldos encontrados
     */
    public List<Saldo> findByDataSaldo(LocalDate data) {
        return em.createQuery("""
                SELECT s FROM saldos s
                LEFT JOIN FETCH s.venda
                WHERE CAST(s.dataSaldo AS DATE) = :data
                ORDER BY s.dataSaldo DESC
                """, Saldo.class)
                .setParameter("data", data)
                .getResultList();
    }

    /**
     * Busca Saldos pela venda.
     *
     * @param idVenda O ID da Venda
     * @return List<Saldo> - Os Saldos encontrados
     */
    public List<Saldo> findByVendaId(int idVenda) {
        return em.createQuery("""
                SELECT s FROM saldos s
                LEFT JOIN FETCH s.venda
                WHERE s.venda.id = :id
                ORDER BY s.id DESC
                """, Saldo.class)
                .setParameter("id", idVenda)
                .getResultList();
    }
}