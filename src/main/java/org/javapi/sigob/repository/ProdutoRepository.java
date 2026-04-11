package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Produto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutoRepository {
    private final EntityManager em;

    /**
     * Cria um novo ProdutoRepository
     *
     * @param em O EntityManager
     * @return ProdutoRepository - O novo ProdutoRepository
     */
    public ProdutoRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva um novo Produto
     *
     * @param produto O Produto
     */
    public void save(Produto produto) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(produto);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Atualiza um Produto
     *
     * @param produto O Produto
     */
    public void update(Produto produto) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.merge(produto);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Deleta um Produto
     *
     * @param produto O Produto
     */
    public void delete(Produto produto) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.remove(manager.contains(produto) ? produto : manager.merge(produto));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Verifica se um Produto está salvo
     *
     * @param produto O Produto
     * @return boolean - true se o Produto estiver salvo, false se nao
     */
    public boolean contains(Produto produto) {
        return em.contains(produto);
    }

    /**
     * Retorna uma lista de todos os Produtos
     *
     * @return List<Produto> - A lista de Produtos
     */
    public List<Produto> findAll() {
        return em.createQuery("select p from produtos p", Produto.class).getResultList();
    }

    /**
     * Busca um Produto pelo id
     *
     * @param id O id do Produto
     * @return Produto - O Produto encontrado
     */
    public Produto findById(int id) {
        return em.find(Produto.class, id);
    }

    /**
     * Busca pelos Produtos que contem o nome
     *
     * @param nome O nome
     * @return List<Produto> - A lista de Produtos encontrados
     */
    public List<Produto> findByNome(String nome) {
        return em.createQuery("select p from produtos p where p.nmProduto like :str", Produto.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }
}
