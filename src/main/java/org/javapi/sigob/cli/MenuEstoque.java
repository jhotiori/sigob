package org.javapi.sigob.cli;

import java.util.List;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuEstoque extends Menu {
    /**
     * Inicializa o menu de estoques e registra as entradas disponíveis.
     */
    public MenuEstoque() {
        super("Estoques");
        adicionarEntrada("Cadastrar", this::cadastrar);
        adicionarEntrada("Atualizar", this::atualizar);
        adicionarEntrada("Excluir", this::excluir);
        adicionarEntrada("Listar (ID)", this::buscarPorId);
        adicionarEntrada("Listar (NOME)", this::buscarPorNome);
        adicionarEntrada("Listar (TODOS)", this::listarTodos);
    }

    private final EstoqueService service = new EstoqueService();
    private final CategoriaService categoriaService = new CategoriaService();

    /**
     * Realiza o cadastro de um novo estoque.
     */
    private void cadastrar() {
        String codigo = Inputter.lerString("Insira o Código do Estoque: ");
        String nome = Inputter.lerString("Insira o Nome do Estoque: ");
        String descricao = Inputter.lerString("Insira a Descrição do Estoque: ");
        int idCategoria = Inputter.lerInt("Insira o ID da Categoria: ");

        try {
            Categoria categoria = categoriaService.findById(idCategoria);

            if (categoria == null) {
                Logger.warn("Categoria não encontrada!");
                return;
            }

            service.save(new Estoque(0, codigo, nome, descricao, categoria));
            Logger.success("Estoque " + nome + " cadastrado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar estoque: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um estoque existente.
     */
    private void atualizar() {
        int id = Inputter.lerInt("Insira o ID do Estoque: ");

        while (service.findById(id) == null) {
            Logger.warn("Estoque não encontrado!");
            id = Inputter.lerInt("Insira o ID do Estoque: ");
        }

        String codigo = Inputter.lerString("Insira o Novo Código do Estoque: ");
        String nome = Inputter.lerString("Insira o Novo Nome do Estoque: ");
        String descricao = Inputter.lerString("Insira a Nova Descrição do Estoque: ");
        int idCategoria = Inputter.lerInt("Insira o ID da Categoria: ");

        try {
            Categoria categoria = categoriaService.findById(idCategoria);

            if (categoria == null) {
                Logger.warn("Categoria não encontrada!");
                return;
            }

            service.update(new Estoque(id, codigo, nome, descricao, categoria));
            Logger.success("Estoque " + id + " atualizado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    /**
     * Busca um estoque pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.lerInt("Insira o ID do Estoque: ");

        try {
            Estoque estoque = service.findById(id);
            if (estoque == null) {
                Logger.warn("Estoque não encontrado!");
            } else {
                System.out.println(estoque);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar estoque: " + e.getMessage());
        }
    }

    /**
     * Busca estoques pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.lerString("Insira o Nome do Estoque: ");

        try {
            List<Estoque> estoques = service.findByNome(nome);

            if (estoques.isEmpty()) {
                Logger.warn("Estoque não encontrado!");
            } else {
                for (Estoque e : estoques) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar estoque: " + e.getMessage());
        }
    }

    /**
     * Lista todos os estoques cadastrados.
     */
    private void listarTodos() {
        try {
            List<Estoque> estoques = service.findAll();

            if (estoques.isEmpty()) {
                Logger.warn("Nenhum estoque cadastrado!");
            } else {
                for (Estoque e : estoques) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao listar estoques: " + e.getMessage());
        }
    }

    /**
     * Remove um estoque pelo ID.
     */
    private void excluir() {
        int id = Inputter.lerInt("Insira o ID do Estoque: ");

        try {
            Estoque estoque = service.findById(id);

            if (estoque == null) {
                Logger.warn("Estoque não encontrado!");
            } else {
                service.delete(estoque);
                Logger.success("Estoque " + id + " excluido com sucesso!");
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir estoque: " + e.getMessage());
        }
    }
}
