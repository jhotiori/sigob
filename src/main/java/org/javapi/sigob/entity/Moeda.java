package org.javapi.sigob.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "meodas")
public class Moeda {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idMoeda;

    @Column (name = "nmMoeda")
    private String nmMoeda;

    @Column (name = "dsCifrao")
    private String dsCifrao;

    @Column (name = "dsSigla")
    private String dsSigla;

    //Constructor
    public Moeda(){}

    public Moeda(int id, String nm, String dsCifrao, String dsSigla){
        this.idMoeda = id;
        this.nmMoeda = nm;
        this.dsCifrao = dsCifrao;
        this.dsSigla = dsSigla;
    }

    //Setter
    public void setIdMoeda(int id){
        this.idMoeda = id;
    }
    public void setNmMoeda(String nm){
        this.nmMoeda = nm;
    }
    public void setDsCifrao(String ds){
        this.dsCifrao = ds;
    }
    public void setDsSigla(String ds){
        this.dsSigla = ds;
    }

    //Getters
    public int getIdMoeda(){
        return this.idMoeda;
    }
    public String getNmMoeda(){
        return this.nmMoeda;
    }
    public String getDsCifrao() {
        return dsCifrao;
    }
    public String getDsSigla() {
        return dsSigla;
    }

    //toString
    @Override
    public String toString(){
        String obj;
        obj = String.format("ID: %d | NM: %s\nSIGLA: %s | CIFRAO: %s",idMoeda, nmMoeda, dsSigla, dsCifrao);
        return obj;
    }
}
