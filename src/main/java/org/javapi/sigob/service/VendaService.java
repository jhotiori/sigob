package org.javapi.sigob.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
     * Busca vendas pelo nome do cliente.
     *
     * @param nome Nome do cliente
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByClienteNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome não pode ser vazio")
                .validate();

        return TransactionExecutor.query(em -> {
            return new VendaRepository(em)
                    .findByClienteNome(nome);
        });
    }

    /**
     * Busca vendas pelo nome do funcionário.
     *
     * @param nome Nome do funcionário
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByFuncionarioNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome não pode ser vazio")
                .validate();

        return TransactionExecutor.query(em -> {
            return new VendaRepository(em)
                    .findByFuncionarioNome(nome);
        });
    }

    /**
     * Busca vendas pela data de abertura.
     *
     * @param data Data desejada
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByDataAbertura(
            LocalDate data
    ) {
        Validator.start()
                .expectNotNull(data, "Data não pode ser nula")
                .validate();

        return TransactionExecutor.query(em -> {
            return new VendaRepository(em)
                    .findByDataAbertura(data);
        });
    }

    /**
     * Busca vendas pela data de fechamento.
     *
     * @param data Data desejada
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByDataFinalizada(
            LocalDate data
    ) {
        Validator.start()
                .expectNotNull(data, "Data não pode ser nula")
                .validate();

        return TransactionExecutor.query(em -> {
            return new VendaRepository(em)
                    .findByDataFinalizada(data);
        });
    }

    /**
     * Busca vendas por período de abertura.
     *
     * @param inicio Data inicial
     * @param fim Data final
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByPeriodo(
            LocalDate inicio,
            LocalDate fim
    ) {
        Validator.start()
                .expectNotNull(inicio, "Data inicial não pode ser nula")
                .expectNotNull(fim, "Data final não pode ser nula")
                .validate();

        return TransactionExecutor.query(em -> {
            return new VendaRepository(em)
                    .findByPeriodo(inicio, fim);
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

    }

}
