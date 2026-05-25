package org.javapi.sigob.cli;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.service.MoedaService;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Menu responsável pelas operações de produto via CLI.
 */
public class MenuProduto extends Menu {

    private final ProdutoService service;
    private final CategoriaService categoriaService;
    private final MoedaService moedaService;

    public MenuProduto(ProdutoService service, CategoriaService categoriaService, MoedaService moedaService) {
        super("Operações de Produtos");

        add("Cadastrar", this::cadastrar);
        add("Atualizar", this::atualizar);
        add("Excluir", this::excluir);
        add("Buscar (ID)", this::buscarPorId);
        add("Buscar (NOME)", this::buscarPorNome);
        add("Listar (TODOS)", this::listarTodos);

        this.service = service;
        this.categoriaService = categoriaService;
        this.moedaService = moedaService;
    }

    /**
     * Realiza o cadastro de um novo produto.
     */
    private void cadastrar() {
        String codigo = Inputter.readString("Código do Produto: ");
        String nome = Inputter.readString("Nome do Produto: ");

        BigDecimal custo = Inputter.readBigDecimal("Valor de Compra: ");
        BigDecimal venda = Inputter.readBigDecimal("Valor de Venda: ");

        Categoria categoria = resolveCategoria();
        Moeda moeda = resolveMoeda();

        try {
            service.save(new Produto(0, codigo, nome, custo, venda, categoria, moeda));
            Logger.success("Produto %s cadastrado com sucesso!".formatted(nome));
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    /**
     * Atualiza um produto existente.
     */
    private void atualizar() {
        Produto atual = resolveProduto();

        String codigo = Inputter.readString("Novo Código [vazio mantém]: ");
        codigo = codigo.isBlank() ? atual.getCodigo() : codigo;

        String nome = Inputter.readString("Novo Nome [vazio mantém]: ");
        nome = nome.isBlank() ? atual.getNome() : nome;

        BigDecimal custo = Inputter.readBigDecimal("Novo Valor de Compra: ");
        BigDecimal venda = Inputter.readBigDecimal("Novo Valor de Venda: ");

        Categoria categoria = atual.getCategoria();
        if (Inputter.readBoolean("Deseja alterar Categoria? [S/N]: ")) {
            categoria = resolveCategoria();
        }

        Moeda moeda = atual.getMoeda();
        if (Inputter.readBoolean("Deseja alterar Moeda? [S/N]: ")) {
            moeda = resolveMoeda();
        }

        try {
            service.update(new Produto(
                    atual.getId(),
                    codigo,
                    nome,
                    custo,
                    venda,
                    categoria,
                    moeda
            ));

            Logger.success("Produto %d atualizado com sucesso!".formatted(atual.getId()));
        } catch (Exception e) {
            Logger.error("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    /**
     * Busca um produto pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.readInt("ID do Produto: ");

        try {
            Optional<Produto> produto = service.findById(id);

            if (produto.isEmpty()) {
                Logger.warn("Produto não encontrado!");
            } else {
                System.out.println(produto.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar produto: " + e.getMessage());
        }
    }

    /**
     * Busca produtos pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.readString("Nome do Produto: ");

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
     * Lista todos os produtos.
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
        Produto produto = resolveProduto();

        try {
            service.delete(produto);
            Logger.success("Produto %d excluído com sucesso!".formatted(produto.getId()));
        } catch (Exception e) {
            Logger.error("Erro ao excluir produto: " + e.getMessage());
        }
    }

    /**
     * Resolve um produto pelo ID.
     *
     * @return Produto - O Produto resolvido
     */
    private Produto resolveProduto() {
        int id = Inputter.readInt("ID do Produto: ");
        Optional<Produto> produto = service.findById(id);

        while (produto.isEmpty()) {
            Logger.warn("Produto não encontrado!");
            id = Inputter.readInt("ID do Produto: ");
            produto = service.findById(id);
        }

        return produto.get();
    }

    /**
     * Resolve uma categoria pelo ID.
     *
     * @return Categoria - A Categoria resolvida
     */
    private Categoria resolveCategoria() {
        int id = Inputter.readInt("ID da Categoria: ");
        Optional<Categoria> categoria = categoriaService.findById(id);

        while (categoria.isEmpty()) {
            Logger.warn("Categoria não encontrada!");
            id = Inputter.readInt("ID da Categoria: ");
            categoria = categoriaService.findById(id);
        }

        return categoria.get();
    }

    /**
     * Resolve uma moeda pelo ID.
     *
     * @return Moeda - A Moeda resolvida
     */
    private Moeda resolveMoeda() {
        int id = Inputter.readInt("ID da Moeda: ");
        Optional<Moeda> moeda = moedaService.findById(id);

        while (moeda.isEmpty()) {
            Logger.warn("Moeda não encontrada!");
            id = Inputter.readInt("ID da Moeda: ");
            moeda = moedaService.findById(id);
        }

        return moeda.get();
    }
}
