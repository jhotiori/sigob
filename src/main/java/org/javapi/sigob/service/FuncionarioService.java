package org.javapi.sigob.service;

import java.util.List;
import java.util.Set;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.exception.FuncionarioException;
import org.javapi.sigob.repository.FuncionarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class FuncionarioService {

    /**
     * Cria uma novo FuncionarioService
     *
     * @return FuncionarioService - O servico de funcionarios
     */
    public FuncionarioService() {
    }

    /**
     * Salva um novo Funcionario
     *
     * @param funcionario O funcionario para salvar
     */
    public void save(Funcionario funcionario) {
        validateFuncionario(funcionario);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        FuncionarioRepository repository = new FuncionarioRepository(em);

        try {
            transaction.begin();
            repository.save(funcionario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Atualiza um Funcionario
     *
     * @param funcionario O funcionario para atualizar
     */
    public void update(Funcionario funcionario) {
        validateNome(funcionario.getNome());
        validateCodigo(funcionario.getCodigo());
        validateAcessos(funcionario.getAcessos());

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        FuncionarioRepository repository = new FuncionarioRepository(em);

        try {
            transaction.begin();
            repository.update(funcionario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Deleta um Funcionario
     *
     * @param funcionario O funcionario para deletar
     */
    public void delete(Funcionario funcionario) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        FuncionarioRepository repository = new FuncionarioRepository(em);

        try {
            transaction.begin();
            repository.deleteById(funcionario.getId());
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Confere se um funcionario existe
     *
     * @param funcionario O funcionario para conferir
     * @return boolean - true se o funcionario existe, false se nao
     */
    public boolean contains(Funcionario funcionario) {
        EntityManager em = JPAConfig.getEntityManager();
        FuncionarioRepository repository = new FuncionarioRepository(em);

        try {
            return repository.contains(funcionario);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista com todos os funcionarios
     *
     * @return List<Funcionario> - A lista de funcionarios
     */
    public List<Funcionario> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        FuncionarioRepository repository = new FuncionarioRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Busca um funcionario pelo id
     *
     * @param id O ID do funcionario
     * @return Funcionario - O funcionario
     */
    public Funcionario findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        FuncionarioRepository repository = new FuncionarioRepository(em);

        try {
            return repository.findById(id).orElse(null);
        } finally {
            em.close();
        }
    }

    /**
     * Busca um por funcionarios que contem o nome
     *
     * @param nome O nome para procurar
     * @return List<Funcionario> - A lista de funcionarios
     */
    public List<Funcionario> findByNome(String nome) {
        validateNome(nome);

        EntityManager em = JPAConfig.getEntityManager();
        FuncionarioRepository repository = new FuncionarioRepository(em);

        try {
            return repository.findByNome(nome);
        } finally {
            em.close();
        }
    }

    /**
     * Busca pelo Funcionario que contem o codigo
     *
     * @param codigo O codigo para procurar
     * @return Funcionario - O funcionario
     */
    public Funcionario findByCodigo(String codigo) {
        validateCodigo(codigo);

        EntityManager em = JPAConfig.getEntityManager();
        FuncionarioRepository repository = new FuncionarioRepository(em);

        try {
            return repository.findByCodigo(codigo);
        } finally {
            em.close();
        }
    }

    private void validateFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new FuncionarioException("Funcionário não pode ser nulo");
        }
        validateNome(funcionario.getNome());
        validateCodigo(funcionario.getCodigo());
        validateAcessos(funcionario.getAcessos());
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new FuncionarioException("Nome do funcionário não pode ser nulo ou vazio");
        }
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new FuncionarioException("Código do funcionário não pode ser nulo ou vazio");
        }
    }

    private void validateAcessos(Set<Acesso> acessos) {
        if (acessos == null || acessos.isEmpty()) {
            throw new FuncionarioException("Funcionário deve possuir ao menos um acesso");
        }
    }
}