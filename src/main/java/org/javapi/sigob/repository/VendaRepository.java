package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Venda;
import java.time.ZonedDateTime;
import java.util.List;

public class VendaRepository {

    private EntityManager em;

    public VendaRepository(EntityManager em) {
        this.em = em;
    }

    public Venda findById(int id) {
        return em.find(Venda.class, id);
    }

    public void create(Venda venda) {
        em.getTransaction().begin();
        em.persist(venda);
        em.getTransaction().commit();
    }

    public void update(Venda venda) {
        em.getTransaction().begin();
        em.merge(venda);
        em.getTransaction().commit();
    }

    public void remove(Venda venda) {
        em.getTransaction().begin();
        em.remove(em.contains(venda) ? venda : em.merge(venda));
        em.getTransaction().commit();
    }

    public List<Venda> findAll() {
        return em.createQuery("select v from vendas v", Venda.class).getResultList();
    }

    public List<Venda> findByDtVenda(ZonedDateTime dtInicio, ZonedDateTime dtFim) {
        return em.createQuery("select v from vendas v where v.dtVenda >= :dtInicial and v.dtVenda <= :dtFinal", Venda.class)
                .setParameter("dtInicial", dtInicio)
                .setParameter("dtFinal", dtFim)
                .getResultList();
    }

    public boolean exists(Venda venda){
        return em.contains(venda);
    }
}
