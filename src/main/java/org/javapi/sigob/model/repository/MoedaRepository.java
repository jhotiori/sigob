package org.javapi.sigob.model.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Moeda;

public interface MoedaRepository extends JpaCrudRepository<Moeda, Integer> {

    /**
     * Busca Moedas pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Moeda> - Moedas encontradas
     */
    List<Moeda> findByNome(String nome);

    /**
     * Busca uma Moeda pela sigla.
     *
     * @param sigla - Sigla para busca
     * @return Optional<Moeda> - Moeda encontrada
     */
    Optional<Moeda> findBySigla(String sigla);

    /**
     * Busca Moedas pelo Cifrao.
     *
     * @param cifrao - Cifrao para busca
     * @return List<Moeda> - Moedas encontradas
     */
    List<Moeda> findByCifrao(String cifrao);
}
