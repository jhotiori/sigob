package org.javapi.sigob.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Produto;

import jakarta.persistence.EntityManager;

public class ProdutoRepository extends BaseRepository<Produto, Integer> {

    /**
     * Cria um novo ProdutoRepository
     *
     * @param em O EntityManager
     */
    public ProdutoRepository(EntityManager em) {
        super(em, Produto.class);
    }

    /**
     * Busca todos os Produtos disponíveis
     *
     * @return List<Produto> - Todos os Produtos
     */
    public List<Produto> findAll() {
        return em.createQuery("select p from produtos p", Produto.class)
                .getResultList();
    }

    /**
     * Busca Produtos cujo nome inicia com o valor informado
     *
     * @param nome O Nome para buscar
     * @return List<Produto> - Os Produtos encontrados
     */
    public List<Produto> findByNome(String nome) {
        return em.createQuery("select p from produtos p where p.nome like :str", Produto.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    /**
     * Busca um Produto pelo código (único)
     *
     * @param codigo O Código do Produto
     * @return Optional<Produto> - O Produto encontrado, se existir
     */
    public Optional<Produto> findByCodigo(String codigo) {
        return Optional.ofNullable(
                em.createQuery("select p from produtos p where p.codigo = :codigo", Produto.class)
                        .setParameter("codigo", codigo)
                        .getSingleResultOrNull()
        );
    }

    /**
     * Busca todos os Produtos de uma Categoria pelo nome (único)
     *
     * @param nomeCategoria O nome da Categoria
     * @return List<Produto> - Os Produtos encontrados
     */
    public List<Produto> findByCategoriaNome(String nomeCategoria) {
        return em.createQuery("""
                        SELECT p FROM produtos p
                        JOIN p.categoria c
                        WHERE c.nome = :nome
                        """, Produto.class)
                .setParameter("nome", nomeCategoria)
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
                        SELECT p FROM produtos p
                        WHERE p.categoria.id = :id
                        """, Produto.class)
                .setParameter("id", idCategoria)
                .getResultList();
    }
}
