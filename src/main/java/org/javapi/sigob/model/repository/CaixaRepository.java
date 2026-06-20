package org.javapi.sigob.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Caixa;

public interface CaixaRepository extends JpaCrudRepository<Caixa, Integer> {

    /**
     * Busca Caixas pelo status.
     *
     * @param status - Status para busca
     * @return List<Caixa> - Caixas encontrados
     */
    List<Caixa> findByStatus(String status);

    /**
     * Busca Caixas pela data de abertura.
     *
     * @param data - Data para busca
     * @return List<Caixa> - Caixas encontrados
     */
    List<Caixa> findByDataAbertura(LocalDate data);

    /**
     * Busca Caixas pela data de fechamento.
     *
     * @param data - Data para busca
     * @return List<Caixa> - Caixas encontrados
     */
    List<Caixa> findByDataFechamento(LocalDate data);

    /**
     * Busca Caixas abertos.
     *
     * @return List<Caixa> - Caixas encontrados
     */
    List<Caixa> findAbertos();

    /**
     * Busca Caixas fechados.
     *
     * @return List<Caixa> - Caixas encontrados
     */
    List<Caixa> findFechados();
}
