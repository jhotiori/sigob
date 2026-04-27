package org.javapi.sigob.config;

public final class MenuConfig {
    private MenuConfig() {}

    public static final String LINE_SYMBOL = "─";
    public static final String LABEL_EXIT = "Sair";
    public static final String LABEL_PROMPT = "Insira uma opção: ";
    public static final String LABEL_INVALID_OPTION = "Opção (%d) inválida!";

    public static int calculateWidth(String title, int maxSize) {
        int base = Math.max(title.length(), maxSize);
        return base + 10; // padding + "[99] - "
    }

    /**
     * Formata um banner com o titulo providenciado
     * @param title Titulo do banner
     * @param width Largura do banner
     * @return String - Banner formatado
     */
    public static String banner(String title, int width) {
        int padding = width - title.length() - 2;
        int left = padding / 2;
        int right = padding - left;
        return "%s %s %s".formatted(line(left), title, line(right));
    }

    /**
     * Formata uma linha com o tamanho providenciado
     * @param size Tamanho da linha
     * @return String - Linha formatada
     */
    public static String line(int size) {
        return LINE_SYMBOL.repeat(size);
    }

    /**
     * Formata uma entrada de menu
     * @param index Indice da entrada
     * @param name Nome da entrada
     * @return String - Entrada formatada
     */
    public static String entry(int index, String name) {
        return "[%d] - %s".formatted(index, name);
    }

    /**
     * Formata uma entrada de menu de sair
     * @return String - Entrada formatada
     */
    public static String entryExit() {
        return entry(0, LABEL_EXIT);
    }
}
