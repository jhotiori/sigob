package org.javapi.sigob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "acessos")
public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAcesso;

    @Column(name = "nmAcesso")
    private String nmAcesso;

    @Column(name = "cdAcesso")
    private String cdAcesso;

    @Column(name = "dsAcesso")
    private String dsAcesso;

    /**
     * Construtor para criar um novo acesso
     *
     * @return Acesso - O acesso criado
     */
    public Acesso() {
    }

    /**
     * Construtor para criar um novo acesso
     *
     * @param idAcesso O ID do acesso
     * @param nmAcesso O nome do acesso
     * @param cdAcesso O código do acesso
     * @param dsAcesso A descrição do acesso
     * @return Acesso - O acesso criado
     */
    public Acesso(int idAcesso, String nmAcesso, String cdAcesso, String dsAcesso) {
        this.idAcesso = idAcesso;
        this.nmAcesso = nmAcesso;
        this.cdAcesso = cdAcesso;
        this.dsAcesso = dsAcesso;
    }

    /**
     * Atribui o ID do acesso
     *
     * @param idAcesso O ID do acesso
     */
    public void setIdAcesso(int idAcesso) {
        this.idAcesso = idAcesso;
    }

    /**
     * Atribui o nome do acesso
     *
     * @param nmAcesso O nome do acesso
     */
    public void setNmAcesso(String nmAcesso) {
        this.nmAcesso = nmAcesso;
    }

    /**
     * Atribui o código do acesso
     *
     * @param cdAcesso O código do acesso
     */
    public void setCdAcesso(String cdAcesso) {
        this.cdAcesso = cdAcesso;
    }

    /**
     * Atribui a descrição do acesso
     *
     * @param dsAcesso
     */
    public void setDsAcesso(String dsAcesso) {
        this.dsAcesso = dsAcesso;
    }

    /**
     * Retorna o ID do acesso
     *
     * @return idAcesso - O ID do acesso
     */
    public int getIdAcesso() {
        return this.idAcesso;
    }

    /**
     * Retorna o nome do acesso
     *
     * @return nmAcesso - O nome do acesso
     */
    public String getNmAcesso() {
        return this.nmAcesso;
    }

    /**
     * Retorna o código do acesso
     *
     * @return cdAcesso - O código do acesso
     */
    public String getCdAcesso() {
        return this.cdAcesso;
    }

    /**
     * Retorna a descrição do acesso
     *
     * @return dsAcesso - A descrição do acesso
     */
    public String getDsAcesso() {
        return this.dsAcesso;
    }

    @Override
    public String toString() {
        return "Acesso(Id = %d, Nome = %s, Codigo = %s, Descricao = %s)"
                .formatted(this.getIdAcesso(), this.getNmAcesso(), this.getCdAcesso(), this.getDsAcesso());
    }
}
