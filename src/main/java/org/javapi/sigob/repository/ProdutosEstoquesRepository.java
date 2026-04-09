package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.ProdutosEstoques;

import jakarta.persistence.EntityManager;

public class ProdutosEstoquesRepository {
    private final EntityManager em;

    public ProdutosEstoquesRepository(EntityManager em) {
        this.em = em;
    }

    public void create(ProdutosEstoques produtoEstoque) {
        em.persist(produtoEstoque);
    }

    public void update(ProdutosEstoques produtoEstoque) {
        em.merge(produtoEstoque);
    }

    public void delete(ProdutosEstoques produtoEstoque) {
        em.remove(em.contains(produtoEstoque) ? produtoEstoque : em.merge(produtoEstoque));
    }

    public boolean contains(ProdutosEstoques produtoEstoque) {
        return em.contains(produtoEstoque);
    }

    public ProdutosEstoques findById(int id) {
        return em.find(ProdutosEstoques.class, id);
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
