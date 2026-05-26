package org.javapi.sigob.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Produto;

import jakarta.persistence.EntityManager;

/**
 * Repositório de produtos.
 */
public class ProdutoRepository
        extends BaseRepository<Produto, Integer> {

    /**
     * Cria um novo ProdutoRepository.
     *
     * @param em - EntityManager utilizado
     */
    public ProdutoRepository(
            EntityManager em
    ) {
        super(em, Produto.class);
    }

    /**
     * Busca todos os produtos disponíveis.
     *
     * @return List<Produto> - Produtos encontrados
     */
    public List<Produto> findAll() {
        return em.createQuery("""
                        SELECT DISTINCT p FROM produtos p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.moeda
                        """, Produto.class)
                .getResultList();
    }

    /**
     * Busca um produto pelo ID.
     *
     * @param id - ID do produto
     * @return Optional<Produto> - Produto encontrado
     */
    @Override
    public Optional<Produto> findById(Integer id) {
        return Optional.ofNullable(
                em.createQuery("""
                                SELECT p FROM produtos p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.moeda
                                WHERE p.id = :id""", Produto.class)
                        .setParameter("id", id)
                        .getSingleResultOrNull()
        );
    }

    /**
     * Busca produtos cujo nome contenha o valor informado.
     *
     * @param nome - Nome para busca
     * @return List<Produto> - Produtos encontrados
     */
    public List<Produto> findByNome(
            String nome
    ) {
        return em.createQuery("""
                        SELECT DISTINCT p FROM produtos p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.moeda
                        WHERE LOWER(p.nome) LIKE LOWER(:str)""", Produto.class)
                .setParameter("str","%" + nome + "%")
                .getResultList();
    }

    /**
     * Busca produto pelo código.
     *
     * @param codigo - Código do produto
     * @return Optional<Produto> - Produto encontrado
     */
    public Optional<Produto> findByCodigo(
            String codigo
    ) {
        return Optional.ofNullable(
                em.createQuery("""
                                SELECT p FROM produtos p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.moeda
                                WHERE LOWER (p.codigo) LIKE LOWER(:str) """, Produto.class)
                        .setParameter("str", "%" + codigo + "%")
                        .getSingleResultOrNull()
        );
    }

    /**
     * Busca produtos pelo nome da categoria.
     *
     * @param nomeCategoria - Nome da categoria
     * @return List<Produto> - Produtos encontrados
     */
    public List<Produto> findByCategoriaNome(
            String nomeCategoria
    ) {
        return em.createQuery("""
                        SELECT DISTINCT p FROM produtos p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.moeda
                        LEFT JOIN FETCH p.categoria c WHERE LOWER(c.nome) LIKE LOWER(:nome)""", Produto.class)
                .setParameter("nome","%" + nomeCategoria + "%")
                .getResultList();
    }

    /**
     * Busca todos os Produtos de uma Categoria pelo ID (único)
     *
     * @param idCategoria O ID da Categoria
     * @return List<Produto> - Os Produtos encontrados
     */
    public List<Produto> findByCategoriaId(int idCategoria) {
        return em.createQuery("""
                        SELECT p FROM produtos p WHERE p.categoria.id = :id""", Produto.class)
                .setParameter("id", idCategoria)
                .getResultList();
    }

    /**
     * Busca todos os Produtos de uma Moeda pelo ID (único)
     *
     * @param idMoeda O ID da Moeda
     * @return List<Produto> - Os Produtos encontrados
     */
    public List<Produto> findByMoedaId(int idMoeda) {
        return em.createQuery("""
                        SELECT p FROM produtos p WHERE p.moeda.id = :id """, Produto.class)
                .setParameter("id", idMoeda)
                .getResultList();
    }
}
