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

    public Produto() {}
    public Produto(int id, String cd, String nm, String ds, BigDecimal vlCusto, BigDecimal vlProduto, Categoria cat, Moeda moe) {
        this.idProduto = id;
        this.cdProduto = cd;
        this.nmProduto = nm;
        this.dsProduto = ds;
        this.vlCusto = vlCusto;
        this.vlProduto = vlProduto;
        this.categoria = cat;
        this.moeda = moe;
    }

    public void setIdProduto(int id) {
        this.idProduto = id;
    }

    public void setCdProduto(String cd) {
        this.cdProduto = cd;
    }

    public void setNmProduto(String nm) {
        this.nmProduto = nm;
    }

    public void setDsProduto(String ds) {
        this.dsProduto = ds;
    }

    public void setVlCusto(BigDecimal vlCusto) {
        this.vlCusto = vlCusto;
    }

    public void setVlProduto(BigDecimal vlProduto) {
        this.vlProduto = vlProduto;
    }

    public void setCategoria(Categoria cat) {
        this.categoria = cat;
    }

    public void setMoeda(Moeda moe) {
        this.moeda = moe;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getCdProduto() {
        return cdProduto;
    }

    public String getNmProduto() {
        return nmProduto;
    }

    public String getDsProduto() {
        return dsProduto;
    }

    public BigDecimal getVlCusto() {
        return vlCusto;
    }

    public BigDecimal getVlProduto() {
        return vlProduto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    @Override
    public String toString() {
        return "Produto(Id = %d, Codigo = %s, Nome = %s, Descricao = %s, Custo = %s, Preco = %s"
            .formatted(this.getIdProduto(), this.getCdProduto(), this.getNmProduto(), this.getDsProduto(), this.getVlCusto(), this.getVlProduto());
    }

}
