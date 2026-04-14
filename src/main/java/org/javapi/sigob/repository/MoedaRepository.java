package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Moeda;

import jakarta.persistence.EntityManager;

public class MoedaRepository {
    private EntityManager em;

    public MoedaRepository(EntityManager em){
        this.em = em;
    }

    public Moeda findById(int id){
        return em.find(Moeda.class, id);
    }

    public void save(Moeda moeda){
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
        return em.createQuery("select m from m.moedas m", Moeda.class).getResultList();
    }

    public List<Moeda> findByNome(String name){
        return em.createQuery("select m from moedas m where m.nmMoeda like :str", Moeda.class)
                .setParameter("str", name + "%")
                .getResultList();
    }

    public Moeda findByCodigo(String codigo){
        return em.createQuery("select m from moedas m where m.dsSigla like :str", Moeda.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }

    public Boolean exists(Moeda moeda){
        return em.contains(moeda);
    }
}
