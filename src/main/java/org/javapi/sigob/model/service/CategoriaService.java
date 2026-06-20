package org.javapi.sigob.model.service;

import java.util.List;

import org.javapi.sigob.model.entity.Categoria;

public interface CategoriaService extends JpaCrudService<Categoria, Integer> {

    /**
     * Busca categorias pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Categoria> - Lista de categorias encontradas.
     */
    List<Categoria> findByNome(String nome);
}
