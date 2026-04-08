package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Cliente;

import java.util.List;

public class ClienteRepository {
    private EntityManager em;

    public ClienteRepository(EntityManager em) {
        this.em = em;
    }

    public Cliente findById(int id){
        return em.find(Cliente.class,id);
    }

    public void create(Cliente cliente){
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }

    public void update(Cliente cliente){
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
    }


    public void remove(Cliente cliente){
        em.getTransaction().begin();
        em.remove(em.contains(cliente) ? cliente : em.merge(cliente));
        em.getTransaction().commit();
    }

    public List<Cliente> findAll(){
        return em.createQuery("select c from clientes c", Cliente.class).getResultList();
    }

    public List<Cliente> findByName(String name){
        return em.createQuery("select c from clientes c where nmCliente like :str", Cliente.class)
                .setParameter("str", name + "%")
                .getResultList();
    }

    public Cliente findByDoc (String doc){
        return em.find(Cliente.class,doc);
    }

    public boolean exists (Cliente cliente){

        return em.contains(cliente);
    }
}
