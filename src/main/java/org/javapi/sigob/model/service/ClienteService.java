package org.javapi.sigob.model.service;

import java.util.List;

import org.javapi.sigob.model.entity.Cliente;

public interface ClienteService extends JpaCrudService<Cliente, Integer> {

    /**
     * Busca clientes pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Cliente> - Lista de clientes encontrados.
     */
    List<Cliente> findByNome(String nome);

    /**
     * Busca clientes pelo documento.
     *
     * @param documento - Documento utilizado na busca.
     * @return List<Cliente> - Lista de clientes encontrados.
     */
    List<Cliente> findByDocumento(String documento);
}
