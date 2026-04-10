package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.repository.FuncionarioRepository;
import org.javapi.sigob.service.FuncionarioService;

import jakarta.persistence.EntityManager;

public class MenuMain extends Menu {

    public MenuMain() {
        this.title = "SIGOB - Login";
    }

    @Override
    public void show() {
        Funcionario funcionario = logar();
        String cdAcesso = funcionario.getAcesso().getCdAcesso();

        switch (cdAcesso) {
            case "Admin" -> buildMenuAdmin();
            case "Estoque" -> buildMenuEstoque();
            case "Vendas" -> buildMenuVendas();
            default -> {
                System.out.println("✗ Nível de acesso não reconhecido: " + cdAcesso);
                return;
            }
        }

        this.title = "SIGOB - Olá, " + funcionario.getNmFuncionario();
        super.show();
    }

    private Funcionario logar() {
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║       SIGOB - Login      ║");
        System.out.println("╚══════════════════════════╝");

        while (true) {
            try {
                String login = lerString("Login : ");
                String senha = lerString("Senha : ");

                EntityManager em = JPAConfig.getEntityManager();
                try {
                    FuncionarioService service = new FuncionarioService(new FuncionarioRepository(em));
                    Funcionario f = service.getByCodigo(senha); // cdFuncionario = senha

                    if (f != null && f.getNmFuncionario().equalsIgnoreCase(login)) {
                        System.out.println("✔ Bem-vindo, " + f.getNmFuncionario() + "!");
                        return f;
                    }

                    System.out.println("✗ Login ou senha incorretos. Tente novamente.\n");
                } finally {
                    em.close();
                }
            } catch (Exception e) {
                System.out.println("✗ Erro: " + e.getMessage() + "\n");
            }
        }
    }

    private void buildMenuAdmin() {
        addEntry("Cadastros", () -> new MenuCadastros().show());
        addEntry("Estoques",  () -> new MenuEstoques().show());
        addEntry("Vendas",    () -> new MenuVendaOps().show());
    }

    private void buildMenuEstoque() {
        addEntry("Estoques", () -> new MenuEstoqueOps().show());
    }

    private void buildMenuVendas() {
        addEntry("Vendas", () -> new MenuVendaOps().show());
    }
}