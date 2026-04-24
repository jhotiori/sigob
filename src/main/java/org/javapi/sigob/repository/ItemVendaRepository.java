package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.ItemVenda;

import jakarta.persistence.EntityManager;

public class ItemVendaRepository extends BaseRepository<ItemVenda, Integer> {

    /**
     * Cria um novo ItemVendaRepository
     *
     * @param em O EntityManager
     */
    public ItemVendaRepository(EntityManager em) {
        super(em, ItemVenda.class);
    }

    /**
     * Verifica se um ItemVenda está gerenciado pelo EntityManager
     *
     * @param itemVenda O ItemVenda para verificar
     * @return boolean - true se gerenciado, false caso contrário
     */
    public boolean contains(ItemVenda itemVenda) {
        return em.contains(itemVenda);
    }

    /**
     * Busca todos os ItemVendas disponíveis
     *
     * @return List<ItemVenda> - Todos os ItemVendas
     */
    public List<ItemVenda> findAll() {
        return em.createQuery("select iv from item_vendas iv", ItemVenda.class)
                .getResultList();
    }

    /**
     * Busca todos os ItemVendas de um ProdutoEstoque
     *
     * @param produtoEstoqueId O ID do ProdutoEstoque
     * @return List<ItemVenda> - Os ItemVendas encontrados
     */
    public List<ItemVenda> findByProdutoEstoque(int produtoEstoqueId) {
        return em.createQuery("""
                        SELECT iv FROM item_vendas iv
                        JOIN FETCH iv.produtoEstoque
                        JOIN FETCH iv.venda
                        WHERE iv.produtoEstoque.id = :produtoEstoqueId
                        """, ItemVenda.class)
                .setParameter("produtoEstoqueId", produtoEstoqueId)
                .getResultList();
    }

    /**
     * Busca todos os ItemVendas de uma Venda
     *
     * @param vendaId O ID da Venda
     * @return List<ItemVenda> - Os ItemVendas encontrados
     */
    public List<ItemVenda> findByVenda(int vendaId) {
        return em.createQuery("""
                        SELECT iv FROM item_vendas iv
                        JOIN FETCH iv.produtoEstoque
                        JOIN FETCH iv.venda
                        WHERE iv.venda.id = :vendaId
                        """, ItemVenda.class)
                .setParameter("vendaId", vendaId)
                .getResultList();
    }
}