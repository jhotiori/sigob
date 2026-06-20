package org.javapi.sigob.model.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Produto;

public interface ProdutoService extends JpaCrudService<Produto, Integer> {

    /**
     * Busca um produto pelo código.
     *
     * @param codigo - Código utilizado na busca.
     * @return Optional<Produto> - Produto encontrado, se existir.
     */
    Optional<Produto> findByCodigo(String codigo);

    /**
     * Busca produtos pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Produto> - Lista de produtos encontrados.
     */
    List<Produto> findByNome(String nome);

    /**
     * Busca produtos pelo nome da categoria.
     *
     * @param nomeCategoria - Nome da categoria utilizada na busca.
     * @return List<Produto> - Lista de produtos encontrados.
     */
    List<Produto> findByCategoria(String nomeCategoria);

    /**
     * Busca produtos pelo nome da moeda.
     *
     * @param moeda - Nome da moeda utilizada na busca.
     * @return List<Produto> - Lista de produtos encontrados.
     */
    List<Produto> findByMoeda(String moeda);
}
