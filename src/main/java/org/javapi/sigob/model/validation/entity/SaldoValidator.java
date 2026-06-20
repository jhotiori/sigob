package org.javapi.sigob.model.validation.entity;

import java.time.LocalDate;

import org.javapi.sigob.model.entity.Saldo;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Saldo.
 */
public final class SaldoValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private SaldoValidator() {
    }

    /**
     * Valida um saldo por completo.
     *
     * @param saldo - Saldo a ser validado
     * @return Saldo - Saldo validado
     */
    public static Saldo validate(Saldo saldo) {
        Validators.notNull(
                saldo,
                "Saldo não pode ser nulo!"
        );

        validateTipo(saldo.getTipo());

        Validators.notNull(
                saldo.getValorSaldo(),
                "Valor do saldo não pode ser nulo!"
        );

        Validators.notNull(
                saldo.getDataSaldo(),
                "Data do saldo não pode ser nula!"
        );

        return saldo;
    }

    /**
     * Valida o tipo do saldo.
     *
     * @param tipo - Tipo a ser validado
     * @return String - Tipo validado
     */
    public static String validateTipo(String tipo) {
        Validators.notBlank(
                tipo,
                "Tipo não pode ser vazio!"
        );

        Validators.maxLength(
                tipo,
                255,
                "Tipo deve possuir no máximo 255 caracteres!"
        );

        return tipo;
    }

    /**
     * Valida a data do saldo.
     *
     * @param data - Data a ser validada
     * @return LocalDate - Data validada
     */
    public static LocalDate validateDataSaldo(LocalDate data) {
        return Validators.notNull(
                data,
                "Data não pode ser nula!"
        );
    }

    /**
     * Valida a descrição do saldo.
     *
     * @param descricao - Descrição a ser validada
     * @return String - Descrição validada
     */
    public static String validateDescricao(String descricao) {
        Validators.notBlank(
                descricao,
                "Descrição nao pode ser vazia!"
        );

        Validators.maxLength(
                descricao,
                255,
                "Descrição nao pode possuir mais de 255 caracteres!"
        );

        return descricao;
    }
}
