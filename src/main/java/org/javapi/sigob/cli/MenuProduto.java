package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.repository.CategoriaRepository;
import org.javapi.sigob.repository.MoedaRepository;
import org.javapi.sigob.repository.ProdutoRepository;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.service.MoedaService;
import org.javapi.sigob.service.ProdutoService;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.util.Inputter;

import java.math.BigDecimal;

public class MenuProduto extends Menu {

    public MenuProduto() {
        super("Produtos");
        adicionarEntrada("Cadastrar produto",   this::cadastrar);
        adicionarEntrada("Atualizar produto",   this::atualizar);
        adicionarEntrada("Buscar por ID",       this::buscarPorId);
        adicionarEntrada("Buscar por nome",     this::buscarPorNome);
        adicionarEntrada("Listar todos",        this::listarTodos);
        adicionarEntrada("Excluir produto",     this::excluir);
    }

    private ProdutoService getService(EntityManager em) {
        return new ProdutoService(new ProdutoRepository(em));
    }

    private void cadastrar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String cd      = Inputter.lerString("Código       : ");
            String nm      = Inputter.lerString("Nome         : ");
            String ds      = Inputter.lerString("Descrição    : ");
            BigDecimal vlCusto  = Inputter.lerBigDecimal("Custo        : ");
            BigDecimal vlVenda  = Inputter.lerBigDecimal("Preço venda  : ");
            int idCategoria     = Inputter.lerInt("ID Categoria : ");
            int idMoeda         = Inputter.lerInt("ID Moeda     : ");

            Categoria cat = new CategoriaService(new CategoriaRepository(em)).findById(idCategoria);
            if (cat == null) { System.out.println("✗ Categoria não encontrada."); return; }

            Moeda moe = new MoedaService(new MoedaRepository(em)).getById(idMoeda);
            if (moe == null) { System.out.println("✗ Moeda não encontrada."); return; }

            Produto p = new Produto(0, cd, nm, ds, vlCusto, vlVenda, cat, moe);
            getService(em).save(p);
            System.out.println("✔ Produto cadastrado!");
        } finally {
            em.close();
        }
    }

    private void atualizar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int    id      = Inputter.lerInt("ID do produto : ");
            String cd      = Inputter.lerString("Novo código   : ");
            String nm      = Inputter.lerString("Novo nome     : ");
            String ds      = Inputter.lerString("Nova descrição: ");
            BigDecimal vlCusto = Inputter.lerBigDecimal("Novo custo    : ");
            BigDecimal vlVenda = Inputter.lerBigDecimal("Novo preço    : ");
            int idCategoria    = Inputter.lerInt("ID Categoria  : ");
            int idMoeda        = Inputter.lerInt("ID Moeda      : ");

            Categoria cat = new CategoriaService(new CategoriaRepository(em)).findById(idCategoria);
            if (cat == null) { System.out.println("✗ Categoria não encontrada."); return; }

            Moeda moe = new MoedaService(new MoedaRepository(em)).getById(idMoeda);
            if (moe == null) { System.out.println("✗ Moeda não encontrada."); return; }

            Produto p = new Produto(id, cd, nm, ds, vlCusto, vlVenda, cat, moe);
            getService(em).update(p);
            System.out.println("✔ Produto atualizado!");
        } finally {
            em.close();
        }
    }

    private void buscarPorId() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = Inputter.lerInt("ID do produto: ");
            Produto p = getService(em).findById(id);
            System.out.println(p != null ? p : "✗ Não encontrado.");
        } finally {
            em.close();
        }
    }

    private void buscarPorNome() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String nm = Inputter.lerString("Nome (prefixo): ");
            getService(em).findByNome(nm).forEach(System.out::println);
        } finally {
            em.close();
        }
    }

    private void listarTodos() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            getService(em).findAll().forEach(System.out::println);
        } finally {
            em.close();
        }
    }

    private void excluir() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = Inputter.lerInt("ID do produto a excluir: ");
            Produto p = getService(em).findById(id);
            if (p == null) { System.out.println("✗ Não encontrado."); return; }
            getService(em).delete(p);
            System.out.println("✔ Produto excluído!");
        } finally {
            em.close();
        }
    }
}
