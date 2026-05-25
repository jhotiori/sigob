package org.javapi.sigob.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Cliente;

import jakarta.persistence.EntityManager;

public class ClienteRepository extends BaseRepository<Cliente, Integer> {

    /**
     * Cria um novo ClienteRepository
     *
     * @param em O EntityManager
     */
    public ClienteRepository(EntityManager em) {
        super(em, Cliente.class);
    }

    /**
     * Busca todos os Clientes disponíveis
     *
     * @return List<Cliente> - Todos os Clientes
     */
    public List<Cliente> findAll() {
        return em.createQuery("""
                SELECT DISTINCT c
                FROM clientes c
                LEFT JOIN FETCH c.documento
                """, Cliente.class)
                .getResultList();
    }

    /**
     * Busca um Cliente pelo ID
     *
     * @param id O ID do Cliente
     * @return Optional<Cliente> - O Cliente encontrado
     */
    @Override
    public Optional<Cliente> findById(Integer id) {
        return Optional.ofNullable(
                em.createQuery("""
                        SELECT c
                        FROM clientes c
                        LEFT JOIN FETCH c.documento
                        WHERE c.id = :id
                        """, Cliente.class)
                        .setParameter("id", id)
                        .getSingleResultOrNull()
        );
    }

    /**
     * Busca Clientes cujo nome contenha o valor informado
     *
     * @param nome O Nome do Cliente
     * @return List<Cliente> - Os Clientes encontrados
     */
    public List<Cliente> findByNome(String nome) {
        return em.createQuery("""
                SELECT DISTINCT c
                FROM clientes c
                LEFT JOIN FETCH c.documento
                WHERE lower(c.nome) like lower(:str)
                """, Cliente.class)
                .setParameter("str", "%" + nome + "%")
                .getResultList();
    }

    /**
     * Busca um Cliente pelo documento vinculado
     *
     * @param documento O número do Documento
     * @return Optional<Cliente> - O Cliente encontrado
     */
    public List<Cliente> findByDocumento(String documento) {
        return em.createQuery("""
                        SELECT c
                        FROM clientes c
                        LEFT JOIN FETCH c.documento d
                        WHERE lower(d.documento) LIKE lower(:str)
                        """, Cliente.class)
                        .setParameter("str", "%" + documento + "%")
                        .getResultList();
    }
}
