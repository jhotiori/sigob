package org.javapi.sigob.model.validation.entity;

import java.time.LocalDate;

import org.javapi.sigob.model.entity.Caixa;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Caixa.
 */
public final class CaixaValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private CaixaValidator() {
    }

    /**
     * Valida um Caixa por completo.
     *
     * @param caixa - O Caixa a ser validado
     */
    public static void validate(Caixa caixa) {
        Validators.notNull(
                caixa,
                "Caixa não pode ser nulo!");

        validateStatus(caixa.getStatus());
    }

    /**
     * Valida o status de um Caixa.
     *
     * @param status - O status a ser validado
     */
    public static void validateStatus(String status) {
        Validators.notBlank(
                status,
                "Status não pode ser nulo ou vazio!");
    }

    /**
     * Valida uma data utilizada em consultas.
     *
     * @param data - A data a ser validada
     */
    public static void validateData(LocalDate data) {
        Validators.notNull(
                data,
                "Data não pode ser nula!");
    }
}
