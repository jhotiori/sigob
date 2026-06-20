package org.javapi.sigob.model.repository.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.repository.FuncionarioRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação de repositório para Funcionario.
 */
public final class FuncionarioRepositoryImpl extends JpaCrudRepositoryImpl<Funcionario, Integer> implements FuncionarioRepository {

    /**
     * Cria um novo FuncionarioRepository.
     *
     * @param entityManager - EntityManager do repositório
     */
    public FuncionarioRepositoryImpl(EntityManager entityManager) {
        super(
                entityManager,
                Funcionario.class
            );
    }

    /**
     * Busca todos os funcionários disponíveis.
     *
     * @return List<Funcionario> - Todos os funcionários
     */
    @Override
    public List<Funcionario> findAll() {
        return query("""
                SELECT f
                FROM %s f
                JOIN FETCH f.acessos
                """)
                .list();
    }

    /**
     * Busca um funcionário pelo ID.
     *
     * @param id - ID do funcionário
     * @return Optional<Funcionario> - Funcionário encontrado
     */
    @Override
    public Optional<Funcionario> findById(Integer id) {
        return query("""
                SELECT f
                FROM %s f
                JOIN FETCH f.acessos
                WHERE f.id = :id
                """)
                .param("id", id)
                .one();
    }

    /**
     * Busca um funcionário pelo código.
     *
     * @param codigo - Código informado para busca
     * @return Optional<Funcionario> - Funcionário encontrado
     */
    @Override
    public Optional<Funcionario> findByCodigo(String codigo) {
        return query("""
                SELECT DISTINCT f
                FROM %s f
                JOIN FETCH f.acessos
                WHERE LOWER(f.codigo) LIKE LOWER(:str)
                """)
                .param("str", like(codigo))
                .one();
    }

    /**
     * Busca funcionários pelo nome.
     *
     * @param nome - Nome informado para busca
     * @return List<Funcionario> - Funcionários encontrados
     */
    @Override
    public List<Funcionario> findByNome(String nome) {
        return query("""
                SELECT f
                FROM %s f
                JOIN FETCH f.acessos
                WHERE LOWER(f.nome) LIKE LOWER(:str)
                """)
                .param("str", like(nome))
                .list();
    }

    /* Busca funcionários pelo documento.
     *
     * @param documento - Documento informado para busca
     * @return List<Funcionario> - Funcionários encontrados
     */
    @Override
    public List<Funcionario> findByDocumento(String documento) {
        return query("""
                SELECT f
                FROM %s f
                JOIN FETCH f.acessos
                WHERE LOWER(f.documento.documento) LIKE LOWER(:str)
                """)
                .param("str", like(documento))
                .list();
    }

    /**
     * Busca funcionários pelo acesso.
     *
     * @param nome - Nome informado para busca
     * @return List<Funcionario> - Funcionários encontrados
     */
    @Override
    public List<Funcionario> findByAcesso(String nome) {
        return query("""
                SELECT DISTINCT f
                FROM %s f
                JOIN FETCH f.acessos a
                WHERE LOWER(a.nome) LIKE LOWER(:str)
                """)
                .param("str", like(nome))
                .list();
    }

}
