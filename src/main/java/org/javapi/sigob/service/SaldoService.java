package org.javapi.sigob.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Saldo;
import org.javapi.sigob.repository.SaldoRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class SaldoService {

    /**
     * Cria um novo SaldoService
     */
    public SaldoService() {
    }

    /**
     * Salva um novo Saldo
     *
     * @param saldo O Saldo a ser salvo
     */
    public void save(Saldo saldo) {
        validateSaldo(saldo);

        TransactionExecutor.executeVoid(em -> {
            new SaldoRepository(em).save(saldo);
        });
    }

    /**
     * Atualiza um Saldo
     *
     * @param saldo O Saldo a ser atualizado
     */
    public void update(Saldo saldo) {
        validateSaldo(saldo);

        TransactionExecutor.executeVoid(em -> {
            new SaldoRepository(em).update(saldo);
        });
    }

    /**
     * Remove um Saldo
     *
     * @param saldo O Saldo a ser removido
     */
    public void delete(Saldo saldo) {
        validateSaldo(saldo);

        TransactionExecutor.executeVoid(em -> {
            new SaldoRepository(em).deleteById(saldo.getId());
        });
    }

    /**
     * Verifica se um Saldo existe
     *
     * @param saldo O Saldo
     * @return boolean - true se existir
     */
    public boolean contains(Saldo saldo) {
        validateSaldo(saldo);

        return TransactionExecutor.query(em -> {
            return new SaldoRepository(em).contains(saldo.getId());
        });
    }

    /**
     * Retorna todos os Saldos
     *
     * @return List<Saldo> - Lista encontrada
     */
    public List<Saldo> findAll() {
        return TransactionExecutor.query(em -> {
            return new SaldoRepository(em).findAll();
        });
    }

    /**
     * Busca um Saldo pelo ID
     *
     * @param id O ID do Saldo
     * @return Optional<Saldo> - Saldo encontrado
     */
    public Optional<Saldo> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new SaldoRepository(em).findById(id);
        });
    }

    /**
     * Busca Saldos pelo tipo
     *
     * @param tipo O tipo desejado
     * @return List<Saldo> - Lista encontrada
     */
    public List<Saldo> findByTipo(String tipo) {
        validateTipo(tipo);

        return TransactionExecutor.query(em -> {
            return new SaldoRepository(em).findByTipo(tipo);
        });
    }

    /**
     * Busca Saldos pela descrição
     *
     * @param descricao A descrição desejada
     * @return List<Saldo> - Lista encontrada
     */
    public List<Saldo> findByDescricao(String descricao) {
        Validator.start()
                .expectNotBlank(descricao, "Descrição não pode ser vazia!")
                .validate();

        return TransactionExecutor.query(em -> {
            return new SaldoRepository(em).findByDescricao(descricao);
        });
    }

    /**
     * Busca Saldos pela data
     *
     * @param data A data desejada
     * @return List<Saldo> - Lista encontrada
     */
    public List<Saldo> findByDataSaldo(LocalDate data) {
        Validator.start()
                .expectNotNull(data, "Data não pode ser nula!")
                .validate();

        return TransactionExecutor.query(em -> {
            return new SaldoRepository(em).findByDataSaldo(data);
        });
    }

    /**
     * Busca Saldos pela Venda
     *
     * @param idVenda O ID da Venda
     * @return List<Saldo> - Lista encontrada
     */
    public List<Saldo> findByVendaId(int idVenda) {
        return TransactionExecutor.query(em -> {
            return new SaldoRepository(em).findByVendaId(idVenda);
        });
    }

    /**
     * Valida um Saldo completo
     *
     * @param saldo O Saldo
     */
    private void validateSaldo(Saldo saldo) {
        Validator.start()
                .expectNotNull(saldo, "Saldo não pode ser nulo!")
                .validate();

        validateTipo(saldo.getTipo());
    }

    /**
     * Valida o tipo do Saldo
     *
     * @param tipo O tipo
     */
    private void validateTipo(String tipo) {
        Validator.start()
                .expectNotBlank(tipo, "Tipo não pode ser vazio!")
                .validate();
    }
}