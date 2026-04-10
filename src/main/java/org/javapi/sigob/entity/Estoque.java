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

    /**
     * Construtor para criar um novo Estoque
     *
     * @return Estoque - O estoque criado
     */
    public Estoque() {
    }

    /**
     * Construtor para criar um novo Estoque
     *
     * @param id O ID do estoque
     * @param cd O Código do estoque
     * @param nm O Nome do estoque
     * @param ds A Descrição do estoque
     * @param cat A categoria do estoque
     * @return Estoque - O estoque criado
     */
    public Estoque(int id, String cd, String nm, String ds, Categoria cat) {
        this.idEstoque = id;
        this.cdEstoque = cd;
        this.nmEstoque = nm;
        this.dsEstoque = ds;
        this.categoria = cat;
    }

    /**
     * Atribui o ID do estoque
     *
     * @param id O ID do estoque
     */
    public void setIdEstoque(int id) {
        this.idEstoque = id;
    }

    /**
     * Atribui o Código do estoque
     *
     * @param cd O Código do estoque
     */
    public void setCdEstoque(String cd) {
        this.cdEstoque = cd;
    }

    /**
     * Atribui o Nome do estoque
     *
     * @param nm O Nome do estoque
     */
    public void setNmEstoque(String nm) {
        this.nmEstoque = nm;
    }

    /**
     * Atribui a Descrição do estoque
     *
     * @param ds A Descrição do estoque
     */
    public void setDsEstoque(String ds) {
        this.dsEstoque = ds;
    }

    /**
     * Atribui a categoria do estoque
     *
     * @param cat A categoria do estoque
     */
    public void setCategoria(Categoria cat) {
        this.categoria = cat;
    }

    /**
     * Retorna o ID do estoque
     *
     * @return idEstoque - O ID do estoque
     */
    public int getIdEstoque() {
        return this.idEstoque;
    }

    /**
     * Retorna o Código do estoque
     *
     * @return cdEstoque - O Código do estoque
     */
    public String getCdEstoque() {
        return this.cdEstoque;
    }

    /**
     * Retorna o Nome do estoque
     *
     * @return nmEstoque - O Nome do estoque
     */
    public String getNmEstoque() {
        return this.nmEstoque;
    }

    /**
     * Retorna a Descrição do estoque
     *
     * @return dsEstoque - A Descrição do estoque
     */
    public String getDsEstoque() {
        return this.dsEstoque;
    }

    /**
     * Retorna a categoria do estoque
     *
     * @return categoria - A categoria do estoque
     */
    public Categoria getCategoria() {
        return this.categoria;
    }

    @Override
    public String toString() {
        return "Estoque(Id = %d, Codigo = %s, Nome = %s, Descricao = %s)"
                .formatted(this.getIdEstoque(), this.getCdEstoque(), this.getNmEstoque(), this.getDsEstoque());
    }
}
