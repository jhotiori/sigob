package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "moedas")
public class Moeda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMoeda;

    @Column(name = "nmMoeda")
    private String nmMoeda;

    @Column(name = "dsCifrao")
    private String dsCifrao;

    @Column(name = "dsSigla")
    private String dsSigla;

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
     * @param id       ID da moeda
     * @param nm       Nome da moeda
     * @param dsCifrao Cifrao da moeda
     * @param dsSigla  Sigla da moeda
     * @return Moeda - A moeda que foi criada
     */
    public Moeda(int id, String nm, String dsCifrao, String dsSigla) {
        this.idMoeda = id;
        this.nmMoeda = nm;
        this.dsCifrao = dsCifrao;
        this.dsSigla = dsSigla;
    }

    /**
     * Atribui o ID da Moeda
     *
     * @param id O ID da Moeda
     */
    public void setIdMoeda(int id) {
        this.idMoeda = id;
    }

    /**
     * Atribui o Nome da Moeda
     *
     * @param nm O Nome da Moeda
     */
    public void setNmMoeda(String nm) {
        this.nmMoeda = nm;
    }

    /**
     * Atribui o Cifrao da Moeda
     *
     * @param ds O Cifrao da Moeda
     */
    public void setDsCifrao(String ds) {
        this.dsCifrao = ds;
    }

    /**
     * Atribui a Sigla da Moeda
     *
     * @param ds A Sigla da Moeda
     */
    public void setDsSigla(String ds) {
        this.dsSigla = ds;
    }

    /**
     * Retorna o ID da Moeda
     *
     * @return idMoeda - O ID da Moeda
     */
    public int getIdMoeda() {
        return this.idMoeda;
    }

    /**
     * Retorna o Nome da Moeda
     *
     * @return nmMoeda - O Nome da Moeda
     */
    public String getNmMoeda() {
        return this.nmMoeda;
    }

    /**
     * Retorna o Cifrao da Moeda
     *
     * @return dsCifrao - O Cifrao da Moeda
     */
    public String getDsCifrao() {
        return this.dsCifrao;
    }

    /**
     * Retorna a Sigla da Moeda
     *
     * @return dsSigla - A Sigla da Moeda
     */
    public String getDsSigla() {
        return this.dsSigla;
    }

    @Override
    public String toString() {
        return "Moeda(Id = %d, Nome = %s, Cifrao = %s, Sigla = %s)"
                .formatted(this.getIdMoeda(), this.getNmMoeda(), this.getDsCifrao(), this.getDsSigla());
    }
}
