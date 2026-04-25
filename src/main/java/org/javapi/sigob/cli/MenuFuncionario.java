package org.javapi.sigob.cli;

import java.util.List;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.service.AcessoService;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;


public class MenuFuncionario extends Menu {

    /**
     * Inicializa o menu de funcionários e registra as entradas disponíveis.
     */
    public MenuFuncionario() {
        super("Funcionários");
        adicionarEntrada("Cadastrar", this::cadastrar);
        adicionarEntrada("Atualizar", this::atualizar);
        adicionarEntrada("Excluir", this::excluir);
        adicionarEntrada("Buscar (ID)", this::buscarPorId);
        adicionarEntrada("Buscar (NOME)", this::buscarPorNome);
        adicionarEntrada("Buscar (CODIGO)", this::buscarPorCodigo);
        adicionarEntrada("Listar (TODOS)", this::listarTodos);
    }

    private final FuncionarioService service = new FuncionarioService();
    private final AcessoService acessoService = new AcessoService();

    /**
     * Realiza o cadastro de um novo funcionário.
     */
    private void cadastrar() {
        String nome = Inputter.lerString("Insira o Nome do Funcionário: ");
        String codigo = Inputter.lerString("Insira o Código do Funcionário: ");
        int idAcesso = Inputter.lerInt("Insira o ID do Acesso: ");

        try {
            Acesso acesso = acessoService.findById(idAcesso);

            if (acesso == null) {
                Logger.warn("Acesso não encontrado!");
                return;
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setNmFuncionario(nome);
            funcionario.setCdFuncionario(codigo);
            funcionario.setAcesso(acesso);

            service.save(funcionario);
            Logger.success("Funcionário " + nome + " cadastrado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um funcionário existente.
     */
    private void atualizar() {
        int id = Inputter.lerInt("Insira o ID do Funcionário: ");

        while (service.findById(id) == null) {
            Logger.warn("Funcionário não encontrado!");
            id = Inputter.lerInt("Insira o ID do Funcionário: ");
        }

        String nome = Inputter.lerString("Insira o Novo Nome do Funcionário: ");
        String codigo = Inputter.lerString("Insira o Novo Código do Funcionário: ");
        int idAcesso = Inputter.lerInt("Insira o ID do Acesso: ");

        try {
            Acesso acesso = acessoService.findById(idAcesso);

            if (acesso == null) {
                Logger.warn("Acesso não encontrado!");
                return;
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setIdFuncionario(id);
            funcionario.setNmFuncionario(nome);
            funcionario.setCdFuncionario(codigo);
            funcionario.setAcesso(acesso);

            service.update(funcionario);
            Logger.success("Funcionário " + id + " atualizado com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    /**
     * Busca um funcionário pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.lerInt("Insira o ID do Funcionário: ");

        try {
            Funcionario funcionario = service.findById(id);
            if (funcionario == null) {
                Logger.warn("Funcionário não encontrado!");
            } else {
                System.out.println(funcionario);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    /**
     * Busca funcionários pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.lerString("Insira o Nome do Funcionário: ");

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
        String codigo = Inputter.lerString("Insira o Código do Funcionário: ");

        try {
            Funcionario funcionario = service.findByCodigo(codigo);
            if (funcionario == null) {
                Logger.warn("Funcionário não encontrado!");
            } else {
                System.out.println(funcionario);
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

    /**
     * Remove um funcionário pelo ID.
     */
    private void excluir() {
        int id = Inputter.lerInt("Insira o ID do Funcionário: ");

        try {
            Funcionario funcionario = service.findById(id);

            if (funcionario == null) {
                Logger.warn("Funcionário não encontrado!");
            } else {
                service.delete(funcionario);
                Logger.success("Funcionário " + id + " excluido com sucesso!");
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir funcionário: " + e.getMessage());
        }
    }
}
