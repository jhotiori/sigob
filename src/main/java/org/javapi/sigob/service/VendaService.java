package org.javapi.sigob.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.exception.VendaException;
import org.javapi.sigob.repository.VendaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class VendaService {

    /**
     * Cria um novo VendaService
     */
    public VendaService() {
    }

    /**
     * Salva uma nova Venda
     *
     * @param venda A Venda para ser salva
     * @throws VendaException Se a Venda for invalida
     * @return Venda - A Venda salvada
     */
    public Venda save(Venda venda) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            validateVenda(venda);
            VendaRepository repository = new VendaRepository(em);
            transaction.begin();

            if (venda.getId() > 0) {
                repository.update(venda);
            } else {
                repository.save(venda);
            }

            transaction.commit();
            return venda;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Deleta uma Venda
     *
     * @param venda A Venda para ser deletada
     */
    public void delete(Venda venda) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            VendaRepository repository = new VendaRepository(em);
            transaction.begin();
            repository.deleteById(venda.getId());
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Verifica se uma venda existe
     *
     * @param venda A Venda para conferir
     * @return boolean - true se a venda existe, false se nao
     */
    public boolean contains(Venda venda) {
        EntityManager em = JPAConfig.getEntityManager();

        try {
            VendaRepository repository = new VendaRepository(em);
            return repository.contains(venda);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista de todas as vendas
     *
     * @return List<Venda> - A lista de vendas
     */
    public List<Venda> findAll() {
        EntityManager em = JPAConfig.getEntityManager();

        try {
            VendaRepository repository = new VendaRepository(em);
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma venda pelo ID
     *
     * @param id O ID da venda
     * @return Venda - A venda
     */
    public Venda findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();

        try {
            VendaRepository repository = new VendaRepository(em);
            return repository.findById(id).orElse(null);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista de vendas pelo intervalo de datas
     *
     * @param dataInicio A data inicial
     * @param dataFim    A data final
     * @return List<Venda> - A lista de vendas
     */
    public List<Venda> findByDataVenda(OffsetDateTime dataInicio, OffsetDateTime dataFim) {
        EntityManager em = JPAConfig.getEntityManager();

        try {
            VendaRepository repository = new VendaRepository(em);
            return repository.findFinalizadas();
        } finally {
            em.close();
        }
    }

    private void validateVenda(Venda venda) {
        if (venda == null) {
            throw new VendaException("Venda nao pode ser nulo!");
        }
        validateCliente(venda.getCliente());
        validateFuncionario(venda.getFuncionario());
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