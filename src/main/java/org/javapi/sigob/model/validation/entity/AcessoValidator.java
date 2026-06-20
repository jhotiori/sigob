package org.javapi.sigob.model.validation.entity;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Acesso.
 */
public final class AcessoValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private AcessoValidator() {
    }

    /**
     * Valida um Acesso por completo.
     *
     * @param acesso - O Acesso a ser validado
     */
    public static void validate(Acesso acesso) {
        Validators.notNull(
            acesso,
            "Acesso não pode ser nulo!"
        );

        validateNome(acesso.getNome());
    }

    /**
     * Valida o nome de um Acesso.
     *
     * @param nome - O nome a ser validado
     */
    public static void validateNome(String nome) {
        Validators.notBlank(
                nome,
                "Nome do acesso não pode ser nulo ou vazio!"
            );
    }
}
