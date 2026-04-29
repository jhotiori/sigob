package org.javapi.sigob.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name = "acessos")
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @ManyToMany(mappedBy = "acessos")
    private final Set<Funcionario> funcionarios = new HashSet<>();

    /**
     * Construtor padrão JPA
     */
    public Acesso() {
    }

    /**
     * Construtor completo para criar um novo Acesso
     *
     * @param id O ID do Acesso
     * @param codigo O Código do Acesso (opcional)
     * @param nome O Nome do Acesso
     * @param descricao A Descrição do Acesso
     */
    public Acesso(int id, String codigo, String nome, String descricao) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    /**
     * Atribui o ID do Acesso
     *
     * @param id O ID do Acesso
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui o Código do Acesso
     *
     * @param codigo O Código do Acesso
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Atribui o Nome do Acesso
     *
     * @param nome O Nome do Acesso
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Atribui a Descrição do Acesso
     *
     * @param descricao A Descrição do Acesso
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o ID do Acesso
     *
     * @return id - O ID do Acesso
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Código do Acesso
     *
     * @return codigo - O Código do Acesso
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o Nome do Acesso
     *
     * @return nome - O Nome do Acesso
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a Descrição do Acesso
     *
     * @return descricao - A Descrição do Acesso
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna os Funcionários (imutável externamente)
     *
     * @return funcionarios - Os Funcionários
     */
    public Set<Funcionario> getFuncionarios() {
        return Set.copyOf(funcionarios);
    }

    public void addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
    }

    @Override
    public String toString() {
        return "Acesso(Id = %d, Codigo = %s, Nome = %s, Descricao = %s)"
                .formatted(this.getId(), this.getCodigo(), this.getNome(), this.getDescricao());
    }
}
