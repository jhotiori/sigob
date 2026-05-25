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
     * Busca Estoques cujo nome inicia com o valor informado
     *
     * @param nome O prefixo do nome
     * @return List<Estoque> - Os Estoques encontrados
     */
    public List<Estoque> findByNome(String nome) {
        return em.createQuery("SELECT e FROM estoques e WHERE e.nome LIKE :prefix", Estoque.class)
                .setParameter("prefix", nome + "%")
                .getResultList();
    }

    /**
     * Busca Estoques cujo codigo inicia com o valor informado
     *
     * @param codigo O prefixo do codigo
     * @return List<Estoque> - Os Estoques encontrados
     */
    public List<Estoque> findByCodigo(String codigo) {
        return em.createQuery("SELECT e FROM estoques e WHERE e.codigo LIKE :prefix", Estoque.class)
                .setParameter("prefix", codigo + "%")
                .getResultList();
    }
}
