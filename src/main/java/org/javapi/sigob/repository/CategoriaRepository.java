package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Categoria;

import jakarta.persistence.EntityManager;

public class CategoriaRepository extends BaseRepository<Categoria, Integer> {

    /**
     * Cria um novo CategoriaRepository
     *
     * @param em EntityManager do Repositorio
     */
    public CategoriaRepository(EntityManager em) {
        super(em, Categoria.class);
    }

    /**
     * Verifica se uma Categoria está gerenciada pelo EntityManager
     *
     * @param categoria A Categoria para verificar
     * @return boolean - true se gerenciada, false caso contrário
     */
    public boolean contains(Categoria categoria) {
        return em.contains(categoria);
    }

    /**
     * Busca todas as Categorias disponíveis
     *
     * @return List<Categoria> - Todas as Categorias
     */
    public List<Categoria> findAll() {
        return em.createQuery("select c from categorias c", Categoria.class)
                .getResultList();
    }

    /**
     * Busca Categorias cujo nome inicia com o valor informado
     *
     * @param prefixo O prefixo do nome
     * @return List<Categoria> - As Categorias encontradas
     */
    public List<Categoria> findByNome(String prefixo) {
        return em.createQuery("select c from categorias c where c.nome like :prefix", Categoria.class)
                .setParameter("prefix", prefixo + "%")
                .getResultList();
    }

    /**
     * Busca uma Categoria cujo codigo inicia com o valor informado
     *
     * @param codigo O codigo da Categoria
     * @return Categoria - A Categoria encontrada (pode ser null)
     */
    public Categoria findByCodigo(String codigo) {
        return em.createQuery("select c from categorias c where c.codigo like :str", Categoria.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }
}