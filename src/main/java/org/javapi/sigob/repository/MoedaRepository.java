package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Moeda;

import java.util.List;

public class MoedaRepository {
    private final EntityManager em;

    public MoedaRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Moeda moeda) {
        em.persist(moeda);
    }

    public void update(Moeda moeda) {
        em.merge(moeda);
    }

    public void delete(Moeda moeda) {
        em.remove(em.contains(moeda) ? moeda : em.merge(moeda));
    }

    public boolean contains(Moeda moeda) {
        return em.contains(moeda);
    }

    public Moeda findById(int id) {
        return em.find(Moeda.class, id);
    }

    public List<Moeda> findByNome(String nome) {
        return em.createQuery("select m from moedas m where m.nmMoeda like :str", Moeda.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    public Moeda findByCodigo(String codigo) {
        return em.createQuery("select m from moedas m where m.cdMoeda = :codigo", Moeda.class)
                .setParameter("codigo", codigo)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Moeda findBySigla(String sigla) {
        return em.createQuery("select m from moedas m where m.dsSigla = :sigla", Moeda.class)
                .setParameter("sigla", sigla)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<Moeda> findAll() {
        return em.createQuery("select m from moedas m", Moeda.class).getResultList();
    }
}

