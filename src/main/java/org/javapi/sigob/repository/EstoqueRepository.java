package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Estoque;

import jakarta.persistence.EntityManager;

public class EstoqueRepository extends BaseRepository<Estoque, Integer> {

    /**
     * Cria um novo EstoqueRepository
     *
     * @param em O EntityManager
     */
    public EstoqueRepository(EntityManager em) {
        super(em, Estoque.class);
    }

    /**
     * Verifica se um Estoque está gerenciado pelo EntityManager
     *
     * @param estoque O Estoque para verificar
     * @return boolean - true se gerenciado, false caso contrário
     */
    public boolean contains(Estoque estoque) {
        return em.contains(estoque);
    }

    /**
     * Busca todos os Estoques disponíveis
     *
     * @return List<Estoque> - Todos os Estoques
     */
    public List<Estoque> findAll() {
        return em.createQuery("select e from estoques e", Estoque.class)
                .getResultList();
    }

    /**
     * Busca Estoques cujo nome inicia com o valor informado
     *
     * @param nome O prefixo do nome
     * @return List<Estoque> - Os Estoques encontrados
     */
    public List<Estoque> findByNome(String nome) {
        return em.createQuery("select e from estoques e where e.nome like :prefix", Estoque.class)
                .setParameter("prefix", nome + "%")
                .getResultList();
    }
}