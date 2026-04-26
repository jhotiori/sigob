package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "produtos_estoques")
public class ProdutosEstoques {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "estoque_id", nullable = false)
    private Estoque estoque;

    /**
     * Construtor padrão JPA
     */
    public ProdutosEstoques() {
    }

    /**
     * Construtor completo para criar um novo ProdutoEstoque
     *
     * @param id O ID do ProdutoEstoque
     * @param quantidade A Quantidade do ProdutoEstoque
     * @param produto O Produto do ProdutoEstoque
     * @param estoque O Estoque do ProdutoEstoque
     */
    public ProdutosEstoques(int id, int quantidade, Produto produto, Estoque estoque) {
        this.id = id;
        this.quantidade = quantidade;
        this.produto = produto;
        this.estoque = estoque;
    }

    /**
     * Atribui o ID do ProdutoEstoque
     *
     * @param id O ID do ProdutoEstoque
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui a Quantidade do ProdutoEstoque
     *
     * @param quantidade A Quantidade do ProdutoEstoque
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Atribui o Produto do ProdutoEstoque
     *
     * @param produto O Produto do ProdutoEstoque
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Atribui o Estoque do ProdutoEstoque
     *
     * @param estoque O Estoque do ProdutoEstoque
     */
    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    /**
     * Retorna o ID do ProdutoEstoque
     *
     * @return id - O ID do ProdutoEstoque
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a Quantidade do ProdutoEstoque
     *
     * @return quantidade - A Quantidade do ProdutoEstoque
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Retorna o Produto do ProdutoEstoque
     *
     * @return produto - O Produto do ProdutoEstoque
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Retorna o Estoque do ProdutoEstoque
     *
     * @return estoque - O Estoque do ProdutoEstoque
     */
    public Estoque getEstoque() {
        return estoque;
    }

    @Override
    public String toString() {
        return "ProdutosEstoques(Id = %d, Quantidade = %d, Produto = %s)"
                .formatted(this.getId(), this.getQuantidade(), this.getProduto().getNome());
    }
}
