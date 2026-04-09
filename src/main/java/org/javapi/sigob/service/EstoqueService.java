package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.exception.EstoqueException;
import org.javapi.sigob.repository.EstoqueRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EstoqueService {
    private final EstoqueRepository repository;

    public EstoqueService(EstoqueRepository repository) {
        this.repository = repository;
    }

    public void save(Estoque estoque) {
        validateEstoque(estoque);
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            this.repository.create(estoque);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(Estoque estoque) {
        validateEstoque(estoque);
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            this.repository.update(estoque);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Estoque estoque) {
        if (this.repository.contains(estoque)) {
            this.repository.delete(estoque);
        }
    }

    public boolean contains(Estoque estoque) {
        return this.repository.contains(estoque);
    }

    public List<Estoque> findAll() {
        return this.repository.findAll();
    }

    public List<Estoque> findByName(String prefixo) {
        return this.repository.findByName(prefixo);
    }

    public Estoque findById(int id) {
        return this.repository.findById(id);
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
