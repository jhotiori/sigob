package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Acesso;

import jakarta.persistence.EntityManager;

public class AcessoRepository extends BaseRepository<Acesso, Integer> {

    /**
     * Cria um novo AcessoRepository
     *
     * @param em EntityManager do Repositorio
     */
    public AcessoRepository(EntityManager em) {
        super(em, Acesso.class);
    }

    /**
     * Busca todos os Acessos disponíveis
     *
     * @return List<Acesso> - Todos os Acessos
     */
    public List<Acesso> findAll() {
        return em.createQuery("select a from acessos a", Acesso.class)
                .getResultList();
    }

    /**
     * Busca Acessos cujo nome inicia com o valor informado
     *
     * @param nome Nome para procurar
     * @return List<Acesso> - Os Acessos encontrados
     */
    public List<Acesso> findByNome(String nome) {
        return em.createQuery("select a from acessos a where a.nome like :str", Acesso.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }
}
