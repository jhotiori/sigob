package org.javapi.sigob.service;

import java.math.BigDecimal;
import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.exception.ProdutoException;
import org.javapi.sigob.repository.ProdutoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutoService {

    /**
     * Cria um novo ProdutoService
     *
     * @return ProdutoService - O novo ProdutoService
     */
    public ProdutoService() {
    }

    /**
     * Salva um Produto
     *
     * @param produto O produto para ser salvo
     * @throws ProdutoException Se o produto for invalido
     */
    public void save(Produto produto) {
        validateProduto(produto);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutoRepository repository = new ProdutoRepository(em);

        try {
            transaction.begin();
            repository.save(produto);
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
     * Atualiza um Produto
     *
     * @param produto O produto para ser atualizado
     * @throws ProdutoException Se o produto for invalido
     */
    public void update(Produto produto) {
        validateProduto(produto);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutoRepository repository = new ProdutoRepository(em);

        try {
            transaction.begin();
            repository.update(produto);
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
     * Deleta um Produto
     *
     * @param produto O produto para ser deletado
     */
    public void delete(Produto produto) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutoRepository repository = new ProdutoRepository(em);

        try {
            transaction.begin();
            repository.deleteById(produto.getId());
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
     * Verifica se um Produto está salvo
     *
     * @param produto O produto para ser verificado
     * @return boolean - true se o produto estiver salvo, false se nao
     */
    public boolean contains(Produto produto) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutoRepository repository = new ProdutoRepository(em);

        try {
            return repository.contains(produto);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista de todos os Produtos
     *
     * @return List<Produto> - A lista de Produtos
     */
    public List<Produto> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutoRepository repository = new ProdutoRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Busca um Produto pelo id
     *
     * @param id O id do Produto
     * @return Produto - O Produto encontrado
     */
    public Produto findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutoRepository repository = new ProdutoRepository(em);

        try {
            return repository.findById(id).orElse(null);
        } finally {
            em.close();
        }
    }

    /**
     * Busca pelos Produtos que contem o nome
     *
     * @param nome O nome
     * @return List<Produto> - A lista de Produtos encontrados
     */
    public List<Produto> findByNome(String nome) {
        validateNome(nome);

        EntityManager em = JPAConfig.getEntityManager();
        ProdutoRepository repository = new ProdutoRepository(em);

        try {
            return repository.findByNome(nome);
        } finally {
            em.close();
        }
    }

    private void validateProduto(Produto produto) {
        if (produto == null) {
            throw new ProdutoException("Produto não pode ser nulo");
        }
        validateNome(produto.getNome());
        validateCodigo(produto.getCodigo());
        validateValorCusto(produto.getValorCompra());
        validateValorVenda(produto.getValorVenda());
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new ProdutoException("Código do produto não pode ser nulo ou vazio");
        }
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ProdutoException("Nome do produto não pode ser nulo ou vazio");
        }
    }

    private void validateValorCusto(BigDecimal custo) {
        if (custo == null || custo.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProdutoException("Custo do produto não pode ser nulo ou menor a zero");
        }
    }

    private void validateValorVenda(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProdutoException("Custo de venda do produto não pode ser nulo ou menor ou igual a zero");
        }
    }
}