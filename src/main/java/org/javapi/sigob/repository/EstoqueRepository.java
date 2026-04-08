package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Estoque;

import jakarta.persistence.EntityManager;

public class EstoqueRepository {
    private EntityManager em;

    public EstoqueRepository(EntityManager em) {
        this.em = em;
    }

    public Estoque findById(int id) {
        return em.find(Estoque.class, id);
    }

    public void create(Estoque estoque) {
        em.getTransaction().begin();
        em.persist(estoque);
        em.getTransaction().commit();
    }

    public void update(Estoque estoque) {
        em.getTransaction().begin();
        em.merge(estoque);
        em.getTransaction().commit();
    }

    public void delete(Estoque estoque) {
        em.getTransaction().begin();
        em.remove(em.contains(estoque) ? estoque : em.merge(estoque));
        em.getTransaction().commit();
    }

    public List<Estoque> findAll() {
        return em.createQuery("select e from estoques e", Estoque.class).getResultList();
    }

    public List<Estoque> findByName(String prefixo) {
        return em.createQuery("select e from estoques e where e.nmEstoque like :prefix", Estoque.class)
                .setParameter("prefix", prefixo + "%")
                .getResultList();
    }
}
