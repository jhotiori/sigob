package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.repository.VendaRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class VendaService {

    /**
     * Cria um novo VendaService
     */
    public VendaService() {
    }

    /**
     * Salva uma nova Venda
     *
     * @param venda A Venda a ser salva
     * @throws IllegalArgumentException Se a Venda for inválida
     */
    public void save(Venda venda) {
        validateVenda(venda);

        TransactionExecutor.executeVoid(em -> {
            new VendaRepository(em).save(venda);
        });
    }

    /**
     * Atualiza uma Venda existente
     *
     * @param venda A Venda a ser atualizada
     * @throws IllegalArgumentException Se a Venda for inválida
     */
    public void update(Venda venda) {
        validateVenda(venda);
        validateId(venda.getId());

        TransactionExecutor.executeVoid(em -> {
            new VendaRepository(em).update(venda);
        });
    }

    /**
     * Deleta uma Venda
     *
     * @param venda A Venda a ser deletada
     * @throws IllegalArgumentException Se a Venda for inválida
     */
    public void delete(Venda venda) {
        validateVenda(venda);

        TransactionExecutor.executeVoid(em -> {
            new VendaRepository(em).deleteById(venda.getId());
        });
    }

    /**
     * Verifica se uma Venda existe
     *
     * @param venda A Venda a ser verificada
     * @return boolean - true se existir, false caso contrário
     * @throws IllegalArgumentException Se a Venda for inválida
     */
    public boolean contains(Venda venda) {
        validateVenda(venda);

        return TransactionExecutor.query(em -> {
            return new VendaRepository(em).contains(venda.getId());
        });
    }

    /**
     * Retorna todas as Vendas
     *
     * @return List<Venda> - Lista de Vendas
     */
    public List<Venda> findAll() {
        return TransactionExecutor.query(em -> {
            return new VendaRepository(em).findAll();
        });
    }

    /**
     * Busca uma Venda pelo ID
     *
     * @param id O ID da Venda
     * @return Optional<Venda> - A Venda encontrada
     * @throws IllegalArgumentException Se o ID for inválido
     */
    public Optional<Venda> findById(int id) {
        validateId(id);

        return TransactionExecutor.query(em -> {
            return new VendaRepository(em).findById(id);
        });
    }

    /**
     * Retorna Vendas com status 'ABERTA'
     *
     * @return List<Venda> - Lista de Vendas abertas
     */
    public List<Venda> findAbertas() {
        return TransactionExecutor.query(em -> {
            return new VendaRepository(em).findAbertas();
        });
    }

    /**
     * Retorna Vendas com status 'FINALIZADA'
     *
     * @return List<Venda> - Lista de Vendas finalizadas
     */
    public List<Venda> findFinalizadas() {
        return TransactionExecutor.query(em -> {
            return new VendaRepository(em).findFinalizadas();
        });
    }

    /**
     * Valida uma Venda por completo
     *
     * @param venda A Venda a ser validada
     * @throws IllegalArgumentException Se inválida
     */
    private void validateVenda(Venda venda) {
        Validator.start()
                .expectNotNull(venda, "Venda não pode ser nula!")
                .validate();

        validateCliente(venda.getCliente());
        validateFuncionario(venda.getFuncionario());
    }

    /**
     * Valida o Cliente da Venda
     *
     * @param cliente O Cliente
     * @throws IllegalArgumentException Se inválido
     */
    private void validateCliente(Cliente cliente) {
        Validator.start()
                .expectNotNull(cliente, "Cliente não pode ser nulo!")
                .validate();

        validateId(cliente.getId());
    }

    /**
     * Valida o Funcionario da Venda
     *
     * @param funcionario O Funcionario
     * @throws IllegalArgumentException Se inválido
     */
    private void validateFuncionario(Funcionario funcionario) {
        Validator.start()
                .expectNotNull(funcionario, "Funcionario não pode ser nulo!")
                .validate();

        validateId(funcionario.getId());
    }

    /**
     * Valida um ID
     *
     * @param id O ID a ser validado
     * @throws IllegalArgumentException Se inválido
     */
    private void validateId(int id) {
        Validator.start()
                .expectNotNull(id, "ID não pode ser nulo!")
                .validate();
    }
}
