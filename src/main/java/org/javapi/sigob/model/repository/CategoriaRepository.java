package org.javapi.sigob.model.repository;

import java.util.List;

import org.javapi.sigob.model.entity.Categoria;

public interface CategoriaRepository extends JpaCrudRepository<Categoria, Integer> {

    /**
     * Busca Categorias pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Categoria> - Categorias encontradas
     */
    List<Categoria> findByNome(String nome);
}
