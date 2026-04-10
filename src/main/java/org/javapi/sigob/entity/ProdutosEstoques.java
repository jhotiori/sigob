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

    /**
     * Construtor para criar ProdutosEstoques
     *
     * @return ProdutosEstoques - O ProdutosEstoques que foi criado
     */
    public ProdutosEstoques() {
    }

    /**
     * Construtor para criar ProdutosEstoques
     *
     * @param id           O ID do ProdutosEstoques
     * @param nrQuantidade A quantidade do ProdutosEstoques
     * @param dsObservacao A observacao do ProdutosEstoques
     * @param produto      O Produto do ProdutosEstoques
     * @param estoque      O Estoque do ProdutosEstoques
     * @return ProdutosEstoques - O ProdutosEstoques que foi criado
     */
    public ProdutosEstoques(int id, int nrQuantidade, String dsObservacao, Produto produto, Estoque estoque) {
        this.idProdutosEstoque = id;
        this.nrQuantidade = nrQuantidade;
        this.dsObservacao = dsObservacao;
        this.produto = produto;
        this.estoque = estoque;
    }

    /**
     * Atribui o ID de ProdutosEstoques
     *
     * @param idProdutosEstoque O ID de ProdutosEstoques
     */
    public void setIdProdutosEstoque(int idProdutosEstoque) {
        this.idProdutosEstoque = idProdutosEstoque;
    }

    /**
     * Atribui a quantidade de ProdutosEstoques
     *
     * @param nrQuantidade A quantidade de ProdutosEstoques
     */
    public void setNrQuantidade(int nrQuantidade) {
        this.nrQuantidade = nrQuantidade;
    }

    /**
     * Atribui a observacao de ProdutosEstoques
     *
     * @param dsObservacao A observacao de ProdutosEstoques
     */
    public void setDsObservacao(String dsObservacao) {
        this.dsObservacao = dsObservacao;
    }

    /**
     * Atribui o Produto de ProdutosEstoques
     *
     * @param produto O Produto de ProdutosEstoques
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Atribui o Estoque de ProdutosEstoques
     *
     * @param estoque O Estoque de ProdutosEstoques
     */
    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    /**
     * Retorna o ID de ProdutosEstoque
     *
     * @return idProdutosEstoque - o ID de ProdutosEstoque
     */
    public int getIdProdutosEstoque() {
        return this.idProdutosEstoque;
    }

    /**
     * Retorna a quantidade de ProdutosEstoque
     *
     * @return nrQuantidade - a quantidade de ProdutosEstoque
     */
    public int getNrQuantidade() {
        return this.nrQuantidade;
    }

    /**
     * Retorna a observacao de ProdutosEstoque
     *
     * @return dsObservacao - a observacao de ProdutosEstoque
     */
    public String getDsObservacao() {
        return this.dsObservacao;
    }

    /**
     * Retorna o Produto de ProdutosEstoque
     *
     * @return produto - o Produto de ProdutosEstoque
     */
    public Produto getProduto() {
        return this.produto;
    }

    /**
     * Retorna o Estoque de ProdutosEstoque
     *
     * @return estoque - o Estoque de ProdutosEstoque
     */
    public Estoque getEstoque() {
        return this.estoque;
    }

    @Override
    public String toString() {
        return "ProdutosEstoques(Id = %d, Quantidade = %d, Observacao = %s, Produto = %s, Estoque = %s)"
                .formatted(this.getIdProdutosEstoque(), this.getNrQuantidade(), this.getDsObservacao(),
                        this.getProduto().toString(), this.getEstoque().toString());
    }
}
