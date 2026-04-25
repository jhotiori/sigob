package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.ProdutosEstoques;

import jakarta.persistence.EntityManager;

public class ProdutosEstoquesRepository extends BaseRepository<ProdutosEstoques, Integer> {

    /**
     * Cria um novo ProdutosEstoquesRepository
     *
     * @param em O EntityManager
     */
    public ProdutosEstoquesRepository(EntityManager em) {
        super(em, ProdutosEstoques.class);
    }

    /**
     * Verifica se um ProdutoEstoque está gerenciado pelo EntityManager
     *
     * @param produtoEstoque O ProdutoEstoque para verificar
     * @return boolean - true se gerenciado, false caso contrário
     */
    public boolean contains(ProdutosEstoques produtoEstoque) {
        return em.contains(produtoEstoque);
    }

    /**
     * Busca todos os ProdutoEstoques disponíveis
     *
     * @return List<ProdutoEstoque> - Todos os ProdutoEstoques
     */
    public List<ProdutosEstoques> findAll() {
        return em.createQuery("select pe from produto_estoques pe", ProdutosEstoques.class)
                .getResultList();
    }

    /**
     * Busca todos os ProdutoEstoques de um Produto
     *
     * @param produtoId O ID do Produto
     * @return List<ProdutoEstoque> - Os ProdutoEstoques encontrados
     */
    public List<ProdutosEstoques> findByProduto(int produtoId) {
        return em.createQuery("""
                        SELECT pe FROM produto_estoques pe
                        JOIN FETCH pe.produto
                        JOIN FETCH pe.estoque
                        WHERE pe.produto.id = :produtoId
                        """, ProdutosEstoques.class)
                .setParameter("produtoId", produtoId)
                .getResultList();
    }

    /**
     * Busca todos os ProdutoEstoques de um Estoque
     *
     * @param estoqueId O ID do Estoque
     * @return List<ProdutoEstoque> - Os ProdutoEstoques encontrados
     */
    public List<ProdutosEstoques> findByEstoque(int estoqueId) {
        return em.createQuery("""
                        SELECT pe FROM produto_estoques pe
                        JOIN FETCH pe.produto
                        JOIN FETCH pe.estoque
                        WHERE pe.estoque.id = :estoqueId
                        """, ProdutosEstoques.class)
                .setParameter("estoqueId", estoqueId)
                .getResultList();
    }

    public void delete(ProdutosEstoques produtoEstoque) {
    }
}