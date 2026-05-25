package org.javapi.sigob.cli;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.service.DocumentoService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Menu responsável pelas operações de cliente via CLI.
 */
public class MenuCliente extends Menu {

    private final ClienteService service;
    private final DocumentoService documentoService;

    public MenuCliente(ClienteService service, DocumentoService documentoService) {
        super("Operações de Clientes");
        add("Cadastrar", this::cadastrar);
        add("Atualizar", this::atualizar);
        add("Excluir", this::excluir);
        add("Listar (ID)", this::buscarPorId);
        add("Listar (NOME)", this::buscarPorNome);
        add("Listar (DOCUMENTO)", this::buscarPorDocumento);
        add("Listar (TODOS)", this::listarTodos);
        this.service = service;
        this.documentoService = documentoService;
    }

    /**
     * Realiza o cadastro de um novo cliente. Fluxo: - 1. Pergunta o nome do
     * cliente - 2. Pergunta se deseja adicionar uma data de nascimento - 3.
     * Pergunta se deseja anexar um documento - 4. Cadastra o cliente
     */
    private void cadastrar() {
        String nome = Inputter.readString("Nome do Cliente: ");

        LocalDate dataNascimento = null;
        if (Inputter.readBoolean("Deseja adicionar Data de Nascimento? [S/N]: ")) {
            dataNascimento = Inputter.readLocalDate("Data de Nascimento [DD-MM-YYYY]: ");
        }

        Documento documento = null;

        if (Inputter.readBoolean("Deseja anexar um Documento? [S/N]: ")) {
            String tipo = Inputter.readNotBlankString("Tipo do Documento: ");
            String valor = Inputter.readNotBlankString("Documento: ");

            documento = new Documento(0, valor, tipo);

            try {
                documentoService.save(documento);
            } catch (Exception e) {
                Logger.error("Erro ao cadastrar documento: " + e.getMessage());
            }
        }

        try {
            service.save(new Cliente(0, nome, dataNascimento, documento));
            Logger.success("Cliente %s cadastrado com sucesso!".formatted(nome));
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um cliente existente. Fluxo: - 1. Busca o cliente
     * pelo ID - 2. Pergunta se deseja atualizar o nome - 3. Pergunta se deseja
     * atualizar a data de nascimento - 4. Pergunta se deseja atualizar o
     * documento - 5. Atualiza o cliente
     */
    private void atualizar() {
        int id = Inputter.readInt("ID do Cliente: ");
        Optional<Cliente> cliente = service.findById(id);

        while (cliente.isEmpty()) {
            Logger.warn("Cliente não encontrado!");
            id = Inputter.readInt("ID do Cliente: ");
            cliente = service.findById(id);
        }

        String nome = Inputter.readString("Novo Nome [vazio para manter]: ");
        nome = nome.isBlank() ? cliente.get().getNome() : nome;
        cliente.get().setNome(nome);

        if (Inputter.readBoolean("Deseja atualizar Data de Nascimento? [S/N]: ")) {
            LocalDate data = Inputter.readLocalDate("Nova Data [vazio mantém]: ");
            data = data == null ? cliente.get().getDataNascimento() : data;
            cliente.get().setDataNascimento(data);
        }

        if (Inputter.readBoolean("Deseja atualizar Documento? [S/N]: ")) {
            Documento documento = cliente.get().getDocumento();

            if (documento == null) {
                String tipo = Inputter.readNotBlankString("Tipo do Documento: ");
                String valor = Inputter.readNotBlankString("Documento: ");

                documento = new Documento(0, valor, tipo);

                try {
                    documentoService.save(documento);
                } catch (Exception e) {
                    Logger.error("Erro ao cadastrar documento: " + e.getMessage());
                }

            } else {
                String tipo = Inputter.readNotBlankString("Novo Tipo: ");
                documento.setTipo(tipo);

                String valor = Inputter.readNotBlankString("Novo Documento: ");
                documento.setDocumento(valor);

                try {
                    documentoService.update(documento);
                } catch (Exception e) {
                    Logger.error("Erro ao atualizar documento: " + e.getMessage());
                }
            }

            cliente.get().setDocumento(documento);
        }

        try {
            service.update(cliente.get());
            Logger.success("Cliente %d atualizado com sucesso!".formatted(id));
        } catch (Exception e) {
            Logger.error("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    /**
     * Remove um cliente pelo ID.
     */
    private void excluir() {
        int id = Inputter.readInt("ID do Cliente: ");

        try {
            Optional<Cliente> cliente = service.findById(id);

            if (cliente.isEmpty()) {
                Logger.warn("Cliente não encontrado!");
            } else {
                service.delete(cliente.get());
                Logger.success("Cliente %d excluído com sucesso!".formatted(id));
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    /**
     * Busca um cliente pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.readInt("ID do Cliente: ");

        try {
            Optional<Cliente> cliente = service.findById(id);

            if (cliente.isEmpty()) {
                Logger.warn("Cliente não encontrado!");
            } else {
                System.out.println(cliente.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca um cliente pelo nome. Pode retornar vários clientes.
     */
    private void buscarPorNome() {
        String nome = Inputter.readString("Nome do Cliente: ");

        try {
            List<Cliente> clientes = service.findByNome(nome);

            if (clientes.isEmpty()) {
                Logger.warn("Cliente não encontrado!");
            } else {
                for (Cliente c : clientes) {
                    System.out.println(c);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca um cliente pelo documento.
     */
    private void buscarPorDocumento() {
        String documento = Inputter.readString("Documento do Cliente: ");

        try {
            Optional<Cliente> cliente = service.findByDocumento(documento);

            if (cliente.isEmpty()) {
                Logger.warn("Cliente não encontrado!");
            } else {
                System.out.println(cliente.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    /**
     * Lista todos os clientes cadastrados.
     */
    private void listarTodos() {
        try {
            List<Cliente> clientes = service.findAll();

            if (clientes.isEmpty()) {
                Logger.warn("Nenhum cliente cadastrado!");
            } else {
                for (Cliente c : clientes) {
                    System.out.println(c);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao listar clientes: " + e.getMessage());
        }
    }
}
