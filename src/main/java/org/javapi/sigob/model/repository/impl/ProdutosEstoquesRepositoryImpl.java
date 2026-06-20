package org.javapi.sigob.model.repository.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.model.repository.ProdutosEstoquesRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação de repositório para ProdutosEstoques.
 */
public final class ProdutosEstoquesRepositoryImpl extends JpaCrudRepositoryImpl<ProdutosEstoques, Integer> implements ProdutosEstoquesRepository {

    /**
     * Cria um novo ProdutosEstoquesRepository.
     *
     * @param entityManager - EntityManager do repositório
     */
    public ProdutosEstoquesRepositoryImpl(EntityManager entityManager) {
        super(entityManager, ProdutosEstoques.class);
    }

    /**
     * Busca ProdutosEstoques pelo Estoque.
     *
     * @param estoqueId - ID do Estoque
     * @return List<ProdutosEstoques> - Os ProdutosEstoques encontrados
     */
    @Override
    public List<ProdutosEstoques> findByEstoque(int estoqueId) {
        return query("""
                SELECT pe
                FROM %s pe
                WHERE pe.estoque.id = :estoqueId
                """)
                .param("estoqueId", estoqueId)
                .list();
    }

    /**
     * Busca ProdutosEstoques pelo Produto.
     *
     * @param produtoId - ID do Produto
     * @return List<ProdutosEstoques> - Os ProdutosEstoques encontrados
     */
    @Override
    public List<ProdutosEstoques> findByProduto(int produtoId) {
        return query("""
                SELECT pe
                FROM %s pe
                WHERE pe.produto.id = :produtoId
                """)
                .param("produtoId", produtoId)
                .list();
    }

    /**
     * Busca o vínculo entre Produto e Estoque.
     *
     * @param produtoId - ID do Produto
     * @param estoqueId - ID do Estoque
     * @return Optional<ProdutosEstoques> - O vínculo encontrado
     */
    @Override
    public Optional<ProdutosEstoques> findUnique(
            int produtoId,
            int estoqueId) {
        return query("""
                SELECT pe
                FROM %s pe
                WHERE pe.produto.id = :produtoId
                  AND pe.estoque.id = :estoqueId
                """)
                .param("produtoId", produtoId)
                .param("estoqueId", estoqueId)
                .one();
    }
}
