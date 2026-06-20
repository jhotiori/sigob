package org.javapi.sigob.model.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.ItemVenda;

public interface ItemVendaRepository extends JpaCrudRepository<ItemVenda, Integer> {

    /**
     * Busca Itens de Venda pelo ProdutoEstoque.
     *
     * @param produtoEstoqueId - ID do ProdutoEstoque
     * @return List<ItemVenda> - Itens encontrados
     */
    List<ItemVenda> findByProdutoEstoque(int produtoEstoqueId);

    /**
     * Busca Itens de Venda pela Venda.
     *
     * @param vendaId - ID da Venda
     * @return List<ItemVenda> - Itens encontrados
     */
    List<ItemVenda> findByVenda(int vendaId);

    /**
     * Busca um Item de Venda pela combinação Venda e ProdutoEstoque.
     *
     * @param vendaId - ID da Venda
     * @param produtoEstoqueId - ID do ProdutoEstoque
     * @return Optional<ItemVenda> - Item encontrado
     */
    Optional<ItemVenda> findByVendaAndProdutoEstoque(int vendaId, int produtoEstoqueId);
}
