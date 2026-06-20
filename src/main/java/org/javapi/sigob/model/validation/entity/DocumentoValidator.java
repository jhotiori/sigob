package org.javapi.sigob.model.validation.entity;

import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Documento.
 */
public final class DocumentoValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private DocumentoValidator() {
    }

    /**
     * Valida um documento por completo.
     *
     * @param documento - Documento a ser validado
     */
    public static void validate(Documento documento) {
        Validators.notNull(
                documento,
                "Documento não pode ser nulo!"
        );

        validateDocumento(documento.getDocumento());
        validateTipo(documento.getTipo());
    }

    /**
     * Valida o número ou identificação de um documento.
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

    /**
     * Valida o tipo de um documento.
     *
     * @param tipo - Tipo a ser validado
     */
    public static void validateTipo(String tipo) {
        Validators.notBlank(
                tipo,
                "Tipo do documento não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                tipo,
                32,
                "Tipo do documento não pode possuir mais de 32 caracteres!"
        );
    }
}
