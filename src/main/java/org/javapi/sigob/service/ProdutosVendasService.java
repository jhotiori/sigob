package org.javapi.sigob.service;

import java.math.BigDecimal;
import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.entity.ProdutosVendas;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.exception.ProdutosVendasException;
import org.javapi.sigob.repository.ProdutosVendasRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutosVendasService {

    /**
     * Cria um novo ProdutosVendasService
     *
     * @return ProdutosVendasService - O ProdutosVendasService criado
     */
    public ProdutosVendasService() {
    }

    /**
     * Salva um ProdutosVendas, atualiza caso o ID seja maior que zero
     *
     * @param produtosVendas O ProdutosVendas
     * @throws ProdutosVendasException Se o ProdutosVendas for valido
     * @return ProdutosVendas - O ProdutosVendas salvo
     */
    public ProdutosVendas save(ProdutosVendas produtosVendas) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutosVendasRepository repository = new ProdutosVendasRepository(em);

        try {
            transaction.begin();
            validateProdutosVendas(produtosVendas);

            int id = produtosVendas.getId();
            Produto produto = produtosVendas.getProduto();
            Venda venda = produtosVendas.getVenda();
            int quantidade = produtosVendas.getQuantidade();
            BigDecimal saldo = produtosVendas.getSaldo();

            ProdutosVendas produtoFinal = new ProdutosVendas(id, quantidade, saldo, produto, venda);

            if (id > 0) {
                repository.update(produtoFinal);
            } else {
                repository.save(produtoFinal);
            }

            transaction.commit();
            return produtoFinal;
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
     * Deleta um ProdutosVendas
     *
     * @param produtosVendas O ProdutosVendas
     */
    public void delete(ProdutosVendas produtosVendas) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ProdutosVendasRepository repository = new ProdutosVendasRepository(em);

        try {
            transaction.begin();
            repository.deleteById(produtosVendas.getId());
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
     * Verifica se um ProdutosVendas existe
     *
     * @param produtosVendas O ProdutosVendas
     * @return boolean - true se o ProdutosVendas existe, false se nao
     */
    public boolean contains(ProdutosVendas produtosVendas) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosVendasRepository repository = new ProdutosVendasRepository(em);

        try {
            return repository.contains(produtosVendas);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista de todos os ProdutosVendas
     *
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosVendasRepository repository = new ProdutosVendasRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna um ProdutosVendas pelo ID
     *
     * @param id O ID do ProdutosVendas
     * @return ProdutosVendas - O ProdutosVendas
     */
    public ProdutosVendas findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosVendasRepository repository = new ProdutosVendasRepository(em);

        try {
            return repository.findById(id).orElse(null);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista de ProdutosVendas pelo ID do Produto
     *
     * @param produto O Produto
     * @throws ProdutosVendasException Se o Produto for invalido
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findByProdutoId(Produto produto) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosVendasRepository repository = new ProdutosVendasRepository(em);

        try {
            validateProduto(produto);
            return repository.findByProdutoId(produto.getId());
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista de ProdutosVendas pelo ID da Venda
     *
     * @param venda O Venda
     * @throws ProdutosVendasException Se a Venda for invalida
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findByVendaId(Venda venda) {
        EntityManager em = JPAConfig.getEntityManager();
        ProdutosVendasRepository repository = new ProdutosVendasRepository(em);

        try {
            return repository.findByVendaId(venda.getId());
        } finally {
            em.close();
        }
    }

    private void validateProdutosVendas(ProdutosVendas produtoVendas) {
        if (produtoVendas == null) {
            throw new ProdutosVendasException("ProdutosVendas nao pode ser nulo!");
        }
        validateQuantidade(produtoVendas.getQuantidade());
        validateSaldo(produtoVendas.getSaldo());
        validateProduto(produtoVendas.getProduto());
        validateVenda(produtoVendas.getVenda());
    }

    private void validateQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new ProdutosVendasException("Quantidade nao pode ser negativa!");
        }
    }

    private void validateSaldo(BigDecimal saldo) {
        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProdutosVendasException("O valor precisa ser maior que 0!");
        }
    }

    private void validateProduto(Produto produto) {
        if (produto == null) {
            throw new ProdutosVendasException("Produto nao pode ser nulo!");
        }
    }

    private void validateVenda(Venda venda) {
        if (venda == null) {
            throw new ProdutosVendasException("Venda nao pode ser nulo!");
        }
    }
}
