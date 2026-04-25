package org.javapi.sigob.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "data_abertura", nullable = false)
    private OffsetDateTime dataAbertura;

    @Column(name = "data_finalizada")
    private OffsetDateTime dataFinalizada;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    /**
     * Construtor padrão JPA
     */
    public Venda() {
    }

    /**
     * Construtor completo para criar uma nova Venda
     *
     * @param id O ID da Venda
     * @param status O Status da Venda (aberta/finalizada)
     * @param dataAbertura A Data de Abertura da Venda
     * @param dataFinalizada A Data de Finalização da Venda (opcional)
     * @param valorTotal O Valor Total da Venda
     * @param cliente O Cliente da Venda
     * @param funcionario O Funcionario da Venda
     */
    public Venda(int id, String status, OffsetDateTime dataAbertura, OffsetDateTime dataFinalizada,
            BigDecimal valorTotal, Cliente cliente, Funcionario funcionario) {
        this.id = id;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFinalizada = dataFinalizada;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    /**
     * Atribui o ID da Venda
     *
     * @param id O ID da Venda
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atribui o Status da Venda
     *
     * @param status O Status da Venda
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Atribui a Data de Abertura da Venda
     *
     * @param dataAbertura A Data de Abertura da Venda
     */
    public void setDataAbertura(OffsetDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * Atribui a Data de Finalização da Venda
     *
     * @param dataFinalizada A Data de Finalização da Venda
     */
    public void setDataFinalizada(OffsetDateTime dataFinalizada) {
        this.dataFinalizada = dataFinalizada;
    }

    /**
     * Atribui o Valor Total da Venda
     *
     * @param valorTotal O Valor Total da Venda
     */
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * Atribui o Cliente da Venda
     *
     * @param cliente O Cliente da Venda
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Atribui o Funcionario da Venda
     *
     * @param funcionario O Funcionario da Venda
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * Retorna o ID da Venda
     *
     * @return id - O ID da Venda
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o Status da Venda
     *
     * @return status - O Status da Venda
     */
    public String getStatus() {
        return status;
    }

    /**
     * Retorna a Data de Abertura da Venda
     *
     * @return dataAbertura - A Data de Abertura da Venda
     */
    public OffsetDateTime getDataAbertura() {
        return dataAbertura;
    }

    /**
     * Retorna a Data de Finalização da Venda
     *
     * @return dataFinalizada - A Data de Finalização da Venda (pode ser null)
     */
    public OffsetDateTime getDataFinalizada() {
        return dataFinalizada;
    }

    /**
     * Retorna o Valor Total da Venda
     *
     * @return valorTotal - O Valor Total da Venda
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * Retorna o Cliente da Venda
     *
     * @return cliente - O Cliente da Venda
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna o Funcionario da Venda
     *
     * @return funcionario - O Funcionario da Venda
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    @Override
    public String toString() {
        return "Venda(Id = %d, Status = %s, DataAbertura = %s, DataFinalizada = %s, ValorTotal = %s)"
                .formatted(this.getId(), this.getStatus(), this.getDataAbertura(), this.getDataFinalizada(), this.getValorTotal());
    }
}
