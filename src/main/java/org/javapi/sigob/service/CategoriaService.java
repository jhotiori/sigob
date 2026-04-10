package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.exception.CategoriaException;
import org.javapi.sigob.repository.CategoriaRepository;

public class CategoriaService {
    private final CategoriaRepository repository;

    /**
     * Construtor para criar um novo CategoriaService
     *
     * @param repository O repositorio do mesmo
     * @return CategoriaService - O novo CategoriaService
     */
    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva uma nova Categoria
     *
     * @param categoria A Categoria para ser salva
     */
    public void save(Categoria categoria) {
        validateCategoria(categoria);

        if (categoria.getIdCategoria() > 0) {
            this.repository.update(categoria);
        } else {
            this.repository.save(categoria);
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
        if (categoria.getIdCategoria() <= 0) {
            throw new CategoriaException("ID da categoria deve ser maior que zero para atualizar");
        }
        this.repository.update(categoria);
    }

    /**
     * Remove uma Categoria
     *
     * @param categoria A Categoria para ser removida
     */
    public void delete(Categoria categoria) {
        this.repository.delete(categoria);
    }

    /**
     * Confere se uma categoria existe
     *
     * @param categoria A categoria
     * @return boolean - true se a categoria existe, false se nao
     */
    public boolean contains(Categoria categoria) {
        return this.repository.contains(categoria);
    }

    /**
     * Retorna uma lista com todas as Categorias
     *
     * @return List<Categoria> - A lista de categorias
     */
    public List<Categoria> findAll() {
        return this.repository.findAll();
    }

    /**
     * Busca uma Categoria pelo seu ID
     *
     * @param id O ID da Categoria
     * @return Categoria - A Categoria buscada
     * @throws CategoriaException Se o ID da Categoria for menor ou igual a zero
     */
    public Categoria findById(int id) {
        if (id <= 0) {
            throw new CategoriaException("ID da categoria não pode ser menor ou igual a zero");
        }
        return this.repository.findById(id);
    }

    /**
     * Busca por categorias que comecam com o prefixo (nome)
     *
     * @param nome O prefixo
     * @return List<Categoria> - A lista de categorias
     */
    public List<Categoria> findByNome(String nome) {
        validateNome(nome);
        return this.repository.findByName(nome);
    }

    /**
     * Busca por uma Categoria pelo seu codigo
     *
     * @param codigo O codigo da categoria
     * @return Categoria - A categoria buscada
     */
    public Categoria findByCodigo(String codigo) {
        validateCodigo(codigo);
        return this.repository.findByCodigo(codigo);
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
