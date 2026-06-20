package org.javapi.sigob.model.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.ItemVenda;

public interface ItemVendaService extends JpaCrudService<ItemVenda, Integer> {

    /**
     * Busca itens de venda pelo produto em estoque.
     *
     * @param produtoEstoqueId - Identificador do produto em estoque.
     * @return List<ItemVenda> - Lista de itens de venda encontrados.
     */
    List<ItemVenda> findByProdutoEstoque(int produtoEstoqueId);

    /**
     * Busca itens de venda pela venda.
     *
     * @param vendaId - Identificador da venda.
     * @return List<ItemVenda> - Lista de itens de venda encontrados.
     */
    List<ItemVenda> findByVenda(int vendaId);

    /**
     * Busca um item de venda pela venda e produto em estoque.
     *
     * @param vendaId - Identificador da venda.
     * @param produtoEstoqueId - Identificador do produto em estoque.
     * @return Optional<ItemVenda> - Item de venda encontrado, se existir.
     */
    Optional<ItemVenda> findByVendaAndProdutoEstoque(int vendaId, int produtoEstoqueId);
}
