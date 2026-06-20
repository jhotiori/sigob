package org.javapi.sigob.model.validation.entity;

import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade ProdutosEstoques.
 */
public final class ProdutosEstoquesValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private ProdutosEstoquesValidator() {
    }

    /**
     * Valida um vínculo de produto e estoque por completo.
     *
     * @param produtoEstoque - Vínculo a ser validado
     * @return ProdutosEstoques - Vínculo validado
     */
    public static ProdutosEstoques validate(
            ProdutosEstoques produtoEstoque
        ) {
        Validators.notNull(
                produtoEstoque,
                "ProdutosEstoques não pode ser nulo!"
        );

        validateProduto(produtoEstoque);
        validateEstoque(produtoEstoque);
        validateQuantidade(produtoEstoque.getQuantidade());

        return produtoEstoque;
    }

    /**
     * Valida o produto associado ao vínculo.
     *
     * @param produtoEstoque - Vínculo a ser validado
     * @return ProdutosEstoques - Vínculo validado
     */
    public static ProdutosEstoques validateProduto(
            ProdutosEstoques produtoEstoque
        ) {
        Validators.notNull(
                produtoEstoque.getProduto(),
                "Produto não pode ser nulo!"
        );

        return produtoEstoque;
    }

    /**
     * Valida o estoque associado ao vínculo.
     *
     * @param produtoEstoque - Vínculo a ser validado
     * @return ProdutosEstoques - Vínculo validado
     */
    public static ProdutosEstoques validateEstoque(
            ProdutosEstoques produtoEstoque
        ) {
        Validators.notNull(
                produtoEstoque.getEstoque(),
                "Estoque não pode ser nulo!"
        );

        return produtoEstoque;
    }

    /**
     * Valida a quantidade do vínculo.
     *
     * @param quantidade - Quantidade a ser validada
     * @return int - Quantidade validada
     */
    public static int validateQuantidade(int quantidade) {
        return Validators.positive(
                quantidade,
                "Quantidade deve ser maior que zero!"
        );
    }

    /**
     * Valida o estoque de destino da transferência.
     *
     * @param destino - Estoque de destino
     * @return Estoque - Estoque validado
     */
    public static Estoque validateDestino(
            Estoque destino
        ) {
        return Validators.notNull(
                destino,
                "Destino não pode ser nulo!"
        );
    }

    /**
     * Valida a quantidade informada para transferência.
     *
     * @param quantidade - Quantidade a ser transferida
     * @return int - Quantidade validada
     */
    public static int validateQuantidadeTransferencia(
            int quantidade
        ) {
        return Validators.positive(
                quantidade,
                "Quantidade deve ser maior que zero!"
        );
    }
}
