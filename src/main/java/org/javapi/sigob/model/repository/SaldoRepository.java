package org.javapi.sigob.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Saldo;

public interface SaldoRepository extends JpaCrudRepository<Saldo, Integer> {

    /**
     * Busca Saldos pelo tipo.
     *
     * @param tipo - Tipo para busca
     * @return List<Saldo> - Saldos encontrados
     */
    List<Saldo> findByTipo(String tipo);

    /**
     * Busca Saldos pela descrição.
     *
     * @param descricao - Descrição para busca
     * @return List<Saldo> - Saldos encontrados
     */
    List<Saldo> findByDescricao(String descricao);

    /**
     * Busca Saldos pela data.
     *
     * @param data - Data para busca
     * @return List<Saldo> - Saldos encontrados
     */
    List<Saldo> findByDataSaldo(LocalDate data);

    /**
     * Busca Saldos pela Venda.
     *
     * @param idVenda - ID da Venda
     * @return List<Saldo> - Saldos encontrados
     */
    List<Saldo> findByVendaId(int idVenda);

    /**
     * Busca Saldos pelo ID do Caixa.
     *
     * @param idCaixa - ID do Caixa
     * @return List<Saldo> - Saldos encontrados
     */
    List<Saldo> findByCaixaId(int idCaixa);
}
