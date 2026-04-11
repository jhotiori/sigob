package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.repository.CategoriaRepository;
import org.javapi.sigob.repository.EstoqueRepository;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.service.EstoqueService;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.util.Inputter;

public class MenuEstoque extends Menu {

    public MenuEstoque() {
        super("Estoques");
        adicionarEntrada("Cadastrar estoque",  this::cadastrar);
        adicionarEntrada("Atualizar estoque",  this::atualizar);
        adicionarEntrada("Buscar por ID",      this::buscarPorId);
        adicionarEntrada("Buscar por nome",    this::buscarPorNome);
        adicionarEntrada("Listar todos",       this::listarTodos);
        adicionarEntrada("Excluir estoque",    this::excluir);
    }

    private EstoqueService getService(EntityManager em) {
        return new EstoqueService(new EstoqueRepository(em));
    }

    private void cadastrar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String cd = Inputter.lerString("Código         : ");
            String nm = Inputter.lerString("Nome           : ");
            String ds = Inputter.lerString("Descrição      : ");
            int idCat = Inputter.lerInt("ID Categoria   : ");

            Categoria cat = new CategoriaService(new CategoriaRepository(em)).findById(idCat);
            if (cat == null) { System.out.println("✗ Categoria não encontrada."); return; }

            Estoque e = new Estoque(0, cd, nm, ds, cat);
            getService(em).save(e);
            System.out.println("✔ Estoque cadastrado!");
        } finally {
            em.close();
        }
    }

    private void atualizar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int    id = Inputter.lerInt("ID do estoque  : ");
            String cd = Inputter.lerString("Novo código    : ");
            String nm = Inputter.lerString("Novo nome      : ");
            String ds = Inputter.lerString("Nova descrição : ");
            int idCat = Inputter.lerInt("ID Categoria   : ");

            Categoria cat = new CategoriaService(new CategoriaRepository(em)).findById(idCat);
            if (cat == null) { System.out.println("✗ Categoria não encontrada."); return; }

            Estoque e = new Estoque(id, cd, nm, ds, cat);
            getService(em).update(e);
            System.out.println("✔ Estoque atualizado!");
        } finally {
            em.close();
        }
    }

    private void buscarPorId() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = Inputter.lerInt("ID do estoque: ");
            Estoque e = getService(em).findById(id);
            System.out.println(e != null ? e : "✗ Não encontrado.");
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
            int id = Inputter.lerInt("ID do estoque a excluir: ");
            Estoque e = getService(em).findById(id);
            if (e == null) { System.out.println("✗ Não encontrado."); return; }
            getService(em).delete(e);
            System.out.println("✔ Estoque excluído!");
        } finally {
            em.close();
        }
    }
}
