package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.exception.ClienteException;
import org.javapi.sigob.repository.ClienteRepository;

public class ClienteService {
    private ClienteRepository repository;

    /**
     * Construtor padrão do servico
     *
     * @return ClienteService - O servico
     */
    public ClienteService() {
    }

    /**
     * Cria um novo ClienteService
     *
     * @param repository O repositorio
     * @return ClienteService - O servico
     */
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva ou atualiza um Cliente
     *
     * @param cliente O Cliente
     * @throws ClienteException Se um cliente com o mesmo documento ja existir
     * @return Cliente - O Cliente salvo
     */
    public Cliente save(Cliente cliente) {
        validateCliente(cliente);
        int id = cliente.getIdCliente();
        String nome = cliente.getNmCliente();
        String documento = cliente.getNrDocumento();

        if (findByDocumento(documento) != null) {
            throw new ClienteException("Cliente com o mesmo documento já cadastrado!");
        }

        var novoCliente = new Cliente(id, nome, documento);
        if (id > 0) {
            this.repository.update(novoCliente);
        } else {
            this.repository.save(novoCliente);
        }

        return novoCliente;
    }

    /**
     * Confere se um cliente existe
     *
     * @param cliente O Cliente
     * @throws ClienteException Se o cliente for invalido
     * @return boolean - true se o Cliente existe, false se nao
     */
    public boolean contains(Cliente cliente) {
        validateCliente(cliente);
        return this.repository.contains(cliente);
    }

    /**
     * Remove um Cliente
     *
     * @param cliente O Cliente para ser removido
     * @throws ClienteException Se o cliente for invalido
     */
    public void delete(Cliente cliente) {
        validateCliente(cliente);
        if (this.repository.contains(cliente)) {
            this.repository.remove(cliente);
        }
    }

    /**
     * Retorna uma lista com todos os Clientes
     *
     * @return List<Cliente> - A lista de clientes
     */
    public List<Cliente> findAll() {
        return this.repository.findAll();
    }

    /**
     * Retorna um Cliente pelo seu ID
     *
     * @param id O ID do Cliente
     * @return Cliente - O Cliente buscado
     */
    public Cliente findById(int id) {
        validateId(id);
        return this.repository.findById(id);
    }

    /**
     * Retorna uma lista com todos os Clientes com o nome informado
     *
     * @param nome O Nome do Cliente
     * @return List<Cliente> - A lista de clientes
     */
    public List<Cliente> findByNome(String nome) {
        validateNome(nome);
        return this.repository.findByNome(nome);
    }

    /**
     * Retorna um Cliente pelo seu documento
     *
     * @param documento O Documento do Cliente
     * @return Cliente - O Cliente buscado
     */
    public Cliente findByDocumento(String documento) {
        validateDocumento(documento);
        return this.repository.findByDocumento(documento);
    }

    private void validateCliente(Cliente cliente) {
        if (cliente == null) {
            throw new ClienteException("Cliente não pode ser nulo");
        }
        validateId(cliente.getIdCliente());
        validateNome(cliente.getNmCliente());
        validateDocumento(cliente.getNrDocumento());
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new ClienteException("Id do cliente deve ser maior que zero");
        }
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
