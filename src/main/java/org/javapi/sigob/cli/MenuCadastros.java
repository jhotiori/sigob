package org.javapi.sigob.cli;

public class MenuCadastros extends Menu {

    public MenuCadastros() {
        super("Operações de Cadastro");
        adicionarEntrada("Acessos", () -> new MenuAcesso().exibir());
        adicionarEntrada("Categorias", () -> new MenuCategoria().show());
        adicionarEntrada("Clientes", () -> new MenuCliente().show());
        adicionarEntrada("Funcionários", () -> new MenuFuncionario().show());
        adicionarEntrada("Moedas", () -> new MenuMoeda().show());
        adicionarEntrada("Produtos", () -> new MenuProduto().show());
        adicionarEntrada("Estoques", () -> new MenuEstoque().show());
    }
}
