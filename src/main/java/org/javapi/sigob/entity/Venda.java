package org.javapi.sigob.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity (name = "vendas")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenda;

    @Column (name = "dtVenda")
    private ZonedDateTime dtVenda;

    @Column (name = "vlVenda")
    private BigDecimal vlVenda;

    @Column (name = "flPago")
    private boolean flPago;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "fk_idCliente")
    private Cliente cliente;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "fk_idFuncionario")
    private Funcionario funcionario;

    //Constructor
    public Venda(){}

    public Venda(int idVenda, ZonedDateTime dtVenda, BigDecimal vlVenda, boolean flPago,Cliente cliente, Funcionario funcionario) {
        this.idVenda = idVenda;
        this.dtVenda = dtVenda;
        this.vlVenda = vlVenda;
        this.flPago = flPago;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    //Setters
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }
    public void setDtVenda(ZonedDateTime dtVenda) {
        this.dtVenda = dtVenda;
    }
    public void setVlVenda(BigDecimal vlVenda) {
        this.vlVenda = vlVenda;
    }
    public void setFlPago(boolean flPago) {
        this.flPago = flPago;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    //Getters
    public int getIdVenda() {
        return idVenda;
    }
    public ZonedDateTime getDtVenda() {
        return dtVenda;
    }
    public BigDecimal getVlVenda() {
        return vlVenda;
    }
    public boolean isFlPago() {
        return flPago;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public Funcionario getFuncionario() {
        return funcionario;
    }

    //ToString
    @Override
    public String toString() {
        String obj;
        obj = String.format("ID: %d | DT: %s | VL: %,.2f\n PAGTO: %s", this.idVenda, this.dtVenda.toString(), this.vlVenda, this.flPago ? "SIM" : "NAO");
        return obj;
    }
}
