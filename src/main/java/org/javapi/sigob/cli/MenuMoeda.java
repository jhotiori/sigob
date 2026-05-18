package org.javapi.sigob.cli;

import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.service.MoedaService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Menu responsável pelas operações de moeda via CLI.
 */
public class MenuMoeda extends Menu {

    private final MoedaService service;

    /**
     * Inicializa o menu de moedas e registra as entradas disponíveis.
     */
    public MenuMoeda(MoedaService service) {
        super("Operações de Moedas");
        add("Cadastrar", this::cadastrar);
        add("Atualizar", this::atualizar);
        add("Excluir", this::excluir);
        add("Buscar (ID)", this::buscarPorId);
        add("Buscar (NOME)", this::buscarPorNome);
        add("Buscar (SIGLA)", this::buscarPorSigla);
        add("Listar (TODOS)", this::listarTodos);
        this.service = service;
    }

    /**
     * Realiza o cadastro de uma nova moeda.
     */
    private void cadastrar() {
        String nome = Inputter.readString("Nome da Moeda: ");
        String cifrao = Inputter.readString("Cifrão: ");
        String sigla = Inputter.readString("Sigla: ");

        try {
            service.save(new Moeda(0, nome, cifrao, sigla));
            Logger.success("Moeda %s cadastrada com sucesso!".formatted(nome));
        } catch (Exception e) {
            Logger.error("Erro ao cadastrar moeda: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma moeda existente.
     */
    private void atualizar() {
        int id = Inputter.readInt("ID da Moeda: ");
        Optional<Moeda> moeda = service.findById(id);

        while (moeda.isEmpty()) {
            Logger.warn("Moeda não encontrada!");
            id = Inputter.readInt("ID da Moeda: ");
            moeda = service.findById(id);
        }

        Moeda atual = moeda.get();

        String nome = Inputter.readString("Novo Nome [vazio mantém]: ");
        nome = nome.isBlank() ? atual.getNome() : nome;

        String cifrao = Inputter.readString("Novo Cifrão [vazio mantém]: ");
        cifrao = cifrao.isBlank() ? atual.getCifrao() : cifrao;

        String sigla = Inputter.readString("Nova Sigla [vazio mantém]: ");
        sigla = sigla.isBlank() ? atual.getSigla() : sigla;

        try {
            service.update(new Moeda(id, nome, cifrao, sigla));
            Logger.success("Moeda %d atualizada com sucesso!".formatted(id));
        } catch (Exception e) {
            Logger.error("Erro ao atualizar moeda: " + e.getMessage());
        }
    }

    /**
     * Busca uma moeda pelo ID.
     */
    private void buscarPorId() {
        int id = Inputter.readInt("ID da Moeda: ");

        try {
            Optional<Moeda> moeda = service.findById(id);

            if (moeda.isEmpty()) {
                Logger.warn("Moeda não encontrada!");
            } else {
                System.out.println(moeda.get());
            }
        } catch (Exception e) {
            Logger.error("Erro ao buscar moeda: " + e.getMessage());
        }
    }

    /**
     * Busca moedas pelo nome.
     */
    private void buscarPorNome() {
        String nome = Inputter.readString("Nome da Moeda: ");

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
     * Busca uma moeda pela sigla.
     */
    private void buscarPorSigla() {
        String sigla = Inputter.readString("Sigla da Moeda: ");

        try {
            Optional<Moeda> moeda = service.findBySigla(sigla);

            if (moeda.isEmpty()) {
                Logger.warn("Moeda não encontrada!");
            } else {
                System.out.println(moeda.get());
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
        int id = Inputter.readInt("ID da Moeda: ");

        try {
            Optional<Moeda> moeda = service.findById(id);

            if (moeda.isEmpty()) {
                Logger.warn("Moeda não encontrada!");
            } else {
                service.delete(moeda.get());
                Logger.success("Moeda %d excluída com sucesso!".formatted(id));
            }
        } catch (Exception e) {
            Logger.error("Erro ao excluir moeda: " + e.getMessage());
        }
    }
}
