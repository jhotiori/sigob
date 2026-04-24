package org.javapi.sigob.repository;

import java.util.List;

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
     * Verifica se um Cliente está gerenciado pelo EntityManager
     *
     * @param cliente O Cliente para verificar
     * @return boolean - true se gerenciado, false caso contrário
     */
    public boolean contains(Cliente cliente) {
        return em.contains(cliente);
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
     * Busca Clientes pelo documento vinculado
     *
     * @param documento O número do Documento
     * @return List<Cliente> - Os Clientes encontrados
     */
    public List<Cliente> findByDocumento(String documento) {
        return em.createQuery("select c from clientes c where c.documento.documento like :str", Cliente.class)
                .setParameter("str", documento + "%")
                .getResultList();
    }
}