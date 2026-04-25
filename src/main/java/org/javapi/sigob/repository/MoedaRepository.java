package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Moeda;

import jakarta.persistence.EntityManager;

public class MoedaRepository extends BaseRepository<Moeda, Integer> {

    /**
     * Cria um novo MoedaRepository
     *
     * @param em O EntityManager
     */
    public MoedaRepository(EntityManager em) {
        super(em, Moeda.class);
    }

    /**
     * Verifica se uma Moeda está gerenciada pelo EntityManager
     *
     * @param moeda A Moeda para verificar
     * @return boolean - true se gerenciada, false caso contrário
     */
    public boolean contains(Moeda moeda) {
        return em.contains(moeda);
    }

    /**
     * Busca todas as Moedas disponíveis
     *
     * @return List<Moeda> - Todas as Moedas
     */
    public List<Moeda> findAll() {
        return em.createQuery("select m from moedas m", Moeda.class)
                .getResultList();
    }

    /**
     * Busca Moedas cujo nome inicia com o valor informado
     *
     * @param nome O Nome para buscar
     * @return List<Moeda> - As Moedas encontradas
     */
    public List<Moeda> findByNome(String nome) {
        return em.createQuery("select m from moedas m where m.nome like :str", Moeda.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    /**
     * Busca uma Moeda pela sigla
     *
     * @param sigla A Sigla da Moeda
     * @return Moeda - A Moeda encontrada (pode ser null)
     */
    public Moeda findBySigla(String sigla) {
        return em.createQuery("select m from moedas m where m.sigla like :str", Moeda.class)
                .setParameter("str", sigla + "%")
                .getSingleResultOrNull();
    }
}