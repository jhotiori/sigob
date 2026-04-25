package org.javapi.sigob.cli;

import java.util.List;

import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.service.MoedaService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuMoeda extends Menu {

    /**
     * Inicializa o menu de moedas e registra as entradas disponíveis.
     */
    public MenuMoeda() {
        super("Moedas");
        adicionarEntrada("Cadastrar", this::cadastrar);
        adicionarEntrada("Atualizar", this::atualizar);
        adicionarEntrada("Excluir", this::excluir);
        adicionarEntrada("Buscar (ID)", this::buscarPorId);
        adicionarEntrada("Buscar (NOME)", this::buscarPorNome);
        adicionarEntrada("Buscar (CODIGO)", this::buscarPorCodigo);
        adicionarEntrada("Listar (TODOS)", this::listarTodos);
    }

    private final MoedaService service = new MoedaService();

    /**
     * Realiza o cadastro de uma nova moeda.
     */
    private void cadastrar() {
        String nome = Inputter.lerString("Insira o Nome da Moeda: ");
        String simbolo = Inputter.lerString("Insira o Símbolo: ");
        String sigla = Inputter.lerString("Insira a Sigla: ");

        try {
            service.save(new Moeda(0, nome, simbolo, sigla));
            Logger.success("Moeda " + nome + " cadastrada com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar moeda: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma moeda existente.
     */
    private void atualizar() {
        int id = Inputter.lerInt("Insira o ID da Moeda: ");

        while (service.findById(id) == null) {
            Logger.warn("Moeda não encontrada!");
            id = Inputter.lerInt("Insira o ID da Moeda: ");
        }

        String nome = Inputter.lerString("Insira o Novo Nome da Moeda: ");
        String simbolo = Inputter.lerString("Insira o Novo Símbolo: ");
        String sigla = Inputter.lerString("Insira a Nova Sigla: ");

        try {
            service.update(new Moeda(id, nome, simbolo, sigla));
            Logger.success("Moeda " + id + " atualizada com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao atualizar moeda: " + e.getMessage());
        }
    }

    /**
     * Busca uma moeda pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.lerInt("Insira o ID da Moeda: ");

        try {
            Moeda moeda = service.findById(id);
            if (moeda == null) {
                Logger.warn("Moeda não encontrada!");
            } else {
                System.out.println(moeda);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar moeda: " + e.getMessage());
        }
    }

    /**
     * Busca moedas pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.lerString("Insira o Nome da Moeda: ");

        try {
            List<Moeda> moedas = service.findByNome(nome);

            if (moedas.isEmpty()) {
                Logger.warn("Moeda não encontrada!");
            } else {
                for (Moeda m : moedas) {
                    System.out.println(m);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar moeda: " + e.getMessage());
        }
    }

    /**
     * Busca uma moeda pelo código.
     */
    private void buscarPorCodigo() {
        String codigo = Inputter.lerString("Insira a Sigla da Moeda: ");

        try {
            Moeda moeda = service.findByCodigo(codigo);
            if (moeda == null) {
                Logger.warn("Moeda não encontrada!");
            } else {
                System.out.println(moeda);
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar moeda: " + e.getMessage());
        }
    }

    /**
     * Lista todas as moedas cadastradas.
     */
    private void listarTodos() {
        try {
            List<Moeda> moedas = service.findAll();

            if (moedas.isEmpty()) {
                Logger.warn("Nenhuma moeda cadastrada!");
            } else {
                for (Moeda m : moedas) {
                    System.out.println(m);
                }
            }
        } catch (Exception e) {
            Logger.error("Erro ao listar moedas: " + e.getMessage());
        }
    }

    /**
     * Remove uma moeda pelo ID.
     */
    private void excluir() {
        int id = Inputter.lerInt("Insira o ID da Moeda: ");

        try {
            Moeda moeda = service.findById(id);

            if (moeda == null) {
                Logger.warn("Moeda não encontrada!");
            } else {
                service.delete(moeda);
                Logger.success("Moeda " + id + " excluída com sucesso!");
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir moeda: " + e.getMessage());
        }
    }
}
