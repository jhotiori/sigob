package org.javapi.sigob.repository;

import org.javapi.sigob.entity.Acesso;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AcessoRepository {
    private final EntityManager em;

    public AcessoRepository(EntityManager em) {
        this.em = em;
    }

    public Acesso findById(int id) {
        return em.find(Acesso.class, id);
    }

    public void create(Acesso acesso) {
        em.persist(acesso);
    }

    public void update(Acesso acesso) {
        em.merge(acesso);
    }

    public void delete(Acesso acesso) {
        em.remove(em.contains(acesso) ? acesso : em.merge(acesso));
    }

    public boolean contains(Acesso acesso) {
        return em.contains(acesso);
    }

    public List<Acesso> findAll() {
        return em.createQuery("select a from Acesso a", Acesso.class).getResultList();
    }

    public List<Acesso> findByName(String nome) {
        return em.createQuery("select a from Acesso a where a.nmAcesso like :str", Acesso.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    public Acesso findByCodigo(String codigo) {
        return em.createQuery("select a from Acesso a where a.cdAcesso = :codigo", Acesso.class)
                .setParameter("codigo", codigo)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}

