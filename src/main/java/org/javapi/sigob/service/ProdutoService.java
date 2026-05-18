package org.javapi.sigob.service;

import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.repository.ProdutoRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoService {

    /**
     * Cria um novo ProdutoService
     */
    public ProdutoService() {
    }

    /**
     * Salva um Produto
     *
     * @param produto O produto para ser salvo
     * @throws IllegalArgumentException Se o produto for inválido
     */
    public void save(Produto produto) {
        validateProduto(produto);

        TransactionExecutor.executeVoid(em -> {
            new ProdutoRepository(em).save(produto);
        });
    }

    /**
     * Atualiza um Produto
     *
     * @param produto O produto para ser atualizado
     * @throws IllegalArgumentException Se o produto for inválido
     */
    public void update(Produto produto) {
        validateProduto(produto);

        TransactionExecutor.executeVoid(em -> {
            new ProdutoRepository(em).update(produto);
        });
    }

    /**
     * Remove um Produto
     *
     * @param produto O produto a ser removido
     * @throws IllegalArgumentException Se o produto for inválido
     */
    public void delete(Produto produto) {
        validateProduto(produto);

        TransactionExecutor.executeVoid(em -> {
            new ProdutoRepository(em).deleteById(produto.getId());
        });
    }

    /**
     * Verifica se um Produto existe
     *
     * @param produto O produto para verificar
     * @return boolean - true se existir, false caso contrário
     * @throws IllegalArgumentException Se o produto for inválido
     */
    public boolean contains(Produto produto) {
        validateProduto(produto);

        return TransactionExecutor.query(em -> {
            return new ProdutoRepository(em).contains(produto.getId());
        });
    }

    /**
     * Retorna todos os Produtos
     *
     * @return List<Produto> - Lista de produtos
     */
    public List<Produto> findAll() {
        return TransactionExecutor.query(em -> {
            return new ProdutoRepository(em).findAll();
        });
    }

    /**
     * Busca um Produto pelo ID
     *
     * @param id O ID do produto
     * @return Optional<Produto> - O produto encontrado, se existir
     */
    public Optional<Produto> findById(int id) {
        validateId(id);

        return TransactionExecutor.query(em -> {
            return new ProdutoRepository(em).findById(id);
        });
    }

    /**
     * Busca um Produto pelo código (único)
     *
     * @param codigo O código do produto
     * @return Optional<Produto> - O produto encontrado, se existir
     * @throws IllegalArgumentException Se o código for inválido
     */
    public Optional<Produto> findByCodigo(String codigo) {
        validateCodigo(codigo);

        return TransactionExecutor.query(em -> {
            return new ProdutoRepository(em).findByCodigo(codigo);
        });
    }

    /**
     * Busca Produtos pelo nome (prefixo)
     *
     * @param nome O nome para busca
     * @return List<Produto> - Lista de produtos encontrados
     * @throws IllegalArgumentException Se o nome for inválido
     */
    public List<Produto> findByNome(String nome) {
        validateNome(nome);

        return TransactionExecutor.query(em -> {
            return new ProdutoRepository(em).findByNome(nome);
        });
    }

    /**
     * Busca Produtos por nome da Categoria (único)
     *
     * @param nomeCategoria O nome da categoria
     * @return List<Produto> - Lista de produtos encontrados
     * @throws IllegalArgumentException Se o nome da categoria for inválido
     */
    public List<Produto> findByCategoria(String nomeCategoria) {
        validateNome(nomeCategoria);

        return TransactionExecutor.query(em -> {
            return new ProdutoRepository(em).findByCategoriaNome(nomeCategoria);
        });
    }

    /**
     * Valida um Produto completo
     *
     * @param produto O produto a validar
     * @throws IllegalArgumentException Se inválido
     */
    private void validateProduto(Produto produto) {
        Validator.start()
                .expectNotNull(produto, "Produto não pode ser nulo!")
                .validate();

        validateNome(produto.getNome());
        validateCodigo(produto.getCodigo());
        validateValorCompra(produto.getValorCompra());
        validateValorVenda(produto.getValorVenda());
        validateCategoria(produto);
        validatePreco(produto.getValorCompra(), produto.getValorVenda());
    }

    /**
     * Valida o nome do Produto
     *
     * @param nome O nome
     * @throws IllegalArgumentException Se inválido
     */
    private void validateNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome do produto não pode ser nulo ou vazio!")
                .validate();
    }

    /**
     * Valida o código do Produto
     *
     * @param codigo O código
     * @throws IllegalArgumentException Se inválido
     */
    private void validateCodigo(String codigo) {
        Validator.start()
                .expectNotBlank(codigo, "Código do produto não pode ser nulo ou vazio!")
                .validate();
    }

    /**
     * Valida o valor de custo
     *
     * @param custo O valor de custo
     * @throws IllegalArgumentException Se inválido
     */
    private void validateValorCompra(BigDecimal custo) {
        Validator.start()
                .expectNotNull(custo, "Valor de compra não pode ser nulo!")
                .expect(custo, c -> c.compareTo(BigDecimal.ZERO) > 0, "Valor de compra deve ser maior que zero!")
                .validate();
    }

    /**
     * Valida o valor de venda
     *
     * @param valor O valor de venda
     * @throws IllegalArgumentException Se inválido
     */
    private void validateValorVenda(BigDecimal valor) {
        Validator.start()
                .expectNotNull(valor, "Valor de venda não pode ser nulo!")
                .expect(valor, v -> v.compareTo(BigDecimal.ZERO) > 0, "Valor de venda deve ser maior que zero!")
                .validate();
    }

    /**
     * Valida a categoria associada ao Produto
     *
     * @param produto O produto
     * @throws IllegalArgumentException Se inválido
     */
    private void validateCategoria(Produto produto) {
        Validator.start()
                .expectNotNull(produto.getCategoria(), "Categoria não pode ser nula!")
                .validate();
    }

    /**
     * Valida a consistência de preços (regra de negócio)
     *
     * @param custo Valor de custo
     * @param venda Valor de venda
     * @throws IllegalArgumentException Se inválido
     */
    private void validatePreco(BigDecimal custo, BigDecimal venda) {
        if (venda.compareTo(custo) < 0) {
            throw new IllegalArgumentException("Valor de venda não pode ser menor que o custo!");
        }
    }

    /**
     * Valida o ID
     *
     * @param id O ID
     * @throws IllegalArgumentException Se inválido
     */
    private void validateId(int id) {
        Validator.start()
                .expectNotNull(id, "ID não pode ser nulo!")
                .validate();
    }
}
