package org.javapi.sigob.model.validation.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Venda.
 */
public final class VendaValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private VendaValidator() {
    }

    /**
     * Valida uma venda por completo.
     *
     * @param venda - Venda a ser validada
     * @return Venda - Venda validada
     */
    public static Venda validate(Venda venda) {
        Validators.notNull(
                venda,
                "Venda não pode ser nula!"
        );

        validateStatus(venda.getStatus());
        validateCliente(venda.getCliente());
        validateFuncionario(venda.getFuncionario());

        return venda;
    }

    /**
     * Valida o status da venda.
     *
     * @param status - Status a ser validado
     * @return String - Status validado
     */
    public static String validateStatus(String status) {
        Validators.notBlank(
                status,
                "Status não pode ser vazio!"
        );

        Validators.maxLength(
                status,
                16,
                "Status deve possuir no máximo 16 caracteres!"
        );

        return status;
    }

    /**
     * Valida o valor total da venda.
     *
     * @param valorTotal - Valor total a ser validado
     * @return BigDecimal - Valor validado
     */
    public static BigDecimal validateValorTotal(BigDecimal valorTotal) {
        Validators.positive(
                valorTotal,
                "Valor total deve ser maior ou igual a zero!"
        );

        return valorTotal;
    }

    /**
     * Valida o cliente da venda.
     *
     * @param cliente - Cliente a ser validado
     * @return Cliente - Cliente validado
     */
    public static Cliente validateCliente(Cliente cliente) {
        Validators.notNull(
                cliente,
                "Cliente não pode ser nulo!"
        );

        return cliente;
    }

    /**
     * Valida o funcionário da venda.
     *
     * @param funcionario - Funcionário a ser validado
     * @return Funcionario - Funcionário validado
     */
    public static Funcionario validateFuncionario(Funcionario funcionario) {
        Validators.notNull(
                funcionario,
                "Funcionário não pode ser nulo!"
        );

        return funcionario;
    }

    /**
     * Valida período de consulta de vendas.
     *
     * @param inicio - Data inicial
     * @param fim    - Data final
     */
    public static void validatePeriodo(LocalDate inicio, LocalDate fim) {
        Validators.notNull(
                inicio,
                "Data inicial não pode ser nula!"
        );

        Validators.notNull(
                fim,
                "Data final não pode ser nula!"
        );

        Validators.expect(
                !fim.isBefore(inicio),
                "Data final não pode ser anterior à data inicial!"
        );
    }
}
