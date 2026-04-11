package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.repository.MoedaRepository;
import org.javapi.sigob.service.MoedaService;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.util.Inputter;

public class MenuMoeda extends Menu {

    public MenuMoeda() {
        super("Moedas");
        adicionarEntrada("Cadastrar moeda",   this::cadastrar);
        adicionarEntrada("Atualizar moeda",   this::atualizar);
        adicionarEntrada("Buscar por ID",     this::buscarPorId);
        adicionarEntrada("Buscar por nome",   this::buscarPorNome);
        adicionarEntrada("Buscar por código", this::buscarPorCodigo);
        adicionarEntrada("Listar todas",      this::listarTodas);
        adicionarEntrada("Excluir moeda",     this::excluir);
    }

    private MoedaService getService(EntityManager em) {
        return new MoedaService(new MoedaRepository(em));
    }

    private void cadastrar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String nm     = Inputter.lerString("Nome   : ");
            String cifrao = Inputter.lerString("Símbolo (ex: R$) : ");
            String sigla  = Inputter.lerString("Sigla   (ex: BRL): ");

            Moeda m = new Moeda(0, nm, cifrao, sigla);
            getService(em).save(m, em);
            System.out.println("✔ Moeda cadastrada!");
        } finally {
            em.close();
        }
    }

    private void atualizar() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int    id     = Inputter.lerInt("ID da moeda : ");
            String nm     = Inputter.lerString("Novo nome   : ");
            String cifrao = Inputter.lerString("Novo símbolo: ");
            String sigla  = Inputter.lerString("Nova sigla  : ");

            Moeda m = new Moeda(id, nm, cifrao, sigla);
            getService(em).update(m);
            System.out.println("✔ Moeda atualizada!");
        } finally {
            em.close();
        }
    }

    private void buscarPorId() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = Inputter.lerInt("ID da moeda: ");
            Moeda m = getService(em).getById(id);
            System.out.println(m != null ? m : "✗ Não encontrada.");
        } finally {
            em.close();
        }
    }

    private void buscarPorNome() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String nm = Inputter.lerString("Nome (prefixo): ");
            getService(em).getByNome(nm).forEach(System.out::println);
        } finally {
            em.close();
        }
    }

    private void buscarPorCodigo() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String cd = Inputter.lerString("Código: ");
            Moeda m = getService(em).getByCodigo(cd);
            System.out.println(m != null ? m : "✗ Não encontrada.");
        } finally {
            em.close();
        }
    }

    private void listarTodas() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            getService(em).getAll().forEach(System.out::println);
        } finally {
            em.close();
        }
    }

    private void excluir() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            int id = Inputter.lerInt("ID da moeda a excluir: ");
            Moeda m = getService(em).getById(id);
            if (m == null) { System.out.println("✗ Não encontrada."); return; }
            getService(em).delete(m);
            System.out.println("✔ Moeda excluída!");
        } finally {
            em.close();
        }
    }
}
