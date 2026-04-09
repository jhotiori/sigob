package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.ProdutosVendas;
import java.util.List;


public class ProdutosVendasRepository {
    private final EntityManager em;

    public ProdutosVendasRepository(EntityManager em) {
        this.em = em;
    }

    public ProdutosVendas findById(int id) {
        return em.find(ProdutosVendas.class, id);
    }

    public void create(ProdutosVendas produtoVenda){
        em.getTransaction().begin();
        em.persist(produtoVenda);
        em.getTransaction().commit();
    }

    public void update(ProdutosVendas produtoVenda){
        em.getTransaction().begin();
        em.merge(produtoVenda);
        em.getTransaction().commit();
    }

    public void delete(ProdutosVendas produtoVenda){
        em.getTransaction().begin();
        em.remove(em.contains(produtoVenda) ? produtoVenda :  em.merge(produtoVenda));
        em.getTransaction().commit();
    }

    public List<ProdutosVendas> findAll(){
        return em.createQuery("select pv from produtosVendas pv", ProdutosVendas.class)
                .getResultList();
    }

    public List<ProdutosVendas> findByProduto(int idProduto){
        return em.createQuery("select pv from produtosVendas pv where pv.produto.idProduto = :id")
                .setParameter("id", idProduto)
                .getResultList();
    }

    public List<ProdutosVendas> findByVenda(int idVenda){
        return em.createQuery("select pv from produtosVendas pv where pv.produto.idProduto = :id")
                .setParameter("id", idVenda)
                .getResultList();
    }


    public boolean exists(ProdutosVendas produtoVenda){
        return em.contains(produtoVenda);
    }
}
