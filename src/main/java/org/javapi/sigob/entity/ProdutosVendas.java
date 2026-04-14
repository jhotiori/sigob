package org.javapi.sigob.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "produtosVendas")
public class ProdutosVendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProdutoVenda;

    @Column(name = "nrQuantidade")
    private int nrQuantidade;

    @Column(name = "vlSaldo")
    private BigDecimal vlSaldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idProduto")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idVenda")
    private Venda venda;

    /**
     * Construtor para criar ProdutosVendas
     *
     * @return ProdutosVendas - O ProdutosVendas criado
     */
    public ProdutosVendas() {
    }

    /**
     * Construtor para criar ProdutosVendas
     *
     * @param idProdutoVenda O ID do ProdutosVendas
     * @param nrQuantidade   A quantidade de ProdutosVendas
     * @param vlSaldo        O valor do ProdutosVendas
     * @param produto        O Produto do ProdutosVendas
     * @param venda          A Venda do ProdutosVendas
     * @return ProdutosVendas - O ProdutosVendas criado
     */
    public ProdutosVendas(int idProdutoVenda, int nrQuantidade, BigDecimal vlSaldo, Produto produto, Venda venda) {
        this.idProdutoVenda = idProdutoVenda;
        this.nrQuantidade = nrQuantidade;
        this.vlSaldo = vlSaldo;
        this.produto = produto;
        this.venda = venda;
    }

    /**
     * Atribui o ID de ProdutosVendas
     *
     * @param idProdutoVenda O ID de ProdutosVendas
     */
    public void setIdProdutoVenda(int idProdutoVenda) {
        this.idProdutoVenda = idProdutoVenda;
    }

    /**
     * Atribui a quantidade de ProdutosVendas
     *
     * @param nrQuantidade A quantidade de ProdutosVendas
     */
    public void setNrQuantidade(int nrQuantidade) {
        this.nrQuantidade = nrQuantidade;
    }

    /**
     * Atribui o valor de ProdutosVendas
     *
     * @param vlSaldo O valor de ProdutosVendas
     */
    public void setVlSaldo(BigDecimal vlSaldo) {
        this.vlSaldo = vlSaldo;
    }

    /**
     * Atribui o Produto de ProdutosVendas
     *
     * @param produto O Produto de ProdutosVendas
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Atribui a Venda de ProdutosVendas
     *
     * @param venda A Venda de ProdutosVendas
     */
    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    /**
     * Retorna o ID de ProdutosVendas
     *
     * @return idProdutoVenda - O ID de ProdutosVendas
     */
    public int getIdProdutoVenda() {
        return this.idProdutoVenda;
    }

    /**
     * Retorna a quantidade de ProdutosVendas
     *
     * @return nrQuantidade - A quantidade de ProdutosVendas
     */
    public int getNrQuantidade() {
        return this.nrQuantidade;
    }

    /**
     * Retorna o valor de ProdutosVendas
     *
     * @return vlSaldo - O valor de ProdutosVendas
     */
    public BigDecimal getVlSaldo() {
        return this.vlSaldo;
    }

    /**
     * Retorna o Produto de ProdutosVendas
     *
     * @return produto - O Produto de ProdutosVendas
     */
    public Produto getProduto() {
        return this.produto;
    }

    /**
     * Retorna a Venda de ProdutosVendas
     *
     * @return venda - A Venda de ProdutosVendas
     */
    public Venda getVenda() {
        return this.venda;
    }

    @Override
    public String toString() {
        return "ProdutosVendas(Id = %d, Quantidade = %d, Saldo = %s, Produto = %s, Venda = %s)"
                .formatted(this.getIdProdutoVenda(), this.getNrQuantidade(), this.getVlSaldo(),
                        this.getProduto().toString(), this.getVenda().toString());
    }
}
