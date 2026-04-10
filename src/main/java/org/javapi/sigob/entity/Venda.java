package org.javapi.sigob.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "vendas")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenda;

    @Column(name = "dtVenda")
    private ZonedDateTime dtVenda;

    @Column(name = "vlVenda")
    private BigDecimal vlVenda;

    @Column(name = "flPago")
    private boolean flPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idCliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_idFuncionario")
    private Funcionario funcionario;

    /**
     * Construtor para criar uma nova Venda
     *
     * @return Venda - A venda que foi criada
     */
    public Venda() {
    }

    /**
     * Construtor para criar uma nova Venda
     *
     * @param idVenda     O ID da venda
     * @param dtVenda     A data da venda
     * @param vlVenda     O valor da venda
     * @param flPago      Se a venda foi paga
     * @param cliente     O cliente da venda
     * @param funcionario O funcionario da venda
     * @return Venda - A venda que foi criada
     */
    public Venda(int idVenda, ZonedDateTime dtVenda, BigDecimal vlVenda, boolean flPago, Cliente cliente,
            Funcionario funcionario) {
        this.idVenda = idVenda;
        this.dtVenda = dtVenda;
        this.vlVenda = vlVenda;
        this.flPago = flPago;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    /**
     * Atribui o ID da Venda
     *
     * @param idVenda o ID da Venda
     */
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    /**
     * Atribui a data da Venda
     *
     * @param dtVenda A data da Venda
     */
    public void setDtVenda(ZonedDateTime dtVenda) {
        this.dtVenda = dtVenda;
    }

    /**
     * Atribui o valor da Venda
     *
     * @param vlVenda O valor da Venda
     */
    public void setVlVenda(BigDecimal vlVenda) {
        this.vlVenda = vlVenda;
    }

    /**
     * Atribui se a Venda foi paga
     *
     * @param flPago Se a Venda foi paga
     */
    public void setFlPago(boolean flPago) {
        this.flPago = flPago;
    }

    /**
     * Atribui o cliente da Venda
     *
     * @param cliente O cliente da Venda
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Atribui o funcionario da Venda
     *
     * @param funcionario O funcionario da Venda
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * Retorna o ID da Venda
     *
     * @return idVenda - O ID da Venda
     */
    public int getIdVenda() {
        return this.idVenda;
    }

    /**
     * Retorna a data da Venda
     *
     * @return dtVenda - A data da Venda
     */
    public ZonedDateTime getDtVenda() {
        return this.dtVenda;
    }

    /**
     * Retorna o valor da Venda
     *
     * @return vlVenda - O valor da Venda
     */
    public BigDecimal getVlVenda() {
        return this.vlVenda;
    }

    /**
     * Retorna se a Venda foi paga
     *
     * @return flPago - Se a Venda foi paga
     */
    public boolean isFlPago() {
        return this.flPago;
    }

    /**
     * Retorna o cliente da Venda
     *
     * @return cliente - O cliente da Venda
     */
    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Retorna o funcionario da Venda
     *
     * @return funcionario - O funcionario da Venda
     */
    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    @Override
    public String toString() {
        return "Venda(Id = %d, Data = %s, Valor = %s, Pago = %s, Cliente = %s, Funcionario = %s)"
                .formatted(this.getIdVenda(), this.getDtVenda(), this.getVlVenda(), this.isFlPago(),
                        this.getCliente().toString(), this.getFuncionario().toString());
    }
}
