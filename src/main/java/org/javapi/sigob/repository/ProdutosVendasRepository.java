package org.javapi.sigob.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import org.javapi.sigob.entity.ProdutosVendas;

public class ProdutosVendasRepository {
    private final EntityManager em;

    /**
     * Cria um novo ProdutosVendasRepository
     *
     * @param em O EntityManager
     * @return ProdutosVendasRepository - O novo ProdutosVendasRepository
     */
    public ProdutosVendasRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva um ProdutoVenda
     *
     * @param produtoVenda O ProdutosVendas para ser salvo
     */
    public void save(ProdutosVendas produtoVenda) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(produtoVenda);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Atualiza um ProdutosVenda
     *
     * @param produtoVenda O ProdutosVenda para ser atualizado
     */
    public void update(ProdutosVendas produtoVenda) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.merge(produtoVenda);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Deleta um ProdutosVenda
     *
     * @param produtoVenda O ProdutosVenda para ser deletado
     */
    public void delete(ProdutosVendas produtoVenda) {
        EntityManager manager = this.em;
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.remove(manager.contains(produtoVenda) ? produtoVenda : manager.merge(produtoVenda));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Verifica se um ProdutosVenda existe
     *
     * @param produtoVenda O ProdutosVenda para conferir
     * @return boolean - true se o ProdutosVenda existe, false se nao
     */
    public boolean contains(ProdutosVendas produtoVenda) {
        return em.contains(produtoVenda);
    }

    /**
     * Retorna uma lista de todos os ProdutosVendas
     *
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findAll() {
        return em.createQuery("select pv from produtosVendas pv", ProdutosVendas.class)
                .getResultList();
    }

    /**
     * Retorna um ProdutosVendas pelo ID
     *
     * @param id O ID do ProdutosVendas
     * @return ProdutosVendas - O ProdutosVendas
     */
    public ProdutosVendas findById(int id) {
        return em.find(ProdutosVendas.class, id);
    }

    /**
     * Retorna uma lista de ProdutosVendas pelo ID do Produto
     *
     * @param idProduto O ID do Produto
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findByProdutoId(int idProduto) {
        return em.createQuery("select pv from produtosVendas pv where pv.produto.idProduto = :id")
                .setParameter("id", idProduto)
                .getResultList();
    }

    /**
     * Retorna uma lista de ProdutosVendas pelo ID da Venda
     *
     * @param idVenda O ID da Venda
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findByVendaId(int idVenda) {
        return em.createQuery("select pv from produtosVendas pv where pv.produto.idProduto = :id")
                .setParameter("id", idVenda)
                .getResultList();
    }
}
