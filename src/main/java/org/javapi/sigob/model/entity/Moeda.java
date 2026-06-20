package org.javapi.sigob.model.entity;

import jakarta.persistence.*;

@Entity(name = "moedas")
public class Moeda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cifrao", nullable = false)
    private String cifrao;

    @Column(name = "sigla", nullable = false)
    private String sigla;

    /**
     * Construtor padrão JPA
     *
     * @return Moeda - A moeda criada
     */
    public Moeda() {
    }

    /**
     * Construtor completo para criar uma nova Moeda
     *
     * @param id ID da moeda
     * @param nome Nome da moeda
     * @param cifrao Cifrao da moeda
     * @param sigla Sigla da moeda
     * @return Moeda - A moeda criada
     */
    public Moeda(int id, String nome, String cifrao, String sigla) {
        this.id = id;
        this.nome = nome;
        this.cifrao = cifrao;
        this.sigla = sigla;
    }

    /**
     * Atribui o ID da Moeda
     *
     * @param id O ID da Moeda
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui o Nome da Moeda
     *
     * @param nome O Nome da Moeda
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Atribui o Cifrao da Moeda
     *
     * @param cifrao O Cifrao da Moeda
     */
    public void setCifrao(String cifrao) {
        this.cifrao = cifrao;
    }

    /**
     * Atribui a Sigla da Moeda
     *
     * @param sigla A Sigla da Moeda
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * Retorna o ID da Moeda
     *
     * @return int - O ID da Moeda
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Nome da Moeda
     *
     * @return String - O Nome da Moeda
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o Cifrao da Moeda
     *
     * @return String - O Cifrao da Moeda
     */
    public String getCifrao() {
        return cifrao;
    }

    /**
     * Retorna a Sigla da Moeda
     *
     * @return String - A Sigla da Moeda
     */
    public String getSigla() {
        return sigla;
    }

    @Override
    public String toString() {
        return "Moeda(Id = %d, Nome = %s, Cifrao = %s, Sigla = %s)"
                .formatted(this.getId(), this.getNome(), this.getCifrao(), this.getSigla());
    }
}
