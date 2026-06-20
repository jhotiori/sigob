package org.javapi.sigob.model.entity;

import jakarta.persistence.*;

@Entity(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    public Categoria(Integer id, String nome) {
        this.id = id;
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
     * Retorna o Nome da categoria
     *
     * @return nmCategoria - O nome da categoria
     */
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Categoria(Id = %d, Nome = %s)"
                .formatted(this.getId(), this.getNome());
    }
}
