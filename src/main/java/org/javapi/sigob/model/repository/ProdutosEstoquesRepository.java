package org.javapi.sigob.model.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.ProdutosEstoques;

public interface ProdutosEstoquesRepository extends JpaCrudRepository<ProdutosEstoques, Integer> {

    /**
     * Busca vínculos pelo Produto.
     *
     * @param produtoId - ID do Produto
     * @return List<ProdutosEstoques> - Vínculos encontrados
     */
    List<ProdutosEstoques> findByProduto(int produtoId);

    /**
     * Busca vínculos pelo Estoque.
     *
     * @param estoqueId - ID do Estoque
     * @return List<ProdutosEstoques> - Vínculos encontrados
     */
    List<ProdutosEstoques> findByEstoque(int estoqueId);

    /**
     * Busca o vínculo único entre Produto e Estoque.
     *
     * @param produtoId - ID do Produto
     * @param estoqueId - ID do Estoque
     * @return Optional<ProdutosEstoques> - Vínculo encontrado
     */
    Optional<ProdutosEstoques> findUnique(int produtoId, int estoqueId);
}
