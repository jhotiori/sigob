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
@Table(name = "estoques")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstoque;

    @Column(name = "cdEstoque")
    private String cdEstoque;

    @Column(name = "nmEstoque")
    private String nmEstoque;

    @Column(name = "dsEstoque")
    private String dsEstoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idCategoria")
    private Categoria categoria;

    public Estoque() {
    }

    public Estoque(int id, String cd, String nm, String ds, Categoria cat) {
        this.idEstoque = id;
        this.cdEstoque = cd;
        this.nmEstoque = nm;
        this.dsEstoque = ds;
        this.categoria = cat;
    }

    public void setIdEstoque(int id) {
        this.idEstoque = id;
    }

    public void setCdEstoque(String cd) {
        this.cdEstoque = cd;
    }

    public void setNmEstoque(String nm) {
        this.nmEstoque = nm;
    }

    public void setDsEstoque(String ds) {
        this.dsEstoque = ds;
    }

    public void setCategoria(Categoria cat) {
        this.categoria = cat;
    }

    public int getIdEstoque() {
        return this.idEstoque;
    }

    public String getCdEstoque() {
        return this.cdEstoque;
    }

    public String getNmEstoque() {
        return this.nmEstoque;
    }

    public String getDsEstoque() {
        return this.dsEstoque;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    @Override
    public String toString() {
       return "Estoque(Id = %d, Codigo = %s, Nome = %s, Descricao = %s)"
        .formatted(this.getIdEstoque(), this.getCdEstoque(), this.getNmEstoque(), this.getDsEstoque());
    }
}
