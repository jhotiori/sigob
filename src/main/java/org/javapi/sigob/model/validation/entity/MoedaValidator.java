package org.javapi.sigob.model.validation.entity;

import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Moeda.
 */
public final class MoedaValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private MoedaValidator() {
    }

    /**
     * Valida uma moeda por completo.
     *
     * @param moeda - Moeda a ser validada
     */
    public static void validate(Moeda moeda) {
        Validators.notNull(
                moeda,
                "Moeda não pode ser nula!"
        );

        validateNome(moeda.getNome());
        validateCifrao(moeda.getCifrao());
        validateSigla(moeda.getSigla());
    }

    /**
     * Valida o nome de uma moeda.
     *
     * @param nome - Nome a ser validado
     */
    public static void validateNome(String nome) {
        Validators.notBlank(
                nome,
                "Nome da moeda não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                nome,
                32,
                "Nome da moeda não pode possuir mais de 32 caracteres!"
        );
    }

    /**
     * Valida o cifrão de uma moeda.
     *
     * @param cifrao - Cifrão a ser validado
     */
    public static void validateCifrao(String cifrao) {
        Validators.notBlank(
                cifrao,
                "Cifrão da moeda não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                cifrao,
                8,
                "Cifrão da moeda não pode possuir mais de 8 caracteres!"
        );
    }

    /**
     * Valida a sigla de uma moeda.
     *
     * @param sigla - Sigla a ser validada
     */
    public static void validateSigla(String sigla) {
        Validators.notBlank(
                sigla,
                "Sigla da moeda não pode ser nula ou vazia!"
        );

        Validators.maxLength(
                sigla,
                8,
                "Sigla da moeda não pode possuir mais de 8 caracteres!"
        );
    }
}
