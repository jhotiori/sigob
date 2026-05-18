package org.javapi.sigob.entity;

import jakarta.persistence.*;

@Entity(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "documento", nullable = false, unique = true)
    private String documento;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    /**
     * Construtor padrão JPA
     */
    public Documento() {
    }

    /**
     * Construtor completo para criar um novo Documento
     *
     * @param id O ID do Documento
     * @param documento O Número/Código do Documento
     * @param tipo O Tipo do Documento (ex: CPF, CNPJ)
     */
    public Documento(int id, String documento, String tipo) {
        this.id = id;
        this.documento = documento;
        this.tipo = tipo;
    }

    /**
     * Atribui o ID do Documento
     *
     * @param id O ID do Documento
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui o Número/Código do Documento
     *
     * @param documento O Número/Código do Documento
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * Atribui o Tipo do Documento
     *
     * @param tipo O Tipo do Documento
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna o ID do Documento
     *
     * @return id - O ID do Documento
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Número/Código do Documento
     *
     * @return documento - O Número/Código do Documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Retorna o Tipo do Documento
     *
     * @return tipo - O Tipo do Documento
     */
    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Documento(Id = %d, Documento = %s, Tipo = %s)"
                .formatted(this.getId(), this.getDocumento(), this.getTipo());
    }
}
