package org.javapi.sigob.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.ItemVenda;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.repository.ItemVendaRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class ItemVendaService {

    /**
     * Cria um novo ItemVendaService
     *
     * @return ItemVendaService - O serviço
     */
    public ItemVendaService() {
    }

    /**
     * Salva um ItemVenda
     *
     * @param itemVenda O ItemVenda a ser salvo
     * @throws IllegalArgumentException Se o ItemVenda for inválido
     */
    public void save(ItemVenda itemVenda) {
        validateItemVenda(itemVenda);

        TransactionExecutor.executeVoid(em -> {
            new ItemVendaRepository(em).save(itemVenda);
        });
    }

    /**
     * Atualiza um ItemVenda
     *
     * @param itemVenda O ItemVenda a ser atualizado
     * @throws IllegalArgumentException Se o ItemVenda for inválido
     */
    public void update(ItemVenda itemVenda) {
        validateItemVenda(itemVenda);

        TransactionExecutor.executeVoid(em -> {
            new ItemVendaRepository(em).update(itemVenda);
        });
    }

    /**
     * Remove um ItemVenda
     *
     * @param itemVenda O ItemVenda a ser removido
     * @throws IllegalArgumentException Se o ItemVenda for inválido
     */
    public void delete(ItemVenda itemVenda) {
        validateItemVenda(itemVenda);

        TransactionExecutor.executeVoid(em -> {
            new ItemVendaRepository(em).deleteById(itemVenda.getId());
        });
    }

    /**
     * Confere se um ItemVenda existe
     *
     * @param itemVenda O ItemVenda
     * @return boolean - true se existir, false caso contrário
     */
    public boolean contains(ItemVenda itemVenda) {
        validateItemVenda(itemVenda);

        return TransactionExecutor.query(em -> {
            return new ItemVendaRepository(em).contains(itemVenda.getId());
        });
    }

    /**
     * Retorna todos os ItemVendas
     *
     * @return List<ItemVenda> - A lista de ItemVenda
     */
    public List<ItemVenda> findAll() {
        return TransactionExecutor.query(em -> {
            return new ItemVendaRepository(em).findAll();
        });
    }

    /**
     * Busca por ID
     *
     * @param id O ID do ItemVenda
     * @return Optional<ItemVenda> - O ItemVenda encontrado
     */
    public Optional<ItemVenda> findById(int id) {
        validateId(id);

        return TransactionExecutor.query(em -> {
            return new ItemVendaRepository(em).findById(id);
        });
    }

    /**
     * Busca por ProdutoEstoque
     *
     * @param produtoEstoqueId ID do ProdutoEstoque
     * @return List<ItemVenda> - A lista de ItemVenda
     */
    public List<ItemVenda> findByProdutoEstoque(int produtoEstoqueId) {
        validateId(produtoEstoqueId);

        return TransactionExecutor.query(em -> {
            return new ItemVendaRepository(em).findByProdutoEstoque(produtoEstoqueId);
        });
    }

    /**
     * Busca por Venda
     *
     * @param vendaId ID da Venda
     * @return List<ItemVenda> - A lista de ItemVenda
     */
    public List<ItemVenda> findByVenda(int vendaId) {
        validateId(vendaId);

        return TransactionExecutor.query(em -> {
            return new ItemVendaRepository(em).findByVenda(vendaId);
        });
    }

    /**
     * Busca única por Venda + ProdutoEstoque
     *
     * @param vendaId ID da Venda
     * @param produtoEstoqueId ID do ProdutoEstoque
     * @return Optional<ItemVenda> - O ItemVenda encontrado
     */
    public Optional<ItemVenda> findByVendaAndProdutoEstoque(int vendaId, int produtoEstoqueId) {
        validateId(vendaId);
        validateId(produtoEstoqueId);

        return TransactionExecutor.query(em -> {
            return new ItemVendaRepository(em)
                    .findByVendaAndProdutoEstoque(vendaId, produtoEstoqueId);
        });
    }

    /**
     * Valida um ItemVenda completo
     *
     * @param itemVenda O ItemVenda
     * @throws IllegalArgumentException Se o ItemVenda for inválido
     */
    private void validateItemVenda(ItemVenda itemVenda) {
        Validator.start()
                .expectNotNull(itemVenda, "ItemVenda não pode ser nulo")
                .validate();

        validateQuantidade(itemVenda.getQuantidade());
        validateValorUnitario(itemVenda.getValorUnitario());
        validateProdutoEstoque(itemVenda.getProdutoEstoque());
        validateVenda(itemVenda.getVenda());
    }

    /**
     * Valida quantidade
     *
     * @param quantidade A quantidade
     * @throws IllegalArgumentException Se a quantidade for inválida
     */
    private void validateQuantidade(int quantidade) {
        Validator.start()
                .expect(quantidade, q -> q > 0, "Quantidade deve ser maior que 0")
                .validate();
    }

    /**
     * Valida valor unitário
     *
     * @param valorUnitario O valor
     * @throws IllegalArgumentException Se o valor for inválido
     */
    private void validateValorUnitario(BigDecimal valorUnitario) {
        Validator.start()
                .expect(valorUnitario,
                        v -> v != null && v.compareTo(BigDecimal.ZERO) > 0,
                        "Valor unitário deve ser maior que 0")
                .validate();
    }

    /**
     * Valida ProdutoEstoque
     *
     * @param produtoEstoque O ProdutoEstoque
     * @throws IllegalArgumentException Se o ProdutoEstoque for inválido
     */
    private void validateProdutoEstoque(ProdutosEstoques produtoEstoque) {
        Validator.start()
                .expectNotNull(produtoEstoque, "ProdutoEstoque não pode ser nulo")
                .validate();
    }

    /**
     * Valida Venda
     *
     * @param venda A Venda
     * @throws IllegalArgumentException Se a Venda for inválida
     */
    private void validateVenda(Venda venda) {
        Validator.start()
                .expectNotNull(venda, "Venda não pode ser nula")
                .validate();
    }

    /**
     * Valida ID
     *
     * @param id O ID
     * @throws IllegalArgumentException Se o ID for inválido
     */
    private void validateId(int id) {
        Validator.start()
                .expectNotNull(id, "ID não pode ser nulo")
                .validate();
    }
}
