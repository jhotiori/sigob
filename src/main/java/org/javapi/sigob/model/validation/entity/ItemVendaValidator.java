package org.javapi.sigob.model.validation.entity;

import java.math.BigDecimal;

import org.javapi.sigob.model.entity.ItemVenda;
import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade ItemVenda.
 */
public final class ItemVendaValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private ItemVendaValidator() {
    }

    /**
     * Valida um item de venda por completo.
     *
     * @param itemVenda - Item de venda a ser validado
     */
    public static void validate(ItemVenda itemVenda) {
        Validators.notNull(
                itemVenda,
                "Item de venda não pode ser nulo!"
            );

        validateQuantidade(itemVenda.getQuantidade());
        validateValorSaldo(itemVenda.getValorSaldo());
        validateProdutoEstoque(itemVenda.getProdutoEstoque());
        validateVenda(itemVenda.getVenda());
    }

    /**
     * Valida a quantidade de um item de venda.
     *
     * @param quantidade - Quantidade a ser validada
     */
    public static void validateQuantidade(int quantidade) {
        Validators.positive(
                quantidade,
                "Quantidade deve ser maior que 0!"
            );
    }

    /**
     * Valida o valor saldo de um item de venda.
     *
     * @param valorSaldo - Valor saldo a ser validado
     */
    public static void validateValorSaldo(BigDecimal valorSaldo) {
        Validators.positive(
                valorSaldo,
                "Valor saldo deve ser maior que 0!"
            );
    }

    /**
     * Valida o produto em estoque de um item de venda.
     *
     * @param produtoEstoque - Produto em estoque a ser validado
     */
    public static void validateProdutoEstoque(ProdutosEstoques produtoEstoque) {
        Validators.notNull(
                produtoEstoque,
                "Produto em estoque não pode ser nulo!"
            );
    }

    /**
     * Valida a venda de um item de venda.
     *
     * @param venda - Venda a ser validada
     */
    public static void validateVenda(Venda venda) {
        Validators.notNull(
                venda,
                "Venda não pode ser nula!"
            );
    }
}
