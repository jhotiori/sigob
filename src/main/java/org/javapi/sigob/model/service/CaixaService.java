package org.javapi.sigob.model.service;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.model.entity.Caixa;

public interface CaixaService extends JpaCrudService<Caixa, Integer> {

    /**
     * Busca caixas pelo status.
     *
     * @param status - Status utilizado na busca.
     * @return List<Caixa> - Lista de caixas encontradas.
     */
    List<Caixa> findByStatus(String status);

    /**
     * Busca caixas pela data de abertura.
     *
     * @param data - Data de abertura utilizada na busca.
     * @return List<Caixa> - Lista de caixas encontrados.
     */
    List<Caixa> findByDataAbertura(LocalDate data);

    /**
     * Busca caixas pela data de fechamento.
     *
     * @param data - Data de fechamento utilizada na busca.
     * @return List<Caixa> - Lista de caixas encontrados.
     */
    List<Caixa> findByDataFechamento(LocalDate data);

    /**
     * Busca todos os caixas abertos.
     *
     * @return List<Caixa> - Lista de caixas abertos.
     */
    List<Caixa> findAbertos();

    /**
     * Busca todos os caixas fechados.
     *
     * @return List<Caixa> - Lista de caixas fechados.
     */
    List<Caixa> findFechados();
}
