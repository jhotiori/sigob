package org.javapi.sigob.repository;

import java.util.List;
import java.util.Optional;

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

    /**
     * Busca um Acesso cujo codigo inicia com o valor informado
     *
     * @param codigo Codigo para procurar
     * @return Optional<Acesso> - O Acesso encontrado (pode ser vazio)
     */
    public Optional<Acesso> findByCodigo(String codigo) {
        return Optional.ofNullable(
                em.createQuery("select a from acessos a where a.codigo like :str", Acesso.class)
                        .setParameter("str", codigo + "%")
                        .getSingleResultOrNull()
        );
    }

    @Override
    public Optional<Acesso> findById(Integer id) {
        return em.createQuery("""
            SELECT a FROM acessos a
            LEFT JOIN FETCH a.funcionarios
            WHERE a.id = :id
            """, Acesso.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
}
