package org.javapi.sigob.model.repository.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.ItemVenda;
import org.javapi.sigob.model.repository.ItemVendaRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação de repositório para ItemVenda.
 */
public final class ItemVendaRepositoryImpl extends JpaCrudRepositoryImpl<ItemVenda, Integer> implements ItemVendaRepository {
        /**
         * Cria um novo ItemVendaRepository.
         *
         * @param entityManager - EntityManager do repositório
         */
        public ItemVendaRepositoryImpl(EntityManager entityManager) {
                super(entityManager, ItemVenda.class);
        }

        /**
         * Busca um ItemVenda pela combinação de Venda e ProdutoEstoque.
         *
         * @param vendaId          - ID da Venda
         * @param produtoEstoqueId - ID do ProdutoEstoque
         * @return Optional<ItemVenda> - O ItemVenda encontrado
         */
        @Override
        public Optional<ItemVenda> findByVendaAndProdutoEstoque(
                        int vendaId,
                        int produtoEstoqueId) {
                return query("""
                                SELECT iv
                                FROM %s iv
                                WHERE iv.venda.id = :vendaId
                                  AND iv.produtoEstoque.id = :produtoEstoqueId
                                """)
                                .param("vendaId", vendaId)
                                .param("produtoEstoqueId", produtoEstoqueId)
                                .one();
        }

        /**
         * Busca ItemVenda pelo ProdutoEstoque.
         *
         * @param produtoEstoqueId - ID do ProdutoEstoque
         * @return List<ItemVenda> - Os ItemVenda encontrados
         */
        @Override
        public List<ItemVenda> findByProdutoEstoque(
                        int produtoEstoqueId) {
                return query("""
                                SELECT iv
                                FROM %s iv
                                WHERE iv.produtoEstoque.id = :produtoEstoqueId
                                """)
                                .param("produtoEstoqueId", produtoEstoqueId)
                                .list();
        }

        /**
         * Busca ItemVenda pela Venda.
         *
         * @param vendaId - ID da Venda
         * @return List<ItemVenda> - Os ItemVenda encontrados
         */
        @Override
        public List<ItemVenda> findByVenda(
                        int vendaId) {
                return query("""
                                SELECT iv
                                FROM %s iv
                                WHERE iv.venda.id = :vendaId
                                """)
                                .param("vendaId", vendaId)
                                .list();
        }
}
