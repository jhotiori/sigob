package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "estoques")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Construtor padrão JPA
     */
    public Estoque() {
    }

    /**
     * Construtor completo para criar um novo Estoque
     *
     * @param id     O ID do Estoque
     * @param codigo O Código do Estoque
     * @param nome   O Nome do Estoque
     */
    public Estoque(int id, String codigo, String nome) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
    }

    /**
     * Atribui o ID do Estoque
     *
     * @param id O ID do Estoque
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui o Código do Estoque
     *
     * @param codigo O Código do Estoque
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Atribui o Nome do Estoque
     *
     * @param nome O Nome do Estoque
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o ID do Estoque
     *
     * @return id - O ID do Estoque
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Código do Estoque
     *
     * @return codigo - O Código do Estoque
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o Nome do Estoque
     *
     * @return nome - O Nome do Estoque
     */
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Estoque{id=%d, codigo='%s', nome='%s'}"
                .formatted(id, codigo, nome);
    }
}