package org.javapi.sigob.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.javapi.sigob.entity.Venda;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class VendaRepository {
    private final EntityManager em;

    /**
     * Cria um novo VendaRepository
     *
     * @param em O EntityManager
     * @return VendaRepository - O VendaRepository novo
     */
    public VendaRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva uma nova Venda
     *
     * @param venda A Venda para ser salva
     */
    public void create(Venda venda) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(venda);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Atualiza uma Venda
     *
     * @param venda A Venda para ser atualizada
     */
    public void update(Venda venda) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.merge(venda);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Deleta uma Venda
     *
     * @param venda A Venda para ser deletada
     */
    public void delete(Venda venda) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.remove(manager.contains(venda) ? venda : manager.merge(venda));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Verifica se uma venda existe
     *
     * @param venda A Venda para conferir
     * @return boolean - true se a venda existe, false se nao
     */
    public boolean contains(Venda venda) {
        return em.contains(venda);
    }

    /**
     * Retorna uma lista de todas as vendas
     *
     * @return List<Venda> - A lista de vendas
     */
    public List<Venda> findAll() {
        return em.createQuery("select v from vendas v", Venda.class).getResultList();
    }

    /**
     * Retorna uma venda pelo ID
     *
     * @param id O ID da venda
     * @return Venda - A venda
     */
    public Venda findById(int id) {
        return em.find(Venda.class, id);
    }

    /**
     * Retorna uma lista de vendas pelo intervalo de datas
     *
     * @param dataInicio A data inicial
     * @param dataFim    A data final
     * @return List<Venda> - A lista de vendas
     */
    public List<Venda> findByDataVenda(ZonedDateTime dataInicio, ZonedDateTime dataFim) {
        return em
                .createQuery("select v from vendas v where v.dtVenda >= :dtInicial and v.dtVenda <= :dtFinal",
                        Venda.class)
                .setParameter("dtInicial", dataInicio)
                .setParameter("dtFinal", dataFim)
                .getResultList();
    }
}
