package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.exception.ProdutosEstoquesException;
import org.javapi.sigob.repository.EstoqueRepository;
import org.javapi.sigob.repository.ProdutoRepository;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.service.ProdutosEstoquesService;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.util.Inputter;

import java.util.List;

public class MenuEstoques extends Menu {
    public MenuEstoques() {
        super("Estoque");
        adicionarEntrada("Listar itens por Estoque", this::listarPorEstoque);
        adicionarEntrada("Adicionar item ao Estoque", this::adicionarItem);
        adicionarEntrada("Transferir entre Estoques", this::transferir);
    }

    private ProdutosEstoquesService getService(EntityManager em) {
        return new ProdutosEstoquesService(new ProdutosEstoquesRepository(em));
    }

    private void listarPorEstoque() {
        EntityManager em = JPAConfig.getEntityManager();
        try {

            System.out.println("\n── Estoques disponíveis ──");
            new EstoqueService(new EstoqueRepository(em))
                    .findAll()
                    .forEach(e -> System.out.printf("[%d] %s%n", e.getIdEstoque(), e.getNmEstoque()));

            int idEstoque = Inputter.lerInt("\nID do estoque: ");

            List<ProdutosEstoques> itens = getService(em).findAll()
                    .stream()
                    .filter(pe -> pe.getEstoque().getIdEstoque() == idEstoque)
                    .toList();

            if (itens.isEmpty()) {
                System.out.println("⚠  Nenhum item encontrado neste estoque.");
                return;
            }

            System.out.println("\n── Itens ──");
            itens.forEach(System.out::println);
        } finally {
            em.close();
        }
    }

    private void adicionarItem() {
        EntityManager em = JPAConfig.getEntityManager();
        try {

            System.out.println("\n── Produtos disponíveis ──");
            new ProdutoService(new ProdutoRepository(em))
                    .findAll()
                    .forEach(p -> System.out.printf("[%d] %s%n", p.getIdProduto(), p.getNmProduto()));

            int idProduto = Inputter.lerInt("\nID do produto  : ");

            System.out.println("\n── Estoques disponíveis ──");
            new EstoqueService(new EstoqueRepository(em))
                    .findAll()
                    .forEach(e -> System.out.printf("[%d] %s%n", e.getIdEstoque(), e.getNmEstoque()));

            int idEstoque = Inputter.lerInt("\nID do estoque  : ");
            int qtde = Inputter.lerInt("Quantidade     : ");
            String obs = Inputter.lerString("Observação     : ");

            Produto p = new ProdutoService(new ProdutoRepository(em)).findById(idProduto);
            if (p == null) {
                System.out.println("✗ Produto não encontrado.");
                return;
            }

            Estoque e = new EstoqueService(new EstoqueRepository(em)).findById(idEstoque);
            if (e == null) {
                System.out.println("✗ Estoque não encontrado.");
                return;
            }

            ProdutosEstoques pe = new ProdutosEstoques(0, qtde, obs, p, e);
            try {
                getService(em).save(pe);
            } catch (ProdutosEstoquesException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("✔ Item adicionado ao estoque!");
        } finally {
            em.close();
        }
    }

    private void transferir() {
        EntityManager em = JPAConfig.getEntityManager();
        try {

            System.out.println("\n── Itens em estoque ──");
            List<ProdutosEstoques> todos = getService(em).findAll();
            todos.forEach(pe -> System.out.printf(
                    "[%d] %s | Qtde: %d | Estoque: %s%n",
                    pe.getIdProdutosEstoque(),
                    pe.getProduto().getNmProduto(),
                    pe.getNrQuantidade(),
                    pe.getEstoque().getNmEstoque()));

            int idItem = Inputter.lerInt("\nID do item a transferir : ");
            ProdutosEstoques origem = null;
            try {
                origem = getService(em).findById(idItem);
            } catch (ProdutosEstoquesException e) {
                throw new RuntimeException(e);
            }
            if (origem == null) {
                System.out.println("✗ Item não encontrado.");
                return;
            }

            System.out.println("\n── Estoques destino ──");
            new EstoqueService(new EstoqueRepository(em))
                    .findAll()
                    .stream()
                    .filter(e -> e.getIdEstoque() != origem.getEstoque().getIdEstoque()) // exclui origem
                    .forEach(e -> System.out.printf("[%d] %s%n", e.getIdEstoque(), e.getNmEstoque()));

            int idEstoqueDestino = Inputter.lerInt("\nID do estoque destino: ");
            int qtdeTransferir = Inputter.lerInt("Quantidade a transferir: ");

            if (qtdeTransferir <= 0 || qtdeTransferir > origem.getNrQuantidade()) {
                System.out.println("✗ Quantidade inválida. Disponível: " + origem.getNrQuantidade());
                return;
            }

            Estoque destino = new EstoqueService(new EstoqueRepository(em)).findById(idEstoqueDestino);
            if (destino == null) {
                System.out.println("✗ Estoque destino não encontrado.");
                return;
            }

            // Deduz da origem
            origem.setNrQuantidade(origem.getNrQuantidade() - qtdeTransferir);
            if (origem.getNrQuantidade() == 0) {
                getService(em).delete(origem);
                System.out.println("ℹ  Item removido do estoque de origem (quantidade zerada).");
            } else {
                try {
                    getService(em).update(origem);
                } catch (ProdutosEstoquesException e) {
                    throw new RuntimeException(e);
                }
            }

            // Cria/adiciona no destino
            ProdutosEstoques novoDestino = new ProdutosEstoques(
                    0, qtdeTransferir, "Transferido de: " + origem.getEstoque().getNmEstoque(),
                    origem.getProduto(), destino);
            try {
                getService(em).save(novoDestino);
            } catch (ProdutosEstoquesException e) {
                throw new RuntimeException(e);
            }

            System.out.printf("✔ %d unidade(s) de '%s' transferida(s) para '%s'!%n",
                    qtdeTransferir,
                    origem.getProduto().getNmProduto(),
                    destino.getNmEstoque());
        } finally {
            em.close();
        }
    }
}
