package org.javapi.sigob.exception;

/**
 * Exception para casos de não encontrados (nulos, vazios, etc)
 */
public final class NotFoundException extends SigobException {

    /**
     * Construtor da exception
     *
     * @param message Mensagem de erro
     * @return NotFoundException - A nova exception
     */
    public NotFoundException(String message) {
        super(message);
    }
}
