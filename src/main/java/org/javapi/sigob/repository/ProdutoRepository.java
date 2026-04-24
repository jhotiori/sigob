package org.javapi.sigob.repository;

import java.util.List;

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
     * Verifica se um Produto está gerenciado pelo EntityManager
     *
     * @param produto O Produto para verificar
     * @return boolean - true se gerenciado, false caso contrário
     */
    public boolean contains(Produto produto) {
        return em.contains(produto);
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
     * Busca um Produto pelo codigo
     *
     * @param codigo O Codigo do Produto
     * @return Produto - O Produto encontrado (pode ser null)
     */
    public Produto findByCodigo(String codigo) {
        return em.createQuery("select p from produtos p where p.codigo = :codigo", Produto.class)
                .setParameter("codigo", codigo)
                .getSingleResultOrNull();
    }

    /**
     * Busca todos os Produtos de uma Categoria
     *
     * @param categoriaId O ID da Categoria
     * @return List<Produto> - Os Produtos encontrados
     */
    public List<Produto> findByCategoria(int categoriaId) {
        return em.createQuery("select p from produtos p where p.categoria.id = :categoriaId", Produto.class)
                .setParameter("categoriaId", categoriaId)
                .getResultList();
    }
}