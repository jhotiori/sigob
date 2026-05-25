package org.javapi.sigob.cli;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.service.AcessoService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Menu responsável pelas operações de acesso via CLI.
 */
public class MenuAcesso extends Menu {

    /**
     * Serviço de Acessos do Menu
     */
    private final AcessoService service;

    /**
     * Inicializa o menu de acessos e registra as entradas disponíveis.
     */
    public MenuAcesso(AcessoService service) {
        super("Operações de Acessos");
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
     * Realiza o cadastro de um novo acesso.
     */
    private void cadastrar() {
        String nome = Inputter.readString("Nome do Acesso: ");
        String codigo = Inputter.readString("Codigo do Acesso: ");
        String descricao = Inputter.readString("Descrição do Acesso: ");

        try {
            service.save(new Acesso(0, codigo, nome, descricao));
            Logger.success("Acesso %s cadastrado com sucesso!".formatted(nome));
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar acesso: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um acesso existente.
     */
    private void atualizar() {
        int id = Inputter.readInt("ID do Acesso: ");
        Optional<Acesso> acesso = service.findById(id);

        while (acesso.isEmpty()) {
            Logger.warn("Acesso não encontrado!");
            id = Inputter.readInt("ID do Acesso: ");
            acesso = service.findById(id);
        }

        String nome = Inputter.readString("Novo Nome do Acesso [vazio para manter o mesmo]: ");
        nome = nome.isBlank() ? acesso.get().getNome() : nome;

        String codigo = Inputter.readString("Novo Codigo do Acesso [vazio para manter o mesmo]: ");
        codigo = codigo.isBlank() ? acesso.get().getCodigo() : codigo;

        String descricao = Inputter.readString("Nova Descricao do Acesso [vazio para manter o mesmo]: ");
        descricao = descricao.isBlank() ? acesso.get().getDescricao() : descricao;

        try {
            service.update(new Acesso(id, codigo, nome, descricao));
            Logger.success("Acesso " + id + " atualizado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao atualizar acesso: " + e.getMessage());
        }
    }

    /**
     * Busca um acesso pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.readInt("ID do Acesso para buscar: ");

        try {
            Optional<Acesso> acesso = service.findById(id);
            if (acesso.isEmpty()) {
                Logger.warn("Acesso não encontrado!");
            } else {
                System.out.println(acesso.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar acesso: " + e.getMessage());
        }
    }

    /**
     * Busca acessos pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.readString("Nome do Acesso: ");

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
        String codigo = Inputter.readString("Codigo do Acesso: ");

        try {
            Optional<Acesso> acesso = service.findByCodigo(codigo);
            if (acesso.isEmpty()) {
                Logger.warn("Acesso não encontrado!");
            } else {
                System.out.println(acesso.get());
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
        int id = Inputter.readInt("ID do Acesso para excluir: ");
        try {
            Optional<Acesso> acesso = service.findById(id);

            if (acesso.isEmpty()) {
                Logger.warn("Acesso não encontrado!");
            } else {
                service.delete(acesso.get());
                Logger.success("Acesso excluido com sucesso!");
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir acesso: " + e.getMessage());
        }
    }
}
