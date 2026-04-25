package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.exception.ProdutosEstoquesException;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutosEstoquesService {
    private final ProdutosEstoquesRepository repository;

    public ProdutosEstoquesService(ProdutosEstoquesRepository repository) {
        this.repository = repository;
    }

    public void save(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        validateProdutosEstoques(produtoEstoque);
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            this.repository.create(produtoEstoque);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        validateProdutosEstoques(produtoEstoque);
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            this.repository.update(produtoEstoque);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(ProdutosEstoques produtoEstoques) {
        if (this.repository.contains(produtoEstoques)) {
            this.repository.delete(produtoEstoques);
        }
    }

    public void contains(ProdutosEstoques produtoEstoque) {
        this.repository.contains(produtoEstoque);
    }

    public ProdutosEstoques findById(int id) throws ProdutosEstoquesException {
        validateId(id);
        return this.repository.findById(id);
    }

    public List<ProdutosEstoques> findAll() {
        return this.repository.findAll();
    }

    public List<ProdutosEstoques> findByName(String name) throws ProdutosEstoquesException {
        validateObservacao(name);
        return this.repository.findByName(name);
    }

    private void validateProdutosEstoques(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        if (produtoEstoque == null) {
            throw new ProdutosEstoquesException("Produtos não pode ser nulo");
        }
        validateObservacao(produtoEstoque.getDsObservacao());
        validateQuantidade(produtoEstoque.getNrQuantidade());
    }

    private void validateId(int id) throws ProdutosEstoquesException {
        if (id < 0) {
            throw new ProdutosEstoquesException("Id não pode ser negativo");
        }
    }

    private void validateQuantidade(int quantidade) throws ProdutosEstoquesException {
        if (quantidade < 0) {
            throw new ProdutosEstoquesException("Quantidade não pode ser negativa");
        }
    }

    private void validateObservacao(String observacao) throws ProdutosEstoquesException {
        if (observacao == null || observacao.isBlank()) {
            throw new ProdutosEstoquesException("Observação não pode ser nulo ou vazio");
        }
    }
}
