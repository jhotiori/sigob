package org.javapi.sigob.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Caixa;
import org.javapi.sigob.repository.CaixaRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class CaixaService {

    /**
     * Cria um novo CaixaService
     */
    public CaixaService() {
    }

    /**
     * Salva um novo Caixa
     *
     * @param caixa O Caixa a ser salvo
     * @throws IllegalArgumentException Se o Caixa for inválido
     */
    public void save(Caixa caixa) {
        validateCaixa(caixa);

        TransactionExecutor.executeVoid(em -> {
            new CaixaRepository(em).save(caixa);
        });
    }

    /**
     * Atualiza um Caixa
     *
     * @param caixa O Caixa a ser atualizado
     * @throws IllegalArgumentException Se o Caixa for inválido
     */
    public void update(Caixa caixa) {
        validateCaixa(caixa);

        TransactionExecutor.executeVoid(em -> {
            new CaixaRepository(em).update(caixa);
        });
    }

    /**
     * Remove um Caixa
     *
     * @param caixa O Caixa a ser removido
     * @throws IllegalArgumentException Se o Caixa for inválido
     */
    public void delete(Caixa caixa) {
        validateCaixa(caixa);

        TransactionExecutor.executeVoid(em -> {
            new CaixaRepository(em).deleteById(caixa.getId());
        });
    }

    /**
     * Verifica se um Caixa existe
     *
     * @param caixa O Caixa para verificar
     * @return boolean - true se existir
     */
    public boolean contains(Caixa caixa) {
        validateCaixa(caixa);

        return TransactionExecutor.query(em -> {
            return new CaixaRepository(em).contains(caixa.getId());
        });
    }

    /**
     * Retorna todos os Caixas
     *
     * @return List<Caixa> - Lista encontrada
     */
    public List<Caixa> findAll() {
        return TransactionExecutor.query(em -> {
            return new CaixaRepository(em).findAll();
        });
    }

    /**
     * Busca um Caixa pelo ID
     *
     * @param id O ID do Caixa
     * @return Optional<Caixa> - Caixa encontrado
     */
    public Optional<Caixa> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new CaixaRepository(em).findById(id);
        });
    }

    /**
     * Busca Caixas pelo status
     *
     * @param status O status desejado
     * @return List<Caixa> - Lista encontrada
     */
    public List<Caixa> findByStatus(String status) {
        validateStatus(status);

        return TransactionExecutor.query(em -> {
            return new CaixaRepository(em).findByStatus(status);
        });
    }

    /**
     * Busca Caixas pela data de abertura
     *
     * @param data A data desejada
     * @return List<Caixa> - Lista encontrada
     */
    public List<Caixa> findByDataAbertura(LocalDate data) {
        Validator.start()
                .expectNotNull(data, "Data não pode ser nula!")
                .validate();

        return TransactionExecutor.query(em -> {
            return new CaixaRepository(em).findByDataAbertura(data);
        });
    }

    /**
     * Busca Caixas pela data de fechamento
     *
     * @param data A data desejada
     * @return List<Caixa> - Lista encontrada
     */
    public List<Caixa> findByDataFecha(LocalDate data) {
        Validator.start()
                .expectNotNull(data, "Data não pode ser nula!")
                .validate();

        return TransactionExecutor.query(em -> {
            return new CaixaRepository(em).findByDataFecha(data);
        });
    }

    /**
     * Retorna Caixas abertos
     *
     * @return List<Caixa> - Lista encontrada
     */
    public List<Caixa> findAbertos() {
        return TransactionExecutor.query(em -> {
            return new CaixaRepository(em).findAbertos();
        });
    }

    /**
     * Retorna Caixas fechados
     *
     * @return List<Caixa> - Lista encontrada
     */
    public List<Caixa> findFechados() {
        return TransactionExecutor.query(em -> {
            return new CaixaRepository(em).findFechados();
        });
    }

    /**
     * Valida um Caixa completo
     *
     * @param caixa O Caixa
     */
    private void validateCaixa(Caixa caixa) {
        Validator.start()
                .expectNotNull(caixa, "Caixa não pode ser nulo!")
                .validate();

        validateStatus(caixa.getStatus());
    }

    /**
     * Valida o status do Caixa
     *
     * @param status O status
     */
    private void validateStatus(String status) {
        Validator.start()
                .expectNotBlank(status, "Status não pode ser vazio!")
                .validate();
    }
}