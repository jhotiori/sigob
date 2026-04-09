package org.javapi.sigob.service;

import java.math.BigDecimal;
import java.util.List;

import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.exception.ProdutoException;
import org.javapi.sigob.repository.ProdutoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void save(Produto produto, EntityManager em) {
        validateProduto(produto);

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int idProduto = produto.getIdProduto();

            if (idProduto > 0) {
                this.repository.update(produto);
            } else {
                this.repository.create(produto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void create(Produto produto) {
        validateProduto(produto);
        this.repository.create(produto);
    }

    public void update(Produto produto) {
        validateProduto(produto);
        if (produto.getIdProduto() <= 0) {
            throw new ProdutoException("ID do produto deve ser maior que zero para atualizar");
        }
        this.repository.update(produto);
    }

    public void delete(Produto produto) {
        this.repository.delete(produto);
    }

    public Produto getById(int id) {
        if (id <= 0) {
            throw new ProdutoException("ID do produto não pode ser menor ou igual a zero");
        }
        return this.repository.findById(id);
    }

    public List<Produto> getByNome(String nome) {
        validateNome(nome);
        return this.repository.findByNome(nome);
    }

    public List<Produto> getAll() {
        return this.repository.findAll();
    }

    private void validateProduto(Produto produto) {
        if (produto == null) {
            throw new ProdutoException("Produto não pode ser nulo");
        }
        validateCodigo(produto.getCdProduto());
        validateNome(produto.getNmProduto());
        validateValorCusto(produto.getVlCusto());
        validateValorVenda(produto.getVlProduto());
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
}
