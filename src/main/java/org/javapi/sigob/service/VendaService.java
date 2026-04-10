package org.javapi.sigob.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.exception.VendaException;
import org.javapi.sigob.repository.VendaRepository;

public class VendaService {
    private final VendaRepository repository;

    /**
     * Cria um novo VendaService
     *
     * @param repository O repositório de vendas
     * @return VendaService - O VendaService
     */
    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva uma nova Venda
     *
     * @param venda A Venda para ser salva
     * @throws VendaException Se a Venda for invalida
     * @return Venda - A Venda salvada
     */
    public Venda save(Venda venda) {
        validateVenda(venda);

        if (venda.getIdVenda() > 0) {
            this.repository.update(venda);
        } else {
            this.repository.create(venda);
        }

        return venda;
    }

    /**
     * Deleta uma Venda
     *
     * @param venda A Venda para ser deletada
     */
    public void delete(Venda venda) {
        if (this.repository.contains(venda)) {
            this.repository.delete(venda);
        }
    }

    /**
     * Verifica se uma venda existe
     *
     * @param venda A Venda para conferir
     * @return boolean - true se a venda existe, false se nao
     */
    public boolean exists(Venda venda) {
        return this.repository.contains(venda);
    }

    /**
     * Retorna uma lista de todas as vendas
     *
     * @return List<Venda> - A lista de vendas
     */
    public List<Venda> findAll() {
        return this.repository.findAll();
    }

    /**
     * Retorna uma venda pelo ID
     *
     * @param id O ID da venda
     * @return Venda - A venda
     */
    public Venda findById(int id) {
        validateId(id);
        return this.repository.findById(id);
    }

    /**
     * Retorna uma lista de vendas pelo intervalo de datas
     *
     * @param dataInicio A data inicial
     * @param dataFim    A data final
     * @return List<Venda> - A lista de vendas
     */
    public List<Venda> findByDataVenda(ZonedDateTime dataInicio, ZonedDateTime dataFim) {
        return this.repository.findByDataVenda(dataInicio, dataFim);
    }

    private void validateVenda(Venda venda) {
        if (venda == null) {
            throw new VendaException("Venda nao pode ser nulo!");
        }
        validatePago(venda.isFlPago());
        validateData(venda.getDtVenda());
        validateValor(venda.getVlVenda());
        validateCliente(venda.getCliente());
        validateFuncionario(venda.getFuncionario());
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new VendaException("Id nao pode ser menor ou igual a zero!");
        }
    }

    private void validateData(ZonedDateTime data) {
        if (data == null) {
            throw new VendaException("Data nao pode ser nula!");
        }
    }

    private void validateValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new VendaException("Valor da venda nao pode ser nulo ou menor ou igual a zero!");
        }
    }

    private void validatePago(boolean pago) {
        if (pago) {
            throw new VendaException("Venda nao pode ser paga!");
        }
    }

    private void validateCliente(Cliente cliente) {
        if (cliente == null) {
            throw new VendaException("Cliente nao pode ser nulo!");
        }
    }

    private void validateFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new VendaException("Funcionario nao pode ser nulo!");
        }
    }
}
