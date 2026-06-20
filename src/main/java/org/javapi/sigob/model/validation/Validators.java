package org.javapi.sigob.model.validation;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.javapi.sigob.exception.ValidationException;

/**
 * Utilitário contendo validações genéricas reutilizáveis.
 */
public final class Validators {

    /**
     * Construtor privado para evitar instanciação.
     */
    private Validators() {
        
    }

    /**
     * Valida se um objeto não é nulo.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da exceção
     * @param <T> - Tipo do objeto
     * @return T - O valor validado
     */
    public static <T> T notNull(T value, String message) {
        if (value == null) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se uma String não é nula ou vazia.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da exceção
     * @return String - O valor validado
     */
    public static String notBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se uma Collection não é nula ou vazia.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da exceção
     * @param <T> - Tipo da Collection
     * @return Collection<T> - O valor validado
     */
    public static <T extends Collection<?>> T notEmpty(T value, String message) {
        if (value == null || value.isEmpty()) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se um Map não é nulo ou vazio.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da exceção
     * @param <T> - Tipo do Map
     * @return T - O valor validado
     */
    public static <T extends Map<?, ?>> T notEmpty(T value, String message) {
        if (value == null || value.isEmpty()) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se um inteiro não é zero.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da exceção
     * @return int - O valor validado
     */
    public static int nonZero(int value, String message) {
        if (value == 0) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se um inteiro é positivo.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da exceção
     * @return int - O valor validado
     */
    public static int positive(int value, String message) {
        if (value <= 0) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se um long é positivo.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da exceção
     * @return long - O valor validado
     */
    public static long positive(long value, String message) {
        if (value <= 0) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se um BigDecimal é positivo.
     *
     * @param value - Valor a ser validado
     * @param message - Mensagem da exceção
     * @return BigDecimal - O valor validado
     */
    public static BigDecimal positive(BigDecimal value, String message) {
        notNull(value, message);

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se uma String possui tamanho máximo.
     *
     * @param value - Valor a ser validado
     * @param maxLength - Tamanho máximo permitido
     * @param message - Mensagem da exceção
     * @return String - O valor validado
     */
    public static String maxLength(
            String value,
            int maxLength,
            String message
        ) {
        notNull(value, message);

        if (value.length() > maxLength) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se uma String possui tamanho mínimo.
     *
     * @param value - Valor a ser validado
     * @param minLength - Tamanho mínimo permitido
     * @param message - Mensagem da exceção
     * @return String - O valor validado
     */
    public static String minLength(
            String value,
            int minLength,
            String message
        ) {
        notNull(value, message);

        if (value.length() < minLength) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida se uma String corresponde a uma expressão regular.
     *
     * @param value - Valor a ser validado
     * @param regex - Expressão regular
     * @param message - Mensagem da exceção
     * @return String - O valor validado
     */
    public static String matches(
            String value,
            String regex,
            String message
        ) {
        notBlank(value, message);

        if (!value.matches(regex)) {
            throw new ValidationException(message);
        }

        return value;
    }

    /**
     * Valida uma condição arbitrária.
     *
     * @param condition - Condição a ser validada
     * @param message   - Mensagem da exceção
     */
    public static void expect(boolean condition, String message) {
        if (!condition) {
            throw new ValidationException(message);
        }
    }
}
