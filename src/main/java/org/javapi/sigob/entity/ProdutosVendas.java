package org.javapi.sigob.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity (name = "produtosVendas")
public class ProdutosVendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProdutoVenda;

    @Column (name = "nrQuantidade")
    private int nrQuantidade;

    @Column (name = "vlSaldo")
    private BigDecimal vlSaldo;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "fk_idProduto")
    private Produto produto;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "fk_idVenda")
    private Venda venda;

    //Constructor
    public ProdutosVendas() {}

    public ProdutosVendas(int idProdutoVenda, int nrQuantidade, BigDecimal vlSaldo,  Produto produto, Venda venda) {
        this.idProdutoVenda = idProdutoVenda;
        this.nrQuantidade = nrQuantidade;
        this.vlSaldo = vlSaldo;
        this.produto = produto;
        this.venda = venda;
    }

    //Setters
    public void setIdProdutoVenda(int idProdutoVenda) {
        this.idProdutoVenda = idProdutoVenda;
    }
    public void setNrQuantidade(int nrQuantidade) {
        this.nrQuantidade = nrQuantidade;
    }
    public void setVlSaldo(BigDecimal vlSaldo) {
        this.vlSaldo = vlSaldo;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    //Getters
    public  int getIdProdutoVenda() {
        return idProdutoVenda;
    }
    public int getNrQuantidade() {
        return nrQuantidade;
    }
    public BigDecimal getVlSaldo() {
        return vlSaldo;
    }
    public Produto getProduto() {
        return produto;
    }
    public Venda getVenda() {
        return venda;
    }

    //ToString
    @Override
    public String toString() {
        String obj;
        obj = String.format("ID: %d | QTDE.: %d | VL: %,.2f", this.idProdutoVenda, this.nrQuantidade, this.vlSaldo);
        return obj;
    }
}
