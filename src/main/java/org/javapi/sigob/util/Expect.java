package org.javapi.sigob.util;

import java.util.Collection;
import java.util.Map;

/**
 * Classe utilitaria para validar dados e argumentos
 */
public final class Expect {

    /**
     * Construtor privado para evitar instanciamento
     */
    private Expect() {
    }

    /**
     * Valida se o valor informado nao e nulo.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da excecao
     */
    public static <T> void notNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se o texto informado nao e nulo nem vazio.
     *
     * @param value - Texto a ser validado
     * @param message - Mensagem da excecao
     */
    public static void notBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se o valor informado nao e NaN.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da excecao
     */
    public static void notNaN(Double value, String message) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se o valor informado e maior ou igual a zero.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da excecao
     */
    public static void notNegative(double value, String message) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se a colecao informada nao e nula nem vazia.
     *
     * @param value - Colecao a ser validada
     * @param message - Mensagem da excecao
     */
    public static void notEmpty(Collection<?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se o mapa informado nao e nulo nem vazio.
     *
     * @param value - Mapa a ser validado
     * @param message - Mensagem da excecao
     */
    public static void notEmpty(Map<?, ?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se o array informado nao e nulo nem vazio.
     *
     * @param value - Array a ser validado
     * @param message - Mensagem da excecao
     */
    public static <T> void notEmpty(T[] value, String message) {
        if (value == null || value.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se o valor informado e positivo.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da excecao
     */
    public static void positive(double value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se o estado atual e valido.
     *
     * @param condition - Estado a ser validado
     * @param message - Mensagem da excecao
     */
    public static void condition(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
