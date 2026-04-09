package org.javapi.sigob.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "acessos")
public class Acesso {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idAcesso;

    @Column (name = "nmAcesso")
    private String nmAcesso;

    @Column (name = "cdAcesso")
    private String cdAcesso;

    @Column (name = "dsAcesso")
    private String dsAcesso;

    //Constructor
    public Acesso() {}

    public Acesso(int idAcesso, String nmAcesso, String cdAcesso, String dsAcesso){
        this.idAcesso = idAcesso;
        this.nmAcesso = nmAcesso;
        this.cdAcesso = cdAcesso;
        this.dsAcesso = dsAcesso;
    }

    //Setters
    public void setIdAcesso(int idAcesso){
        this.idAcesso = idAcesso;
    }
    public void setNmAcesso(String nmAcesso){
        this.nmAcesso = nmAcesso;
    }
    public void setCdAcesso(String cdAcesso){
        this.cdAcesso = cdAcesso;
    }
    public void setDsAcesso(String dsAcesso){
        this.dsAcesso = dsAcesso;
    }

    //Getters
    public int getIdAcesso(){
        return this.idAcesso;
    }
    public String getNmAcesso(){
        return this.nmAcesso;
    }
    public String getCdAcesso(){
        return this.cdAcesso;
    }
    public String getDsAcesso(){
        return this.dsAcesso;
    }

    //ToString
    @Override
    public String toString(){
        String obj;
        obj = String.format("ID: %d | CD: s% | NM: %s\nDS: %s", this.idAcesso, this.cdAcesso, this.nmAcesso, this.dsAcesso);
        return obj;
    }
}

