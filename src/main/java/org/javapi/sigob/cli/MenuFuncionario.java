package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.repository.AcessoRepository;
import org.javapi.sigob.repository.FuncionarioRepository;
import org.javapi.sigob.service.AcessoService;
import org.javapi.sigob.service.FuncionarioService;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.util.Inputter;

public class MenuFuncionario extends Menu {

    public MenuFuncionario() {
        super("Funcionários");
        adicionarEntrada("Cadastrar funcionário",   this::cadastrar);
        adicionarEntrada("Atualizar funcionário",   this::atualizar);
        adicionarEntrada("Buscar por ID",           this::buscarPorId);
        adicionarEntrada("Buscar por nome",         this::buscarPorNome);
        adicionarEntrada("Buscar por código",       this::buscarPorCodigo);
        adicionarEntrada("Listar todos",            this::listarTodos);
        adicionarEntrada("Excluir funcionário",     this::excluir);
    }

    private FuncionarioService getService(EntityManager em) {
        return new FuncionarioService(new FuncionarioRepository(em));
    }

    private void cadastrar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String nm  = Inputter.lerString("Nome              : ");
            String cd  = Inputter.lerString("Código            : ");
            int idAcesso = Inputter.lerInt("ID do Acesso      : ");

            Acesso acesso = new AcessoService(new AcessoRepository(em)).findById(idAcesso);
            if (acesso == null) { System.out.println("✗ Acesso não encontrado."); return; }

            Funcionario f = new Funcionario();
            f.setNmFuncionario(nm);
            f.setCdFuncionario(cd);
            f.setAcesso(acesso);

            getService(em).save(f);
            System.out.println("✔ Funcionário cadastrado!");
        } finally {
            em.close();
        }
    }

    private void atualizar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int    id      = Inputter.lerInt("ID do funcionário : ");
            String nm      = Inputter.lerString("Novo nome         : ");
            String cd      = Inputter.lerString("Novo código       : ");
            int    idAcesso = Inputter.lerInt("ID do Acesso      : ");

            Acesso acesso = new AcessoService(new AcessoRepository(em)).findById(idAcesso);
            if (acesso == null) { System.out.println("✗ Acesso não encontrado."); return; }

            Funcionario f = new Funcionario();
            f.setIdFuncionario(id);
            f.setNmFuncionario(nm);
            f.setCdFuncionario(cd);
            f.setAcesso(acesso);

            getService(em).update(f);
            System.out.println("✔ Funcionário atualizado!");
        } finally {
            em.close();
        }
    }

    private void buscarPorId() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = Inputter.lerInt("ID do funcionário: ");
            Funcionario f = getService(em).findById(id);
            System.out.println(f != null ? f : "✗ Não encontrado.");
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
            Funcionario f = getService(em).findByCodigo(cd);
            System.out.println(f != null ? f : "✗ Não encontrado.");
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
            int id = Inputter.lerInt("ID do funcionário a excluir: ");
            Funcionario f = getService(em).findById(id);
            if (f == null) { System.out.println("✗ Não encontrado."); return; }
            getService(em).delete(f);
            System.out.println("✔ Funcionário excluído!");
        } finally {
            em.close();
        }
    }
}
