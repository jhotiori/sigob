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
     * Busca todas as Categorias disponíveis
     *
     * @return List<Categoria> - Todas as Categorias
     */
    public List<Categoria> findAll() {
        return em.createQuery("SELECT c FROM categorias c", Categoria.class)
                .getResultList();
    }

    /**
     * Busca Categorias no banco de dados com base em um nome
     *
     * @param nome string informada para busca
     * @return List<Categoria> - As Categorias encontradas
     */
    public List<Categoria> findByNome(String nome) {
        return em.createQuery("SELECT c FROM categorias c WHERE LOWER (c.nome) LIKE LOWER (:str)", Categoria.class)
                .setParameter("str", "%" + nome + "%")
                .getResultList();
    }
}
