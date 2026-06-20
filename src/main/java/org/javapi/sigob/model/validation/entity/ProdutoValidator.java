package org.javapi.sigob.model.validation.entity;

import java.math.BigDecimal;

import org.javapi.sigob.model.entity.Produto;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Produto.
 */
public final class ProdutoValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private ProdutoValidator() {
    }

    /**
     * Valida um produto por completo.
     *
     * @param produto - Produto a ser validado
     * @return Produto - Produto validado
     */
    public static Produto validate(Produto produto) {
        Validators.notNull(
                produto,
                "Produto não pode ser nulo!"
            );

        validateNome(produto.getNome());
        validateCodigo(produto.getCodigo());
        validateValorCompra(produto.getValorCompra());
        validateValorVenda(produto.getValorVenda());
        validateCategoria(produto);
        validatePreco(
                produto.getValorCompra(),
                produto.getValorVenda()
            );

        return produto;
    }

    /**
     * Valida o nome do produto.
     *
     * @param nome - Nome a ser validado
     * @return String - Nome validado
     */
    public static String validateNome(String nome) {
        Validators.notBlank(
                nome,
                "Nome do produto não pode ser nulo ou vazio!"
            );

        Validators.maxLength(
                nome,
                128,
                "Nome do produto deve possuir no máximo 128 caracteres!"
            );

        return nome;
    }

    /**
     * Valida o código do produto.
     *
     * @param codigo - Código a ser validado
     * @return String - Código validado
     */
    public static String validateCodigo(String codigo) {
        Validators.notBlank(
                codigo,
                "Código do produto não pode ser nulo ou vazio!"
            );

        Validators.maxLength(
                codigo,
                64,
                "Código do produto deve possuir no máximo 64 caracteres!"
            );

        return codigo;
    }

    /**
     * Valida o valor de compra.
     *
     * @param valorCompra - Valor de compra a ser validado
     * @return BigDecimal - Valor validado
     */
    public static BigDecimal validateValorCompra(BigDecimal valorCompra) {
        Validators.positive(
                valorCompra,
                "Valor de compra deve ser maior que zero!"
            );

        return valorCompra;
    }

    /**
     * Valida o valor de venda.
     *
     * @param valorVenda - Valor de venda a ser validado
     * @return BigDecimal - Valor validado
     */
    public static BigDecimal validateValorVenda(BigDecimal valorVenda) {
        Validators.positive(
                valorVenda,
                "Valor de venda deve ser maior que zero!"
            );

        return valorVenda;
    }

    /**
     * Valida a categoria associada ao produto.
     *
     * @param produto - Produto a ser validado
     * @return Produto - Produto validado
     */
    public static Produto validateCategoria(Produto produto) {
        Validators.notNull(
                produto.getCategoria(),
                "Categoria não pode ser nula!"
            );

        return produto;
    }

    /**
     * Valida a consistência dos preços do produto.
     *
     * @param custo - Valor de custo
     * @param venda - Valor de venda
     */
    public static void validatePreco(
            BigDecimal custo,
            BigDecimal venda
        ) {
        Validators.expect(
                venda.compareTo(custo) >= 0,
                "Valor de venda não pode ser menor que o custo!"
            );
    }

    /**
     * Valida o nome da moeda.
     *
     * @param moeda - Nome da moeda a ser validado
     */
    public static void validateMoeda(String moeda) {
        Validators.notBlank(
                moeda,
                "Moeda nao pode ser nula ou vazia!"
            );
    }
}
