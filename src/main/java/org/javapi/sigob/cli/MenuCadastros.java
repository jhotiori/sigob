package org.javapi.sigob.cli;

public class MenuCadastros extends Menu {

    public MenuCadastros() {
        this.title = "Cadastros";
        addEntry("Acessos",      () -> new MenuAcesso().show());
        addEntry("Categorias",   () -> new MenuCategoria().show());
        addEntry("Clientes",     () -> new MenuCliente().show());
        addEntry("Funcionários", () -> new MenuFuncionario().show());
        addEntry("Moedas",       () -> new MenuMoeda().show());
        addEntry("Produtos",     () -> new MenuProduto().show());
        addEntry("Estoques",     () -> new MenuEstoque().show());
    }
}