package org.javapi.sigob.model.service;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Venda;

public interface VendaService extends JpaCrudService<Venda, Integer> {

    /**
     * Busca vendas abertas.
     *
     * @return List<Venda> - Lista de vendas abertas.
     */
    List<Venda> findAbertas();

    /**
     * Busca vendas finalizadas.
     *
     * @return List<Venda> - Lista de vendas finalizadas.
     */
    List<Venda> findFinalizadas();

    /**
     * Busca vendas pelo nome do cliente.
     *
     * @param nome - Nome do cliente utilizado na busca.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    List<Venda> findByClienteNome(String nome);

    /**
     * Busca vendas pelo nome do funcionário.
     *
     * @param nome - Nome do funcionário utilizado na busca.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    List<Venda> findByFuncionarioNome(String nome);

    /**
     * Busca vendas pela data de abertura.
     *
     * @param data - Data de abertura utilizada na busca.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    List<Venda> findByDataAbertura(LocalDate data);

    /**
     * Busca vendas pela data de finalização.
     *
     * @param data - Data de finalização utilizada na busca.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    List<Venda> findByDataFinalizada(LocalDate data);

    /**
     * Busca vendas dentro de um período.
     *
     * @param inicio - Data inicial do período.
     * @param fim - Data final do período.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    List<Venda> findByPeriodo(LocalDate inicio, LocalDate fim);
}
