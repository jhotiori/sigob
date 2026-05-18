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
}
