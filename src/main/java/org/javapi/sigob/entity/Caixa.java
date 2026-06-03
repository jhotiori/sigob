package org.javapi.sigob.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity(name = "caixas")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "valor_abertura", nullable = false)
    private BigDecimal valorAbertura;

    @Column(name = "valor_saldo", nullable = false)
    private BigDecimal valorSaldo;

    @Column(name = "valor_fecha", nullable = false)
    private BigDecimal valorFecha;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "data_abertura", nullable = false)
    private OffsetDateTime dataAbertura;

    @Column(name = "data_fecha", nullable = false)
    private OffsetDateTime dataFecha;

    /**
     * Construtor padrão JPA
     */
    public Caixa() {
    }

    /**
     * Construtor completo para criar um novo Caixa
     *
     * @param id O ID do Caixa
     * @param valorAbertura Valor de abertura
     * @param valorSaldo Valor atual do saldo
     * @param valorFecha Valor de fechamento
     * @param status Status do caixa
     * @param dataAbertura Data de abertura
     * @param dataFecha Data de fechamento
     */
    public Caixa(int id,
                 BigDecimal valorAbertura,
                 BigDecimal valorSaldo,
                 BigDecimal valorFecha,
                 String status,
                 OffsetDateTime dataAbertura,
                 OffsetDateTime dataFecha) {

        this.id = id;
        this.valorAbertura = valorAbertura;
        this.valorSaldo = valorSaldo;
        this.valorFecha = valorFecha;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFecha = dataFecha;
    }

    /**
     * Retorna o ID do Caixa
     *
     * @return id do Caixa
     */
    public int getId() {
        return id;
    }

    /**
     * Atribui o ID do Caixa
     *
     * @param id O ID do Caixa
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o valor de abertura
     *
     * @return valorAbertura
     */
    public BigDecimal getValorAbertura() {
        return valorAbertura;
    }

    /**
     * Atribui o valor de abertura
     *
     * @param valorAbertura Valor de abertura
     */
    public void setValorAbertura(BigDecimal valorAbertura) {
        this.valorAbertura = valorAbertura;
    }

    /**
     * Retorna o saldo atual
     *
     * @return valorSaldo
     */
    public BigDecimal getValorSaldo() {
        return valorSaldo;
    }

    /**
     * Atribui o saldo atual
     *
     * @param valorSaldo Saldo atual
     */
    public void setValorSaldo(BigDecimal valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

    /**
     * Retorna o valor de fechamento
     *
     * @return valorFecha
     */
    public BigDecimal getValorFecha() {
        return valorFecha;
    }

    /**
     * Atribui o valor de fechamento
     *
     * @param valorFecha Valor de fechamento
     */
    public void setValorFecha(BigDecimal valorFecha) {
        this.valorFecha = valorFecha;
    }

    /**
     * Retorna o status do caixa
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Atribui o status do caixa
     *
     * @param status Status do caixa
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retorna a data de abertura
     *
     * @return dataAbertura
     */
    public OffsetDateTime getDataAbertura() {
        return dataAbertura;
    }

    /**
     * Atribui a data de abertura
     *
     * @param dataAbertura Data de abertura
     */
    public void setDataAbertura(OffsetDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * Retorna a data de fechamento
     *
     * @return dataFecha
     */
    public OffsetDateTime getDataFecha() {
        return dataFecha;
    }

    /**
     * Atribui a data de fechamento
     *
     * @param dataFecha Data de fechamento
     */
    public void setDataFecha(OffsetDateTime dataFecha) {
        this.dataFecha = dataFecha;
    }

    @Override
    public String toString() {
        return "Caixa(Id = %d, ValorAbertura = %s, ValorSaldo = %s, ValorFecha = %s, Status = %s, DataAbertura = %s, DataFecha = %s)"
                .formatted(
                        this.getId(),
                        this.getValorAbertura(),
                        this.getValorSaldo(),
                        this.getValorFecha(),
                        this.getStatus(),
                        this.getDataAbertura(),
                        this.getDataFecha()
                );
    }
}