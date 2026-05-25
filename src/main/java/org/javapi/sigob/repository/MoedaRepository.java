package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Moeda;

import java.util.List;
import java.util.Optional;

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
     * Busca uma Moeda cuja sigla inicia com o valor informado
     *
     * @param sigla A Sigla da Moeda
     * @return Optional<Moeda> - A Moeda encontrada (pode ser null)
     */
    public Optional<Moeda> findBySigla(String sigla) {
        return Optional.ofNullable(
                em.createQuery("select m from moedas m where m.sigla like :str", Moeda.class)
                        .setParameter("str", sigla + "%")
                        .getSingleResultOrNull()
        );
    }
}
