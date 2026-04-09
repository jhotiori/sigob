package org.javapi.sigob.repository;

import org.javapi.sigob.entity.Funcionario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FuncionarioRepository {
    private final EntityManager em;

    public FuncionarioRepository(EntityManager em) {
        this.em = em;
    }

    public Funcionario findById(int id) {
        return em.find(Funcionario.class, id);
    }

    public void create(Funcionario funcionario) {
        em.persist(funcionario);
    }

    public void update(Funcionario funcionario) {
        em.merge(funcionario);
    }

    public void delete(Funcionario funcionario) {
        em.remove(em.contains(funcionario) ? funcionario : em.merge(funcionario));
    }

    public boolean contains(Funcionario funcionario) {
        return em.contains(funcionario);
    }

    public List<Funcionario> findAll() {
        return em.createQuery("select f from Funcionario f", Funcionario.class).getResultList();
    }

    public List<Funcionario> findByName(String nome) {
        return em.createQuery("select f from Funcionario f where f.nmFuncionario like :str", Funcionario.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    public Funcionario findByCodigo(String codigo) {
        return em.createQuery("select f from Funcionario f where f.cdFuncionario = :codigo", Funcionario.class)
                .setParameter("codigo", codigo)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
