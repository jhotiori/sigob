package org.javapi.sigob.cli;

import java.math.BigDecimal;
import java.util.List;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.service.MoedaService;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuProduto extends Menu {

    /**
     * Inicializa o menu de produtos e registra as entradas disponíveis.
     */
    public MenuProduto() {
        super("Produtos");
        adicionarEntrada("Cadastrar", this::cadastrar);
        adicionarEntrada("Atualizar", this::atualizar);
        adicionarEntrada("Excluir", this::excluir);
        adicionarEntrada("Buscar (ID)", this::buscarPorId);
        adicionarEntrada("Buscar (NOME)", this::buscarPorNome);
        adicionarEntrada("Listar (TODOS)", this::listarTodos);
    }

    private final ProdutoService service = new ProdutoService();
    private final CategoriaService categoriaService = new CategoriaService();
    private final MoedaService moedaService = new MoedaService();

    /**
     * Realiza o cadastro de um novo produto.
     */
    private void cadastrar() {
        String codigo = Inputter.lerString("Insira o Código do Produto: ");
        String nome = Inputter.lerString("Insira o Nome do Produto: ");
        String descricao = Inputter.lerString("Insira a Descrição: ");
        BigDecimal custo = Inputter.lerBigDecimal("Insira o Custo: ");
        BigDecimal venda = Inputter.lerBigDecimal("Insira o Preço de Venda: ");
        int idCategoria = Inputter.lerInt("Insira o ID da Categoria: ");
        int idMoeda = Inputter.lerInt("Insira o ID da Moeda: ");

        try {
            Categoria categoria = categoriaService.findById(idCategoria);
            if (categoria == null) {
                Logger.warn("Categoria não encontrada!");
                return;
            }

            Moeda moeda = moedaService.findById(idMoeda);
            if (moeda == null) {
                Logger.warn("Moeda não encontrada!");
                return;
            }

            service.save(new Produto(0, codigo, nome, descricao, custo, venda, categoria, moeda));
            Logger.success("Produto " + nome + " cadastrado com sucesso!");

        } catch (Exception e) {
            Logger.error("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um produto existente.
     */
    private void atualizar() {
        int id = Inputter.lerInt("Insira o ID do Produto: ");

        while (service.findById(id) == null) {
            Logger.warn("Produto não encontrado!");
            id = Inputter.lerInt("Insira o ID do Produto: ");
        }

        String codigo = Inputter.lerString("Insira o Novo Código: ");
        String nome = Inputter.lerString("Insira o Novo Nome: ");
        String descricao = Inputter.lerString("Insira a Nova Descrição: ");
        BigDecimal custo = Inputter.lerBigDecimal("Insira o Novo Custo: ");
        BigDecimal venda = Inputter.lerBigDecimal("Insira o Novo Preço: ");
        int idCategoria = Inputter.lerInt("Insira o ID da Categoria: ");
        int idMoeda = Inputter.lerInt("Insira o ID da Moeda: ");

        try {
            Categoria categoria = categoriaService.findById(idCategoria);
            if (categoria == null) {
                Logger.warn("Categoria não encontrada!");
                return;
            }

            Moeda moeda = moedaService.findById(idMoeda);
            if (moeda == null) {
                Logger.warn("Moeda não encontrada!");
                return;
            }

            service.update(new Produto(id, codigo, nome, descricao, custo, venda, categoria, moeda));
            Logger.success("Produto " + id + " atualizado com sucesso!");

        } catch (Exception e) {
            Logger.error("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    /**
     * Busca um produto pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.lerInt("Insira o ID do Produto: ");

        try {
            Produto produto = service.findById(id);
            if (produto == null) {
                Logger.warn("Produto não encontrado!");
            } else {
                System.out.println(produto);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar produto: " + e.getMessage());
        }
    }

    /**
     * Busca produtos pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.lerString("Insira o Nome do Produto: ");

        try {
            List<Produto> produtos = service.findByNome(nome);

            if (produtos.isEmpty()) {
                Logger.warn("Produto não encontrado!");
            } else {
                for (Produto p : produtos) {
                    System.out.println(p);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar produto: " + e.getMessage());
        }
    }

    /**
     * Lista todos os produtos cadastrados.
     */
    private void listarTodos() {
        try {
            List<Produto> produtos = service.findAll();

            if (produtos.isEmpty()) {
                Logger.warn("Nenhum produto cadastrado!");
            } else {
                for (Produto p : produtos) {
                    System.out.println(p);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao listar produtos: " + e.getMessage());
        }
    }

    /**
     * Remove um produto pelo ID.
     */
    private void excluir() {
        int id = Inputter.lerInt("Insira o ID do Produto: ");

        try {
            Produto produto = service.findById(id);

            if (produto == null) {
                Logger.warn("Produto não encontrado!");
            } else {
                service.delete(produto);
                Logger.success("Produto " + id + " excluído com sucesso!");
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir produto: " + e.getMessage());
        }
    }
}
