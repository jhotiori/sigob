package org.javapi.sigob.view.v2.framework.ui;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Utilitário para validações com feedback de erro.
 */
public final class UIValidation {

    /**
     * Construtor privado.
     */
    private UIValidation() {
    }

    /**
     * Exibe mensagem de erro.
     *
     * @param message - Mensagem de erro
     * @return false
     */
    private static boolean fail(String message) {
        UIDialogs.error(message);

        return false;
    }

    /**
     * Valida o valor não seja nulo.
     *
     * @param value - Valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean notNull(
            Object value,
            String message
    ) {
        return value != null || fail(message);
    }

    /**
     * Valida o valor não seja vazio.
     *
     * @param value - Valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean notBlank(
            String value,
            String message
    ) {
        return value != null
                && !value.isBlank()
                || fail(message);
    }

    /**
     * Valida a coleção não seja vazia.
     *
     * @param value   - Coleção
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean notEmpty(
                    Collection<?> value,
                    String message) {
            return value != null
                            && !value.isEmpty()
                            || fail(message);
    }

    /**
     * Valida o mapa não seja vazio.
     *
     * @param value   - Mapa
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean notEmpty(
                    Map<?, ?> value,
                    String message
                ) {
            return value != null
                            && !value.isEmpty()
                            || fail(message);
    }

    /**
     * Valida o array não seja vazio.
     *
     * @param value   - Array
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean notEmpty(
                    Object[] value,
                    String message) {
            return value != null
                            && value.length > 0
                            || fail(message);
    }

    /**
     * Valida o valor não seja vazio ou em branco.
     *
     * @param value - Valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean required(
            String value,
            String message
    ) {
        return value != null
                && !value.isBlank()
                || fail(message);
    }

    /**
     * Valida o tamanho mínimo do texto.
     *
     * @param value - Valor
     * @param minLength - Tamanho mínimo
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean minLength(
            String value,
            int minLength,
            String message
    ) {
        return value != null
                && value.length() >= minLength
                || fail(message);
    }

    /**
     * Valida o tamanho máximo do texto.
     *
     * @param value - Valor
     * @param maxLength - Tamanho máximo
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean maxLength(
            String value,
            int maxLength,
            String message
    ) {
        return value != null
                && value.length() <= maxLength
                || fail(message);
    }

    /**
     * Valida o tamanho do texto esteja dentro do intervalo.
     *
     * @param value - Valor
     * @param minLength - Tamanho mínimo
     * @param maxLength - Tamanho máximo
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean lengthBetween(
            String value,
            int minLength,
            int maxLength,
            String message
    ) {
        return value != null
                && value.length() >= minLength
                && value.length() <= maxLength
                || fail(message);
    }

    /**
     * Valida o valor seja maior ou igual a zero.
     *
     * @param value - Valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean nonNegative(
            Double value,
            String message
    ) {
        return value != null
                && value >= 0
                || fail(message);
    }

    /**
     * Valida o valor seja maior que zero.
     *
     * @param value - Valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean positiveNumber(
            Double value,
            String message
    ) {
        return value != null
                && value > 0
                || fail(message);
    }

    /**
     * Valida o valor esteja dentro do intervalo.
     *
     * @param value - Valor
     * @param min - Valor mínimo
     * @param max - Valor máximo
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean range(
            Double value,
            double min,
            double max,
            String message
    ) {
        return value != null
                && value >= min
                && value <= max
                || fail(message);
    }

    /**
     * Valida o valor seja maior ou igual a zero.
     *
     * @param value - Valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean nonNegative(
            BigDecimal value,
            String message
    ) {
        return value != null
                && value.compareTo(BigDecimal.ZERO) >= 0
                || fail(message);
    }

    /**
     * Valida o valor seja maior que zero.
     *
     * @param value - Valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean positiveNumber(
            BigDecimal value,
            String message
    ) {
        return value != null
                && value.compareTo(BigDecimal.ZERO) > 0
                || fail(message);
    }

    /**
     * Valida o valor esteja dentro do intervalo.
     *
     * @param value - Valor
     * @param min - Valor mínimo
     * @param max - Valor máximo
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean range(
            BigDecimal value,
            BigDecimal min,
            BigDecimal max,
            String message
    ) {
        return value != null
                && value.compareTo(min) >= 0
                && value.compareTo(max) <= 0
                || fail(message);
    }

    /**
     * Valida dois valores sejam iguais.
     *
     * @param value - Primeiro valor
     * @param other - Segundo valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean equals(
            Object value,
            Object other,
            String message
    ) {
        return Objects.equals(value, other)
                || fail(message);
    }

    /**
     * Valida dois valores sejam diferentes.
     *
     * @param value - Primeiro valor
     * @param other - Segundo valor
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean notEquals(
            Object value,
            Object other,
            String message
    ) {
        return !Objects.equals(value, other)
                || fail(message);
    }

    /**
     * Valida o valor corresponda ao padrão informado.
     *
     * @param value - Valor
     * @param regex - Expressão regular
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean matches(
            String value,
            String regex,
            String message
    ) {
        return value != null
                && value.matches(regex)
                || fail(message);
    }

    /**
     * Valida uma condição.
     *
     * @param condition - Condição
     * @param message - Mensagem de erro
     * @return boolean - Resultado da validação
     */
    public static boolean condition(
            boolean condition,
            String message
    ) {
        return condition || fail(message);
    }

}
