package org.javapi.sigob.service;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.exception.VendaException;
import org.javapi.sigob.repository.VendaRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class VendaService {
    private EntityManager em;
    private VendaRepository repos;

    public VendaService(){}

    public VendaService(EntityManager em, VendaRepository repos) {
        this.em = em;
        this.repos = repos;
    }

    public Venda save(Venda venda){

        var idVenda = venda.getIdVenda();
        var dtVenda = venda.getDtVenda();
        var vlVenda = venda.getVlVenda();
        var flPago = venda.isFlPago();
        var cliente = venda.getCliente();
        var funcionario = venda.getFuncionario();

        if(vlVenda == null || vlVenda.compareTo(BigDecimal.ZERO) <= 0) {
            throw new VendaException("Valor da venda incorreto!");
        }

        if(cliente == null){
            throw new VendaException("Cliente não encontrado!");
        }
        if(funcionario == null){
            throw new VendaException("Funcionário não encontrado!");
        }

        var resultVenda = new Venda(idVenda, dtVenda, vlVenda, flPago, cliente, funcionario);

        if(idVenda > 0){
            repos.update(resultVenda);
        } else{
            repos.create(resultVenda);
        }

        return resultVenda;
    }

    public Venda getById(int id){
        return repos.findById(id);
    }

    public List<Venda> getByData(ZonedDateTime dtInicio, ZonedDateTime dtFim){
        return repos.findByDtVenda(dtInicio, dtFim);
    }

    public void Delete(Venda venda){
        if(repos.exists(venda)){
            repos.remove(venda);
        }
    }

    public boolean exists(Venda venda){
        return repos.exists(venda);
    }
}
