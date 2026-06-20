package org.javapi.sigob.model.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.entity.ProdutosEstoques;

public interface ProdutosEstoquesService extends JpaCrudService<ProdutosEstoques, Integer> {

    /**
     * Transfere uma quantidade de produtos para outro estoque.
     *
     * @param origem - Registro de origem da transferência.
     * @param destino - Estoque de destino.
     * @param quantidade - Quantidade a ser transferida.
     */
    void transferir(ProdutosEstoques origem, Estoque destino, int quantidade);

    /**
     * Adiciona quantidade a um produto existente no estoque.
     *
     * @param produtoEstoque - Produto estoque
     */
    void adicionarEstoque(ProdutosEstoques produtoEstoque);

    /**
     * Busca registros pelo produto.
     *
     * @param produtoId - Identificador do produto.
     * @return List<ProdutosEstoques> - Lista de registros encontrados.
     */
    List<ProdutosEstoques> findByProduto(int produtoId);

    /**
     * Busca registros pelo estoque.
     *
     * @param estoqueId - Identificador do estoque.
     * @return List<ProdutosEstoques> - Lista de registros encontrados.
     */
    List<ProdutosEstoques> findByEstoque(int estoqueId);

    /**
     * Busca um registro único pela combinação de produto e estoque.
     *
     * @param produtoId - Identificador do produto.
     * @param estoqueId - Identificador do estoque.
     * @return Optional<ProdutosEstoques> - Registro encontrado, se existir.
     */
    Optional<ProdutosEstoques> findUnique(int produtoId, int estoqueId);
}
