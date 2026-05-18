package org.javapi.sigob.view.popups;

/**
 * Utilitário centralizado para valores de popup.
 */
public final class PopupValues {
    /**
     * Privado para evitar instanciamento.
     */
    private PopupValues() {
    }

    /**
     * Verifica se o popup foi cancelado.
     *
     * @param value - Valor para verificar
     * @return boolean - true se o popup foi cancelado
     */
    public static boolean wasCancelled(String value) {
        return value == null;
    }

    /**
     * Verifica se o valor deve ser mantido.
     *
     * @param value - Valor para verificar
     * @return boolean - true se o valor deve ser mantido
     */
    public static boolean shouldKeep(String value) {
        return value != null
                && value.isBlank();
    }

    /**
     * Verifica se o valor deve ser limpo.
     *
     * @param value - Valor para verificar
     * @return boolean - true se o valor deve ser limpo
     */
    public static boolean shouldClear(String value) {
        return value != null
                && value.equalsIgnoreCase("null");
    }

}
