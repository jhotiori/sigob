package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.exception.ProdutosEstoquesException;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutosEstoquesService {

    /**
     * Cria um novo ProdutosEstoquesService
     *
     * @return ProdutosEstoquesService - O ProdutosEstoquesService
     */
    public ProdutosEstoquesService() {
    }

    /**
     * Salva um novo ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     * @throws ProdutosEstoquesException Se o ProdutosEstoques for invalido
     */
    public void save(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        validateProdutosEstoques(produtoEstoque);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            transaction.begin();
            repository.save(produtoEstoque);
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
     * Atualiza um ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     * @throws ProdutosEstoquesException Se o ProdutosEstoques for invalido
     */
    public void update(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        validateProdutosEstoques(produtoEstoque);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            transaction.begin();
            repository.update(produtoEstoque);
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
     * Deleta um ProdutosEstoques
     *
     * @param produtoEstoques O ProdutosEstoques
     */
    public void delete(ProdutosEstoques produtoEstoques) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            transaction.begin();
            repository.delete(produtoEstoques);
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
     * Verifica se um ProdutosEstoques está contido no EntityManager
     *
     * @param produtoEstoque O ProdutosEstoques
     * @return boolean - true se o ProdutosEstoques estiver contido, false se nao
     */
    public boolean contains(ProdutosEstoques produtoEstoque) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            return repository.contains(produtoEstoque);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista com todos os ProdutosEstoques
     *
     * @return List<ProdutosEstoques> - A lista de ProdutosEstoques
     */
    public List<ProdutosEstoques> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna um ProdutosEstoques pelo ID
     *
     * @param id O ID do ProdutosEstoques
     * @return ProdutosEstoques - O ProdutosEstoques
     */
    public ProdutosEstoques findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            return repository.findById(id);
        } finally {
            em.close();
        }
    }

    /**
     * Busca um ProdutosEstoques pelo nome
     *
     * @param nome O nome do ProdutosEstoques
     * @return List<ProdutosEstoques> - A lista de ProdutosEstoques
     */
    public List<ProdutosEstoques> findByNome(String nome) throws ProdutosEstoquesException {
        validateObservacao(nome);

        EntityManager em = JPAConfig.getEntityManager();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            return repository.findByNome(nome);
        } finally {
            em.close();
        }
    }

    private void validateProdutosEstoques(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        if (produtoEstoque == null) {
            throw new ProdutosEstoquesException("Produtos não pode ser nulo");
        }
        validateObservacao(produtoEstoque.getDsObservacao());
        validateQuantidade(produtoEstoque.getNrQuantidade());
    }

    private void validateQuantidade(int quantidade) throws ProdutosEstoquesException {
        if (quantidade < 0) {
            throw new ProdutosEstoquesException("Quantidade não pode ser negativa");
        }
    }

    private void validateObservacao(String observacao) throws ProdutosEstoquesException {
        if (observacao == null || observacao.isBlank()) {
            throw new ProdutosEstoquesException("Observação não pode ser nulo ou vazio");
        }
    }
}
