package org.javapi.sigob.repository;

import org.javapi.sigob.entity.Categoria;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CategoriaRepository {
    private final EntityManager em;

    public CategoriaRepository(EntityManager em) {
        this.em = em;
    }

    public Categoria findById(int id) {
        return em.find(Categoria.class, id);
    }

    public void create(Categoria categoria) {
        em.persist(categoria);
    }

    public void update(Categoria categoria) {
        em.merge(categoria);
    }

    public void delete(Categoria categoria) {
        em.remove(em.contains(categoria) ? categoria : em.merge(categoria));
    }

    public boolean contains(Categoria categoria) {
        return em.contains(categoria);
    }

    public List<Categoria> findAll() {
        return em.createQuery("select c from Categoria c", Categoria.class).getResultList();
    }

    public List<Categoria> findByName(String prefixo) {
        return em.createQuery("select c from Categoria c where c.nmCategoria like :prefix", Categoria.class)
                .setParameter("prefix", prefixo + "%")
                .getResultList();
    }

    public Categoria findByCodigo(String codigo) {
        return em.createQuery("select c from Categoria c where c.cdCategoria = :codigo", Categoria.class)
                .setParameter("codigo", codigo)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
