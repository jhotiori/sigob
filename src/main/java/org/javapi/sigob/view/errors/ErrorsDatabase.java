package org.javapi.sigob.view.errors;

/**
 * Utilitário para tratamento de erros de banco de dados.
 */
public final class ErrorsDatabase {

    /**
     * Construtor privado.
     */
    private ErrorsDatabase() {
    }

    /**
     * Retorna causa raiz da exceção.
     *
     * @param throwable - Exceção original
     * @return Throwable - Causa raiz encontrada
     */
    public static Throwable rootCause(
            Throwable throwable
    ) {
        if (throwable == null) {
            return null;
        }

        Throwable current = throwable;

        while (current.getCause() != null
                && current.getCause() != current) {
            current = current.getCause();
        }

        return current;
    }

    /**
     * Retorna mensagem da causa raiz.
     *
     * @param throwable - Exceção original
     * @return String - Mensagem encontrada
     */
    public static String message(
            Throwable throwable
    ) {
        Throwable root = rootCause(throwable);

        if (root == null || root.getMessage() == null) {
            return "Erro desconhecido";
        }

        return root.getMessage();
    }

    /**
     * Verifica se exceção é de foreign key.
     *
     * @param throwable - Exceção analisada
     * @return boolean - true se for foreign key
     */
    public static boolean isForeignKey(
            Throwable throwable
    ) {
        return matches(
                throwable,
                "foreign key"
        ) || matches(
                throwable,
                "violates foreign key constraint"
        );
    }

    /**
     * Verifica se exceção é de unique.
     *
     * @param throwable - Exceção analisada
     * @return boolean - true se for unique
     */
    public static boolean isUnique(
            Throwable throwable
    ) {
        return matches(
                throwable,
                "unique"
        ) || matches(
                throwable,
                "duplicate"
        ) || matches(
                throwable,
                "already exists"
        );
    }

    /**
     * Verifica se exceção é de constraint.
     *
     * @param throwable - Exceção analisada
     * @return boolean - true se for constraint
     */
    public static boolean isConstraint(
            Throwable throwable
    ) {
        return matches(
                throwable,
                "constraint"
        );
    }

    /**
     * Verifica se mensagem contém valor.
     *
     * @param throwable - Exceção analisada
     * @param value - Valor procurado
     * @return boolean - true se encontrado
     */
    public static boolean matches(
            Throwable throwable,
            String value
    ) {
        if (throwable == null || value == null) {
            return false;
        }

        String message = message(throwable)
                .toLowerCase();

        return message.contains(
                value.toLowerCase()
        );
    }

}
