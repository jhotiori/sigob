package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.exception.EstoqueException;
import org.javapi.sigob.repository.EstoqueRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EstoqueService {

    /**
     * Cria um novo EstoqueService
     *
     * @return EstoqueService - O serviço de estoques
     */
    public EstoqueService() {
    }

    /**
     * Salva um estoque
     *
     * @param estoque O estoque para ser salvo
     * @throws EstoqueException Se o estoque for invalido
     */
    public void save(Estoque estoque) {
        validateEstoque(estoque);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        EstoqueRepository repository = new EstoqueRepository(em);

        try {
            transaction.begin();
            repository.save(estoque);
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
     * Atualiza um estoque
     *
     * @param estoque O estoque para ser atualizado
     * @throws EstoqueException Se o estoque for invalido
     */
    public void update(Estoque estoque) {
        validateEstoque(estoque);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        EstoqueRepository repository = new EstoqueRepository(em);

        try {
            transaction.begin();
            repository.update(estoque);
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
     * Deleta um estoque
     *
     * @param estoque O estoque para ser deletado
     */
    public void delete(Estoque estoque) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        EstoqueRepository repository = new EstoqueRepository(em);

        try {
            transaction.begin();
            repository.delete(estoque);
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
     * Confere se um estoque existe
     *
     * @param estoque O estoque para conferir
     * @return boolean - true se o estoque existe, false se nao
     */
    public boolean contains(Estoque estoque) {
        EntityManager em = JPAConfig.getEntityManager();
        EstoqueRepository repository = new EstoqueRepository(em);

        try {
            return repository.contains(estoque);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista com todos os estoques
     *
     * @return List<Estoque> - A lista de estoques
     */
    public List<Estoque> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        EstoqueRepository repository = new EstoqueRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Busca um estoque pelo id
     *
     * @param id O id do estoque
     * @return Estoque - O estoque encontrado
     */
    public Estoque findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EstoqueRepository repository = new EstoqueRepository(em);

        try {
            return repository.findById(id);
        } finally {
            em.close();
        }
    }

    /**
     * Busca estoques pelo prefixo
     *
     * @param prefixo O prefixo do estoque
     * @return List<Estoque> - A lista de estoques encontrados
     */
    public List<Estoque> findByNome(String prefixo) {
        EntityManager em = JPAConfig.getEntityManager();
        EstoqueRepository repository = new EstoqueRepository(em);

        try {
            return repository.findByNome(prefixo);
        } finally {
            em.close();
        }
    }

    private void validateEstoque(Estoque estoque) {
        if (estoque == null) {
            throw new EstoqueException("Estoque não pode ser nulo");
        }
        validateCodigo(estoque.getCdEstoque());
        validateNome(estoque.getNmEstoque());
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new EstoqueException("Código do estoque não pode ser nulo ou vazio");
        }
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new EstoqueException("Nome do estoque não pode ser nulo ou vazio");
        }
    }
}
