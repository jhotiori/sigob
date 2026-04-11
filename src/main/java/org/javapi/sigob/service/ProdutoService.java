package org.javapi.sigob.service;

import java.math.BigDecimal;
import java.util.List;

import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.exception.ProdutoException;
import org.javapi.sigob.repository.ProdutoRepository;

public class ProdutoService {
    private final ProdutoRepository repository;

    /**
     * Cria um novo ProdutoService
     *
     * @param repository O repositório de Produtos
     * @return ProdutoService - O novo ProdutoService
     */
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva um Produto
     *
     * @param produto O produto para ser salvo
     * @throws ProdutoException Se o produto for invalido
     */
    public void save(Produto produto) {
        validateProduto(produto);
        this.repository.save(produto);
    }

    /**
     * Atualiza um Produto
     *
     * @param produto O produto para ser atualizado
     * @throws ProdutoException Se o produto for invalido
     */
    public void update(Produto produto) {
        validateProduto(produto);
        this.repository.update(produto);
    }

    /**
     * Deleta um Produto
     *
     * @param produto O produto para ser deletado
     */
    public void delete(Produto produto) {
        if (this.repository.contains(produto)) {
            this.repository.delete(produto);
        }
    }

    /**
     * Verifica se um Produto está salvo
     *
     * @param produto O produto para ser verificado
     * @return boolean - true se o produto estiver salvo, false se nao
     */
    public boolean contains(Produto produto) {
        return this.repository.contains(produto);
    }

    /**
     * Retorna uma lista de todos os Produtos
     *
     * @return List<Produto> - A lista de Produtos
     */
    public List<Produto> findAll() {
        return this.repository.findAll();
    }

    /**
     * Busca um Produto pelo id
     *
     * @param id O id do Produto
     * @return Produto - O Produto encontrado
     */
    public Produto findById(int id) {
        validateId(id);
        return this.repository.findById(id);
    }

    /**
     * Busca pelos Produtos que contem o nome
     *
     * @param nome O nome
     * @return List<Produto> - A lista de Produtos encontrados
     */
    public List<Produto> findByNome(String nome) {
        validateNome(nome);
        return this.repository.findByNome(nome);
    }

    private void validateProduto(Produto produto) {
        if (produto == null) {
            throw new ProdutoException("Produto não pode ser nulo");
        }
        validateId(produto.getIdProduto());
        validateNome(produto.getNmProduto());
        validateCodigo(produto.getCdProduto());
        validateValorCusto(produto.getVlCusto());
        validateValorVenda(produto.getVlProduto());
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new ProdutoException("Id do produto não pode ser menor ou igual a zero");
        }
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new ProdutoException("Código do produto não pode ser nulo ou vazio");
        }
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ProdutoException("Nome do produto não pode ser nulo ou vazio");
        }
    }

    private void validateValorCusto(BigDecimal custo) {
        if (custo == null || custo.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProdutoException("Custo do produto não pode ser nulo ou menor a zero");
        }
    }

    private void validateValorVenda(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProdutoException("Custo de venda do produto não pode ser nulo ou menor ou igual a zero");
        }
    }

    public boolean exists(Produto produto) {
        return this.repository.contains(produto);
    }
}
