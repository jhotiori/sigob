package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFuncionario;

    @Column(name = "nmFuncionario")
    private String nmFuncionario;

    @Column(name = "cdFuncionario")
    private String cdFuncionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idAcesso")
    private Acesso acesso;

    /**
     * Construtor para criar um novo Funcionario
     *
     * @return Funcionario - O funcionario que foi criado
     */
    public Funcionario() {
    }

    /**
     * Construtor para criar um novo Funcionario
     *
     * @param idFuncionario O ID do funcionario
     * @param nmFuncionario O Nome do funcionario
     * @param cdFuncionario O Codigo do funcionario
     * @return Funcionario - O funcionario que foi criado
     */
    public Funcionario(int idFuncionario, String nmFuncionario, String cdFuncionario) {
        this.idFuncionario = idFuncionario;
        this.nmFuncionario = nmFuncionario;
        this.cdFuncionario = cdFuncionario;
        this.acesso = new Acesso();
    }

    /**
     * Atribui o ID do Funcionario
     *
     * @param idFuncionario - O ID do funcionario
     */
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     * Atribui o Nome do Funcionario
     *
     * @param nmFuncionario - O Nome do funcionario
     */
    public void setNmFuncionario(String nmFuncionario) {
        this.nmFuncionario = nmFuncionario;
    }

    /**
     * Atribui o Codigo do Funcionario
     *
     * @param cdFuncionario - O Codigo do funcionario
     */
    public void setCdFuncionario(String cdFuncionario) {
        this.cdFuncionario = cdFuncionario;
    }

    /**
     * Atribui o Acesso do Funcionario
     *
     * @param acesso - O Acesso do funcionario
     */
    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    /**
     * Retorna o ID do Funcionario
     *
     * @return idFuncionario - O ID do funcionario
     */
    public int getIdFuncionario() {
        return this.idFuncionario;
    }

    /**
     * Retorna o Nome do Funcionario
     *
     * @return nmFuncionario - O Nome do funcionario
     */
    public String getNmFuncionario() {
        return this.nmFuncionario;
    }

    /**
     * Retorna o Codigo do Funcionario
     *
     * @return cdFuncionario - O Codigo do funcionario
     */
    public String getCdFuncionario() {
        return this.cdFuncionario;
    }

    /**
     * Retorna o Acesso do Funcionario
     *
     * @return acesso - O Acesso do funcionario
     */
    public Acesso getAcesso() {
        return this.acesso;
    }

    @Override
    public String toString() {
        return "Funcionario(Id = %d, Nome = %s, Codigo = %s, Acesso = %s)"
                .formatted(this.getIdFuncionario(), this.getNmFuncionario(), this.getCdFuncionario(),
                        this.getAcesso().toString());
    }
}
