package org.javapi.sigob.util;

import org.javapi.sigob.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Classe utilitaria para validar dados e argumentos
 */
public final class Validator {

    private final List<String> errors = new ArrayList<>();

    /**
     * Construtor privado para evitar instanciamento
     */
    private Validator() {
    }

    /**
     * Construtor alternativo para iniciar (criar) um novo Validator
     *
     * @return Validator - Instancia do Validator
     */
    public static Validator start() {
        return new Validator();
    }

    /**
     * Espera que determinada condição seja verdadeira, se não, adiciona a
     * mensagem à lista de erros.
     *
     * @param value Valor a ser verificado
     * @param validator Validador da condição
     * @param message Mensagem de erro
     * @return Validator - Instancia do Validator
     */
    public <T> Validator expect(T value, Predicate<T> validator, String message) {
        if (value == null || !validator.test(value)) {
            this.errors.add(message);
        }
        return this;
    }

    /**
     * Espera que o valor passado não seja igual a null.
     *
     * @param value Valor a ser verificado
     * @param message Mensagem de erro
     * @return Validator - Instancia do Validator
     */
    public Validator expectNotNull(Object value, String message) {
        return expect(value, v -> v != null, message);
    }

    /**
     * Espera que o valor passado seja diferente de null e não seja vazio.
     *
     * @param value Valor a ser verificado
     * @param message Mensagem de erro
     * @return Validator - Instancia do Validator
     */
    public Validator expectNotBlank(String value, String message) {
        return expect(value, v -> v != null && !v.isBlank(), message);
    }

    /**
     * Confere se o validator é valído (livre de erros)
     *
     * @return boolean - true se livre de erros, false caso contrário
     */
    public boolean isValid() {
        return this.errors.isEmpty();
    }

    /**
     * Valida o validator, caso haja algum erro, vai disparar uma exception
     *
     * @throws ValidationException Se o validator for inválido
     */
    public void validate() {
        if (!this.isValid()) {
            throw new ValidationException(String.join(", ", this.errors));
        }
    }
}
