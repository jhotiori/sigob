package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.ProdutosEstoques;

import jakarta.persistence.EntityManager;

public class ProdutosEstoquesRepository {
    private EntityManager em;

    public ProdutosEstoquesRepository(EntityManager em) {
        this.em = em;
    }

    public ProdutosEstoques findById(int id) {
        return em.find(ProdutosEstoques.class, id);
    }

    public void create(ProdutosEstoques produtoEstoque) {
        em.getTransaction().begin();
        em.persist(produtoEstoque);
        em.getTransaction().commit();
    }

    public void update(ProdutosEstoques produtoEstoque) {
        em.getTransaction().begin();
        em.merge(produtoEstoque);
        em.getTransaction().commit();
    }

    public void delete(ProdutosEstoques produtoEstoque) {
        em.getTransaction().begin();
        em.remove(em.contains(produtoEstoque) ? produtoEstoque : em.merge(produtoEstoque));
        em.getTransaction().commit();
    }

    public List<ProdutosEstoques> findAll() {
        return em.createQuery("select pe from produtosEstoques pe", ProdutosEstoques.class).getResultList();
    }

    public List<ProdutosEstoques> findByName(String name) {
        return em
                .createQuery("select pe from produtosEstoques pe where pe.dsObservacao like :obs",
                        ProdutosEstoques.class)
                .setParameter("obs", name + "%")
                .getResultList();
    }

}
