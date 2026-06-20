package org.javapi.sigob.model.repository.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.model.repository.ClienteRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação do repositório de clientes.
 */
public final class ClienteRepositoryImpl extends JpaCrudRepositoryImpl<Cliente, Integer> implements ClienteRepository {

    /**
     * Cria um novo repositório de clientes.
     *
     * @param entityManager - EntityManager do repositório
     */
    public ClienteRepositoryImpl(EntityManager entityManager) {
        super(
                entityManager,
                Cliente.class
            );
    }

    /**
     * Busca todos os clientes disponíveis.
     *
     * @return List<Cliente> - Todos os clientes encontrados
     */
    @Override
    public List<Cliente> findAll() {
        return query("""
                SELECT DISTINCT c
                FROM %s c
                LEFT JOIN FETCH c.documento
                """)
                .list();
    }

    /**
     * Busca um cliente pelo identificador.
     *
     * @param id - Identificador do cliente
     * @return Optional<Cliente> - Cliente encontrado
     */
    @Override
    public Optional<Cliente> findById(Integer id) {
        return query("""
                SELECT c
                FROM %s c
                LEFT JOIN FETCH c.documento
                WHERE c.id = :id
                """)
                .param("id", id)
                .one();
    }

    /**
     * Busca clientes pelo documento.
     *
     * @param documento - Documento para procurar
     * @return List<Cliente> - Clientes encontrados
     */
    @Override
    public List<Cliente> findByDocumento(String documento) {
        return query("""
                SELECT c
                FROM %s c
                LEFT JOIN FETCH c.documento d
                WHERE LOWER(d.documento) LIKE LOWER(:str)
                """)
                .param("str", like(documento))
                .list();
    }

    /**
     * Busca clientes pelo nome.
     *
     * @param nome - Nome para procurar
     * @return List<Cliente> - Clientes encontrados
     */
    @Override
    public List<Cliente> findByNome(String nome) {
        return query("""
                SELECT DISTINCT c
                FROM %s c
                LEFT JOIN FETCH c.documento
                WHERE LOWER(c.nome) LIKE LOWER(:str)
                """)
                .param("str", like(nome))
                .list();
    }
}
