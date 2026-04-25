package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Venda;

import java.util.List;

public class VendaRepository extends BaseRepository <Venda, Integer>{

    /**
     * Cria um novo VendaRepository
     *
     * @param em O EntityManager
     */
    public VendaRepository(EntityManager em) {
        super(em, Venda.class);
    }

    /**
     * Verifica se um Venda está gerenciado pelo EntityManager
     *
     * @param venda O Venda para verificar
     * @return boolean - true se gerenciado, false caso contrário
     */
    public boolean contains(Venda venda) {
        return em.contains(venda);
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
    public List<Venda> findAbertas( ) {
        return em.createQuery("select v from vendas v where v.status = 'ABERTA'", Venda.class)
                .getResultList();
    }

    /**
     * Busca Venda que tenham status 'FINALIZADA'
     *
     * @return List<Venda> - Os Venda encontrados
     */
    public List<Venda> findFinalizadas( ) {
        return em.createQuery("select v from vendas v where v.status = 'FINALIZADA'", Venda.class)
                .getResultList();
    }
}
