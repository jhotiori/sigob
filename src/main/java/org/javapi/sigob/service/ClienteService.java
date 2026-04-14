package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.exception.ClienteException;
import org.javapi.sigob.repository.ClienteRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ClienteService {

    /**
     * Cria um novo ClienteService
     *
     * @return ClienteService - O servico
     */
    public ClienteService() {
    }

    /**
     * Salva ou atualiza um Cliente
     *
     * @param cliente O Cliente
     * @throws ClienteException Se um cliente com o mesmo documento ja existir
     * @return Cliente - O Cliente salvo
     */
    public Cliente save(Cliente cliente) {
        validateNome(cliente.getNmCliente());
        validateDocumento(cliente.getNrDocumento());

        int id = cliente.getIdCliente();
        String nome = cliente.getNmCliente();
        String documento = cliente.getNrDocumento();

        if (!findByDocumento(documento).isEmpty()) {
            throw new ClienteException("Cliente com o mesmo documento já cadastrado!");
        }

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ClienteRepository repository = new ClienteRepository(em);

        try {
            transaction.begin();

            var novoCliente = new Cliente(id, nome, documento);
            if (id > 0) {
                repository.update(novoCliente);
            } else {
                repository.save(novoCliente);
            }

            transaction.commit();
            return novoCliente;
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
     * Confere se um cliente existe
     *
     * @param cliente O Cliente
     * @throws ClienteException Se o cliente for invalido
     * @return boolean - true se o Cliente existe, false se nao
     */
    public boolean contains(Cliente cliente) {
        EntityManager em = JPAConfig.getEntityManager();
        ClienteRepository repository = new ClienteRepository(em);

        try {
            return repository.contains(cliente);
        } finally {
            em.close();
        }
    }

    /**
     * Remove um Cliente
     *
     * @param cliente O Cliente para ser removido
     * @throws ClienteException Se o cliente for invalido
     */
    public void delete(Cliente cliente) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ClienteRepository repository = new ClienteRepository(em);

        try {
            transaction.begin();
            repository.remove(cliente);
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
     * Retorna uma lista com todos os Clientes
     *
     * @return List<Cliente> - A lista de clientes
     */
    public List<Cliente> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        ClienteRepository repository = new ClienteRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna um Cliente pelo seu ID
     *
     * @param id O ID do Cliente
     * @return Cliente - O Cliente buscado
     */
    public Cliente findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        ClienteRepository repository = new ClienteRepository(em);

        try {
            return repository.findById(id);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista com todos os Clientes com o nome informado
     *
     * @param nome O Nome do Cliente
     * @return List<Cliente> - A lista de clientes
     */
    public List<Cliente> findByNome(String nome) {
        validateNome(nome);

        EntityManager em = JPAConfig.getEntityManager();
        ClienteRepository repository = new ClienteRepository(em);

        try {
            return repository.findByNome(nome);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna um Cliente pelo seu documento
     *
     * @param documento O Documento do Cliente
     * @return Cliente - O Cliente buscado
     */
    public List<Cliente> findByDocumento(String documento) {
        validateDocumento(documento);

        EntityManager em = JPAConfig.getEntityManager();
        ClienteRepository repository = new ClienteRepository(em);

        try {
            return repository.findByDocumento(documento);
        } finally {
            em.close();
        }
    }

    private void validateCliente(Cliente cliente) {
        if (cliente == null) {
            throw new ClienteException("Cliente não pode ser nulo");
        }
        validateNome(cliente.getNmCliente());
        validateDocumento(cliente.getNrDocumento());
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ClienteException("Nome do cliente não pode ser nulo ou vazio");
        }
    }

    private void validateDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new ClienteException("Documento do cliente não pode ser nulo ou vazio");
        }
    }
}
