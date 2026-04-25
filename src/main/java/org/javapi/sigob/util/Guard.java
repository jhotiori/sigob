package org.javapi.sigob.util;

import java.util.function.Predicate;

/**
 * Classe utilitaria para validar dados em tempo de execução
 */
public final class Guard {

    /**
     * Construtor privado para evitar instanciamento
     */
    private Guard() {

    }

    /**
     * Valida um argumento, se a condição for falsa, vai disparar uma exception
     *
     * @param value O argumento a ser validado
     * @param validator Validador da condição
     * @param message A mensagem de erro
     * @throws IllegalArgumentException Se a condição for falsa
     * @return T - O argumento validado
     */
    public static <T> T ensure(T value, Predicate<T> validator, String message) {
        if (value == null || !validator.test(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Valida um argumento, se o argumento for null, vai disparar uma exception
     *
     * @param value O argumento a ser validado
     * @param message A mensagem de erro
     * @throws IllegalArgumentException Se o argumento for null
     * @return T - O argumento validado
     */
    public static <T> T ensureNotNull(T value, String message) {
        return ensure(value, v -> v != null, message);
    }

    /**
     * Valida uma String para não ser nula e vazia
     *
     * @param value A String a ser validada
     * @param message A mensagem de erro
     * @throws IllegalArgumentException Se a String for nula ou vazia
     * @return String - A String validada
     */
    public static String ensureNotBlank(String value, String message) {
        return ensure(value, v -> v != null && !v.isBlank(), message);
    }
}
