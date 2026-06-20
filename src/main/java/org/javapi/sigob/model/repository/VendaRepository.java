package org.javapi.sigob.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Venda;

public interface VendaRepository extends JpaCrudRepository<Venda, Integer> {

    /**
     * Busca Vendas pelo nome do Cliente.
     *
     * @param nome - Nome do Cliente
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findByClienteNome(String nome);

    /**
     * Busca Vendas pelo ID do Cliente.
     *
     * @param id - ID do Cliente
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findByClienteId(int id);

    /**
     * Busca Vendas pelo nome do Funcionário.
     *
     * @param nome - Nome do Funcionário
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findByFuncionarioNome(String nome);

    /**
     * Busca Vendas pelo ID do Funcionário.
     *
     * @param id - ID do Funcionário
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findByFuncionarioId(int id);

    /**
     * Busca Vendas pela data de abertura.
     *
     * @param data - Data de abertura
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findByDataAbertura(LocalDate data);

    /**
     * Busca Vendas pela data de finalização.
     *
     * @param data - Data de finalização
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findByDataFinalizada(LocalDate data);

    /**
     * Busca Vendas por período.
     *
     * @param inicio - Data inicial
     * @param fim - Data final
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findByPeriodo(LocalDate inicio, LocalDate fim);

    /**
     * Busca Vendas abertas.
     *
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findAbertas();

    /**
     * Busca Vendas finalizadas.
     *
     * @return List<Venda> - Vendas encontradas
     */
    List<Venda> findFinalizadas();
}
