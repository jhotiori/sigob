package org.javapi.sigob.model.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Acesso;

public interface AcessoRepository extends JpaCrudRepository<Acesso, Integer> {

    /**
     * Busca Acessos pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Acesso> - Acessos encontrados
     */
    List<Acesso> findByNome(String nome);

    /**
     * Busca um Acesso pelo código.
     *
     * @param codigo - Código para busca
     * @return Optional<Acesso> - Acesso encontrado
     */
    Optional<Acesso> findByCodigo(String codigo);
}
