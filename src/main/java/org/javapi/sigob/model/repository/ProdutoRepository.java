package org.javapi.sigob.model.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Produto;

public interface ProdutoRepository extends JpaCrudRepository<Produto, Integer> {

    /**
     * Busca Produtos pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Produto> - Produtos encontrados
     */
    List<Produto> findByNome(String nome);

    /**
     * Busca um Produto pelo código.
     *
     * @param codigo - Código para busca
     * @return Optional<Produto> - Produto encontrado
     */
    Optional<Produto> findByCodigo(String codigo);

    /**
     * Busca Produtos pelo nome da Categoria.
     *
     * @param nomeCategoria - Nome da Categoria
     * @return List<Produto> - Produtos encontrados
     */
    List<Produto> findByCategoriaNome(String nomeCategoria);

    /**
     * Busca Produtos pelo ID da Categoria.
     *
     * @param idCategoria - ID da Categoria
     * @return List<Produto> - Produtos encontrados
     */
    List<Produto> findByCategoriaId(int idCategoria);

    /**
     * Busca Produtos pelo ID da Moeda.
     *
     * @param idMoeda - ID da Moeda
     * @return List<Produto> - Produtos encontrados
     */
    List<Produto> findByMoedaId(int idMoeda);

    /**
     * Busca Produtos pelo nome da Moeda.
     *
     * @param moeda - Nome da Moeda
     * @return List<Produto> - Produtos encontrados
     */
    List<Produto> findByMoedaNome(String moeda);
}
