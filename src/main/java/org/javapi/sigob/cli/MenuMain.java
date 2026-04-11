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
        adicionarEntrada("Módulo de Administração", this::buildMenuAdmin);
        adicionarEntrada("Módulo de Estoque", this::buildMenuEstoque);
        adicionarEntrada("Módulo de Vendas", this::buildMenuVendas);
    }

    @Override
    public void exibir() {
        Funcionario funcionario = logar();
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
                    Funcionario funcionario = service.findByCodigo(senha); // cdFuncionario = senha

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
        adicionarEntrada("Estoques", () -> new MenuEstoques().exibir());
        adicionarEntrada("Vendas", () -> new MenuVendas().exibir());
    }

    private void buildMenuEstoque() {
        adicionarEntrada("Estoques", () -> new MenuEstoques().exibir());
    }

    private void buildMenuVendas() {
        adicionarEntrada("Vendas", () -> new MenuVendas().exibir());
    }
}
