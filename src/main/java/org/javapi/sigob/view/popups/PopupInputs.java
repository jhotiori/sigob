package org.javapi.sigob.view.popups;

import java.math.BigDecimal;

/**
 * Utilitário centralizado para entradas tipadas via popup.
 */
public final class PopupInputs {

    /**
     * Impede instanciação utilitária.
     */
    private PopupInputs() {
    }

    /**
     * Solicita entrada textual.
     *
     * @param title - Título do diálogo
     * @param message - Mensagem exibida
     * @return String - Valor informado ou null
     */
    public static String text(
            String title,
            String message
    ) {
        String value = Popups.input(
                title,
                message
        );

        if (PopupValues.wasCancelled(value)) {
            return null;
        }

        return value.trim();
    }

    /**
     * Solicita entrada textual obrigatória.
     *
     * @param title - Título do diálogo
     * @param message - Mensagem exibida
     * @return String - Valor informado ou null
     */
    public static String requiredText(
            String title,
            String message
    ) {
        String value = text(
                title,
                message
        );

        if (value == null || value.isBlank()) {
            return null;
        }

        return value;
    }

    /**
     * Solicita número inteiro.
     *
     * @param title - Título do diálogo
     * @param message - Mensagem exibida
     * @return Integer - Valor informado ou null
     */
    public static Integer integer(
            String title,
            String message
    ) {
        String value = requiredText(
                title,
                message
        );

        if (value == null) {
            return null;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Solicita número long.
     *
     * @param title - Título do diálogo
     * @param message - Mensagem exibida
     * @return Long - Valor informado ou null
     */
    public static Long longValue(
            String title,
            String message
    ) {
        String value = requiredText(
                title,
                message
        );

        if (value == null) {
            return null;
        }

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Solicita número decimal.
     *
     * @param title - Título do diálogo
     * @param message - Mensagem exibida
     * @return Double - Valor informado ou null
     */
    public static Double doubleValue(
            String title,
            String message
    ) {
        String value = requiredText(
                title,
                message
        );

        if (value == null) {
            return null;
        }

        try {
            return Double.valueOf(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Solicita valor monetário decimal.
     *
     * @param title - Título do diálogo
     * @param message - Mensagem exibida
     * @return BigDecimal - Valor informado ou null
     */
    public static BigDecimal bigDecimal(
            String title,
            String message
    ) {
        String value = requiredText(
                title,
                message
        );

        if (value == null) {
            return null;
        }

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

}
