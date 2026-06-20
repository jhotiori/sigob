package org.javapi.sigob.model.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Moeda;

public interface MoedaService extends JpaCrudService<Moeda, Integer> {

    /**
     * Busca moedas pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Moeda> - Lista de moedas encontradas.
     */
    List<Moeda> findByNome(String nome);

    /**
     * Busca uma moeda pela sigla.
     *
     * @param sigla - Sigla utilizada na busca.
     * @return Optional<Moeda> - Moeda encontrada, se existir.
     */
    Optional<Moeda> findBySigla(String sigla);

    /**
     * Busca moedas pelo Cifrao.
     *
     * @param cifrao - Cifrao utilizado na busca.
     * @return List<Moeda> - Lista de moedas encontradas.
     */
    List<Moeda> findByCifrao(String cifrao);
}
