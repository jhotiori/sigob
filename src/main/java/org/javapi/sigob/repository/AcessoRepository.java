package org.javapi.sigob.repository;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.javapi.sigob.entity.Acesso;

public class AcessoRepository {
    private final EntityManager em;

    /**
     * Cria um novo AcessoRepository
     *
     * @param em EntityManager do Repositorio
     */
    public AcessoRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Cria um novo Acesso no Repositorio
     *
     * @param acesso O acesso para ser criado
     */
    public void create(Acesso acesso) {
        em.persist(acesso);
    }

    /**
     * Atualiza um Acesso
     *
     * @param acesso O acesso para ser atualizado
     */
    public void update(Acesso acesso) {
        em.merge(acesso);
    }

    /**
     * Deleta um Acesso
     *
     * @param acesso O acesso para ser deletado
     */
    public void delete(Acesso acesso) {
        em.remove(em.contains(acesso) ? acesso : em.merge(acesso));
    }

    /**
     * Conferere se um acesso existe
     *
     * @param acesso O acesso para conferir
     * @return boolean - true se o Acesso existe, false se não existir
     */
    public boolean contains(Acesso acesso) {
        return em.contains(acesso);
    }

    /**
     * Busca por todos os acessos disponiveis
     *
     * @return List<Acesso> - Todos os acessos
     */
    public List<Acesso> findAll() {
        return em.createQuery("select a from acessos a").getResultList();
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
     * Busca por acessos que dão "match" no nome
     *
     * @param nome Nome para procurar
     * @return List<Acesso> - Os acessos encontrados
     */
    public List<Acesso> findByNome(String nome) {
        return em.createQuery("select a from acessos a where nmAcesso like :str", Acesso.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    /**
     * Busca por acessos que dão "match" no codigo
     *
     * @param codigo Codigo para procurar
     * @return List<Acesso> - Os acessos encontrados
     */
    public Acesso findByCodigo(String codigo) {
        return em.createQuery("select a from acessos a where cdAcesso like :str", Acesso.class)
                .setParameter("str", codigo + "%")
                .getSingleResultOrNull();
    }
}
