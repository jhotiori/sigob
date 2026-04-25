package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

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
     * @param codigo O Codigo da categoria
     * @param nome O Nome da categoria
     * @return Categoria - A categoria que foi criada
     */
    public Categoria(Integer id, String codigo, String nome) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
    }

    /**
     * Atribui o ID da categoria
     *
     * @param id O ID da categoria
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Atribui o Codigo da categoria
     *
     * @param codigo O Codigo da categoria
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Atribui o Nome da categoria
     *
     * @param nome O Nome da categoria
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o ID da categoria
     *
     * @return idCategoria - O ID da categoria
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Codigo da categoria
     *
     * @return cdCategoria - O Codigo da categoria
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o Nome da categoria
     *
     * @return nmCategoria - O nome da categoria
     */
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Categoria(Id = %d, Codigo = %s, Nome = %s)"
                .formatted(this.getId(), this.getCodigo(), this.getNome());
    }
}
