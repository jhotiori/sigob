package org.javapi.sigob.model.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Saldo;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.SaldoRepositoryImpl;
import org.javapi.sigob.model.service.SaldoService;
import org.javapi.sigob.model.validation.entity.SaldoValidator;

public class SaldoServiceImpl implements SaldoService {

    /**
     * Cria um novo SaldoService
     */
    public SaldoServiceImpl() {
    }

    /**
     * Salva um novo Saldo
     *
     * @param saldo O Saldo a ser salvo
     */
    @Override
    public void save(Saldo saldo) {
        SaldoValidator.validate(saldo);
        Database.write(
                SaldoRepositoryImpl::new,
                repo -> repo.save(saldo)
        );
    }

    /**
     * Atualiza um Saldo
     *
     * @param saldo O Saldo a ser atualizado
     */
    @Override
    public void update(Saldo saldo) {
        SaldoValidator.validate(saldo);
        Database.write(
                SaldoRepositoryImpl::new,
                repo -> repo.update(saldo)
        );
    }

    /**
     * Remove um Saldo
     *
     * @param saldo O Saldo a ser removido
     */
    @Override
    public void delete(Saldo saldo) {
        Database.write(
                SaldoRepositoryImpl::new,
                repo -> repo.deleteById(saldo.getId())
        );
    }

    /**
     * Verifica se um Saldo existe
     *
     * @param id O ID do Saldo
     * @return boolean - true se existir
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                SaldoRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Retorna todos os Saldos
     *
     * @return List<Saldo> - Lista encontrada
     */
    @Override
    public List<Saldo> findAll() {
        return Database.read(
                SaldoRepositoryImpl::new,
                SaldoRepositoryImpl::findAll
        );
    }

    /**
     * Busca um Saldo pelo ID
     *
     * @param id O ID do Saldo
     * @return Optional<Saldo> - Saldo encontrado
     */
    @Override
    public Optional<Saldo> findById(Integer id) {
        return Database.read(
                SaldoRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca saldos pelo tipo.
     *
     * @param tipo - Tipo utilizado na busca.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    @Override
    public List<Saldo> findByTipo(String tipo) {
        SaldoValidator.validateTipo(tipo);
        return Database.read(
                SaldoRepositoryImpl::new,
                repo -> repo.findByTipo(tipo)
        );
    }

    /**
     * Busca saldos pela descrição.
     *
     * @param descricao - Descrição utilizada na busca.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    @Override
    public List<Saldo> findByDescricao(String descricao) {
        SaldoValidator.validateDescricao(descricao);
        return Database.read(
                SaldoRepositoryImpl::new,
                repo -> repo.findByDescricao(descricao)
        );
    }

    /**
     * Busca saldos pela data do saldo.
     *
     * @param data - Data utilizada na busca.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    @Override
    public List<Saldo> findByDataSaldo(LocalDate data) {
        SaldoValidator.validateDataSaldo(data);
        return Database.read(
                SaldoRepositoryImpl::new,
                repo -> repo.findByDataSaldo(data)
        );
    }

    /**
     * Busca saldos vinculados a uma venda.
     *
     * @param idVenda - Identificador da venda.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    @Override
    public List<Saldo> findByVendaId(int idVenda) {
        return Database.read(
                SaldoRepositoryImpl::new,
                repo -> repo.findByVendaId(idVenda)
        );
    }

    /**
     * Busca saldos vinculados a um caixa.
     *
     * @param idCaixa - Identificador do caixa.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    @Override
    public List<Saldo> findByCaixaId(int idCaixa) {
        return Database.read(
                SaldoRepositoryImpl::new,
                repo -> repo.findByCaixaId(idCaixa)
        );
    }
}
