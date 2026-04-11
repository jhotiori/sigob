package org.javapi.sigob.cli;

public class MenuCadastros extends Menu {

    public MenuCadastros() {
        super("Operações de Cadastro");
        adicionarEntrada("Acessos", () -> new MenuAcesso().exibir());
        adicionarEntrada("Categorias", () -> new MenuCategoria().exibir());
        adicionarEntrada("Clientes", () -> new MenuCliente().exibir());
        adicionarEntrada("Funcionários", () -> new MenuFuncionario().exibir());
        adicionarEntrada("Moedas", () -> new MenuMoeda().exibir());
        adicionarEntrada("Produtos", () -> new MenuProduto().exibir());
        adicionarEntrada("Estoques", () -> new MenuEstoque().exibir());
    }
}
