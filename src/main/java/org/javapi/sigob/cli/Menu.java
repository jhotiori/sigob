package org.javapi.sigob.cli;

import java.util.ArrayList;
import java.util.List;

import org.javapi.sigob.config.MenuConfig;
import org.javapi.sigob.util.Inputter;

public abstract class Menu {
    public record Entry(String nome, Runnable callback) {}
    private final List<Entry> entradas = new ArrayList<>();
    private String titulo;

    protected Menu(String titulo) {
        this.titulo = titulo;
    }

    protected void adicionarEntrada(String nome, Runnable callback) {
        entradas.add(new Entry(nome, callback));
    }

    protected void removerEntrada(String nome) {
        for (int index = 0; index < entradas.size(); index++) {
            if (entradas.get(index).nome().equals(nome)) {
                entradas.remove(index);
                return;
            }
        }
    }

    protected void limparEntradas() {
        entradas.clear();
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void exibir() {
        while (true) {
            render();
            int opcao = Inputter.lerInt(MenuConfig.LABEL_PROMPT);
            if (opcao == 0) break;
            executar(opcao);
        }
    }

    private void render() {
        String banner = MenuConfig.banner(titulo);
        System.out.println(banner);

        for (int index = 0; index < entradas.size(); index++) {
            System.out.printf("[%d] - %s%n", index + 1, entradas.get(index).nome());
        }

        System.out.println(MenuConfig.exitOption());
        System.out.println(MenuConfig.footer(banner));
    }

    private void executar(int opcao) {
        if (opcao < 1 || opcao > entradas.size()) return;
        entradas.get(opcao - 1).callback().run();
    }
}
