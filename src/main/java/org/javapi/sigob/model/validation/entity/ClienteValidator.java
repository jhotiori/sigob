package org.javapi.sigob.model.validation.entity;

import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Cliente.
 */
public final class ClienteValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private ClienteValidator() {
    }

    /**
     * Valida um cliente por completo.
     *
     * @param cliente - Cliente a ser validado
     */
    public static void validate(Cliente cliente) {
        Validators.notNull(
                cliente,
                "Cliente não pode ser nulo!"
        );

        validateNome(cliente.getNome());
    }

    /**
     * Valida o nome de um cliente.
     *
     * @param nome - Nome a ser validado
     */
    public static void validateNome(String nome) {
        Validators.notBlank(
                nome,
                "Nome do cliente não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                nome,
                64,
                "Nome do cliente não pode possuir mais de 64 caracteres!"
        );
    }

    /**
     * Valida um documento.
     *
     * @param documento - Documento a ser validado
     */
    public static void validateDocumento(String documento) {
        Validators.notBlank(
                documento,
                "Documento não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                documento,
                64,
                "Documento não pode possuir mais de 64 caracteres!"
        );
    }
}
