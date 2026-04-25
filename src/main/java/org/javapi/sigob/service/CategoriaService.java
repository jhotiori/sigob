package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.exception.CategoriaException;
import org.javapi.sigob.repository.CategoriaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CategoriaService {

    /**
     * Construtor para criar um novo CategoriaService
     *
     * @return CategoriaService - O novo CategoriaService
     */
    public CategoriaService() {
    }

    /**
     * Salva uma nova Categoria
     *
     * @param categoria A Categoria para ser salva
     */
    public void save(Categoria categoria) {
        validateNome(categoria.getNome());
        validateCodigo(categoria.getCodigo());

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        CategoriaRepository repository = new CategoriaRepository(em);

        try {
            transaction.begin();

            if (categoria.getId() > 0) {
                repository.update(categoria);
            } else {
                repository.save(categoria);
            }

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
     * Atualiza uma Categoria
     *
     * @param categoria A Categoria para ser atualizada
     * @throws CategoriaException Se o ID da Categoria for menor ou igual a zero
     */
    public void update(Categoria categoria) {
        validateCategoria(categoria);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        CategoriaRepository repository = new CategoriaRepository(em);

        try {
            transaction.begin();
            repository.update(categoria);
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
     * Remove uma Categoria
     *
     * @param categoria A Categoria para ser removida
     */
    public void delete(Categoria categoria) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        CategoriaRepository repository = new CategoriaRepository(em);

        try {
            transaction.begin();
            repository.deleteById(categoria.getId());
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
     * Confere se uma categoria existe
     *
     * @param categoria A categoria
     * @return boolean - true se a categoria existe, false se nao
     */
    public boolean contains(Categoria categoria) {
        EntityManager em = JPAConfig.getEntityManager();
        CategoriaRepository repository = new CategoriaRepository(em);

        try {
            return repository.contains(categoria);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista com todas as Categorias
     *
     * @return List<Categoria> - A lista de categorias
     */
    public List<Categoria> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        CategoriaRepository repository = new CategoriaRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Busca uma Categoria pelo seu ID
     *
     * @param id O ID da Categoria
     * @return Categoria - A Categoria buscada
     * @throws CategoriaException Se o ID da Categoria for menor ou igual a zero
     */
    public Optional<Categoria> findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        CategoriaRepository repository = new CategoriaRepository(em);

        try {
            return repository.findById(id);
        } finally {
            em.close();
        }
    }

    /**
     * Busca por categorias que comecam com o prefixo (nome)
     *
     * @param nome O prefixo
     * @return List<Categoria> - A lista de categorias
     */
    public List<Categoria> findByNome(String nome) {
        validateNome(nome);

        EntityManager em = JPAConfig.getEntityManager();
        CategoriaRepository repository = new CategoriaRepository(em);

        try {
            return repository.findByNome(nome);
        } finally {
            em.close();
        }
    }

    /**
     * Busca por uma Categoria pelo seu codigo
     *
     * @param codigo O codigo da categoria
     * @return Categoria - A categoria buscada
     */
    public Categoria findByCodigo(String codigo) {
        validateCodigo(codigo);

        EntityManager em = JPAConfig.getEntityManager();
        CategoriaRepository repository = new CategoriaRepository(em);

        try {
            return repository.findByCodigo(codigo);
        } finally {
            em.close();
        }
    }

    private void validateCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new CategoriaException("Categoria não pode ser nula");
        }
        validateNome(categoria.getNome());
        validateCodigo(categoria.getCodigo());
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