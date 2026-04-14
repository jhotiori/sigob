package org.javapi.sigob.config;

public final class MenuConfig {
    private MenuConfig() {}

    public static final String LINE_SYMBOL = "─";
    public static final int LINE_SIZE = 25;

    public static final String LABEL_EXIT = "Sair";
    public static final String LABEL_PROMPT = "Insira uma opção: ";

    public static String line() {
        return LINE_SYMBOL.repeat(LINE_SIZE);
    }

    public static String banner(String titulo) {
        String line = line();
        return "%s %s %s".formatted(line, titulo, line);
    }

    public static String footer(String banner) {
        return LINE_SYMBOL.repeat(banner.length());
    }

    public static String exitOption() {
        return "[0] - %s".formatted(LABEL_EXIT);
    }
}
