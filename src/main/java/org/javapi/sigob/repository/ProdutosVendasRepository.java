package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.ProdutosVendas;

import jakarta.persistence.EntityManager;

public class ProdutosVendasRepository {
//    private final EntityManager em;
//
//    /**
//     * Cria um novo ProdutosVendasRepository
//     *
//     * @param em O EntityManager
//     * @return ProdutosVendasRepository - O novo ProdutosVendasRepository
//     */
//    public ProdutosVendasRepository(EntityManager em) {
//        this.em = em;
//    }
//
//    /**
//     * Salva um ProdutoVenda
//     *
//     * @param produtoVenda O ProdutosVendas para ser salvo
//     */
//    public void save(ProdutosVendas produtoVenda) {
//        em.persist(produtoVenda);
//    }
//
//    /**
//     * Atualiza um ProdutosVenda
//     *
//     * @param produtoVenda O ProdutosVenda para ser atualizado
//     */
//    public void update(ProdutosVendas produtoVenda) {
//        em.merge(produtoVenda);
//    }
//
//    /**
//     * Deleta um ProdutosVenda
//     *
//     * @param produtoVenda O ProdutosVenda para ser deletado
//     */
//    public void delete(ProdutosVendas produtoVenda) {
//        em.remove(em.contains(produtoVenda) ? produtoVenda : em.merge(produtoVenda));
//    }
//
//    /**
//     * Verifica se um ProdutosVenda existe
//     *
//     * @param produtoVenda O ProdutosVenda para conferir
//     * @return boolean - true se o ProdutosVenda existe, false se nao
//     */
//    public boolean contains(ProdutosVendas produtoVenda) {
//        return em.find(ProdutosVendas.class, produtoVenda.getIdProdutoVenda()) != null;
//    }
//
//    /**
//     * Retorna uma lista de todos os ProdutosVendas
//     *
//     * @return List<ProdutosVendas> - A lista de ProdutosVendas
//     */
//    public List<ProdutosVendas> findAll() {
//        return em.createQuery("select pv from produtosVendas pv", ProdutosVendas.class)
//                .getResultList();
//    }
//
//    /**
//     * Retorna um ProdutosVendas pelo ID
//     *
//     * @param id O ID do ProdutosVendas
//     * @return ProdutosVendas - O ProdutosVendas
//     */
//    public ProdutosVendas findById(int id) {
//        return em.find(ProdutosVendas.class, id);
//    }
//
//    /**
//     * Retorna uma lista de ProdutosVendas pelo ID do Produto
//     *
//     * @param idProduto O ID do Produto
//     * @return List<ProdutosVendas> - A lista de ProdutosVendas
//     */
//    public List<ProdutosVendas> findByProdutoId(int idProduto) {
//        return em.createQuery("select pv from produtosVendas pv where pv.produto.idProduto = :id")
//                .setParameter("id", idProduto)
//                .getResultList();
//    }
//
//    /**
//     * Retorna uma lista de ProdutosVendas pelo ID da Venda
//     *
//     * @param idVenda O ID da Venda
//     * @return List<ProdutosVendas> - A lista de ProdutosVendas
//     */
//    public List<ProdutosVendas> findByVendaId(int idVenda) {
//        return em.createQuery("select pv from produtosVendas pv where pv.venda.idVenda = :id")
//                .setParameter("id", idVenda)
//                .getResultList();
//    }
}
