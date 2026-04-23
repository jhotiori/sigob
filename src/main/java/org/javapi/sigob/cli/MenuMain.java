package org.javapi.sigob.cli;

import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuMain extends Menu {

    /**
     * Inicializa o menu principal.
     */
    public MenuMain() {
        super("SIGOB - Seja bem-vindo(a)!");
    }

    private final FuncionarioService service = new FuncionarioService();

    @Override
    public void exibir() {
        Funcionario funcionario = logar();
        String cdAcesso = funcionario.getAcesso().getCdAcesso();
        limparEntradas();

        switch (cdAcesso.toUpperCase()) {
            case "ADMIN" -> buildMenuAdmin();
            case "ESTOQUE" -> buildMenuEstoque();
            case "VENDAS" -> buildMenuVendas();
            default -> {
                Logger.warn("Nível de acesso não reconhecido: " + cdAcesso);
                return;
            }
        }

        setTitulo("SIGOB - Olá, " + funcionario.getNmFuncionario() + "!");
        super.exibir();
    }

    /**
     * Realiza o login do funcionário.
     *
     * @return funcionário autenticado
     */
    private Funcionario logar() {
        Logger.info("<< SIGOB - LOGIN >>");

        while (true) {
            try {
                String login = Inputter.lerString("Insira o Login: ");
                String senha = Inputter.lerString("Insira a Senha: ");

                Funcionario funcionario = service.findByCodigo(senha);

                if (funcionario != null && funcionario.getNmFuncionario().equalsIgnoreCase(login)) {
                    Logger.success("Login efetuado com sucesso!");
                    return funcionario;
                }
                Logger.warn("Login ou senha incorretos!");
            } catch (Exception e) {
                Logger.error("Erro no login: " + e.getMessage());
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
