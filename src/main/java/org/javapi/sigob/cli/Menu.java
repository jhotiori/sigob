package org.javapi.sigob.cli;

import java.util.ArrayList;
import java.util.List;

import org.javapi.sigob.util.Inputter;

public abstract class Menu {
    static class Entry {
        final String nome;
        final Runnable callback;

        Entry(String nome, Runnable callback) {
            this.nome = nome;
            this.callback = callback;
        }
    }

    private String titulo;
    private final List<Entry> entradas;

    public Menu(String titulo) {
        this.titulo = titulo;
        this.entradas = new ArrayList<>();
    }

    protected void adicionarEntrada(String nome, Runnable callback) {
        this.entradas.add(new Entry(nome, callback));
    }
    
    protected void limparEntradas() {
        this.entradas.clear();
    }

    public void exibir() {
        while (true) {
            String divisores = "─".repeat(25);
            String banner = "%s %s %s".formatted(divisores, this.getTitulo(), divisores);
            String footer = "─".repeat(banner.length());

            System.out.println(banner);
            for (int index = 0; index < this.entradas.size(); index++) {
                System.out.println("[%d] - %s".formatted(index + 1, this.entradas.get(index).nome));
            }
            System.out.println(footer);

            int opcao = Inputter.lerInt("Insira uma opção: ");

            if (opcao == 0) {
                break;
            }

            this.rodar(opcao);
        }
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public static void exibirCustom(String tituloCustom, List<Entry> entradasCustom) {
        String divisores = "─".repeat(25);
        String banner = "%s %s %s".formatted(divisores, tituloCustom, divisores);
        String footer = "─".repeat(banner.length());

        System.out.println(banner);
        for (int index = 0; index < entradasCustom.size(); index++) {
            System.out.println("[%d] - %s".formatted(index + 1, entradasCustom.get(index).nome));
        }
        System.out.println(footer);
    }

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected void rodar(int index) {
        if (index > 0 && index <= this.entradas.size()) {
            this.entradas.get(index - 1).callback.run();
        }
    }
}
