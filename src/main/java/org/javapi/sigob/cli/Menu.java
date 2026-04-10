package org.javapi.sigob.cli;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Menu {
    static class Entry {
        String name;
        Runnable action;

        Entry(String name, Runnable action) {
            this.name   = name;
            this.action = action;
        }
    }

    private final List<Entry> entries = new ArrayList<>();
    private final Scanner     scanner = new Scanner(System.in);
    protected     String      title   = "Menu";

    protected void addEntry(String name, Runnable action) {
        entries.add(new Entry(name, action));
    }

    public void show() {
        while (true) {
            System.out.println("\n╔══════════════════════════╗");
            System.out.printf( "║  %-24s  ║%n", title);
            System.out.println("╚══════════════════════════╝");

            for (int i = 0; i < entries.size(); i++) {
                System.out.printf("[%d] %s%n", i + 1, entries.get(i).name);
            }
            System.out.println("[0] Voltar / Sair");
            System.out.print("\nEscolha: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 0) break;
                run(choice - 1);
            } catch (NumberFormatException e) {
                System.out.println("⚠  Entrada inválida. Digite um número.");
            } catch (Exception e) {
                System.out.println("✗  Erro: " + e.getMessage());
            }
        }
    }

    protected void run(int index) {
        if (index >= 0 && index < entries.size()) {
            entries.get(index).action.run();
        } else {
            System.out.println("⚠  Opção inválida.");
        }
    }

    protected String lerString(String prompt) {
        System.out.print(prompt);
        String val = scanner.nextLine().trim();
        if (val.isBlank()) throw new IllegalArgumentException("Valor não pode ser vazio.");
        return val;
    }

    protected int lerInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido. Digite um número inteiro.");
        }
    }

    protected BigDecimal lerDecimal(String prompt) {
        System.out.print(prompt);
        try {
            return new BigDecimal(scanner.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido. Ex: 10.50");
        }
    }

    protected boolean lerBoolean(String prompt) {
        System.out.print(prompt + " (s/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("s");
    }
}
