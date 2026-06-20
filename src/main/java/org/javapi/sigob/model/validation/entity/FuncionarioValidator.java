package org.javapi.sigob.model.validation.entity;

import java.util.Set;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.validation.Validators;

/**
 * Validador da entidade Funcionário.
 */
public final class FuncionarioValidator {

    /**
     * Construtor privado para evitar instanciação.
     */
    private FuncionarioValidator() {
    }

    /**
     * Valida um funcionário por completo.
     *
     * @param funcionario - Funcionário a ser validado
     */
    public static void validate(Funcionario funcionario) {
        Validators.notNull(
                funcionario,
                "Funcionário não pode ser nulo!"
        );

        validateNome(funcionario.getNome());
        validateCodigo(funcionario.getCodigo());
        validateAcessos(funcionario.getAcessos());
    }

    /**
     * Valida o nome de um funcionário.
     *
     * @param nome - Nome a ser validado
     */
    public static void validateNome(String nome) {
        Validators.notBlank(
                nome,
                "Nome do funcionário não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                nome,
                64,
                "Nome do funcionário não pode possuir mais de 64 caracteres!"
        );
    }

    /**
     * Valida o código de um funcionário.
     *
     * @param codigo - Código a ser validado
     */
    public static void validateCodigo(String codigo) {
        Validators.notBlank(
                codigo,
                "Código do funcionário não pode ser nulo ou vazio!"
        );

        Validators.maxLength(
                codigo,
                16,
                "Código do funcionário não pode possuir mais de 16 caracteres!"
        );
    }

    /**
     * Valida os acessos de um funcionário.
     *
     * @param acessos - Conjunto de acessos a ser validado
     */
    public static void validateAcessos(Set<Acesso> acessos) {
        Validators.notEmpty(
                acessos,
                "Funcionário deve possuir ao menos um acesso!"
        );
    }

    /**
     * Valida o documento de um funcionário.
     *
     * @param documento - Documento a ser validado
     */
    public static void validateDocumento(String documento) {
        Validators.notBlank(
                documento,
                "Documento do funcionário não pode ser nulo ou vazio!"
        );
    }

    /**
     * Valida o telefone de um funcionário.
     *
     * @param telefone - Telefone a ser validado
     */
    public static void validateAcesso(String acesso) {
        Validators.notBlank(
                acesso,
                "Acesso do funcionário não pode ser nulo ou vazio!"
        );
    }
}
