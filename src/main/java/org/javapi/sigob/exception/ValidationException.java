package org.javapi.sigob.exception;

/**
 * Exception para validações
 */
public class ValidationException extends SigobException {

    /**
     * Construtor da exception
     *
     * @param message A mensagem de erro
     * @return ValidationException - A nova exception
     */
    public ValidationException(String message) {
        super(message);
    }
}
