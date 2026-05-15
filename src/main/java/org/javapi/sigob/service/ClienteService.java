package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.repository.ClienteRepository;
import org.javapi.sigob.repository.DocumentoRepository;
import org.javapi.sigob.repository.VendaRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class ClienteService {

    /**
     * Cria um novo ClienteService
     *
     * @return ClienteService - O servico
     */
    public ClienteService() {
    }

    /**
     * Salva um Cliente
     *
     * @param cliente O Cliente
     */
    public void save(Cliente cliente) {
        validateCliente(cliente);

        TransactionExecutor.executeVoid(em -> {
            new ClienteRepository(em).save(cliente);
        });
    }

    /**
     * Atualiza um Cliente
     *
     * @param cliente O Cliente
     */
    public void update(Cliente cliente) {
        validateCliente(cliente);

        TransactionExecutor.executeVoid(em -> {
            new ClienteRepository(em).update(cliente);
        });
    }

    /**
     * Confere se um cliente existe
     *
     * @param cliente O Cliente
     * @return boolean - true se o Cliente existe, false se nao
     */
    public boolean contains(Cliente cliente) {
        validateCliente(cliente);

        return TransactionExecutor.query(em -> {
            return new ClienteRepository(em).contains(cliente.getId());
        });
    }

    /**
     * Remove um Cliente
     *
     * @param cliente O Cliente para ser removido
     */
    public void delete(Cliente cliente) {
        //validateCliente(cliente); -- não é necessário validar o objeto recém recuperado do banco

        if (validateDeleteCliente(cliente)){
            int documento_id = cliente.getDocumento().getId();

            TransactionExecutor.executeVoid(em -> {
                new ClienteRepository(em).deleteById(cliente.getId());
            });

            //após deletar o cliente tem que deletar o documento que ele tinha
            if(documento_id > 0){
                TransactionExecutor.executeVoid(em -> {
                    new DocumentoRepository(em).deleteById(documento_id);
                });
            }

        } else{
            throw new SigobException("O Cliente possuí vínculo com Vendas, não podendo ser removido!");
        }

    }

    /**
     * Retorna uma lista com todos os Clientes
     *
     * @return List<Cliente> - A lista de clientes
     */
    public List<Cliente> findAll() {
        return TransactionExecutor.query(em -> {
            return new ClienteRepository(em).findAll();
        });
    }

    /**
     * Retorna um Cliente pelo seu ID
     *
     * @param id O ID do Cliente
     * @return Optional<Cliente> - O Cliente buscado
     */
    public Optional<Cliente> findById(int id) {
        validateId(id);

        return TransactionExecutor.query(em -> {
            return new ClienteRepository(em).findById(id);
        });
    }

    /**
     * Retorna uma lista com todos os Clientes com o nome informado
     *
     * @param nome O Nome do Cliente
     * @return List<Cliente> - A lista de clientes
     */
    public List<Cliente> findByNome(String nome) {
        validateNome(nome);

        return TransactionExecutor.query(em -> {
            return new ClienteRepository(em).findByNome(nome);
        });
    }

    /**
     * Retorna um Cliente pelo seu documento
     *
     * @param documento O Documento do Cliente
     * @return Optional<Cliente> - O Cliente buscado
     */
    public Optional<Cliente> findByDocumento(String documento) {
        validateDocumento(documento);

        return TransactionExecutor.query(em -> {
            return new ClienteRepository(em).findByDocumento(documento);
        });
    }

    /**
     * Valida um Cliente por completo
     *
     * @param cliente O Cliente a ser validado
     * @throws IllegalArgumentException Se o cliente for invalido
     */
    private void validateCliente(Cliente cliente) {
        Validator.start()
                .expectNotNull(cliente, "Cliente não pode ser nulo")
                .validate();

        validateNome(cliente.getNome());
    }

    /**
     * Valida o nome de um Cliente
     *
     * @param nome O nome a ser validado
     * @throws IllegalArgumentException Se o nome for invalido
     */
    private void validateNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome do cliente não pode ser nulo ou vazio")
                .validate();
    }

    /**
     * Valida o ID de um Cliente
     *
     * @param id O ID a ser validado
     * @throws IllegalArgumentException Se o ID for invalido
     */
    private void validateId(int id) {
        Validator.start()
                .expectNotNull(id, "ID não pode ser nulo")
                .validate();
    }

    /**
     * Valida o documento de um Cliente
     *
     * @param documento O documento a ser validado
     * @throws IllegalArgumentException Se o documento for invalido
     */
    private void validateDocumento(String documento) {
        Validator.start()
                .expectNotBlank(documento, "Documento não pode ser nulo ou vazio")
                .validate();
    }

    private boolean validateDeleteCliente(Cliente cliente){
        return TransactionExecutor.query(em -> {
            return (new VendaRepository(em).findByClienteId(cliente.getId()).isEmpty() ? true : false);
        });
    }
}
