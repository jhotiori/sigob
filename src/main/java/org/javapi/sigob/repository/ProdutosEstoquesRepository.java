package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.ProdutosEstoques;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutosEstoquesRepository {
    private final EntityManager em;

    /**
     * Cria um novo ProdutosEstoquesRepository
     *
     * @param em O EntityManager
     * @return ProdutosEstoquesRepository - O novo ProdutosEstoquesRepository
     */
    public ProdutosEstoquesRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva um novo ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     */
    public void save(ProdutosEstoques produtoEstoque) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(produtoEstoque);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Atualiza um ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     */
    public void update(ProdutosEstoques produtoEstoque) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.merge(produtoEstoque);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Deleta um ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     */
    public void delete(ProdutosEstoques produtoEstoque) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.remove(manager.contains(produtoEstoque) ? produtoEstoque : manager.merge(produtoEstoque));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Verifica se um ProdutosEstoques está contido no EntityManager
     *
     * @param produtoEstoque O ProdutosEstoques
     * @return boolean - true se o ProdutosEstoques estiver contido, false se nao
     */
    public boolean contains(ProdutosEstoques produtoEstoque) {
        return em.contains(produtoEstoque);
    }

    /**
     * Retorna uma lista com todos os ProdutosEstoques
     *
     * @return List<ProdutosEstoques> - A lista de ProdutosEstoques
     */
    public List<ProdutosEstoques> findAll() {
        return em.createQuery("select pe from produtosEstoques pe", ProdutosEstoques.class).getResultList();
    }

    /**
     * Retorna um ProdutosEstoques pelo ID
     *
     * @param id O ID do ProdutosEstoques
     * @return ProdutosEstoques - O ProdutosEstoques
     */
    public ProdutosEstoques findById(int id) {
        return em.find(ProdutosEstoques.class, id);
    }

    /**
     * Busca um ProdutosEstoques pelo nome
     *
     * @param nome O nome do ProdutosEstoques
     * @return List<ProdutosEstoques> - A lista de ProdutosEstoques
     */
    public List<ProdutosEstoques> findByNome(String nome) {
        return em
                .createQuery("select pe from produtosEstoques pe where pe.dsObservacao like :obs",
                        ProdutosEstoques.class)
                .setParameter("obs", nome + "%")
                .getResultList();
    }

}
