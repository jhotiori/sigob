package org.javapi.sigob.util;

import java.time.LocalTime;

public final class Logger {
    private Logger() {
    }

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    private static final String INFO_SYMBOL = "";
    private static final String SUCCESS_SYMBOL = "";
    private static final String WARN_SYMBOL = "";
    private static final String ERROR_SYMBOL = "";

    /**
     * Exibe uma mensagem de informação no console.
     *
     * @param message Mensagem a ser exibida no log
     */
    public static void info(String message) {
        log("INFO", INFO_SYMBOL, BLUE, message);
    }

    /**
     * Exibe uma mensagem de sucesso no console.
     *
     * @param message Mensagem a ser exibida no log
     */
    public static void success(String message) {
        log("SUCCESS", SUCCESS_SYMBOL, GREEN, message);
    }

    /**
     * Exibe uma mensagem de aviso no console.
     *
     * @param message Mensagem a ser exibida no log
     */
    public static void warn(String message) {
        log("WARN", WARN_SYMBOL, YELLOW, message);
    }

    /**
     * Exibe uma mensagem de erro no console.
     *
     * @param message Mensagem a ser exibida no log
     */
    public static void error(String message) {
        log("ERROR", ERROR_SYMBOL, RED, message);
    }

    /**
     * Método interno responsável por formatar e imprimir o log.
     *
     * @param level   Nível do log (INFO, WARN, ERROR, etc.)
     * @param symbol  Símbolo representativo do nível
     * @param color   Código ANSI da cor
     * @param message Mensagem a ser exibida
     */
    private static void log(String level, String symbol, String color, String message) {
        String formatted = "[%s] [%s] (%s) %s".formatted(now(), symbol, level, message);
        System.out.println(color + formatted + RESET);
    }

    /**
     * Obtém o horário atual formatado como HH:mm:ss.
     *
     * @return String - String representando o horário atual
     */
    private static String now() {
        LocalTime time = LocalTime.now();
        return "%02d:%02d:%02d".formatted(time.getHour(), time.getMinute(), time.getSecond());
    }
}
