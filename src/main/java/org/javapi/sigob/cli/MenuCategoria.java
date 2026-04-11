package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.repository.CategoriaRepository;
import org.javapi.sigob.service.CategoriaService;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.util.Inputter;

public class MenuCategoria extends Menu {

    public MenuCategoria() {
        super("Categorias");
        adicionarEntrada("Cadastrar categoria",  this::cadastrar);
        adicionarEntrada("Atualizar categoria",  this::atualizar);
        adicionarEntrada("Buscar por ID",        this::buscarPorId);
        adicionarEntrada("Buscar por nome",      this::buscarPorNome);
        adicionarEntrada("Buscar por código",    this::buscarPorCodigo);
        adicionarEntrada("Listar todas",         this::listarTodas);
        adicionarEntrada("Excluir categoria",    this::excluir);
    }

    private CategoriaService getService(EntityManager em) {
        return new CategoriaService(new CategoriaRepository(em));
    }

    private void cadastrar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String cd = Inputter.lerString("Código   : ");
            String nm = Inputter.lerString("Nome     : ");

            Categoria cat = new Categoria(0, cd, nm);
            getService(em).save(cat);
            System.out.println("✔ Categoria cadastrada!");
        } finally {
            em.close();
        }
    }

    private void atualizar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int    id = Inputter.lerInt("ID da categoria : ");
            String cd = Inputter.lerString("Novo código     : ");
            String nm = Inputter.lerString("Novo nome       : ");

            Categoria cat = new Categoria(id, cd, nm);
            getService(em).update(cat);
            System.out.println("✔ Categoria atualizada!");
        } finally {
            em.close();
        }
    }

    private void buscarPorId() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = Inputter.lerInt("ID da categoria: ");
            Categoria c = getService(em).findById(id);
            System.out.println(c != null ? c : "✗ Não encontrada.");
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

    private void buscarPorCodigo() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String cd = Inputter.lerString("Código: ");
            Categoria c = getService(em).findByCodigo(cd);
            System.out.println(c != null ? c : "✗ Não encontrada.");
        } finally {
            em.close();
        }
    }

    private void listarTodas() {
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
            int id = Inputter.lerInt("ID da categoria a excluir: ");
            Categoria c = getService(em).findById(id);
            if (c == null) { System.out.println("✗ Não encontrada."); return; }
            getService(em).delete(c);
            System.out.println("✔ Categoria excluída!");
        } finally {
            em.close();
        }
    }
}
