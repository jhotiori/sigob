package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Categoria;

import java.util.List;

public class CategoriaRepository {
    private EntityManager em;
    public CategoriaRepository(EntityManager em){
        this.em = em;
    }

    public Categoria findById(int id){
        return em.find(Categoria.class, id);
    }

    public void create(Categoria categoria){
        em.getTransaction().begin();
        em.persist(categoria);
        em.getTransaction().commit();
    }

    public void update(Categoria categoria){
        em.getTransaction().begin();
        em.merge(categoria);
        em.getTransaction().commit();
    }

    public void delete(Categoria categoria){
        em.getTransaction().begin();
        em.remove(em.contains(categoria) ? categoria : em.merge(categoria));
        em.getTransaction().commit();
    }

    public List<Categoria> findAll(){
        return em.createQuery("select c from categorias c", Categoria.class).getResultList();
    }

    public List<Categoria> findByName(String prefixo){
        return em.createQuery("select c from categorias c where c.nmCategoria like :prefix", Categoria.class)
                .setParameter("prefix", prefixo + "%")
                .getResultList();
    }

    public Categoria findByCodigo(String codigo){
        return em.createQuery("select c from categorias c where cdCategoria like :str", Categoria.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }

    public Boolean exists(Categoria categoria){
        return em.contains(categoria);
    }
}
