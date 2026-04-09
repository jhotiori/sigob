package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Moeda;

import java.util.List;

public class MoedaRepository {
    private EntityManager em;

    public MoedaRepository(EntityManager em){
        this.em = em;
    }

    public Moeda findById(int id){
        return em.find(Moeda.class, id);
    }

    public void create(Moeda moeda){
        em.getTransaction().begin();
        em.persist(moeda);
        em.getTransaction().commit();
    }

    public void update(Moeda moeda){
        em.getTransaction().begin();
        em.merge(moeda);
        em.getTransaction().commit();
    }

    public void delete(Moeda moeda){
        em.getTransaction().begin();
        em.remove(em.contains(moeda) ? moeda : em.merge(moeda));
        em.getTransaction().commit();
    }

    public List<Moeda> findAll(){
        return em.createQuery("select m from moedas m", Moeda.class).getResultList();
    }

    public List<Moeda> findByName(String name){
        return em.createQuery("select m from moedas m where nmMoeda like :str", Moeda.class)
                .setParameter("str", name + "%")
                .getResultList();
    }
}
