package org.javapi.sigob.cli;

import java.util.ArrayList;
import java.util.List;

import org.javapi.sigob.config.MenuConfig;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public abstract class Menu {

    /**
     * Representa uma ação de menu
     *
     * @implNote Usado internamente
     */
    @FunctionalInterface
    public interface MenuAction {

        void execute() throws Exception;
    }

    /**
     * Representa uma entrada de menu
     *
     * @param name Nome da entrada
     * @param action Ação da entrada
     */
    public record MenuEntry(String name, MenuAction action) {

    }

    /**
     * Lista de entradas do Menu
     */
    private final List<MenuEntry> entries = new ArrayList<>();

    /**
     * Titulo do menu
     */
    private String title;

    /**
     * Cria um novo menu
     *
     * @param title Titulo do menu
     * @return Menu - O novo menu criado
     */
    protected Menu(String title) {
        this.title = title;
    }

    /**
     * Atribui o titulo do menu
     *
     * @param title Titulo do menu
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retorna o titulo do menu
     *
     * @return title - O titulo do menu
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Adiciona uma nova entrada ao menu
     *
     * @param name Nome da Entrada
     * @param action Ação da Entrada
     * @return Menu - O menu modificado
     * @implNote Propositalmente retorna menu para encadear métodos
     */
    protected Menu add(String name, MenuAction action) {
        this.entries.add(new MenuEntry(name, action));
        return this;
    }

    /**
     * Remove uma entrada ao menu
     *
     * @param name Nome da Entrada
     * @return Menu - O menu modificado
     * @implNote Propositalmente retorna menu para encadear métodos
     */
    protected Menu remove(String name) {
        this.entries.removeIf(entry -> entry.name().equals(name));
        return this;
    }

    /**
     * Limpa o menu, removendo todas as entradas
     *
     * @return Menu - O menu modificado
     * @implNote Propositalmente retorna menu para encadear métodos
     */
    protected Menu clear() {
        this.entries.clear();
        return this;
    }

    /**
     * Renderiza o menu por completo, seguindo a ordem de: - 1. Cabeçalho
     * (Header) - 2. Entradas (Entries) - 3. Rodapé (Footer)
     */
    private void render() {
        int width = resolveWidth();
        renderHeader(width);
        renderEntries();
        renderFooter(width);
    }

    /**
     * Inicia a execução do menu, mostrando-o e executando as opções
     * providenciadas até que sejam encerradas por meio da opção 0
     */
    public void show() {
        while (true) {
            render();
            int opcao = Inputter.readInt(MenuConfig.LABEL_PROMPT);
            if (opcao == 0) {
                break;
            }
            execute(opcao);
        }
    }

    /**
     * Executa a entrada no qual possuio a opção providenciada
     *
     * @param opcao Opção da entrada
     */
    private void execute(int opcao) {
        List<MenuEntry> list = this.entries;

        if (opcao < 1 || opcao > list.size()) {
            Logger.warn(MenuConfig.LABEL_INVALID_OPTION.formatted(opcao));
            return;
        }

        try {
            list.get(opcao - 1).action().execute();
        } catch (Exception e) {
            Logger.error("Erro ao Executar Opção (%d): %s".formatted(opcao, e.getMessage()));
        }
    }

    /**
     * Calcula o tamanho total do menu, se baseando nas entries (entradas) dele
     *
     * @return int - Tamanho total do menu
     */
    private int resolveWidth() {
        int max = this.entries.stream()
                .map(e -> e.name().length())
                .max(Integer::compare)
                .orElse(0);

        return MenuConfig.calculateWidth(this.title, max);
    }

    /**
     * Renderiza o Header do Menu
     */
    protected void renderHeader(int width) {
        System.out.println(MenuConfig.banner(this.title, width));
    }

    /**
     * Renderiza as entires (entradas) do menu
     */
    protected void renderEntries() {
        List<MenuEntry> list = this.entries;

        for (int index = 0; index < list.size(); index++) {
            MenuEntry entry = list.get(index);
            System.out.println(MenuConfig.entry(index + 1, entry.name()));
        }
    }

    /**
     * Renderiza o Footer do Menu
     */
    protected void renderFooter(int width) {
        System.out.println(MenuConfig.entryExit());
        System.out.println(MenuConfig.line(width));
    }
}
