package org.javapi.sigob.repository;

import jakarta.persistence.*;
import org.javapi.sigob.entity.Acesso;
import java.util.List;

public class AcessoRepository {
    private EntityManager em;

    public AcessoRepository(EntityManager em){
        this.em = em;
    }

    public Acesso findById(int id){
        return em.find(Acesso.class,id);
    }

    public void create(Acesso acesso){
        em.getTransaction().begin();
        em.persist(acesso);
        em.getTransaction().commit();
    }

    public void update(Acesso acesso){
        em.getTransaction().begin();
        em.merge(acesso);
        em.getTransaction().commit();
    }

    public void delete(Acesso acesso){
        em.getTransaction().begin();
        em.remove(em.contains(acesso) ? acesso : em.merge(acesso));
        em.getTransaction().commit();
    }

    public List<Acesso> findAll(){
        return em.createQuery("select a from acessos a").getResultList();
    }

    public List<Acesso> findByName(String name){
        return em.createQuery("select a from acessos a where nmAcesso like :str", Acesso.class)
                .setParameter("str", name + "%")
                .getResultList();
    }

    public boolean exists(Acesso acesso){
        return em.contains(acesso);
    }

    public Acesso findByCodigo(String codigo){
        return em.createQuery("select a from acessos a where cdAcesso like :str", Acesso.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }
}
