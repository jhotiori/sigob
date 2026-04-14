package org.javapi.sigob.repository;

import java.util.List;

import org.javapi.sigob.entity.Funcionario;

import jakarta.persistence.EntityManager;

public class FuncionarioRepository {
    private final EntityManager em;

    /**
     * Cria um novo FuncionarioRepository
     *
     * @param em O EntityManager
     * @return FuncionarioRepository - O repositorio
     */
    public FuncionarioRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva um novo Funcionario
     *
     * @param funcionario O Funcionario para salvar
     */
    public void save(Funcionario funcionario) {
        em.persist(funcionario);
    }

    /**
     * Atualiza um Funcionario
     *
     * @param funcionario O funcionario para atualizar
     */
    public void update(Funcionario funcionario) {
        em.merge(funcionario);
    }

    /**
     * Deleta um funcionario
     *
     * @param funcionario O funcionario para deletar
     */
    public void delete(Funcionario funcionario) {
        em.remove(em.contains(funcionario) ? funcionario : em.merge(funcionario));
    }

    /**
     * Confer se um funcionario existe
     *
     * @param funcionario O funcionario para conferir
     * @return boolean - true se o funcionario existe, false se nao
     */
    public boolean contains(Funcionario funcionario) {
        return em.find(Funcionario.class, funcionario.getIdFuncionario()) != null;
    }

    /**
     * Retorna uma lista com todos os funcionarios
     *
     * @return List<Funcionario> - A lista de funcionarios
     */
    public List<Funcionario> findAll() {
        return em.createQuery("select f from funcionarios f", Funcionario.class).getResultList();
    }

    /**
     * Busca um funcionario pelo id
     *
     * @param id O ID do funcionario
     * @return Funcionario - O funcionario
     */
    public Funcionario findById(int id) {
        return em.find(Funcionario.class, id);
    }

    /**
     * Busca um por funcionarios que contem o nome
     *
     * @param nome O nome para procurar
     * @return List<Funcionario> - A lista de funcionarios
     */
    public List<Funcionario> findByNome(String nome) {
        return em.createQuery("select f from funcionarios f where f.nmFuncionario like :str", Funcionario.class)
                .setParameter("str", nome + "%")
                .getResultList();
    }

    /**
     * Busca um por funcionarios que contem o codigo
     *
     * @param codigo O codigo para procurar
     * @return Funcionario - O funcionario
     */
    public Funcionario findByCodigo(String codigo) {
        return em.createQuery("""
                    SELECT f FROM funcionarios f
                    JOIN FETCH f.acesso
                    WHERE f.cdFuncionario = :codigo
                """, Funcionario.class)
                .setParameter("codigo", codigo)
                .getSingleResultOrNull();
    }
}
