package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Categoria;

import jakarta.persistence.EntityManager;

public class CategoriaRepository {
    private final EntityManager em;

    /**
     * Cria um novo CategoriaRepository
     *
     * @param em EntityManager do repositorio
     */
    public CategoriaRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva uma nova Categoria no repositorio
     *
     * @param categoria A categoria a ser salva
     */
    public void save(Categoria categoria) {
        em.persist(categoria);
    }

    /**
     * Atualiza uma Categoria no repositorio
     *
     * @param categoria A categoria a ser atualizada
     */
    public void update(Categoria categoria) {
        em.merge(categoria);
    }

    /**
     * Remove uma Categoria do repositorio
     *
     * @param categoria A categoria a ser removida
     */
    public void delete(Categoria categoria) {
        em.remove(em.contains(categoria) ? categoria : em.merge(categoria));
    }

    /**
     * Confere se uma categoria existe
     *
     * @param categoria A categoria
     * @return boolean - true se a categoria existe, false se nao
     */
    public boolean contains(Categoria categoria) {
        return em.find(Categoria.class, categoria.getIdCategoria()) != null;
    }

    /**
     * Retorna uma lista com todas as Categorias
     *
     * @return List<Categoria> - A lista de categorias
     */
    public List<Categoria> findAll() {
        return em.createQuery("select c from categorias c", Categoria.class)
                .getResultList();
    }

    /**
     * Busca por um Categoria pelo seu ID
     *
     * @param id ID do Categoria
     * @return Categoria - A categoria buscada
     */
    public Categoria findById(int id) {
        return em.find(Categoria.class, id);
    }

    /**
     * Retorna uma lista com todas as Categorias que comecam com o prefixo
     *
     * @param prefixo O prefixo
     * @return List<Categoria> - A lista de categorias
     */
    public List<Categoria> findByName(String prefixo) {
        return em.createQuery("select c from categorias c where c.nmCategoria like :prefix", Categoria.class)
                .setParameter("prefix", prefixo + "%")
                .getResultList();
    }

    /**
     * Busca por uma Categoria pelo seu codigo
     *
     * @param codigo O codigo da categoria
     * @return Categoria - A categoria buscada
     */
    public Categoria findByCodigo(String codigo) {
        return em.createQuery("select c from categorias c where c.cdCategoria like :str", Categoria.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }
}
