package org.javapi.sigob.cli;

import java.util.List;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuCategoria extends Menu {
    /**
     * Inicializa o menu de categorias e registra as entradas disponíveis.
     */
    public MenuCategoria() {
        super("Categorias");
        adicionarEntrada("Cadastrar", this::cadastrar);
        adicionarEntrada("Atualizar", this::atualizar);
        adicionarEntrada("Excluir", this::excluir);
        adicionarEntrada("Listar (ID)", this::buscarPorId);
        adicionarEntrada("Listar (NOME)", this::buscarPorNome);
        adicionarEntrada("Listar (CODIGO)", this::buscarPorCodigo);
        adicionarEntrada("Listar (TODOS)", this::listarTodas);
    }

    private final CategoriaService service = new CategoriaService();

    /**
     * Realiza o cadastro de uma nova categoria.
     */
    private void cadastrar() {
        String codigo = Inputter.lerString("Insira o Código da Categoria: ");
        String nome = Inputter.lerString("Insira o Nome da Categoria: ");

        try {
            service.save(new Categoria(0, codigo, nome));
            Logger.success("Categoria " + nome + " cadastrada com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar categoria: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma categoria existente.
     */
    private void atualizar() {
        int id = Inputter.lerInt("Insira o ID da Categoria: ");

        while (service.findById(id) == null) {
            Logger.warn("Categoria não encontrada!");
            id = Inputter.lerInt("Insira o ID da Categoria: ");
        }

        String codigo = Inputter.lerString("Insira o Novo Código da Categoria: ");
        String nome = Inputter.lerString("Insira o Novo Nome da Categoria: ");

        try {
            service.update(new Categoria(id, codigo, nome));
            Logger.success("Categoria " + id + " atualizada com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    /**
     * Busca uma categoria pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.lerInt("Insira o ID da Categoria: ");

        try {
            Categoria categoria = service.findById(id);
            if (categoria == null) {
                Logger.warn("Categoria não encontrada!");
            } else {
                System.out.println(categoria);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar categoria: " + e.getMessage());
        }
    }

    /**
     * Busca categorias pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.lerString("Insira o Nome da Categoria: ");

        try {
            List<Categoria> categorias = service.findByNome(nome);

            if (categorias.isEmpty()) {
                Logger.warn("Categoria não encontrada!");
            } else {
                for (Categoria c : categorias) {
                    System.out.println(c);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar categoria: " + e.getMessage());
        }
    }

    /**
     * Busca uma categoria pelo código.
     */
    private void buscarPorCodigo() {
        String codigo = Inputter.lerString("Insira o Código da Categoria: ");

        try {
            Categoria categoria = service.findByCodigo(codigo);
            if (categoria == null) {
                Logger.warn("Categoria não encontrada!");
            } else {
                System.out.println(categoria);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar categoria: " + e.getMessage());
        }
    }

    /**
     * Lista todas as categorias cadastradas.
     */
    private void listarTodas() {
        try {
            List<Categoria> categorias = service.findAll();

            if (categorias.isEmpty()) {
                Logger.warn("Nenhuma categoria cadastrada!");
            } else {
                for (Categoria c : categorias) {
                    System.out.println(c);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao listar categorias: " + e.getMessage());
        }
    }

    /**
     * Remove uma categoria pelo ID.
     */
    private void excluir() {
        int id = Inputter.lerInt("Insira o ID da Categoria: ");

        try {
            Categoria categoria = service.findById(id);

            if (categoria == null) {
                Logger.warn("Categoria não encontrada!");
            } else {
                service.delete(categoria);
                Logger.success("Categoria " + id + " excluida com sucesso!");
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir categoria: " + e.getMessage());
        }
    }
}
