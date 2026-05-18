package org.javapi.sigob.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "item_vendas")
@Table(
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"venda_id", "produtoEstoque_id"})
        }
)
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "valor_saldo", nullable = false)
    private BigDecimal valorSaldo;

    @ManyToOne
    @JoinColumn(name = "produtoEstoque_id", nullable = false)
    private ProdutosEstoques produtoEstoque;

    @ManyToOne
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    /**
     * Construtor padrão JPA
     */
    public ItemVenda() {
    }

    /**
     * Construtor completo para criar um novo ItemVenda
     *
     * @param id O ID do ItemVenda
     * @param quantidade A Quantidade do ItemVenda
     * @param valorSaldo O Valor Unitário do ItemVenda
     * @param produtoEstoque O ProdutoEstoque do ItemVenda
     * @param venda A Venda do ItemVenda
     */
    public ItemVenda(int id, int quantidade, BigDecimal valorSaldo, ProdutosEstoques produtoEstoque, Venda venda) {
        this.id = id;
        this.quantidade = quantidade;
        this.valorSaldo = valorSaldo;
        this.produtoEstoque = produtoEstoque;
        this.venda = venda;
    }

    /**
     * Atribui o ID do ItemVenda
     *
     * @param id O ID do ItemVenda
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui a Quantidade do ItemVenda
     *
     * @param quantidade A Quantidade do ItemVenda
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Atribui o Valor Unitário do ItemVenda
     *
     * @param valorSaldo O Valor Unitário do ItemVenda
     */
    public void setValorSaldo(BigDecimal valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

    /**
     * Atribui o ProdutoEstoque do ItemVenda
     *
     * @param produtoEstoque O ProdutoEstoque do ItemVenda
     */
    public void setProdutoEstoque(ProdutosEstoques produtoEstoque) {
        this.produtoEstoque = produtoEstoque;
    }

    /**
     * Atribui a Venda do ItemVenda
     *
     * @param venda A Venda do ItemVenda
     */
    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    /**
     * Retorna o ID do ItemVenda
     *
     * @return id - O ID do ItemVenda
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a Quantidade do ItemVenda
     *
     * @return quantidade - A Quantidade do ItemVenda
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Retorna o Valor Unitário do ItemVenda
     *
     * @return valorSaldo - O Valor Unitário do ItemVenda
     */
    public BigDecimal getValorSaldo() {
        return valorSaldo;
    }

    /**
     * Retorna o ProdutoEstoque do ItemVenda
     *
     * @return produtoEstoque - O ProdutoEstoque do ItemVenda
     */
    public ProdutosEstoques getProdutoEstoque() {
        return produtoEstoque;
    }

    /**
     * Retorna a Venda do ItemVenda
     *
     * @return venda - A Venda do ItemVenda
     */
    public Venda getVenda() {
        return venda;
    }

    @Override
    public String toString() {
        return "ItemVenda(Id = %d, Quantidade = %d, valorSaldo = %s)"
                .formatted(this.getId(), this.getQuantidade(), this.getValorSaldo());
    }
}
