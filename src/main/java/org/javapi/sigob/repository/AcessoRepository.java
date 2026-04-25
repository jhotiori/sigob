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
     * Verifica se um Acesso está gerenciado pelo EntityManager
     *
     * @param acesso O Acesso para verificar
     * @return boolean - true se gerenciado, false caso contrário
     */
    public boolean contains(Acesso acesso) {
        return em.contains(acesso);
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
     * Busca por um acesso pelo seu ID
     *
     * @param id ID do Acesso
     * @return Acesso - O acesso que foi buscado (pode ser null)
     */
    public Acesso findById(int id) {
        return em.find(Acesso.class, id);
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

    /**
     * Busca um Acesso cujo codigo inicia com o valor informado
     *
     * @param codigo Codigo para procurar
     * @return Acesso - O Acesso encontrado (pode ser null)
     */
    public Acesso findByCodigo(String codigo) {
        return em.createQuery("select a from acessos a where a.codigo like :str", Acesso.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }
}
