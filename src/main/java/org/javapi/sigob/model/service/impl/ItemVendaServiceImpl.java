package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.ItemVenda;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.ItemVendaRepositoryImpl;
import org.javapi.sigob.model.service.ItemVendaService;
import org.javapi.sigob.model.validation.entity.ItemVendaValidator;

public class ItemVendaServiceImpl implements ItemVendaService {

    /**
     * Cria um novo ItemVendaService
     *
     * @return ItemVendaService - O serviço
     */
    public ItemVendaServiceImpl() {
    }

    /**
     * Salva um novo item de venda.
     *
     * @param itemVenda - Item de venda a ser salvo.
     */
    @Override
    public void save(ItemVenda itemVenda) {
        ItemVendaValidator.validate(itemVenda);
        Database.write(
                ItemVendaRepositoryImpl::new,
                repo -> repo.save(itemVenda)
        );
    }

    /**
     * Atualiza um item de venda existente.
     *
     * @param itemVenda - Item de venda a ser atualizado.
     */
    @Override
    public void update(ItemVenda itemVenda) {
        ItemVendaValidator.validate(itemVenda);
        Database.write(
                ItemVendaRepositoryImpl::new,
                repo -> repo.update(itemVenda)
        );
    }

    /**
     * Remove um item de venda.
     *
     * @param itemVenda - Item de venda a ser removido.
     */
    @Override
    public void delete(ItemVenda itemVenda) {
        Database.write(
                ItemVendaRepositoryImpl::new,
                repo -> repo.deleteById(itemVenda.getId())
        );
    }

    /**
     * Verifica se existe um item de venda com o ID informado.
     *
     * @param id - ID do item de venda.
     * @return boolean - True caso exista, false caso contrário.
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                ItemVendaRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca todos os itens de venda cadastrados.
     *
     * @return List<ItemVenda> - Lista de itens de venda encontrados.
     */
    @Override
    public List<ItemVenda> findAll() {
        return Database.read(
                ItemVendaRepositoryImpl::new,
                ItemVendaRepositoryImpl::findAll
        );
    }

    /**
     * Busca um item de venda pelo ID.
     *
     * @param id - ID do item de venda.
     * @return Optional<ItemVenda> - Item de venda encontrado, se existir.
     */
    @Override
    public Optional<ItemVenda> findById(Integer id) {
        return Database.read(
                ItemVendaRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca itens de venda pelo produto em estoque.
     *
     * @param produtoEstoqueId - ID do produto em estoque utilizado na busca.
     * @return List<ItemVenda> - Lista de itens de venda encontrados.
     */
    @Override
    public List<ItemVenda> findByProdutoEstoque(int produtoEstoqueId) {
        return Database.read(
                ItemVendaRepositoryImpl::new,
                repo -> repo.findByProdutoEstoque(produtoEstoqueId)
        );
    }

    /**
     * Busca itens de venda pela venda.
     *
     * @param vendaId - ID da venda utilizada na busca.
     * @return List<ItemVenda> - Lista de itens de venda encontrados.
     */
    @Override
    public List<ItemVenda> findByVenda(int vendaId) {
        return Database.read(
                ItemVendaRepositoryImpl::new,
                repo -> repo.findByVenda(vendaId)
        );
    }

    /**
     * Busca um item de venda pela venda e produto em estoque.
     *
     * @param vendaId - ID da venda utilizada na busca.
     * @param produtoEstoqueId - ID do produto em estoque utilizado na busca.
     * @return Optional<ItemVenda> - Item de venda encontrado, se existir.
     */
    @Override
    public Optional<ItemVenda> findByVendaAndProdutoEstoque(
            int vendaId,
            int produtoEstoqueId
    ) {
        return Database.read(
                ItemVendaRepositoryImpl::new,
                repo -> repo.findByVendaAndProdutoEstoque(vendaId, produtoEstoqueId)
        );
    }
}
