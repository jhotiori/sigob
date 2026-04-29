package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Venda;

import jakarta.persistence.EntityManager;

public class VendaRepository extends BaseRepository<Venda, Integer> {

    /**
     * Cria um novo VendaRepository
     *
     * @param em O EntityManager
     */
    public VendaRepository(EntityManager em) {
        super(em, Venda.class);
    }

    /**
     * Busca todos os Venda disponíveis
     *
     * @return List<Venda> - Todos os Venda
     */
    public List<Venda> findAll() {
        return em.createQuery("select v from vendas v", Venda.class)
                .getResultList();
    }

    /**
     * Busca Venda que tenham status 'ABERTA'
     *
     * @return List<Venda> - Os Venda encontrados
     */
    public List<Venda> findAbertas() {
        return em.createQuery("select v from vendas v where v.status = 'ABERTA'", Venda.class)
                .getResultList();
    }

    /**
     * Busca Venda que tenham status 'FINALIZADA'
     *
     * @return List<Venda> - Os Venda encontrados
     */
    public List<Venda> findFinalizadas() {
        return em.createQuery("select v from vendas v where v.status = 'FINALIZADA'", Venda.class)
                .getResultList();
    }

    /**
     * Busca todas as Vendas de um Cliente pelo ID (único)
     *
     * @param idCliente O ID da Cliente
     * @return List<Venda> - Os Vendas encontradas
     */
    public List<Venda> findByClienteId(int idCliente) {
        return em.createQuery("""
                        SELECT v FROM vendas v
                        WHERE v.cliente.id = :id
                        """, Venda.class)
                .setParameter("id", idCliente)
                .getResultList();
    }

    /**
     * Busca todas as Vendas de um Cliente pelo ID (único)
     *
     * @param idFuncionario O ID da Cliente
     * @return List<Venda> - Os Vendas encontradas
     */
    public List<Venda> findByFuncionarioId(int idFuncionario) {
        return em.createQuery("""
                        SELECT v FROM vendas v
                        WHERE v.funcionario.id = :id
                        """, Venda.class)
                .setParameter("id", idFuncionario)
                .getResultList();
    }
}
