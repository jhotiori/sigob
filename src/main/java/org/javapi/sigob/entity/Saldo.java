package org.javapi.sigob.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity(name = "saldos")
public class Saldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "valor_saldo", nullable = false)
    private BigDecimal valorSaldo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "data_saldo", nullable = false)
    private OffsetDateTime dataSaldo;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    /**
     * Construtor padrão JPA
     */
    public Saldo() {
    }

    /**
     * Construtor completo para criar um novo Saldo
     *
     * @param id O ID do Saldo
     * @param valorSaldo O valor do Saldo
     * @param descricao A descrição do Saldo
     * @param tipo O tipo do Saldo
     * @param dataSaldo A data de geração do Saldo
     * @param venda A venda vinculada a um Saldo
     */
    public Saldo(int id, BigDecimal valorSaldo, String descricao,
                 String tipo, OffsetDateTime dataSaldo, Venda venda) {

        this.id = id;
        this.valorSaldo = valorSaldo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.dataSaldo = dataSaldo;
        this.venda = venda;
    }

    /**
     * Retorna o ID do Saldo
     *
     * @return id - O ID do Saldo
     */
    public int getId() {
        return id;
    }

    /**
     * Atribui o ID do Saldo
     *
     * @param id O ID do Saldo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o valor do Saldo
     *
     * @return valorSaldo - O valor do Saldo
     */
    public BigDecimal getValorSaldo() {
        return valorSaldo;
    }

    /**
     * Atribui o valor do Saldo
     *
     * @param valorSaldo O valor do Saldo
     */
    public void setValorSaldo(BigDecimal valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

    /**
     * Retorna a descrição do Saldo
     *
     * @return descricao do Saldo
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Atribui a descrição do Saldo
     *
     * @param descricao A descrição do Saldo
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o tipo do Saldo
     *
     * @return tipo do Saldo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Atribui o tipo do Saldo
     *
     * @param tipo O tipo do Saldo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna a data do Saldo
     *
     * @return dataSaldo do Saldo
     */
    public OffsetDateTime getDataSaldo() {
        return dataSaldo;
    }

    /**
     * Atribui a data do Saldo
     *
     * @param dataSaldo A data do Saldo
     */
    public void setDataSaldo(OffsetDateTime dataSaldo) {
        this.dataSaldo = dataSaldo;
    }

    /**
     * Retorna a venda vinculada ao Saldo (se houver)
     *
     * @return venda do Saldo
     */
    public Venda getVenda() {
        return venda;
    }

    /**
     * Atribui a venda vinculada ao Saldo
     *
     * @param venda A venda vinculada ao Saldo
     */
    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public String toString() {
        return "Saldo(Id = %d, Valor = %s, Descrição = %s, Tipo = %s, Data = %s)"
                .formatted(
                        this.getId(),
                        this.getValorSaldo(),
                        this.getDescricao(),
                        this.getTipo(),
                        this.getDataSaldo()
                );
    }
}