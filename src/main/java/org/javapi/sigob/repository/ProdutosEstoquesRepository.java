package org.javapi.sigob.repository;

import java.util.List;
import java.util.Optional;

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
     * Busca todos os ProdutosEstoques disponíveis
     *
     * @return List<ProdutosEstoques> - Todos os ProdutosEstoques
     */
    public List<ProdutosEstoques> findAll() {
        return em.createQuery("select pe from produtos_estoques pe", ProdutosEstoques.class)
                .getResultList();
    }

    /**
     * Busca todos os ProdutosEstoques de um Produto
     *
     * @param produtoId O ID do Produto
     * @return List<ProdutosEstoques> - Os ProdutosEstoques encontrados
     */
    public List<ProdutosEstoques> findByProduto(int produtoId) {
        return em.createQuery("""
                SELECT pe FROM produtos_estoques pe
                WHERE pe.produto.id = :produtoId
                """, ProdutosEstoques.class)
                .setParameter("produtoId", produtoId)
                .getResultList();
    }

    /**
     * Busca todos os ProdutosEstoques de um Estoque
     *
     * @param estoqueId O ID do Estoque
     * @return List<ProdutosEstoques> - Os ProdutosEstoques encontrados
     */
    public List<ProdutosEstoques> findByEstoque(int estoqueId) {
        return em.createQuery("""
                SELECT pe FROM produtos_estoques pe
                WHERE pe.estoque.id = :estoqueId
                """, ProdutosEstoques.class)
                .setParameter("estoqueId", estoqueId)
                .getResultList();
    }

    /**
     * Busca o vínculo único entre Produto e Estoque
     *
     * @param produtoId O ID do Produto
     * @param estoqueId O ID do Estoque
     * @return ProdutosEstoques - O vínculo encontrado (ou null)
     */
    public Optional<ProdutosEstoques> findUnique(int produtoId, int estoqueId) {
        return Optional.ofNullable(
                em.createQuery("""
                SELECT pe FROM produtos_estoques pe
                WHERE pe.produto.id = :produtoId
                  AND pe.estoque.id = :estoqueId
                """, ProdutosEstoques.class)
                        .setParameter("produtoId", produtoId)
                        .setParameter("estoqueId", estoqueId)
                        .getSingleResultOrNull()
        );
    }
}
