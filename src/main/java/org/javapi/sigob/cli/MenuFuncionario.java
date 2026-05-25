package org.javapi.sigob.cli;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.service.AcessoService;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.service.DocumentoService;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Menu responsável pelas operações de funcionários via CLI.
 */
public class MenuFuncionario extends Menu {

    private final FuncionarioService service;
    private final AcessoService acessoService;
    private final DocumentoService documentoService;
    private final ClienteService clienteService;

    public MenuFuncionario(FuncionarioService service, AcessoService acessoService, DocumentoService documentoService, ClienteService clienteService) {
        super("Operações de Funcionários");
        add("Cadastrar", this::cadastrar);
        add("Atualizar", this::atualizar);
        add("Excluir", this::excluir);
        add("Buscar (ID)", this::buscarPorId);
        add("Buscar (NOME)", this::buscarPorNome);
        add("Buscar (CODIGO)", this::buscarPorCodigo);
        add("Listar (TODOS)", this::listarTodos);

        this.service = service;
        this.acessoService = acessoService;
        this.documentoService = documentoService;
        this.clienteService = clienteService;
    }

    /**
     * Realiza o cadastro de um novo funcionário.
     */
    private void cadastrar() {
        String nome = Inputter.readNotBlankString("Nome do Funcionário: ");
        String codigo = Inputter.readNotBlankString("Código do Funcionário: ");

        // Documento obrigatório
        String tipo = Inputter.readNotBlankString("Tipo do Documento: ");
        String valor = Inputter.readNotBlankString("Documento: ");

        Documento documento = new Documento(0, valor, tipo);

        // Acessos (mínimo 1)
        Set<Acesso> acessos = new HashSet<>();
        List<Acesso> disponiveis = acessoService.findAll();

        if (disponiveis.isEmpty()) {
            Logger.warn("Nenhum acesso cadastrado! Não é possível criar funcionário.");
            return;
        }

        System.out.println("Selecione os acessos (digite IDs, 0 para finalizar):");

        while (true) {
            for (Acesso a : disponiveis) {
                System.out.println(a);
            }

            int id = Inputter.readInt("ID do Acesso: ");

            if (id == 0) {
                if (acessos.isEmpty()) {
                    Logger.warn("Funcionário deve possuir ao menos um acesso!");
                    continue;
                }
                break;
            }

            Optional<Acesso> acesso = acessoService.findById(id);

            if (acesso.isEmpty()) {
                Logger.warn("Acesso não encontrado!");
                continue;
            }

            acessos.add(acesso.get());
        }

        try {
            // 1. salva documento
            documentoService.save(documento);

            //FUNCIONALIDADE TEMPORARIAMENTE REMOVIDA
            // 2. cria cliente automaticamente
            //Cliente cliente = new Cliente(0, nome, null, documento);
            //clienteService.save(cliente);

            // 3. cria funcionario
            Funcionario funcionario = new Funcionario(0, nome, codigo, documento);

            for (Acesso a : acessos) {
                funcionario.addAcesso(a);
            }

            service.save(funcionario);

            Logger.success("Funcionário %s cadastrado com sucesso!".formatted(nome));
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    /**
     * Realiza a atualização de um funcionário.
     */
    private void atualizar() {
        int id = Inputter.readInt("ID do Funcionário: ");

        Optional<Funcionario> funcionario = service.findById(id);

        while (funcionario.isEmpty()) {
            Logger.warn("Funcionário não encontrado!");
            id = Inputter.readInt("ID do Funcionário: ");
            funcionario = service.findById(id);
        }

        String nome = Inputter.readString("Novo Nome [vazio mantém]: ");
        nome = nome.isBlank() ? funcionario.get().getNome() : nome;

        String codigo = Inputter.readString("Novo Código [vazio mantém]: ");
        codigo = codigo.isBlank() ? funcionario.get().getCodigo() : codigo;

        Funcionario f = funcionario.get();
        f.setNome(nome);
        f.setCodigo(codigo);

        try {
            service.update(f);
            Logger.success("Funcionário %d atualizado com sucesso!".formatted(id));
        } catch (Exception e) {
            Logger.error("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    /**
     * Remove um funcionário pelo ID.
     */
    private void excluir() {
        int id = Inputter.readInt("ID do Funcionário: ");

        try {
            Optional<Funcionario> funcionario = service.findById(id);

            if (funcionario.isEmpty()) {
                Logger.warn("Funcionário não encontrado!");
            } else {
                service.delete(funcionario.get());
                Logger.success("Funcionário %d excluído com sucesso!".formatted(id));
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir funcionário: " + e.getMessage());
        }
    }

    /**
     * Busca um funcionário pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.readInt("ID do Funcionário: ");

        try {
            Optional<Funcionario> funcionario = service.findById(id);

            if (funcionario.isEmpty()) {
                Logger.warn("Funcionário não encontrado!");
            } else {
                System.out.println(funcionario.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    /**
     * Busca um funcionário pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.readString("Nome do Funcionário: ");

        try {
            List<Funcionario> funcionarios = service.findByNome(nome);

            if (funcionarios.isEmpty()) {
                Logger.warn("Funcionário não encontrado!");
            } else {
                for (Funcionario f : funcionarios) {
                    System.out.println(f);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    /**
     * Busca um funcionário pelo código.
     */
    private void buscarPorCodigo() {
        String codigo = Inputter.readString("Código do Funcionário: ");

        try {
            Optional<Funcionario> funcionario = service.findByCodigo(codigo);

            if (funcionario.isEmpty()) {
                Logger.warn("Funcionário não encontrado!");
            } else {
                System.out.println(funcionario.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    /**
     * Lista todos os funcionários cadastrados.
     */
    private void listarTodos() {
        try {
            List<Funcionario> funcionarios = service.findAll();

            if (funcionarios.isEmpty()) {
                Logger.warn("Nenhum funcionário cadastrado!");
            } else {
                for (Funcionario f : funcionarios) {
                    System.out.println(f);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao listar funcionários: " + e.getMessage());
        }
    }
}
