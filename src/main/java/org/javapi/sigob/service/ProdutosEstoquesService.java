package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.exception.ProdutosEstoquesException;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutosEstoquesService {

    /**
     * Cria um novo ProdutosEstoquesService
     */
    public ProdutosEstoquesService() {
    }

    /**
     * Salva um novo ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     * @throws ProdutosEstoquesException Se o ProdutosEstoques for inválido
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
     * @throws ProdutosEstoquesException Se o ProdutosEstoques for inválido
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
     * @param produtoEstoque O ProdutosEstoques
     */
    public void delete(ProdutosEstoques produtoEstoque) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            transaction.begin();
            repository.delete(produtoEstoque);
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
     * Verifica se um ProdutosEstoques está gerenciado pelo EntityManager
     *
     * @param produtoEstoque O ProdutosEstoques
     * @return boolean - true se gerenciado, false caso contrário
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
     * @return ProdutosEstoques - O ProdutosEstoques ou null se não encontrado
     */
    public Optional<ProdutosEstoques> findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            return repository.findById(id);
        } finally {
            em.close();
        }
    }

    /**
     * Busca ProdutosEstoques pelo ID do Produto
     *
     * @param produtoId O ID do Produto
     * @return List<ProdutosEstoques> - Lista de ProdutosEstoques do produto
     */
    public List<ProdutosEstoques> findByProduto(int produtoId) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            return repository.findByProduto(produtoId);
        } finally {
            em.close();
        }
    }

    /**
     * Busca ProdutosEstoques pelo ID do Estoque
     *
     * @param estoqueId O ID do Estoque
     * @return List<ProdutosEstoques> - Lista de ProdutosEstoques do estoque
     */
    public List<ProdutosEstoques> findByEstoque(int estoqueId) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

        try {
            return repository.findByEstoque(estoqueId);
        } finally {
            em.close();
        }
    }

    private void validateProdutosEstoques(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        if (produtoEstoque == null) {
            throw new ProdutosEstoquesException("ProdutosEstoques não pode ser nulo");
        }
        if (produtoEstoque.getProduto() == null) {
            throw new ProdutosEstoquesException("Produto não pode ser nulo");
        }
        if (produtoEstoque.getEstoque() == null) {
            throw new ProdutosEstoquesException("Estoque não pode ser nulo");
        }
        validateQuantidade(produtoEstoque.getQuantidade());
    }

    private void validateQuantidade(int quantidade) throws ProdutosEstoquesException {
        if (quantidade < 0) {
            throw new ProdutosEstoquesException("Quantidade não pode ser negativa");
        }
    }
}