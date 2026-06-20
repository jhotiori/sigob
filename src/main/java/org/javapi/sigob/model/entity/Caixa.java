package org.javapi.sigob.model.entity;

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

    @Column(name = "valor_saldo", nullable = true)
    private BigDecimal valorSaldo;

    @Column(name = "valor_fechamento", nullable = true)
    private BigDecimal valorFechamento;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "data_abertura", nullable = false)
    private OffsetDateTime dataAbertura;

    @Column(name = "data_fechamento", nullable = true)
    private OffsetDateTime dataFechamento;

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
                 BigDecimal valorFechamento,
                 String status,
                 OffsetDateTime dataAbertura,
                 OffsetDateTime dataFechamento) {

        this.id = id;
        this.valorAbertura = valorAbertura;
        this.valorSaldo = valorSaldo;
        this.valorFechamento = valorFechamento;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
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
    public BigDecimal getValorFechamento() {
        return valorFechamento;
    }

    /**
     * Atribui o valor de fechamento
     *
     * @param valorFecha Valor de fechamento
     */
    public void setValorFechamento(BigDecimal valorFechamento) {
        this.valorFechamento = valorFechamento;
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
    public OffsetDateTime getDataFechamento() {
        return dataFechamento;
    }

    /**
     * Atribui a data de fechamento
     *
     * @param dataFecha Data de fechamento
     */
    public void setDataFechamento(OffsetDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    @Override
    public String toString() {
        return "Caixa(Id = %d, ValorAbertura = %s, ValorSaldo = %s, ValorFecha = %s, Status = %s, DataAbertura = %s, DataFecha = %s)"
                .formatted(
                        this.getId(),
                        this.getValorAbertura(),
                        this.getValorSaldo(),
                        this.getValorFechamento(),
                        this.getStatus(),
                        this.getDataAbertura(),
                        this.getDataFechamento()
                );
    }
}
