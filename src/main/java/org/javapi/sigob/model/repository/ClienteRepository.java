package org.javapi.sigob.model.repository;

import java.util.List;

import org.javapi.sigob.model.entity.Cliente;

public interface ClienteRepository extends JpaCrudRepository<Cliente, Integer> {

    /**
     * Busca Clientes pelo nome.
     *
     * @param nome - Nome para busca
     * @return List<Cliente> - Clientes encontrados
     */
    List<Cliente> findByNome(String nome);

    /**
     * Busca Clientes pelo documento.
     *
     * @param documento - Documento para busca
     * @return List<Cliente> - Clientes encontrados
     */
    List<Cliente> findByDocumento(String documento);
}
