package org.javapi.sigob.repository;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.entity.Caixa;

import jakarta.persistence.EntityManager;

public class CaixaRepository extends BaseRepository<Caixa, Integer> {

    /**
     * Cria um novo CaixaRepository.
     *
     * @param em O EntityManager
     */
    public CaixaRepository(EntityManager em) {
        super(em, Caixa.class);
    }

    /**
     * Busca todos os Caixas.
     *
     * @return List<Caixa> - Os Caixas encontrados
     */
    public List<Caixa> findAll() {
        return em.createQuery("""
                SELECT c FROM caixas c ORDER BY c.id DESC
                """, Caixa.class)
                .getResultList();
    }

    /**
     * Busca Caixas pelo status.
     *
     * @param status O status desejado
     * @return List<Caixa> - Os Caixas encontrados
     */
    public List<Caixa> findByStatus(String status) {
        return em.createQuery("""
                SELECT c FROM caixas c
                WHERE LOWER(c.status) = LOWER(:status)
                ORDER BY c.id DESC
                """, Caixa.class)
                .setParameter("status", status)
                .getResultList();
    }

    /**
     * Busca Caixas pela data de abertura.
     *
     * @param data Data desejada
     * @return List<Caixa> - Os Caixas encontrados
     */
    public List<Caixa> findByDataAbertura(LocalDate data) {
        return em.createQuery("""
                SELECT c FROM caixas c
                WHERE CAST(c.dataAbertura AS DATE) = :data
                ORDER BY c.dataAbertura DESC
                """, Caixa.class)
                .setParameter("data", data)
                .getResultList();
    }

    /**
     * Busca Caixas pela data de fechamento.
     *
     * @param data Data desejada
     * @return List<Caixa> - Os Caixas encontrados
     */
    public List<Caixa> findByDataFecha(LocalDate data) {
        return em.createQuery("""
                SELECT c FROM caixas c
                WHERE CAST(c.dataFecha AS DATE) = :data
                ORDER BY c.dataFecha DESC
                """, Caixa.class)
                .setParameter("data", data)
                .getResultList();
    }

    /**
     * Busca Caixas abertos.
     *
     * @return List<Caixa> - Os Caixas encontrados
     */
    public List<Caixa> findAbertos() {
        return em.createQuery("""
                SELECT c FROM caixas c
                WHERE LOWER(c.status) = 'aberto'
                ORDER BY c.id DESC
                """, Caixa.class)
                .getResultList();
    }

    /**
     * Busca Caixas fechados.
     *
     * @return List<Caixa> - Os Caixas encontrados
     */
    public List<Caixa> findFechados() {
        return em.createQuery("""
                SELECT c FROM caixas c
                WHERE LOWER(c.status) = 'fechado'
                ORDER BY c.id DESC
                """, Caixa.class)
                .getResultList();
    }
}