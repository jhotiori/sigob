package org.javapi.sigob.cli;

import org.javapi.sigob.service.*;

public class MenuCadastros extends Menu {

    public MenuCadastros() {
        super("Operações de Cadastro");
        add("Cadastrar Acessos", () -> new MenuAcesso(new AcessoService()).show());
        add("Cadastrar Categorias", () -> new MenuCategoria(new CategoriaService()).show());
        add("Cadastrar Clientes", () -> new MenuCliente(new ClienteService(), new DocumentoService()).show());
        add("Cadastrar Funcionários", () -> new MenuFuncionario(new FuncionarioService(), new AcessoService(), new DocumentoService(), new ClienteService()).show());
        add("Cadastrar Moedas", () -> new MenuMoeda(new MoedaService()).show());
        add("Cadastrar Produtos", () -> new MenuProduto(new ProdutoService(), new CategoriaService(), new MoedaService()).show());
        add("Cadastrar Estoques", () -> new MenuEstoque(new EstoqueService()).show());
    }
}
