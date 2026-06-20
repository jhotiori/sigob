package org.javapi.sigob.model.service;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Saldo;

public interface SaldoService extends JpaCrudService<Saldo, Integer> {

    /**
     * Busca saldos pelo tipo.
     *
     * @param tipo - Tipo utilizado na busca.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    List<Saldo> findByTipo(String tipo);

    /**
     * Busca saldos pela descrição.
     *
     * @param descricao - Descrição utilizada na busca.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    List<Saldo> findByDescricao(String descricao);

    /**
     * Busca saldos pela data do saldo.
     *
     * @param data - Data utilizada na busca.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    List<Saldo> findByDataSaldo(LocalDate data);

    /**
     * Busca saldos vinculados a uma venda.
     *
     * @param idVenda - Identificador da venda.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    List<Saldo> findByVendaId(int idVenda);

    /**
     * Busca saldos vinculados a um caixa.
     *
     * @param idCaixa - Identificador do caixa.
     * @return List<Saldo> - Lista de saldos encontrados.
     */
    List<Saldo> findByCaixaId(int idCaixa);
}
