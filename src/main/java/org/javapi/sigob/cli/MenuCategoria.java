package org.javapi.sigob.cli;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.service.CategoriaService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

/**
 * Menu responsável pelas operações de categoria via CLI.
 */
public class MenuCategoria extends Menu {

    /**
     * Serviço de Categorias do Menu
     */
    private final CategoriaService service;

    /**
     * Inicializa o menu de categorias e registra as entradas disponíveis.
     */
    public MenuCategoria(CategoriaService service) {
        super("Operações de Categorias");
        add("Cadastrar", this::cadastrar);
        add("Atualizar", this::atualizar);
        add("Excluir", this::excluir);
        add("Listar (ID)", this::buscarPorId);
        add("Listar (NOME)", this::buscarPorNome);
        add("Listar (CODIGO)", this::buscarPorCodigo);
        add("Listar (TODOS)", this::listarTodos);
        this.service = service;
    }

    /**
     * Realiza o cadastro de uma nova categoria.
     */
    private void cadastrar() {
        String codigo = Inputter.readString("Codigo da Categoria: ");
        String nome = Inputter.readString("Nome da Categoria: ");

        try {
            service.save(new Categoria(0, codigo, nome));
            Logger.success("Categoria %s cadastrada com sucesso!".formatted(nome));
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar categoria: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma categoria existente.
     */
    private void atualizar() {
        int id = Inputter.readInt("ID da Categoria: ");
        Optional<Categoria> categoria = service.findById(id);

        while (categoria.isEmpty()) {
            Logger.warn("Categoria não encontrada!");
            id = Inputter.readInt("ID da Categoria: ");
            categoria = service.findById(id);
        }

        String codigo = Inputter.readString("Novo Codigo [vazio para manter o mesmo]: ");
        codigo = codigo.isBlank() ? categoria.get().getCodigo() : codigo;

        String nome = Inputter.readString("Novo Nome [vazio para manter o mesmo]: ");
        nome = nome.isBlank() ? categoria.get().getNome() : nome;

        try {
            service.update(new Categoria(id, codigo, nome));
            Logger.success("Categoria %d atualizada com sucesso!".formatted(id));
        } catch (Exception e) {
            Logger.error("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    /**
     * Busca uma categoria pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.readInt("ID da Categoria: ");

        try {
            Optional<Categoria> categoria = service.findById(id);

            if (categoria.isEmpty()) {
                Logger.warn("Categoria não encontrada!");
            } else {
                System.out.println(categoria.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar categoria: " + e.getMessage());
        }
    }

    /**
     * Busca categorias pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.readString("Nome da Categoria: ");

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
        String codigo = Inputter.readString("Codigo da Categoria: ");

        try {
            Optional<Categoria> categoria = service.findByCodigo(codigo);

            if (categoria.isEmpty()) {
                Logger.warn("Categoria não encontrada!");
            } else {
                System.out.println(categoria.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar categoria: " + e.getMessage());
        }
    }

    /**
     * Lista todas as categorias cadastradas.
     */
    private void listarTodos() {
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
        int id = Inputter.readInt("ID da Categoria: ");

        try {
            Optional<Categoria> categoria = service.findById(id);

            if (categoria.isEmpty()) {
                Logger.warn("Categoria não encontrada!");
            } else {
                service.delete(categoria.get());
                Logger.success("Categoria %d excluída com sucesso!".formatted(id));
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir categoria: " + e.getMessage());
        }
    }
}
