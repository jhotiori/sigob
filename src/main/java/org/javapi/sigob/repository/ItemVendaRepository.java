package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.ItemVenda;

import java.util.List;
import java.util.Optional;

public class ItemVendaRepository extends BaseRepository<ItemVenda, Integer> {

    /**
     * Cria um novo ItemVendaRepository.
     *
     * @param em O EntityManager para conexão com o banco de dados
     */
    public ItemVendaRepository(EntityManager em) {
        super(em, ItemVenda.class);
    }

    /**
     * Busca todos os ItemVenda.
     *
     * @return List<ItemVenda> - A lista de ItemVenda
     */
    public List<ItemVenda> findAll() {
        return em.createQuery("SELECT iv FROM item_vendas iv", ItemVenda.class)
                .getResultList();
    }

    /**
     * Busca todos os ItemVenda baseado no ID do ProdutoEstoque.
     *
     * @param produtoEstoqueId O ID do ProdutoEstoque
     * @return List<ItemVenda> - A lista de ItemVenda
     */
    public List<ItemVenda> findByProdutoEstoque(int produtoEstoqueId) {
        return em.createQuery("""
                        SELECT iv FROM item_vendas iv
                        WHERE iv.produtoEstoque.id = :produtoEstoqueId
                        """, ItemVenda.class)
                .setParameter("produtoEstoqueId", produtoEstoqueId)
                .getResultList();
    }

    /**
     * Busca todos os ItemVenda baseado no ID da Venda.
     *
     * @param vendaId O ID da Venda
     * @return List<ItemVenda> - A lista de ItemVenda
     */
    public List<ItemVenda> findByVenda(int vendaId) {
        return em.createQuery("""
                        SELECT iv FROM item_vendas iv
                        WHERE iv.venda.id = :vendaId
                        """, ItemVenda.class)
                .setParameter("vendaId", vendaId)
                .getResultList();
    }

    /**
     * Busca um ItemVenda único baseado na combinação Venda + ProdutoEstoque.
     *
     * Observação: A unicidade é garantida pelo banco de dados (constraint
     * UNIQUE).
     *
     * @param vendaId ID da Venda
     * @param produtoEstoqueId ID do ProdutoEstoque
     * @return Optional<ItemVenda> - O ItemVenda encontrado
     */
    public Optional<ItemVenda> findByVendaAndProdutoEstoque(int vendaId, int produtoEstoqueId) {
        return Optional.ofNullable(
                em.createQuery("""
                        SELECT iv FROM item_vendas iv
                        WHERE iv.venda.id = :vendaId
                          AND iv.produtoEstoque.id = :produtoEstoqueId
                        """, ItemVenda.class)
                        .setParameter("vendaId", vendaId)
                        .setParameter("produtoEstoqueId", produtoEstoqueId)
                        .getSingleResultOrNull()
        );
    }
}
