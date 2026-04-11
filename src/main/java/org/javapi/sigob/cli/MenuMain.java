package org.javapi.sigob.cli;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.repository.FuncionarioRepository;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.util.Inputter;

import jakarta.persistence.EntityManager;

public class MenuMain extends Menu {

    public MenuMain() {
        super("SIGOB - Seja bem-vindo(a)!");
    }

    @Override
    public void exibir() {
        Funcionario funcionario = logar();
        String cdAcesso = funcionario.getAcesso().getCdAcesso();

        limparEntradas(); // evita duplicar entradas a cada login

        switch (cdAcesso.toUpperCase()) {
            case "ADMIN"   -> buildMenuAdmin();
            case "ESTOQUE" -> buildMenuEstoque();
            case "VENDAS"  -> buildMenuVendas();
            default -> {
                System.out.println("✗ Nível de acesso não reconhecido: " + cdAcesso);
                return;
            }
        }

        this.setTitulo("SIGOB - Olá, %s!".formatted(funcionario.getNmFuncionario()));
        super.exibir();
    }

    private Funcionario logar() {
        System.out.println("<< SIGOB - LOGIN SHELL >>");

        while (true) {
            try {
                String login = Inputter.lerString("Insira o Login (nome): ");
                String senha = Inputter.lerString("Insira a Senha: ");
                EntityManager em = JPAConfig.getEntityManager();

                try {
                    FuncionarioService service = new FuncionarioService(new FuncionarioRepository(em));
                    Funcionario funcionario = service.findByCodigo(senha);

                    if (funcionario != null && funcionario.getNmFuncionario().equalsIgnoreCase(login)) {
                        System.out.println("[✓] Login efetuado com sucesso - logado como %s!"
                                .formatted(funcionario.getNmFuncionario()));
                        return funcionario;
                    }

                    System.out.println("[✗] Login ou senha incorretos. Tente novamente.\n");
                } finally {
                    em.close();
                }
            } catch (Exception e) {
                System.out.println("[✗] Erro: " + e.getMessage() + "\n");
            }
        }
    }

    private void buildMenuAdmin() {
        adicionarEntrada("Cadastros", () -> new MenuCadastros().exibir());
        adicionarEntrada("Estoques",  () -> new MenuEstoques().exibir());
        adicionarEntrada("Vendas",    () -> new MenuVendas().exibir());
    }

    private void buildMenuEstoque() {
        adicionarEntrada("Estoques", () -> new MenuEstoques().exibir());
    }

    private void buildMenuVendas() {
        adicionarEntrada("Vendas", () -> new MenuVendas().exibir());
    }
}