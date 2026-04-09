package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Produto;

import jakarta.persistence.EntityManager;

public class ProdutoRepository {
    private final EntityManager em;

    public ProdutoRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Produto produto) {
        em.persist(produto);
    }

    public void update(Produto produto) {
        em.merge(produto);
    }

    public void delete(Produto produto) {
        em.remove(em.contains(produto) ? produto : em.merge(produto));
    }

    public boolean contains(Produto produto) {
        return em.contains(produto);
    }

     public List<Produto> findByNome(String nome) {
        return em.createQuery("select p from produtos p where p.nmProduto like :str", Produto.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    public Produto findById(int id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> findAll() {
        return em.createQuery("select p from produtos p", Produto.class).getResultList();
    }
}
