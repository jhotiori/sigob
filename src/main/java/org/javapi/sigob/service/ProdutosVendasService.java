package org.javapi.sigob.service;

import java.math.BigDecimal;
import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.entity.ProdutosVendas;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.exception.ProdutosVendasException;
import org.javapi.sigob.repository.ProdutoRepository;
import org.javapi.sigob.repository.ProdutosVendasRepository;
import org.javapi.sigob.repository.VendaRepository;

public class ProdutosVendasService {
    private final ProdutosVendasRepository repository;

    /**
     * Cria um novo ProdutosVendasService
     *
     * @param repository O repositorio de ProdutosVendas
     * @return ProdutosVendasService - O ProdutosVendasService criado
     */
    public ProdutosVendasService(ProdutosVendasRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva um ProdutosVendas, atualiza caso o ID seja maior que zero
     *
     * @param produtosVendas O ProdutosVendas
     * @throws ProdutosVendasException Se o ProdutosVendas for valido
     * @return ProdutosVendas - O ProdutosVendas salvo
     */
    public ProdutosVendas save(ProdutosVendas produtosVendas) {
        validateProdutosVendas(produtosVendas);
        int idProdutoVenda = produtosVendas.getIdProdutoVenda();
        Produto produto = produtosVendas.getProduto();
        Venda venda = produtosVendas.getVenda();
        int quantidade = produtosVendas.getNrQuantidade();
        BigDecimal saldo = produtosVendas.getVlSaldo();

        ProdutoService produtoService = new ProdutoService(new ProdutoRepository(JPAConfig.getEntityManager()));
        if (!produtoService.contains(produto)) {
            throw new ProdutosVendasException("Produto não localizado!");
        }

        VendaService vendaService = new VendaService(new VendaRepository(JPAConfig.getEntityManager()));
        if (!vendaService.exists(venda)) {
            throw new ProdutosVendasException("Venda não localizada!");
        }

        ProdutosVendas produtoFinal = new ProdutosVendas(idProdutoVenda, quantidade, saldo, produto, venda);

        if (idProdutoVenda > 0) {
            this.repository.update(produtoFinal);
        } else {
            this.repository.save(produtoFinal);
        }

        return produtoFinal;
    }

    /**
     * Deleta um ProdutosVendas
     *
     * @param produtosVendas O ProdutosVendas
     */
    public void delete(ProdutosVendas produtosVendas) {
        if (this.repository.contains(produtosVendas)) {
            this.repository.delete(produtosVendas);
        }
    }

    /**
     * Verifica se um ProdutosVendas existe
     *
     * @param produtosVendas O ProdutosVendas
     * @return boolean - true se o ProdutosVendas existe, false se nao
     */
    public boolean contains(ProdutosVendas produtosVendas) {
        return this.repository.contains(produtosVendas);
    }

    /**
     * Retorna uma lista de todos os ProdutosVendas
     *
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findAll() {
        return this.repository.findAll();
    }

    /**
     * Retorna um ProdutosVendas pelo ID
     *
     * @param id O ID do ProdutosVendas
     * @return ProdutosVendas - O ProdutosVendas
     */
    public ProdutosVendas findById(int id) {
        validateId(id);
        return this.repository.findById(id);
    }

    /**
     * Retorna uma lista de ProdutosVendas pelo ID do Produto
     *
     * @param produto O Produto
     * @throws ProdutosVendasException Se o Produto for invalido
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findByProdutoId(Produto produto) {
        validateProduto(produto);
        validateId(produto.getIdProduto());
        return this.repository.findByProdutoId(produto.getIdProduto());
    }

    /**
     * Retorna uma lista de ProdutosVendas pelo ID da Venda
     *
     * @param venda O Venda
     * @throws ProdutosVendasException Se a Venda for invalida
     * @return List<ProdutosVendas> - A lista de ProdutosVendas
     */
    public List<ProdutosVendas> findByVendaId(Venda venda) {
        validateVenda(venda);
        validateId(venda.getIdVenda());
        return this.repository.findByVendaId(venda.getIdVenda());
    }

    private void validateProdutosVendas(ProdutosVendas produtoVendas) {
        if (produtoVendas == null) {
            throw new ProdutosVendasException("ProdutosVendas nao pode ser nulo!");
        }
        validateQuantidade(produtoVendas.getNrQuantidade());
        validateSaldo(produtoVendas.getVlSaldo());
        validateProduto(produtoVendas.getProduto());
        validateVenda(produtoVendas.getVenda());
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new ProdutosVendasException("Id nao pode ser menor ou igual a zero!");
        }
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
