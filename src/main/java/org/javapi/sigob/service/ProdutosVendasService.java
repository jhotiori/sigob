package org.javapi.sigob.service;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.entity.ProdutosVendas;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.exception.ProdutosVendasException;
import org.javapi.sigob.repository.ProdutoRepository;
import org.javapi.sigob.repository.ProdutosVendasRepository;
import org.javapi.sigob.repository.VendaRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProdutosVendasService {
    private ProdutosVendasRepository repos;

    public ProdutosVendasService(ProdutosVendasRepository repos) {
        this.repos = repos;
    }

    public boolean exists(ProdutosVendas produtosVendas){
        return repos.exists(produtosVendas);
    }

    public ProdutosVendas findById(int id) {
        return repos.findById(id);
    }

    public ProdutosVendas save(ProdutosVendas produtosVendas){
        var idProdutoVenda = produtosVendas.getIdProdutoVenda();
        var nrQuantidade = produtosVendas.getNrQuantidade();
        var vlSaldo =  produtosVendas.getVlSaldo();
        var produto = produtosVendas.getProduto();
        var venda = produtosVendas.getVenda();

        if(nrQuantidade <= 0){
            throw new ProdutosVendasException("A quantidade de produtos precisa ser maior que 0!");
        }

        if(vlSaldo.compareTo(BigDecimal.ZERO) <= 0){
            throw new ProdutosVendasException("O valor precisa ser maior que 0!");
        }

        EntityManager em = JPAConfig.getEntityManager();
        var prodRepos = new ProdutoRepository(em);
        var prodService = new ProdutoService(prodRepos);
        if(produto == null || prodService.exists(produto)){
            throw new ProdutosVendasException("Produto não localizado!");
        }

        var vendService = new VendaService(new VendaRepository(em));
        if(venda == null || vendService.exists(venda)){
            throw new ProdutosVendasException("Venda não localizado!");
        }

        //Cria o objeto final
        var resultProdutoVenda = new ProdutosVendas(idProdutoVenda, nrQuantidade, vlSaldo, produto, venda);

        //Valida se é CREATE ou UPDATE
        if(idProdutoVenda > 0){
            repos.update(resultProdutoVenda);
        } else {
            repos.create(resultProdutoVenda);
        }

        return resultProdutoVenda;
    }

    public List<ProdutosVendas> findByProduto(Produto produto){
        return repos.findByProduto(produto.getIdProduto());
    }

    public List<ProdutosVendas> findByVenda(Venda venda){
        return repos.findByVenda(venda.getIdVenda());
    }

    public List<ProdutosVendas> findAll(){
        return repos.findAll();
    }

    public void delete(ProdutosVendas produtosVendas){
        if(repos.exists(produtosVendas)){
            repos.delete(produtosVendas);
        }
    }
}
