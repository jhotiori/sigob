package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Estoque;

import java.util.List;

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
     * Busca todos os Estoques disponíveis
     *
     * @return List<Estoque> - Todos os Estoques
     */
    public List<Estoque> findAll() {
        return em.createQuery("SELECT e FROM estoques e", Estoque.class)
                .getResultList();
    }

    /**
     * Busca Estoques no banco de dados com base em um nome
     *
     * @param nome string informada para busca
     * @return List<Estoque> - Os Estoques encontrados
     */
    public List<Estoque> findByNome(String nome) {
        return em.createQuery("SELECT e FROM estoques e WHERE LOWER (e.nome) LIKE LOWER (:str)", Estoque.class)
                .setParameter("str", "%" + nome + "%")
                .getResultList();
    }

    /**
     * Busca Estoques no banco de dados com base em um codigo
     *
     * @param codigo string informada para busca
     * @return List<Estoque> - Os Estoques encontrados
     */
    public List<Estoque> findByCodigo(String codigo) {
        return em.createQuery("SELECT e FROM estoques e WHERE LOWER (e.codigo) LIKE LOWER (:str)", Estoque.class)
                .setParameter("str", "%" + codigo + "%")
                .getResultList();
    }
}
