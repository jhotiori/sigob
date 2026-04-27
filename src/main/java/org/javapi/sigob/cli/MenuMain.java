package org.javapi.sigob.cli;

import java.util.Optional;

import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.service.ItemVendaService;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

/**
 * Menu principal responsável por autenticação e roteamento por nível de acesso.
 */
public class MenuMain extends Menu {

    private final FuncionarioService service;

    public MenuMain(FuncionarioService service) {
        super("SIGOB - Seja bem-vindo(a)!");
        this.service = service;
    }

    /**
     * Ponto de entrada do sistema.
     */
    public void start() {
        Funcionario funcionario = logar();

        clear();
        buildMenuByRole(funcionario);

        setTitle("SIGOB - Olá, %s!".formatted(funcionario.getNome()));
        show();
    }

    /**
     * Realiza o login do funcionário.
     */
    private Funcionario logar() {
        Logger.info("(<< SIGOB - LOGIN >>)");

        while (true) {
            try {
                String login = Inputter.readString("Login (Nome): ");
                String senha = Inputter.readString("Senha (Código): ");

                Optional<Funcionario> funcionario = service.findByCodigo(senha);

                if (funcionario.isPresent() && funcionario.get().getNome().equalsIgnoreCase(login)) {
                    Logger.success("Login efetuado com sucesso!");
                    return funcionario.get();
                }

                Logger.warn("Login ou senha incorretos!");
            } catch (Exception e) {
                Logger.error("Erro no login: " + e.getMessage());
            }
        }
    }

    /**
     * Constrói o menu com base nos acessos do funcionário.
     */
    private void buildMenuByRole(Funcionario funcionario) {

        if (funcionario.hasAcesso("ADMIN")) {
            buildMenuAdmin();
            return;
        }

        if (funcionario.hasAcesso("ESTOQUE")) {
            buildMenuEstoque();
            return;
        }

        if (funcionario.hasAcesso("VENDAS")) {
            buildMenuVendas();
            return;
        }

        Logger.warn("Funcionário não possui acessos válidos!");
    }

    private void buildMenuAdmin() {
        add("Cadastros", () -> new MenuCadastros().show());
        add("Estoques", () -> new MenuEstoques(new ProdutosEstoquesService(), new ProdutoService(), new EstoqueService()).show());
        add("Vendas", () -> new MenuVendas(new VendaService(), new ItemVendaService(), new ProdutosEstoquesService(), new ClienteService(), new FuncionarioService()).show());
    }

    private void buildMenuEstoque() {
        add("Estoques", () -> new MenuEstoques(new ProdutosEstoquesService(), new ProdutoService(), new EstoqueService()).show());
    }

    private void buildMenuVendas() {
        add("Vendas", () -> new MenuVendas(new VendaService(), new ItemVendaService(), new ProdutosEstoquesService(), new ClienteService(), new FuncionarioService()).show());
    }
}
