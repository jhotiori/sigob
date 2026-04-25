package org.javapi.sigob.repository;

import java.util.List;

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
     * Verifica se um Funcionario está gerenciado pelo EntityManager
     *
     * @param funcionario O Funcionario para verificar
     * @return boolean - true se gerenciado, false caso contrário
     */
    public boolean contains(Funcionario funcionario) {
        return em.contains(funcionario);
    }

    /**
     * Busca todos os Funcionarios disponíveis
     *
     * @return List<Funcionario> - Todos os Funcionarios
     */
    public List<Funcionario> findAll() {
        return em.createQuery("select f from funcionarios f", Funcionario.class)
                .getResultList();
    }

    /**
     * Busca Funcionarios cujo nome inicia com o valor informado
     *
     * @param nome O Nome para procurar
     * @return List<Funcionario> - Os Funcionarios encontrados
     */
    public List<Funcionario> findByNome(String nome) {
        return em.createQuery("select f from funcionarios f where f.nome like :str", Funcionario.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    /**
     * Busca um Funcionario pelo codigo, carregando Acesso e Documento
     *
     * @param codigo O Codigo do Funcionario
     * @return Funcionario - O Funcionario encontrado (pode ser null)
     */
    public Funcionario findByCodigo(String codigo) {
        return em.createQuery("""
                    SELECT f FROM funcionarios f
                    JOIN FETCH f.acesso
                    JOIN FETCH f.documento
                    WHERE f.codigo = :codigo
                """, Funcionario.class)
                .setParameter("codigo", codigo)
                .getSingleResultOrNull();
    }
}