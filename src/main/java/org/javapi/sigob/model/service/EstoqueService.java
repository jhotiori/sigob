package org.javapi.sigob.model.service;

import java.util.List;

import org.javapi.sigob.model.entity.Estoque;

public interface EstoqueService extends JpaCrudService<Estoque, Integer> {

    /**
     * Busca estoques pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Estoque> - Lista de estoques encontrados.
     */
    List<Estoque> findByNome(String nome);

    /**
     * Busca estoques pelo código.
     *
     * @param codigo - Código utilizado na busca.
     * @return List<Estoque> - Lista de estoques encontrados.
     */
    List<Estoque> findByCodigo(String codigo);
}
