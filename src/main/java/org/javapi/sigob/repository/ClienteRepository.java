package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Cliente;

import jakarta.persistence.EntityManager;

public class ClienteRepository {
    private final EntityManager em;

    /**
     * Cria um novo ClienteRepository
     *
     * @param em O EntityManager
     * @return ClienteRepository - O repositorio
     */
    public ClienteRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva um novo Cliente
     *
     * @param cliente O Cliente para ser salvo
     */
    public void save(Cliente cliente) {
        em.persist(cliente);
    }

    /**
     * Atualiza um Cliente
     *
     * @param cliente O Cliente para ser atualizado
     */
    public void update(Cliente cliente) {
        em.merge(cliente);
    }

    /**
     * Remove um Cliente
     *
     * @param cliente O Cliente para ser removido
     */
    public void remove(Cliente cliente) {
        em.remove(em.contains(cliente) ? cliente : em.merge(cliente));
    }

    /**
     * Confere se um Cliente existe
     *
     * @param cliente O Cliente
     * @return boolean - true se o Cliente existe, false se nao
     */
    public boolean contains(Cliente cliente) {
        return em.find(Cliente.class, cliente.getIdCliente()) != null;
    }

    /**
     * Retorna uma lista com todos os Clientes
     *
     * @return List<Cliente> - A lista de clientes
     */
    public List<Cliente> findAll() {
        return em.createQuery("select c from clientes c", Cliente.class)
                .getResultList();
    }

    /**
     * Retorna um Cliente pelo seu ID
     *
     * @param id O ID do Cliente
     * @return Cliente - O Cliente buscado
     */
    public Cliente findById(int id) {
        return em.find(Cliente.class, id);
    }

    /**
     * Retorna uma lista com todos os Clientes com o nome informado
     *
     * @param nome O Nome do Cliente
     * @return List<Cliente> - A lista de clientes
     */
    public List<Cliente> findByNome(String nome) {
        return em.createQuery("select c from clientes c where nmCliente like :str", Cliente.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    /**
     * Retorna um Cliente pelo seu documento
     *
     * @param documento O Documento do Cliente
     * @return Cliente - O Cliente buscado
     */
    public List<Cliente> findByDocumento(String documento) {
        return em.createQuery("select c from clientes c where nrDocumento like :str", Cliente.class)
                .setParameter("str", documento + "%")
                .getResultList();
    }
}
