package org.javapi.sigob.cli;

import java.util.List;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.service.AcessoService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

/**
 * Menu responsável pelas operações de acesso via CLI.
 */
public class MenuAcesso extends Menu {

    /**
     * Inicializa o menu de acessos e registra as entradas disponíveis.
     */
    public MenuAcesso() {
        super("Acessos");
        adicionarEntrada("Cadastrar", this::cadastrar);
        adicionarEntrada("Atualizar", this::atualizar);
        adicionarEntrada("Excluir", this::excluir);
        adicionarEntrada("Listar (ID)", this::buscarPorId);
        adicionarEntrada("Listar (NOME)", this::buscarPorNome);
        adicionarEntrada("Listar (CODIGO)", this::buscarPorCodigo);
        adicionarEntrada("Listar (TODOS)", this::listarTodos);
    }

    private final AcessoService service = new AcessoService();

    /**
     * Realiza o cadastro de um novo acesso.
     */
    private void cadastrar() {
        String nome = Inputter.lerString("Insira o Nome do Acesso: ");
        String codigo = Inputter.lerString("Insira o Codigo do Acesso: ");
        String descricao = Inputter.lerString("Insira a Descricao do Acesso: ");

        try {
            service.save(new Acesso(0, nome, codigo, descricao));
            Logger.success("Acesso " + nome + " cadastrado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar acesso: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um acesso existente.
     */
    private void atualizar() {
        int id = Inputter.lerInt("Insira o ID do Acesso: ");

        while (service.findById(id) == null) {
            Logger.warn("Acesso não encontrado!");
            id = Inputter.lerInt("Insira o ID do Acesso: ");
        }

        String nome = Inputter.lerString("Insira o Novo nome do Acesso: ");
        String codigo = Inputter.lerString("Insira o Novo Codigo do Acesso: ");
        String descricao = Inputter.lerString("Insira a Nova Descricao do Acesso: ");

        try {
            service.update(new Acesso(id, nome, codigo, descricao));
            Logger.success("Acesso " + id + " atualizado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao atualizar acesso: " + e.getMessage());
        }
    }

    /**
     * Busca um acesso pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.lerInt("Insira o ID do Acesso: ");

        try {
            Acesso acesso = service.findById(id);
            if (acesso == null) {
                Logger.warn("Acesso não encontrado!");
            } else {
                System.out.println(acesso);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar acesso: " + e.getMessage());
        }
    }

    /**
     * Busca acessos pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.lerString("Insira o Nome do Acesso: ");

        try {
            List<Acesso> acessos = service.findByNome(nome);

            if (acessos.isEmpty()) {
                Logger.warn("Acesso não encontrado!");
            } else {
                for (Acesso a : acessos) {
                    System.out.println(a);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar acesso: " + e.getMessage());
        }
    }

    /**
     * Busca um acesso pelo código.
     */
    private void buscarPorCodigo() {
        String codigo = Inputter.lerString("Insira o Codigo do Acesso: ");

        try {
            Acesso acesso = service.findByCodigo(codigo);
            if (acesso == null) {
                Logger.warn("Acesso não encontrado!");
            } else {
                System.out.println(acesso);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar acesso: " + e.getMessage());
        }
    }

    /**
     * Lista todos os acessos cadastrados.
     */
    private void listarTodos() {
        try {
            List<Acesso> acessos = service.findAll();
            if (acessos.isEmpty()) {
                Logger.warn("Nenhum acesso cadastrado!");
            } else {
                for (Acesso a : acessos) {
                    System.out.println(a);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao listar acessos: " + e.getMessage());
        }
    }

    /**
     * Remove um acesso pelo ID.
     */
    private void excluir() {
        int id = Inputter.lerInt("Insira o ID do Acesso: ");
        try {
            Acesso acesso = service.findById(id);

            if (acesso == null) {
                Logger.warn("Acesso não encontrado!");
            } else {
                service.delete(acesso);
                Logger.success("Acesso excluido com sucesso!");
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir acesso: " + e.getMessage());
        }
    }
}
