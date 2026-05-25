package org.javapi.sigob.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Acesso;
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
                em.createQuery("select f from funcionarios f join fetch f.acessos where f.id = :id", Funcionario.class)
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
        return em.createQuery("select f from funcionarios f join fetch f.acessos", Funcionario.class)
                .getResultList();
    }

    /**
     * Busca Funcionarios cujo nome inicia com o valor informado
     *
     * @param nome O Nome para procurar
     * @return List<Funcionario> - Os Funcionarios encontrados
     */
    public List<Funcionario> findByNome(String nome) {
        return em.createQuery("select f from funcionarios f join fetch f.acessos where f.nome like :str", Funcionario.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    /**
     * Busca um Funcionario pelo codigo
     *
     * @param codigo O Codigo do Funcionario
     * @return Optional<Funcionario> - O Funcionario encontrado
     */
    public Optional<Funcionario> findByCodigo(String codigo) {
        return Optional.ofNullable(
                em.createQuery("""
                        SELECT DISTINCT f FROM funcionarios f
                        JOIN FETCH f.acessos
                        WHERE f.codigo = :codigo
                        """, Funcionario.class)
                        .setParameter("codigo", codigo)
                        .getSingleResultOrNull()
        );
    }

    public List<Funcionario> findByAcessoId (int idAcesso){
        return em.createQuery("""
                        SELECT f FROM funcionarios f
                        WHERE f.acesso.id = :id
                        """, Funcionario.class)
                .setParameter("id", idAcesso)
                .getResultList();
    }

    @Override
    public Optional<Funcionario> findById(Integer id) {
        return em.createQuery("""
            SELECT f FROM funcionarios f
            LEFT JOIN FETCH f.acessos
            WHERE f.id = :id
            """, Funcionario.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
}
