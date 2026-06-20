package org.javapi.sigob.model.repository.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.repository.AcessoRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação do repositório de acessos.
 */
public final class AcessoRepositoryImpl extends JpaCrudRepositoryImpl<Acesso, Integer> implements AcessoRepository {

    /**
     * Cria um novo repositório de acessos.
     *
     * @param entityManager - EntityManager do repositório
     */
    public AcessoRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Acesso.class);
    }

    /**
     * Busca todos os acessos disponíveis.
     *
     * @return List<Acesso> - Todos os acessos encontrados
     */
    @Override
    public List<Acesso> findAll() {
        return query("""
                SELECT a
                FROM %s a
                """)
                .list();
    }

    /**
     * Busca acessos cujo nome inicia com o valor informado.
     *
     * @param nome - Nome para procurar
     * @return List<Acesso> - Acessos encontrados
     */
    @Override
    public List<Acesso> findByNome(String nome) {
        return query("""
                SELECT a
                FROM %s a
                WHERE LOWER(a.nome) LIKE LOWER(:str)
                """)
                .param("str", like(nome))
                .list();
    }

    /**
     * Busca um acesso cujo código inicia com o valor informado.
     *
     * @param codigo - Código para procurar
     * @return Optional<Acesso> - Acesso encontrado
     */
    @Override
    public Optional<Acesso> findByCodigo(String codigo) {
        return query("""
                SELECT a
                FROM %s a
                WHERE LOWER(a.codigo) LIKE LOWER(:str)
                """)
                .param("str", like(codigo))
                .one();
    }

    /**
     * Busca um acesso pelo identificador.
     *
     * @param id - Identificador do acesso
     * @return Optional<Acesso> - Acesso encontrado
     */
    @Override
    public Optional<Acesso> findById(Integer id) {
        return query("""
                SELECT a
                FROM %s a
                LEFT JOIN FETCH a.funcionarios
                WHERE a.id = :id
                """)
                .param("id", id)
                .first();
    }
}
