package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Funcionario;
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
