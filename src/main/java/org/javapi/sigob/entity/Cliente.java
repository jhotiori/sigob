package org.javapi.sigob.entity;

import jakarta.persistence.*;

@Entity(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idCliente;

    @Column (name = "nmCliente")
    private String nmCliente;

    @Column (name = "nrDocumento")
    private String nrDocumento;

    //Constructor
    public Cliente() {};

    public Cliente(int idCliente, String nmCliente, String nrDocumento) {
        this.idCliente = idCliente;
        this.nmCliente = nmCliente;
        this.nrDocumento = nrDocumento;
    }

    //Setters
    public void  setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public void  setNmCliente(String nmCliente) {
        this.nmCliente = nmCliente;
    }
    public void  setNrDocumento(String nrDocumento) {
        this.nrDocumento = nrDocumento;
    }

    //Getters
    public int getIdCliente() {
        return idCliente;
    }
    public String getNmCliente() {
        return nmCliente;
    }
    public String getNrDocumento() {
        return nrDocumento;
    }

    //ToString
    @Override
    public String toString() {
        String obj;
        obj = String.format("ID: %d\nNM: %s | DOC: %s", idCliente, nmCliente, nrDocumento);
        return obj;
    }
}
