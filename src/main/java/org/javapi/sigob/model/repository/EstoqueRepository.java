package org.javapi.sigob.model.repository;

import java.util.List;

import org.javapi.sigob.model.entity.Estoque;

public interface EstoqueRepository extends JpaCrudRepository<Estoque, Integer> {

    /**
     * Busca Estoques pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Estoque> - Estoques encontrados
     */
    List<Estoque> findByNome(String nome);

    /**
     * Busca Estoques pelo código.
     *
     * @param codigo - Código para busca
     * @return List<Estoque> - Estoques encontrados
     */
    List<Estoque> findByCodigo(String codigo);
}
