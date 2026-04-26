package org.javapi.sigob.exception;

/**
 * Exception base para a aplicação Sigob
 */
public class SigobException extends RuntimeException {

    /**
     * Construtor para criar uma nova SigobException
     *
     * @param message A mensagem da exception
     * @return SigobException - A nova exception
     */
    public SigobException(String message) {
        super(message);
    }
}
