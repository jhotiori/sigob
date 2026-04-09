package org.javapi.sigob.entity;

import jakarta.persistence.*;
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

    //Constructor
    public Categoria(){}

    public Categoria(int id, String cd, String nm) {
        this.idCategoria = id;
        this.cdCategoria = cd;
        this.nmCategoria = nm;
    }

    //Setters
    public void setIdCategoria(int id){
        this.idCategoria = id;
    }
    public void setCdCategoria(String cd){ this.cdCategoria = cd; }
    public void setNmCategoria(String nm){
        this.nmCategoria = nm;
    }

    //Getter
    public int getIdCategoria() {
        return idCategoria;
    }
    public String getCdCategoria() {
        return cdCategoria;
    }
    public String getNmCategoria() {
        return nmCategoria;
    }

    //toString
    @Override
    public String toString(){
        String obj;
        obj = String.format("ID: %d | CD: %s | NM: %s",this.idCategoria,this.cdCategoria,this.nmCategoria);
        return obj;
    }
}
