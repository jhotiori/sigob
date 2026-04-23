package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    @Column(name = "nmCliente")
    private String nmCliente;

    @Column(name = "nrDocumento")
    private String nrDocumento;

    /**
     * Construtor para criar um novo Cliente
     *
     * @return Cliente - O Cliente criado
     */
    public Cliente() {
    };

    /**
     * Construtor para criar um novo Cliente
     *
     * @param idCliente   O ID do Cliente
     * @param nmCliente   O Nome do Cliente
     * @param nrDocumento O Documento do Cliente
     * @return Cliente - O Cliente criado
     */
    public Cliente(int idCliente, String nmCliente, String nrDocumento) {
        this.idCliente = idCliente;
        this.nmCliente = nmCliente;
        this.nrDocumento = nrDocumento;
    }

    /**
     * Atribui o ID do Cliente
     *
     * @param idCliente O ID do Cliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Atribui o Nome do Cliente
     *
     * @param nmCliente O Nome do Cliente
     */
    public void setNmCliente(String nmCliente) {
        this.nmCliente = nmCliente;
    }

    /**
     * Atribui o Documento do Cliente
     *
     * @param nrDocumento O Documento do Cliente
     */
    public void setNrDocumento(String nrDocumento) {
        this.nrDocumento = nrDocumento;
    }

    /**
     * Retorna o ID do Cliente
     *
     * @return idCliente - O ID do Cliente
     */
    public int getIdCliente() {
        return this.idCliente;
    }

    /**
     * Retorna o Nome do Cliente
     *
     * @return nmCliente - O Nome do Cliente
     */
    public String getNmCliente() {
        return this.nmCliente;
    }

    /**
     * Retorna o Documento do Cliente
     *
     * @return nrDocumento - O Documento do Cliente
     */
    public String getNrDocumento() {
        return this.nrDocumento;
    }

    @Override
    public String toString() {
        return "Cliente(Id = %d, Nome = %s, Documento = %s)"
                .formatted(this.getIdCliente(), this.getNmCliente(), this.getNrDocumento());
    }
}
