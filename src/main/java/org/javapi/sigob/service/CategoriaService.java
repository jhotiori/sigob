package org.javapi.sigob.service;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.exception.CategoriaException;
import org.javapi.sigob.repository.CategoriaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public void save(Categoria categoria, EntityManager em) {
        validateCategoria(categoria);

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int idCategoria = categoria.getIdCategoria();

            if (idCategoria > 0) {
                this.repository.update(categoria);
            } else {
                this.repository.create(categoria);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void create(Categoria categoria) {
        validateCategoria(categoria);
        this.repository.create(categoria);
    }

    public void update(Categoria categoria) {
        validateCategoria(categoria);
        if (categoria.getIdCategoria() <= 0) {
            throw new CategoriaException("ID da categoria deve ser maior que zero para atualizar");
        }
        this.repository.update(categoria);
    }

    public Categoria getById(int id) {
        if (id <= 0) {
            throw new CategoriaException("ID da categoria não pode ser menor ou igual a zero");
        }
        return this.repository.findById(id);
    }

    public List<Categoria> getByNome(String nome) {
        validateNome(nome);
        return this.repository.findByName(nome);
    }

    public Categoria getByCodigo(String codigo) {
        validateCodigo(codigo);
        return this.repository.findByCodigo(codigo);
    }

    public List<Categoria> getAll() {
        return this.repository.findAll();
    }

    public void delete(Categoria categoria) {
        this.repository.delete(categoria);
    }

    private void validateCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new CategoriaException("Categoria não pode ser nula");
        }
        validateNome(categoria.getNmCategoria());
        validateCodigo(categoria.getCdCategoria());
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new CategoriaException("Nome da categoria não pode ser nulo ou vazio");
        }
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new CategoriaException("Código da categoria não pode ser nulo ou vazio");
        }
    }
}