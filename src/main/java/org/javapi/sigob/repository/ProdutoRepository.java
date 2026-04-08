package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Produto;

import jakarta.persistence.EntityManager;

public class ProdutoRepository {
    private EntityManager em;

    public ProdutoRepository(EntityManager em) {
        this.em = em;
    }

    public Produto findById(int id) {
        return em.find(Produto.class, id);
    }

    public void create(Produto produto) {
        em.getTransaction().begin();
        em.persist(produto);
        em.getTransaction().commit();
    }

    public void update(Produto produto) {
        em.getTransaction().begin();
        em.merge(produto);
        em.getTransaction().commit();
    }

    public void delete(Produto produto) {
        em.getTransaction().begin();
        em.remove(em.contains(produto) ? produto : em.merge(produto));
        em.getTransaction().commit();
    }

    public List<Produto> findAll() {
        return em.createQuery("select p from produtos p", Produto.class).getResultList();
    }

    public List<Produto> findByName(String name) {
        return em.createQuery("select p from produtos p where p.nmProduto like :str", Produto.class)
                .setParameter("str", name + "%")
                .getResultList();
    }

}
