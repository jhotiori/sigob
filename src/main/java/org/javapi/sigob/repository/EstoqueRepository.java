package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Estoque;

import jakarta.persistence.EntityManager;

public class EstoqueRepository {
    private final EntityManager em;

    /**
     * Cria um novo EstoqueRepository
     *
     * @param em O EntityManager
     * @return EstoqueRepository - Um novo EstoqueRepository
     */
    public EstoqueRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva um estoque
     *
     * @param estoque O estoque para ser salvo
     */
    public void save(Estoque estoque) {
        em.persist(estoque);
    }

    /**
     * Atualiza um estoque
     *
     * @param estoque O estoque para ser atualizado
     */
    public void update(Estoque estoque) {
        em.merge(estoque);
    }

    /**
     * Confere se um estoque existe
     *
     * @param estoque O estoque para conferir
     * @return boolean - true se o estoque existe, false se nao
     */
    public boolean contains(Estoque estoque) {
        return em.find(Estoque.class, estoque.getIdEstoque()) != null;
    }

    /**
     * Deleta um estoque
     *
     * @param estoque O estoque para ser deletado
     */
    public void delete(Estoque estoque) {
        em.remove(em.contains(estoque) ? estoque : em.merge(estoque));
    }

    /**
     * Retorna uma lista com todos os estoques
     *
     * @return List<Estoque> - A lista de estoques
     */
    public List<Estoque> findAll() {
        return em.createQuery("select e from estoques e", Estoque.class).getResultList();
    }

    /**
     * Busca um estoque pelo id
     *
     * @param id O id do estoque
     * @return Estoque - O estoque encontrado
     */
    public Estoque findById(int id) {
        return em.find(Estoque.class, id);
    }

    /**
     * Busca estoques pelo prefixo
     *
     * @param nome O prefixo do estoque
     * @return List<Estoque> - A lista de estoques encontrados
     */
    public List<Estoque> findByNome(String nome) {
        return em.createQuery("select e from estoques e where e.nmEstoque like :prefix", Estoque.class)
                .setParameter("prefix", nome + "%")
                .getResultList();
    }
}
