package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCategoria;

    @Column(name = "cdCategoria")
    private String cdCategoria;

    @Column(name = "nmCategoria")
    private String nmCategoria;

    /**
     * Construtor para criar uma nova categoria
     *
     * @return Categoria - A categoria que foi criada
     */
    public Categoria() {
    }

    /**
     * Construtor para criar uma nova categoria
     *
     * @param id O ID da categoria
     * @param cd O Codigo da categoria
     * @param nm O Nome da categoria
     * @return Categoria - A categoria que foi criada
     */
    public Categoria(int id, String cd, String nm) {
        this.idCategoria = id;
        this.cdCategoria = cd;
        this.nmCategoria = nm;
    }

    /**
     * Atribui o ID da categoria
     *
     * @param id O ID da categoria
     */
    public void setIdCategoria(int id) {
        this.idCategoria = id;
    }

    /**
     * Atribui o Codigo da categoria
     *
     * @param cd O Codigo da categoria
     */
    public void setCdCategoria(String cd) {
        this.cdCategoria = cd;
    }

    /**
     * Atribui o Nome da categoria
     *
     * @param nm O Nome da categoria
     */
    public void setNmCategoria(String nm) {
        this.nmCategoria = nm;
    }

    /**
     * Retorna o ID da categoria
     *
     * @return idCategoria - O ID da categoria
     */
    public int getIdCategoria() {
        return this.idCategoria;
    }

    /**
     * Retorna o Codigo da categoria
     *
     * @return cdCategoria - O Codigo da categoria
     */
    public String getCdCategoria() {
        return this.cdCategoria;
    }

    /**
     * Retorna o Nome da categoria
     *
     * @return nmCategoria - O nome da categoria
     */
    public String getNmCategoria() {
        return this.nmCategoria;
    }

    @Override
    public String toString() {
        return "Categoria(Id = %d, Codigo = %s, Nome = %s)"
                .formatted(this.getIdCategoria(), this.getCdCategoria(), this.getNmCategoria());
    }
}
