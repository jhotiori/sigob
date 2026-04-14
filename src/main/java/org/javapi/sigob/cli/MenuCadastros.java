package org.javapi.sigob.cli;

public class MenuCadastros extends Menu {
    public MenuCadastros() {
        super("Operações de Cadastro");
        adicionarEntrada("Cadastrar Acessos", () -> new MenuAcesso().exibir());
        adicionarEntrada("Cadastrar Categorias", () -> new MenuCategoria().exibir());
        adicionarEntrada("Cadastrar Clientes", () -> new MenuCliente().exibir());
        adicionarEntrada("Cadastrar Funcionários", () -> new MenuFuncionario().exibir());
        adicionarEntrada("Cadastrar Moedas", () -> new MenuMoeda().exibir());
        adicionarEntrada("Cadastrar Produtos", () -> new MenuProduto().exibir());
        adicionarEntrada("Cadastrar Estoques", () -> new MenuEstoque().exibir());
    }
}
