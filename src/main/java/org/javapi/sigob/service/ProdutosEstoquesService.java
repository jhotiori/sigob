package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.exception.ProdutosEstoquesException;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;

public class ProdutosEstoquesService {
    private final ProdutosEstoquesRepository repository;

    /**
     * Cria um novo ProdutosEstoquesService
     *
     * @param repository O repositorio de ProdutosEstoques
     * @return ProdutosEstoquesService - O ProdutosEstoquesService
     */
    public ProdutosEstoquesService(ProdutosEstoquesRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva um novo ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     * @throws ProdutosEstoquesException Se o ProdutosEstoques for invalido
     */
    public void save(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        validateProdutosEstoques(produtoEstoque);
        this.repository.save(produtoEstoque);
    }

    /**
     * Atualiza um ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     * @throws ProdutosEstoquesException Se o ProdutosEstoques for invalido
     */
    public void update(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        validateProdutosEstoques(produtoEstoque);
        this.repository.update(produtoEstoque);
    }

    /**
     * Deleta um ProdutosEstoques
     *
     * @param produtoEstoques O ProdutosEstoques
     */
    public void delete(ProdutosEstoques produtoEstoques) {
        if (this.repository.contains(produtoEstoques)) {
            this.repository.delete(produtoEstoques);
        }
    }

    /**
     * Verifica se um ProdutosEstoques está contido no EntityManager
     *
     * @param produtoEstoque O ProdutosEstoques
     * @return boolean - true se o ProdutosEstoques estiver contido, false se nao
     */
    public boolean contains(ProdutosEstoques produtoEstoque) {
        return this.repository.contains(produtoEstoque);
    }

    /**
     * Retorna uma lista com todos os ProdutosEstoques
     *
     * @return List<ProdutosEstoques> - A lista de ProdutosEstoques
     */
    public List<ProdutosEstoques> findAll() {
        return this.repository.findAll();
    }

    /**
     * Retorna um ProdutosEstoques pelo ID
     *
     * @param id O ID do ProdutosEstoques
     * @return ProdutosEstoques - O ProdutosEstoques
     */
    public ProdutosEstoques findById(int id) throws ProdutosEstoquesException {
        validateId(id);
        return this.repository.findById(id);
    }

    /**
     * Busca um ProdutosEstoques pelo nome
     *
     * @param nome O nome do ProdutosEstoques
     * @return List<ProdutosEstoques> - A lista de ProdutosEstoques
     */
    public List<ProdutosEstoques> findByNome(String nome) throws ProdutosEstoquesException {
        validateObservacao(nome);
        return this.repository.findByNome(nome);
    }

    private void validateProdutosEstoques(ProdutosEstoques produtoEstoque) throws ProdutosEstoquesException {
        if (produtoEstoque == null) {
            throw new ProdutosEstoquesException("Produtos não pode ser nulo");
        }
        validateId(produtoEstoque.getIdProdutosEstoque());
        validateObservacao(produtoEstoque.getDsObservacao());
        validateQuantidade(produtoEstoque.getNrQuantidade());
    }

    private void validateId(int id) throws ProdutosEstoquesException {
        if (id < 0) {
            throw new ProdutosEstoquesException("Id não pode ser negativo");
        }
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
