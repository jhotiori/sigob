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
        return em.createQuery("select c from clientes c", Cliente.class)
                .getResultList();
    }

    /**
     * Busca Clientes cujo nome inicia com o valor informado
     *
     * @param nome O Nome do Cliente
     * @return List<Cliente> - Os Clientes encontrados
     */
    public List<Cliente> findByNome(String nome) {
        return em.createQuery("select c from clientes c where c.nome like :str", Cliente.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    /**
     * Busca um Cliente pelo documento vinculado
     *
     * @param documento O número do Documento
     * @return Optional<Cliente> - O Cliente encontrado
     */
    public Optional<Cliente> findByDocumento(String documento) {
        return Optional.ofNullable(
                em.createQuery("select c from clientes c where c.documento.documento like :str", Cliente.class)
                        .setParameter("str", documento + "%")
                        .getSingleResultOrNull()
        );
    }
}
