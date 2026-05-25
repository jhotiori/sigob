package org.javapi.sigob.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "valor_compra", nullable = false)
    private BigDecimal valorCompra;

    @Column(name = "valor_venda", nullable = false)
    private BigDecimal valorVenda;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "moeda_id", nullable = true)
    private Moeda moeda;

    /**
     * Construtor padrão JPA
     */
    public Produto() {
    }

    /**
     * Construtor completo para criar um novo Produto
     *
     * @param id O ID do Produto
     * @param codigo O Código do Produto
     * @param nome O Nome do Produto
     * @param valorCompra O Valor de Compra do Produto
     * @param valorVenda O Valor de Venda do Produto
     * @param categoria A Categoria do Produto
     * @param moeda A Moeda do Produto (opcional)
     */
    public Produto(int id, String codigo, String nome, BigDecimal valorCompra, BigDecimal valorVenda,
            Categoria categoria, Moeda moeda) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.valorCompra = valorCompra;
        this.valorVenda = valorVenda;
        this.categoria = categoria;
        this.moeda = moeda;
    }

    /**
     * Atribui o ID do Produto
     *
     * @param id O ID do Produto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui o Código do Produto
     *
     * @param codigo O Código do Produto
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Atribui o Nome do Produto
     *
     * @param nome O Nome do Produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Atribui o Valor de Compra do Produto
     *
     * @param valorCompra O Valor de Compra do Produto
     */
    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    /**
     * Atribui o Valor de Venda do Produto
     *
     * @param valorVenda O Valor de Venda do Produto
     */
    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    /**
     * Atribui a Categoria do Produto
     *
     * @param categoria A Categoria do Produto
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Atribui a Moeda do Produto
     *
     * @param moeda A Moeda do Produto
     */
    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    /**
     * Retorna o ID do Produto
     *
     * @return id - O ID do Produto
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Código do Produto
     *
     * @return codigo - O Código do Produto
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o Nome do Produto
     *
     * @return nome - O Nome do Produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o Valor de Compra do Produto
     *
     * @return valorCompra - O Valor de Compra do Produto
     */
    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    /**
     * Retorna o Valor de Venda do Produto
     *
     * @return valorVenda - O Valor de Venda do Produto
     */
    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    /**
     * Retorna a Categoria do Produto
     *
     * @return categoria - A Categoria do Produto
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Retorna a Moeda do Produto
     *
     * @return moeda - A Moeda do Produto (pode ser null)
     */
    public Moeda getMoeda() {
        return moeda;
    }

    @Override
    public String toString() {
        return "Produto(Id = %d, Codigo = %s, Nome = %s, ValorCompra = %s, ValorVenda = %s)"
                .formatted(this.getId(), this.getCodigo(), this.getNome(), this.getValorCompra(), this.getValorVenda());
    }
}
