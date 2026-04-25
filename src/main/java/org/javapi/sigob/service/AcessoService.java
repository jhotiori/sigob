package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.exception.AcessoException;
import org.javapi.sigob.repository.AcessoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AcessoService {

    /**
     * Cria um novo AcessoService
     *
     * @return AcessoService - O servico
     */
    public AcessoService() {
    }

    /**
     * Salva um acesso
     *
     * @param acesso O acesso a ser salvo
     * @throws AcessoException Se o acesso for invalido
     */
    public void save(Acesso acesso) {
        validateNome(acesso.getNome());
        validateCodigo(acesso.getCodigo());

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        AcessoRepository repository = new AcessoRepository(em);

        try {
            transaction.begin();
            repository.save(acesso);
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
     * Atualiza um acesso
     *
     * @param acesso O acesso a ser atualizado
     * @throws AcessoException Se o acesso for invalido
     */
    public void update(Acesso acesso) {
        validateAcesso(acesso);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        AcessoRepository repository = new AcessoRepository(em);

        try {
            transaction.begin();
            repository.update(acesso);
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
     * Deleta um acesso
     *
     * @param acesso O acesso a ser deletado
     */
    public void delete(Acesso acesso) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        AcessoRepository repository = new AcessoRepository(em);

        try {
            transaction.begin();
            // Re-anexa a entidade ao contexto JPA antes de deletar
            Acesso managed = em.contains(acesso) ? acesso : em.merge(acesso);
            repository.deleteById(acesso.getId());
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
     * Confere se um acesso existe
     *
     * @param acesso O acesso para conferir
     * @return boolean - true se o Acesso existe, false se nao existir
     */
    public boolean contains(Acesso acesso) {
        EntityManager em = JPAConfig.getEntityManager();
        AcessoRepository repository = new AcessoRepository(em);

        try {
            return repository.contains(acesso);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista com todos os Acessos
     *
     * @return List<Acesso> - A lista de Acessos
     */
    public List<Acesso> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        AcessoRepository repository = new AcessoRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Busca um Acesso pelo seu ID
     *
     * @param id O ID do Acesso
     * @return Acesso - O Acesso buscado
     */
    public Acesso findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        AcessoRepository repository = new AcessoRepository(em);

        try {
            return repository.findById(id);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista de acessos que possuem o nome informado
     *
     * @param nome O nome para procurar
     * @return List<Acesso> - A lista de Acessos
     */
    public List<Acesso> findByNome(String nome) {
        validateNome(nome);

        EntityManager em = JPAConfig.getEntityManager();
        AcessoRepository repository = new AcessoRepository(em);

        try {
            return repository.findByNome(nome);
        } finally {
            em.close();
        }
    }

    /**
     * Busca um Acesso pelo seu codigo
     *
     * @param codigo O codigo do Acesso
     * @return Acesso - O Acesso buscado
     */
    public Acesso findByCodigo(String codigo) {
        validateCodigo(codigo);

        EntityManager em = JPAConfig.getEntityManager();
        AcessoRepository repository = new AcessoRepository(em);

        try {
            return repository.findByCodigo(codigo);
        } finally {
            em.close();
        }
    }

    private void validateAcesso(Acesso acesso) {
        if (acesso == null) {
            throw new AcessoException("Acesso não pode ser nulo");
        }
        validateNome(acesso.getNome());
        validateCodigo(acesso.getCodigo());
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new AcessoException("Nome do acesso não pode ser nulo ou vazio");
        }
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new AcessoException("Código do acesso não pode ser nulo ou vazio");
        }
    }
}
