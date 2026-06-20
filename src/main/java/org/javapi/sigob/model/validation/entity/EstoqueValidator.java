package org.javapi.sigob.model.validation.entity;

import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Estoque.
 */
public final class EstoqueValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private EstoqueValidator() {
    }

    /**
     * Valida um estoque por completo.
     *
     * @param estoque - Estoque a ser validado
     */
    public static void validate(Estoque estoque) {
        Validators.notNull(
                estoque,
                "Estoque não pode ser nulo!"
        );

        validateCodigo(estoque.getCodigo());
        validateNome(estoque.getNome());
    }

    /**
     * Valida o código de um estoque.
     *
     * @param codigo - Código a ser validado
     */
    public static void validateCodigo(String codigo) {
        Validators.notBlank(
                codigo,
                "Código do estoque não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                codigo,
                32,
                "Código do estoque não pode possuir mais de 32 caracteres!"
        );
    }

    /**
     * Valida o nome de um estoque.
     *
     * @param nome - Nome a ser validado
     */
    public static void validateNome(String nome) {
        Validators.notBlank(
                nome,
                "Nome do estoque não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                nome,
                128,
                "Nome do estoque não pode possuir mais de 128 caracteres!"
        );
    }
}
