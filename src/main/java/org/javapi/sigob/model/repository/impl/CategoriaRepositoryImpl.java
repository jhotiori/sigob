package org.javapi.sigob.model.repository.impl;

import java.util.List;

import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.model.repository.CategoriaRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação do repositório de categorias.
 */
public final class CategoriaRepositoryImpl extends JpaCrudRepositoryImpl<Categoria, Integer> implements CategoriaRepository {

    /**
     * Cria um novo repositório de categorias.
     *
     * @param entityManager - EntityManager do repositório
     */
    public CategoriaRepositoryImpl(EntityManager entityManager) {
        super(
                entityManager,
                Categoria.class);
    }

    /**
     * Busca categorias pelo nome.
     *
     * @param nome - Nome para procurar
     * @return List<Categoria> - Categorias encontradas
     */
    @Override
    public List<Categoria> findByNome(String nome) {
        return query("""
                SELECT c
                FROM %s c
                WHERE LOWER(c.nome) LIKE LOWER(:str)
                """)
                .param("str", like(nome))
                .list();
    }
}
