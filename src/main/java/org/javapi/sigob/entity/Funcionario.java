package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "acesso_id", nullable = false)
    private Acesso acesso;

    @ManyToOne
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    /**
     * Construtor padrão JPA
     */
    public Funcionario() {
    }

    /**
     * Construtor completo para criar um novo Funcionario
     *
     * @param id        O ID do Funcionario
     * @param nome      O Nome do Funcionario
     * @param codigo    O Código do Funcionario
     * @param acesso    O Acesso do Funcionario
     * @param documento O Documento do Funcionario
     */
    public Funcionario(int id, String nome, String codigo, Acesso acesso, Documento documento) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.acesso = acesso;
        this.documento = documento;
    }

    /**
     * Atribui o ID do Funcionario
     *
     * @param id O ID do Funcionario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui o Nome do Funcionario
     *
     * @param nome O Nome do Funcionario
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Atribui o Código do Funcionario
     *
     * @param codigo O Código do Funcionario
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Atribui o Acesso do Funcionario
     *
     * @param acesso O Acesso do Funcionario
     */
    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    /**
     * Atribui o Documento do Funcionario
     *
     * @param documento O Documento do Funcionario
     */
    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    /**
     * Retorna o ID do Funcionario
     *
     * @return id - O ID do Funcionario
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Nome do Funcionario
     *
     * @return nome - O Nome do Funcionario
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o Código do Funcionario
     *
     * @return codigo - O Código do Funcionario
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o Acesso do Funcionario
     *
     * @return acesso - O Acesso do Funcionario
     */
    public Acesso getAcesso() {
        return acesso;
    }

    /**
     * Retorna o Documento do Funcionario
     *
     * @return documento - O Documento do Funcionario
     */
    public Documento getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return "Funcionario{id=%d, nome='%s', codigo='%s', acesso=%s, documento=%s}"
                .formatted(id, nome, codigo,
                        acesso != null ? acesso.getId() : "null",
                        documento != null ? documento.getId() : "null");
    }
}