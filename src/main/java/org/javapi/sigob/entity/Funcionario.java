package org.javapi.sigob.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @ManyToMany
    @JoinTable(
            name = "funcionarios_acessos",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "acesso_id")
    )
    private Set<Acesso> acessos = new HashSet<>();

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
     * @param id O ID do Funcionario
     * @param nome O Nome do Funcionario
     * @param codigo O Código do Funcionario
     * @param documento O Documento do Funcionario
     */
    public Funcionario(int id, String nome, String codigo, Documento documento) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
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
     * Retorna o Documento do Funcionario
     *
     * @return documento - O Documento do Funcionario
     */
    public Documento getDocumento() {
        return documento;
    }

    /**
     * Adiciona um acesso ao funcionário garantindo consistência bidirecional
     *
     * @param acesso O acesso a ser adicionado
     * @return true se foi adicionado, false se já existia
     */
    public boolean addAcesso(Acesso acesso) {
        if (acesso == null) {
            return false;
        }
        boolean adicionado = this.acessos.add(acesso);
        /*if (adicionado) {
            acesso.getFuncionarios().add(this);
        }*/
        return adicionado;
    }

    /**
     * Remove um acesso do funcionário garantindo consistência bidirecional
     *
     * @param acesso O acesso a ser removido
     * @return true se foi removido, false se nao existia
     */
    public boolean removeAcesso(Acesso acesso) {
        if (acesso == null) {
            return false;
        }

        return this.acessos.remove(acesso);
    }

    /**
     * Verifica se o funcionário possui determinado acesso
     *
     * @param nome Nome do acesso
     * @return true se possui, false caso contrário
     */
    public boolean hasAcesso(String nome) {
        return this.acessos.stream()
                .anyMatch(a -> a.getNome().equalsIgnoreCase(nome));
    }

    /**
     * Retorna os acessos (imutável externamente)
     */
    public Set<Acesso> getAcessos() {
        return acessos;
    }

    @Override
    public String toString() {
        return "Funcionario(Id = %d, Nome = %s, Codigo = %s)"
                .formatted(this.getId(), this.getNome(), this.getCodigo());
    }
}
