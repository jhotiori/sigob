package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.entity.Funcionario;

import java.util.List;

public class FuncionarioRepository {
    private EntityManager em;

    public FuncionarioRepository(EntityManager em) {
        this.em = em;
    }

    public Funcionario findById(int id){
        return em.find(Funcionario.class, id);
    }

    public void create(Funcionario funcionario){
        em.getTransaction().begin();
        em.persist(funcionario);
        em.getTransaction().commit();
    }

    public void update(Funcionario funcionario){
        em.getTransaction().begin();
        em.merge(funcionario);
        em.getTransaction().commit();
    }

    public void delete(Funcionario funcionario){
        em.getTransaction().begin();
        em.remove(em.contains(funcionario) ? funcionario : em.merge(funcionario));
        em.getTransaction().commit();
    }

    public List<Funcionario> findAll(){
        return em.createQuery("select f from funcionarios f", Funcionario.class).getResultList();
    }

    public List<Funcionario> findByName(String name){
        return em.createQuery("select f from funcionarios f where nmFuncionario like :str", Funcionario.class)
                .setParameter("str", name+"%")
                .getResultList();
    }

    public Funcionario findByCodigo(String codigo){
        return em.createQuery("select f from funcionarios f where cdFuncionario like :str", Funcionario.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }

    public Boolean exists(Funcionario funcionario){
        return em.contains(funcionario);
    }
}
