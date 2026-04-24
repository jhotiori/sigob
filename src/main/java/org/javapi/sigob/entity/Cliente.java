package org.javapi.sigob.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "documento_id", nullable = true)
    private Documento documento;

    /**
     * Construtor para criar um novo Cliente
     *
     * @return Cliente - O Cliente criado
     */
    public Cliente() {
    };

    /**
     * Construtor completo para criar um novo Cliente
     *
     * @param id             O ID do Cliente
     * @param nome           O Nome do Cliente
     * @param dataNascimento A Data de Nascimento do Cliente
     * @param documento      O Documento do Cliente (opcional)
     */
    public Cliente(int id, String nome, LocalDate dataNascimento, Documento documento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.documento = documento;
    }

    /**
     * Atribui o ID do Cliente
     *
     * @param id O ID do Cliente
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui o Nome do Cliente
     *
     * @param nome O Nome do Cliente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Atribui a Data de Nascimento do Cliente
     *
     * @param dataNascimento A Data de Nascimento do Cliente
     */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * Atribui o Documento do Cliente
     *
     * @param documento O Documento do Cliente
     */
    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    /**
     * Retorna o ID do Cliente
     *
     * @return id - O ID do Cliente
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Nome do Cliente
     *
     * @return nome - O Nome do Cliente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a Data de Nascimento do Cliente
     *
     * @return dataNascimento - A Data de Nascimento do Cliente
     */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Retorna o Documento do Cliente
     *
     * @return documento - O Documento do Cliente (pode ser null)
     */
    public Documento getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return "Cliente{id=%d, nome='%s', dataNascimento=%s, documento=%s}"
                .formatted(id, nome, dataNascimento, documento != null ? documento.getId() : "null");
    }
}
