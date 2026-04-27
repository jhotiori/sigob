package org.javapi.sigob.cli;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

/**
 * Menu responsável pelas operações de estoque via CLI.
 */
public class MenuEstoque extends Menu {

    /**
     * Serviço de Estoques do Menu
     */
    private final EstoqueService service;

    /**
     * Inicializa o menu de estoques e registra as entradas disponíveis.
     *
     * @param service O serviço de estoques
     */
    public MenuEstoque(EstoqueService service) {
        super("Operações de Estoques");
        add("Cadastrar", this::cadastrar);
        add("Atualizar", this::atualizar);
        add("Excluir", this::excluir);
        add("Listar (ID)", this::buscarPorId);
        add("Listar (NOME)", this::buscarPorNome);
        add("Listar (TODOS)", this::listarTodos);
        this.service = service;
    }

    /**
     * Realiza o cadastro de um novo estoque.
     */
    private void cadastrar() {
        String codigo = Inputter.readString("Código do Estoque: ");
        String nome = Inputter.readString("Nome do Estoque: ");

        try {
            service.save(new Estoque(0, codigo, nome));
            Logger.success("Estoque %s cadastrado com sucesso!".formatted(nome));
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar estoque: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um estoque existente.
     */
    private void atualizar() {
        int id = Inputter.readInt("ID do Estoque: ");
        Optional<Estoque> estoque = service.findById(id);

        while (estoque.isEmpty()) {
            Logger.warn("Estoque não encontrado!");
            id = Inputter.readInt("ID do Estoque: ");
            estoque = service.findById(id);
        }

        String codigo = Inputter.readString("Novo Código [vazio para manter o mesmo]: ");
        codigo = codigo.isBlank() ? estoque.get().getCodigo() : codigo;

        String nome = Inputter.readString("Novo Nome [vazio para manter o mesmo]: ");
        nome = nome.isBlank() ? estoque.get().getNome() : nome;

        try {
            service.update(new Estoque(id, codigo, nome));
            Logger.success("Estoque %d atualizado com sucesso!".formatted(id));
        } catch (Exception e) {
            Logger.error("Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    /**
     * Busca um estoque pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.readInt("ID do Estoque: ");

        try {
            Optional<Estoque> estoque = service.findById(id);

            if (estoque.isEmpty()) {
                Logger.warn("Estoque não encontrado!");
            } else {
                System.out.println(estoque.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar estoque: " + e.getMessage());
        }
    }

    /**
     * Busca estoques pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.readString("Nome do Estoque: ");

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
        int id = Inputter.readInt("ID do Estoque: ");

        try {
            Optional<Estoque> estoque = service.findById(id);

            if (estoque.isEmpty()) {
                Logger.warn("Estoque não encontrado!");
            } else {
                service.delete(estoque.get());
                Logger.success("Estoque %d excluído com sucesso!".formatted(id));
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir estoque: " + e.getMessage());
        }
    }
}
