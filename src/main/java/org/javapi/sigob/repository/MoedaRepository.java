package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Moeda;

import jakarta.persistence.EntityManager;

public class MoedaRepository {
    private final EntityManager em;

    /**
     * Cria um novo MoedaRepository
     *
     * @param em O EntityManager
     * @return MoedaRepository - O repositorio
     */
    public MoedaRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva uma nova moeda
     *
     * @param moeda A moeda para salvar
     */
    public void save(Moeda moeda) {
        em.persist(moeda);
    }

    /**
     * Atualiza uma moeda
     *
     * @param moeda A moeda para atualizar
     */
    public void update(Moeda moeda) {
        em.merge(moeda);
    }

    /**
     * Remove uma moeda
     *
     * @param moeda A moeda para remover
     */
    public void delete(Moeda moeda) {
        em.remove(em.contains(moeda) ? moeda : em.merge(moeda));
    }

    /**
     * Verifica se uma moeda ja existe
     *
     * @param moeda A moeda para verificar
     * @return boolean - Se a moeda ja existe
     */
    public boolean contains(Moeda moeda) {
        return em.find(Moeda.class, moeda.getIdMoeda()) != null;
    }

    /**
     * Retorna uma lista com todas as moedas
     *
     * @return List<Moeda> - A lista de moedas
     */
    public List<Moeda> findAll() {
        return em.createQuery("select m from m.moedas m", Moeda.class).getResultList();
    }

    /**
     * Busca uma moeda pelo seu ID
     *
     * @param id O ID da moeda
     * @return Moeda - A moeda encontrada
     */
    public Moeda findById(int id) {
        return em.find(Moeda.class, id);
    }

    /**
     * Busca moedas pelo nome
     *
     * @param name O nome para buscar
     * @return List<Moeda> - A lista de moedas encontradas
     */
    public List<Moeda> findByNome(String name) {
        return em.createQuery("select m from moedas m where m.nmMoeda like :str", Moeda.class)
                .setParameter("str", name + "%")
                .getResultList();
    }

    /**
     * Busca uma moeda pelo codigo
     *
     * @param codigo O codigo da moeda
     * @return Moeda - A moeda encontrada
     */
    public Moeda findByCodigo(String codigo) {
        return em.createQuery("select m from moedas m where m.dsSigla like :str", Moeda.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }
}
