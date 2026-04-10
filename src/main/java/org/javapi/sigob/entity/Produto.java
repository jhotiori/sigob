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
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProduto;

    @Column(name = "cdProduto")
    private String cdProduto;

    @Column(name = "nmProduto")
    private String nmProduto;

    @Column(name = "dsProduto")
    private String dsProduto;

    @Column(name = "vlCusto")
    private BigDecimal vlCusto;

    @Column(name = "vlProduto")
    private BigDecimal vlProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idCategoria")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idMoeda")
    private Moeda moeda;

    /**
     * Construtor para criar um novo Produto
     *
     * @return Produto - O produto que foi criado
     */
    public Produto() {
    }

    /**
     * Construtor para criar um novo Produto
     *
     * @param id        O ID do Produto
     * @param cd        O Codigo do Produto
     * @param nm        O Nome do Produto
     * @param ds        A Descricao do Produto
     * @param vlCusto   O Valor de Custo do Produto
     * @param vlProduto O Valor do Produto
     * @param cat       A Categoria do Produto
     * @param moe       A Moeda do Produto
     * @return Produto - O produto que foi criado
     */
    public Produto(int id, String cd, String nm, String ds, BigDecimal vlCusto, BigDecimal vlProduto, Categoria cat,
            Moeda moe) {
        this.idProduto = id;
        this.cdProduto = cd;
        this.nmProduto = nm;
        this.dsProduto = ds;
        this.vlCusto = vlCusto;
        this.vlProduto = vlProduto;
        this.categoria = cat;
        this.moeda = moe;
    }

    /**
     * Atribui o ID do Produto
     *
     * @param id O ID do Produto
     */
    public void setIdProduto(int id) {
        this.idProduto = id;
    }

    /**
     * Atribui o Codigo do Produto
     *
     * @param cd O Codigo do Produto
     */
    public void setCdProduto(String cd) {
        this.cdProduto = cd;
    }

    /**
     * Atribui o Nome do Produto
     *
     * @param nm O Nome do Produto
     */
    public void setNmProduto(String nm) {
        this.nmProduto = nm;
    }

    /**
     * Atribui a Descricao do Produto
     *
     * @param ds A Descricao do Produto
     */
    public void setDsProduto(String ds) {
        this.dsProduto = ds;
    }

    /**
     * Atribui o Valor de Custo do Produto
     *
     * @param vlCusto O Valor de Custo do Produto
     */
    public void setVlCusto(BigDecimal vlCusto) {
        this.vlCusto = vlCusto;
    }

    /**
     * Atribui o Valor do Produto
     *
     * @param vlProduto O Valor do Produto
     */
    public void setVlProduto(BigDecimal vlProduto) {
        this.vlProduto = vlProduto;
    }

    /**
     * Atribui a Categoria do Produto
     *
     * @param cat A Categoria do Produto
     */
    public void setCategoria(Categoria cat) {
        this.categoria = cat;
    }

    /**
     * Atribui a Moeda do Produto
     *
     * @param moe A Moeda do Produto
     */
    public void setMoeda(Moeda moe) {
        this.moeda = moe;
    }

    /**
     * Retorna o ID do Produto
     *
     * @return idProduto - O ID do Produto
     */
    public int getIdProduto() {
        return this.idProduto;
    }

    /**
     * Retorna o Codigo do Produto
     *
     * @return cdProduto - O Codigo do Produto
     */
    public String getCdProduto() {
        return this.cdProduto;
    }

    /**
     * Retorna o Nome do Produto
     *
     * @return nmProduto - O Nome do Produto
     */
    public String getNmProduto() {
        return this.nmProduto;
    }

    /**
     * Retorna a Descricao do Produto
     *
     * @return dsProduto - A Descricao do Produto
     */
    public String getDsProduto() {
        return this.dsProduto;
    }

    /**
     * Retorna o Valor de Custo do Produto
     *
     * @return vlCusto - O Valor de Custo do Produto
     */
    public BigDecimal getVlCusto() {
        return this.vlCusto;
    }

    /**
     * Retorna o Valor do Produto
     *
     * @return vlProduto - O Valor do Produto
     */
    public BigDecimal getVlProduto() {
        return this.vlProduto;
    }

    /**
     * Retorna a Categoria do Produto
     *
     * @return categoria - A Categoria do Produto
     */
    public Categoria getCategoria() {
        return this.categoria;
    }

    /**
     * Retorna a Moeda do Produto
     *
     * @return moeda - A Moeda do Produto
     */
    public Moeda getMoeda() {
        return this.moeda;
    }

    @Override
    public String toString() {
        return "Produto(Id = %d, Codigo = %s, Nome = %s, Descricao = %s, Custo = %s, Preco = %s"
                .formatted(this.getIdProduto(), this.getCdProduto(), this.getNmProduto(), this.getDsProduto(),
                        this.getVlCusto(), this.getVlProduto());
    }

}
