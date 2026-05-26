package org.javapi.sigob.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Funcionario;

import jakarta.persistence.EntityManager;

public class FuncionarioRepository extends BaseRepository<Funcionario, Integer> {

    /**
     * Cria um novo FuncionarioRepository
     *
     * @param em O EntityManager
     */
    public FuncionarioRepository(EntityManager em) {
        super(em, Funcionario.class);
    }

    /**
     * Busca um Funcionario pelo ID
     *
     * @param id O ID do Funcionario
     * @return Optional<Funcionario> - O Funcionario encontrado
     */
    @Override
    public Optional<Funcionario> findById(Integer id) {
        return Optional.ofNullable(
                em.createQuery("SELECT f FROM funcionarios f JOIN FETCH f.acessos" +
                                " WHERE f.id = :id", Funcionario.class)
                                .setParameter("id", id)
                                .getSingleResultOrNull()
        );
    }

    /**
     * Busca todos os Funcionarios disponíveis
     *
     * @return List<Funcionario> - Todos os Funcionarios
     */
    public List<Funcionario> findAll() {
        return em.createQuery("SELECT f FROM funcionarios f JOIN FETCH f.acessos", Funcionario.class)
                .getResultList();
    }

    /**
     * Busca Funcionarios no banco de dados com base em um nome
     *
     * @param nome string informada para busca
     * @return List<Funcionario> - Os Funcionarios encontrados
     */
    public List<Funcionario> findByNome(String nome) {
        return em.createQuery("SELECT f FROM funcionarios f JOIN FETCH f.acessos " +
                        "WHERE LOWER (f.nome) LIKE LOWER (:str)", Funcionario.class)
                        .setParameter("str", "%" + nome + "%")
                        .getResultList();
    }

    /**
     * Busca Funcionarios no banco de dados com base em um codigo
     *
     * @param codigo string informada para busca
     * @return Optional<Funcionario> - O Funcionario encontrado
     */
    public Optional<Funcionario> findByCodigo(String codigo) {
        return Optional.ofNullable(
                em.createQuery("""
                        SELECT DISTINCT f FROM funcionarios f JOIN FETCH f.acessos
                        WHERE LOWER (f.codigo) LIKE LOWER (:str)
                                /**
                                                                     * 
                                co de dados com base em um codigo
                                                                     *
                                                                     * @
                                mada para busca
                                                                     * @return
                                O F
                                                                                                  */""", Funcionario.class)
                        .setParameter("str", "%" + codigo + "%")
                        .getSingleResultOrNull()
        );
    }
    /**
     * Busca Funcionarios no banco de dados com base em um ID de Acesso
     *
     * @param idAcesso id informado para busca
     * @return Optional<Funcionario> - O Funcionario encontrado
     */
    public List<Funcionario> findByAcessoId (int idAcesso){
        return em.createQuery("""
                        SELECT f FROM funcionarios f WHERE f.acesso.id = :id """, Funcionario.class)
                        .setParameter("id", idAcesso)
                        .getResultList();
    }
}
