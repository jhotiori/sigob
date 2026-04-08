package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtosEstoques")
public class ProdutosEstoques {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProdutosEstoque;

    @Column(name = "nrQuantidade")
    private int nrQuantidade;

    @Column(name = "dsObservacao")
    private String dsObservacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idProduto")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idEstoque")
    private Estoque estoque;

    public ProdutosEstoques() {}
    public ProdutosEstoques(int id, int nrQuantidade, String dsObservacao, Produto produto, Estoque estoque) {
        this.idProdutosEstoque = id;
        this.nrQuantidade = nrQuantidade;
        this.dsObservacao = dsObservacao;
        this.produto = produto;
        this.estoque = estoque;
    }

    public void setIdProdutosEstoque(int idProdutosEstoque) {
        this.idProdutosEstoque = idProdutosEstoque;
    }

    public void setNrQuantidade(int nrQuantidade) {
        this.nrQuantidade = nrQuantidade;
    }

    public void setDsObservacao(String dsObservacao) {
        this.dsObservacao = dsObservacao;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public int getIdProdutosEstoque() {
        return idProdutosEstoque;
    }

    public int getNrQuantidade() {
        return nrQuantidade;
    }

    public String getDsObservacao() {
        return dsObservacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    @Override
    public String toString() {
        return "ProdutosEstoques(Id = %d, Quantidade = %d, Observacao = %s, Produto = %s, Estoque = %s)"
            .formatted(this.getIdProdutosEstoque(), this.getNrQuantidade(), this.getDsObservacao(), this.getProduto().toString(), this.getEstoque().toString());
    }
}
