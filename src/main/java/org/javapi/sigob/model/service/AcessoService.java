package org.javapi.sigob.model.service;

import java.util.List;

import org.javapi.sigob.model.entity.Acesso;

public interface AcessoService extends JpaCrudService<Acesso, Integer> {

    /**
     * Busca acessos pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Acesso> - Lista de acessos encontrados.
     */
    List<Acesso> findByNome(String nome);
}
