package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.repository.ClienteRepository;
import org.javapi.sigob.service.ClienteService;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.util.Inputter;

import java.util.List;

public class MenuCliente extends Menu {

    public MenuCliente() {
        super("Clientes");
        adicionarEntrada("Cadastrar cliente",   this::cadastrar);
        adicionarEntrada("Atualizar cliente",   this::atualizar);
        adicionarEntrada("Buscar por ID",       this::buscarPorId);
        adicionarEntrada("Buscar por nome",     this::buscarPorNome);
        adicionarEntrada("Buscar por documento",this::buscarPorDoc);
        adicionarEntrada("Listar todos",        this::listarTodos);
        adicionarEntrada("Excluir cliente",     this::excluir);
    }

    private ClienteService getService(EntityManager em) {
        return new ClienteService(new ClienteRepository(em));
    }

    private void cadastrar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String nm  = Inputter.lerString("Nome      : ");
            String doc = Inputter.lerString("Documento : ");

            Cliente c = new Cliente(0, nm, doc);
            getService(em).save(c);
            System.out.println("✔ Cliente cadastrado!");
        } finally {
            em.close();
        }
    }

    private void atualizar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int    id  = Inputter.lerInt("ID do cliente  : ");
            String nm  = Inputter.lerString("Novo nome      : ");
            String doc = Inputter.lerString("Novo documento : ");

            Cliente c = new Cliente(id, nm, doc);
            getService(em).save(c);
            System.out.println("✔ Cliente atualizado!");
        } finally {
            em.close();
        }
    }

    private void buscarPorId() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = Inputter.lerInt("ID do cliente: ");
            Cliente c = getService(em).findById(id);
            System.out.println(c != null ? c : "✗ Não encontrado.");
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

    private void buscarPorDoc() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String doc = Inputter.lerString("Documento: ");
            List<Cliente> c = getService(em).findByDocumento(doc);
            System.out.println(c != null ? c : "✗ Não encontrado.");
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
            int id = Inputter.lerInt("ID do cliente a excluir: ");
            Cliente c = getService(em).findById(id);
            if (c == null) { System.out.println("✗ Não encontrado."); return; }
            getService(em).delete(c);
            System.out.println("✔ Cliente excluído!");
        } finally {
            em.close();
        }
    }
}
