package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutosEstoqueService {
    private final ProdutosEstoquesRepository repository;

    public ProdutosEstoqueService(ProdutosEstoquesRepository repository) {
        this.repository = repository;
    }

    public void save(ProdutosEstoques produtoEstoque) {
        validateProdutosEstoques(produtoEstoque);
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            this.repository.create(produtoEstoque);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(ProdutosEstoques produtoEstoque) {
        validateProdutosEstoques(produtoEstoque);
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            this.repository.update(produtoEstoque);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
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

    public ProdutosEstoques findById(int id) {
        validateId(id);
        return this.repository.findById(id);
    }

    public List<ProdutosEstoques> findAll() {
        return this.repository.findAll();
    }

    public List <ProdutosEstoques> findByName(String name) {
        validateObservacao(name);
        return this.repository.findByName(name);
    }

    private void validateProdutosEstoques(ProdutosEstoques produtoEstoque) {
        if (produtoEstoque == null) {
            throw new ProdutosEstoquesException("ProdutosEstoques não pode ser nulo");
        }
        validateObservacao(produtoEstoque.getDsObservacao());
        validateQuantidade(produtoEstoque.getNrQuantidade());
    }

    private void validateId(int id) {
        if (id < 0) {
            throw new ProdutosEstoquesException("Id não pode ser negativo");
        }
    }

    private void validateQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new ProdutosEstoquesException("Quantidade não pode ser negativa");
        }
    }

    private void validateObservacao(String observacao) {
        if (observacao == null || observacao.isBlank()) {
            throw new ProdutosEstoquesException("Observação não pode ser nulo ou vazio");
        }
    }
}
