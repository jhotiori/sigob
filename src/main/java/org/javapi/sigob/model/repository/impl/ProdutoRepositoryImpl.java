package org.javapi.sigob.model.repository.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Produto;
import org.javapi.sigob.model.repository.ProdutoRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação de repositório para Produto.
 */
public final class ProdutoRepositoryImpl extends JpaCrudRepositoryImpl<Produto, Integer> implements ProdutoRepository {

    /**
     * Cria um novo ProdutoRepository.
     *
     * @param entityManager - EntityManager do repositório
     */
    public ProdutoRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Produto.class);
    }

    /**
     * Busca todos os Produtos disponíveis.
     *
     * @return List<Produto> - Os Produtos encontrados
     */
    @Override
    public List<Produto> findAll() {
        return query("""
                SELECT DISTINCT p
                FROM %s p
                LEFT JOIN FETCH p.categoria
                LEFT JOIN FETCH p.moeda
                """)
                .list();
    }

    /**
     * Busca um Produto pelo ID.
     *
     * @param id - ID do Produto
     * @return Optional<Produto> - O Produto encontrado
     */
    @Override
    public Optional<Produto> findById(Integer id) {
        return query("""
                SELECT p
                FROM %s p
                LEFT JOIN FETCH p.categoria
                LEFT JOIN FETCH p.moeda
                WHERE p.id = :id
                """)
                .param("id", id)
                .one();
    }

    /**
     * Busca Produtos pelo ID da Categoria.
     *
     * @param idCategoria - ID da Categoria
     * @return List<Produto> - Os Produtos encontrados
     */
    @Override
    public List<Produto> findByCategoriaId(int idCategoria) {
        return query("""
                SELECT p
                FROM %s p
                LEFT JOIN FETCH p.categoria
                LEFT JOIN FETCH p.moeda
                WHERE p.categoria.id = :id
                """)
                .param("id", idCategoria)
                .list();
    }

    /**
     * Busca Produtos pelo nome da Categoria.
     *
     * @param nomeCategoria - Nome da Categoria
     * @return List<Produto> - Os Produtos encontrados
     */
    @Override
    public List<Produto> findByCategoriaNome(String nomeCategoria) {
        return query("""
                SELECT DISTINCT p
                FROM %s p
                LEFT JOIN FETCH p.categoria c
                LEFT JOIN FETCH p.moeda
                WHERE LOWER(c.nome) LIKE LOWER(:nome)
                """)
                .param("nome", like(nomeCategoria))
                .list();
    }

    /**
     * Busca um Produto pelo código.
     *
     * @param codigo - Código do Produto
     * @return Optional<Produto> - O Produto encontrado
     */
    @Override
    public Optional<Produto> findByCodigo(String codigo) {
        return query("""
                SELECT p
                FROM %s p
                LEFT JOIN FETCH p.categoria
                LEFT JOIN FETCH p.moeda
                WHERE LOWER(p.codigo) LIKE LOWER(:str)
                """)
                .param("str", like(codigo))
                .one();
    }

    /**
     * Busca Produtos pelo ID da Moeda.
     *
     * @param idMoeda - ID da Moeda
     * @return List<Produto> - Os Produtos encontrados
     */
    @Override
    public List<Produto> findByMoedaId(int idMoeda) {
        return query("""
                SELECT p
                FROM %s p
                LEFT JOIN FETCH p.categoria
                LEFT JOIN FETCH p.moeda
                WHERE p.moeda.id = :id
                """)
                .param("id", idMoeda)
                .list();
    }

    /**
     * Busca Produtos pelo nome da Moeda.
     *
     * @param moeda - Nome da Moeda
     * @return List<Produto> - Os Produtos encontrados
     */
    @Override
    public List<Produto> findByMoedaNome(String moeda) {
        return query("""
                SELECT DISTINCT p
                FROM %s p
                LEFT JOIN FETCH p.categoria
                LEFT JOIN FETCH p.moeda m
                WHERE LOWER(m.nome) LIKE LOWER(:str)
                """)
                .param("str", like(moeda))
                .list();
    }

    /**
     * Busca Produtos pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Produto> - Os Produtos encontrados
     */
    @Override
    public List<Produto> findByNome(String nome) {
        return query("""
                SELECT DISTINCT p
                FROM %s p
                LEFT JOIN FETCH p.categoria
                LEFT JOIN FETCH p.moeda
                WHERE LOWER(p.nome) LIKE LOWER(:str)
                """)
                .param("str", like(nome))
                .list();
    }
}
