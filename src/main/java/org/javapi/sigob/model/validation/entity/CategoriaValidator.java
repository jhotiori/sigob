package org.javapi.sigob.model.validation.entity;

import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Categoria.
 */
public final class CategoriaValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private CategoriaValidator() {
    }

    /**
     * Valida uma Categoria por completo.
     *
     * @param categoria - A Categoria a ser validada
     */
    public static void validate(Categoria categoria) {
        Validators.notNull(
                categoria,
                "Categoria não pode ser nula!"
            );

        validateNome(categoria.getNome());
    }

    /**
     * Valida o nome de uma Categoria.
     *
     * @param nome - O nome a ser validado
     */
    public static void validateNome(String nome) {
        Validators.notBlank(
                nome,
                "Nome da Categoria não pode ser nulo ou vazio!"
            );
    }
}
