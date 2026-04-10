package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.exception.EstoqueException;
import org.javapi.sigob.repository.EstoqueRepository;

public class EstoqueService {
    private final EstoqueRepository repository;

    /**
     * Cria um novo EstoqueService
     * @param repository O repositorio de estoques
     * @return EstoqueService - O serviço de estoques
     */
    public EstoqueService(EstoqueRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva um estoque
     * @param estoque O estoque para ser salvo
     * @throws EstoqueException Se o estoque for invalido
     */
    public void save(Estoque estoque) {
        validateEstoque(estoque);
        this.repository.save(estoque);
    }

    /**
     * Atualiza um estoque
     * @param estoque O estoque para ser atualizado
     * @throws EstoqueException Se o estoque for invalido
     */
    public void update(Estoque estoque) {
        validateEstoque(estoque);
        this.repository.update(estoque);
    }

    /**
     * Deleta um estoque
     * @param estoque O estoque para ser deletado
     */
    public void delete(Estoque estoque) {
        if (this.repository.contains(estoque)) {
            this.repository.delete(estoque);
        }
    }

    /**
     * Confere se um estoque existe
     * @param estoque O estoque para conferir
     * @return boolean - true se o estoque existe, false se nao
     */
    public boolean contains(Estoque estoque) {
        return this.repository.contains(estoque);
    }

    /**
     * Retorna uma lista com todos os estoques
     * @return List<Estoque> - A lista de estoques
     */
    public List<Estoque> findAll() {
        return this.repository.findAll();
    }

    /**
     * Busca um estoque pelo id
     * @param id O id do estoque
     * @return Estoque - O estoque encontrado
     */
    public Estoque findById(int id) {
        return this.repository.findById(id);
    }

    /**
     * Busca estoques pelo prefixo
     * @param prefixo O prefixo do estoque
     * @return List<Estoque> - A lista de estoques encontrados
     */
    public List<Estoque> findByNome(String prefixo) {
        return this.repository.findByNome(prefixo);
    }

    private void validateEstoque(Estoque estoque) {
        if (estoque == null) {
            throw new EstoqueException("Estoque não pode ser nulo");
        }
        validateId(estoque.getIdEstoque());
        validateCodigo(estoque.getCdEstoque());
        validateNome(estoque.getNmEstoque());
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new EstoqueException("Id do estoque não pode ser menor ou igual a zero");
        }
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
