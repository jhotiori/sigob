package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "moedas")
public class Moeda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nmMoeda")
    private String nome;

    @Column(name = "dsCifrao")
    private String cifrao;

    @Column(name = "dsSigla")
    private String sigla;

    /**
     * Construtor para criar uma nova Mo
     *
     * @return Moeda - A moeda que foi criada
     */
    public Moeda() {
    }

    /**
     * Construtor para criar uma nova Moeda
     *
     * @param id     ID da moeda
     * @param nome   Nome da moeda
     * @param cifrao Cifrao da moeda
     * @param sigla  Sigla da moeda
     * @return Moeda - A moeda que foi criada
     */
    public Moeda(Integer id, String nome, String cifrao, String sigla) {
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
    public void setId(Integer id) {
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
     * @return idMoeda - O ID da Moeda
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Nome da Moeda
     *
     * @return nmMoeda - O Nome da Moeda
     */
    public String getNome() {
        return nome;
    }
    /**
     * Retorna o Cifrao da Moeda
     *
     * @return dsCifrao - O Cifrao da Moeda
     */
    public String getCifrao() {
        return cifrao;
    }
    /**
     * Retorna a Sigla da Moeda
     *
     * @return dsSigla - A Sigla da Moeda
     */
    public String getSigla() {
        return sigla;
    }

    @Override
    public String toString() {
        return "Moeda{id=%d, nome='%s', cifrao='%s', sigla='%s'}".formatted(id, nome, cifrao, sigla);
    }
}
