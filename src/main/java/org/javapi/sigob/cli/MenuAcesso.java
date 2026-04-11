package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.repository.AcessoRepository;
import org.javapi.sigob.service.AcessoService;

import jakarta.persistence.EntityManager;

public class MenuAcesso extends Menu {

    public MenuAcesso() {
        super("Acessos");
        adicionarEntrada("Cadastrar acesso",  this::cadastrar);
        adicionarEntrada("Atualizar acesso",  this::atualizar);
        adicionarEntrada("Buscar por ID",     this::buscarPorId);
        adicionarEntrada("Buscar por nome",   this::buscarPorNome);
        adicionarEntrada("Buscar por código", this::buscarPorCodigo);
        adicionarEntrada("Listar todos",      this::listarTodos);
        adicionarEntrada("Excluir acesso",    this::excluir);
    }

    private AcessoService getService(EntityManager em) {
        return new AcessoService(new AcessoRepository(em));
    }

    private void cadastrar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String nm = lerString("Nome do acesso : ");
            String cd = lerString("Código         : ");
            String ds = lerString("Descrição      : ");

            Acesso acesso = new Acesso(0, nm, cd, ds);
            getService(em).save(acesso, em);
            System.out.println("✔ Acesso cadastrado com sucesso!");
        } finally {
            em.close();
        }
    }

    private void atualizar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id    = lerInt("ID do acesso   : ");
            String nm = lerString("Novo nome      : ");
            String cd = lerString("Novo código    : ");
            String ds = lerString("Nova descrição : ");

            Acesso acesso = new Acesso(id, nm, cd, ds);
            getService(em).update(acesso);
            System.out.println("✔ Acesso atualizado!");
        } finally {
            em.close();
        }
    }

    private void buscarPorId() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = lerInt("ID do acesso: ");
            Acesso a = getService(em).findById(id);
            System.out.println(a != null ? a : "✗ Não encontrado.");
        } finally {
            em.close();
        }
    }

    private void buscarPorNome() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String nm = lerString("Nome (prefixo): ");
            getService(em).findByNome(nm).forEach(System.out::println);
        } finally {
            em.close();
        }
    }

    private void buscarPorCodigo() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String cd = lerString("Código: ");
            Acesso a = getService(em).findByCodigo(cd);
            System.out.println(a != null ? a : "✗ Não encontrado.");
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
            int id = lerInt("ID do acesso a excluir: ");
            Acesso a = getService(em).findById(id);
            if (a == null) { System.out.println("✗ Não encontrado."); return; }
            getService(em).delete(a);
            System.out.println("✔ Acesso excluído!");
        } finally {
            em.close();
        }
    }
}
