package org.javapi.sigob.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFuncionario;

    @Column (name = "nmFuncionario")
    private String nmFuncionario;

    @Column (name = "cdFuncionario")
    private String cdFuncionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "fk_idAcesso")
    private Acesso acesso;

    //Constructor
    public void Funcionario() {}

    public void Funcionario(int idFuncionario, String nmFuncionario, String cdFuncionario) {
        this.idFuncionario = idFuncionario;
        this.nmFuncionario = nmFuncionario;
        this.cdFuncionario = cdFuncionario;
        this.acesso = new Acesso();
    }

    //Setters
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    public void setNmFuncionario(String nmFuncionario) {
        this.nmFuncionario = nmFuncionario;
    }
    public void setCdFuncionario(String cdFuncionario) {
        this.cdFuncionario = cdFuncionario;
    }
    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    //Getters
    public int getIdFuncionario() {
        return idFuncionario;
    }
    public String getNmFuncionario() {
        return nmFuncionario;
    }
    public String getCdFuncionario() {
        return cdFuncionario;
    }
    public Acesso getAcesso() {
        return acesso;
    }

    //ToString
    @Override
    public String toString(){
        String obj;
        obj = String.format("ID: %d | CD: %s | NM: %s",this.idFuncionario, this.cdFuncionario, this.nmFuncionario);
        return obj;
    }
}
