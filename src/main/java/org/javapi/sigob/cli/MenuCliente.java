package org.javapi.sigob.cli;

import java.util.List;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuCliente extends Menu {
    /**
     * Inicializa o menu de clientes e registra as entradas disponíveis.
     */
    public MenuCliente() {
        super("Clientes");
        adicionarEntrada("Cadastrar", this::cadastrar);
        adicionarEntrada("Atualizar", this::atualizar);
        adicionarEntrada("Excluir", this::excluir);
        adicionarEntrada("Listar (ID)", this::buscarPorId);
        adicionarEntrada("Listar (NOME)", this::buscarPorNome);
        adicionarEntrada("Listar (DOCUMENTO)", this::buscarPorDoc);
        adicionarEntrada("Listar (TODOS)", this::listarTodos);
    }

    private final ClienteService service = new ClienteService();

    /**
     * Realiza o cadastro de um novo cliente.
     */
    private void cadastrar() {
        String nome = Inputter.lerString("Insira o Nome do Cliente: ");
        String documento = Inputter.lerString("Insira o Documento do Cliente: ");

        try {
            service.save(new Cliente(0, nome, documento));
            Logger.success("Cliente " + nome + " cadastrado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um cliente existente.
     */
    private void atualizar() {
        int id = Inputter.lerInt("Insira o ID do Cliente: ");

        while (service.findById(id) == null) {
            Logger.warn("Cliente não encontrado!");
            id = Inputter.lerInt("Insira o ID do Cliente: ");
        }

        String nome = Inputter.lerString("Insira o Novo Nome do Cliente: ");
        String documento = Inputter.lerString("Insira o Novo Documento do Cliente: ");

        try {
            service.save(new Cliente(id, nome, documento));
            Logger.success("Cliente " + id + " atualizado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca um cliente pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.lerInt("Insira o ID do Cliente: ");

        try {
            Cliente cliente = service.findById(id);
            if (cliente == null) {
                Logger.warn("Cliente não encontrado!");
            } else {
                System.out.println(cliente);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca clientes pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.lerString("Insira o Nome do Cliente: ");

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
     * Busca clientes pelo documento.
     */
    private void buscarPorDoc() {
        String documento = Inputter.lerString("Insira o Documento do Cliente: ");

        try {
            List<Cliente> clientes = service.findByDocumento(documento);

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

    /**
     * Remove um cliente pelo ID.
     */
    private void excluir() {
        int id = Inputter.lerInt("Insira o ID do Cliente: ");

        try {
            Cliente cliente = service.findById(id);

            if (cliente == null) {
                Logger.warn("Cliente não encontrado!");
            } else {
                service.delete(cliente);
                Logger.success("Cliente " + id + " excluido com sucesso!");
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir cliente: " + e.getMessage());
        }
    }
}
