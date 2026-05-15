package org.javapi.sigob.view;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * Utilitário para mensagens da aplicação.
 */
public final class Messages {

    /**
     * Impede instanciação.
     */
    private Messages() {
    }

    /**
     * Exibe mensagem informativa.
     *
     * @param parent - Componente pai
     * @param message - Mensagem exibida
     */
    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Informação",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Exibe mensagem informativa.
     *
     * @param message - Mensagem exibida
     */
    public static void info(String message) {
        info(null, message);
    }

    /**
     * Exibe mensagem de aviso.
     *
     * @param parent - Componente pai
     * @param message - Mensagem exibida
     */
    public static void warn(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Exibe mensagem de aviso.
     *
     * @param message - Mensagem exibida
     */
    public static void warn(String message) {
        warn(null, message);
    }

    /**
     * Exibe mensagem de erro.
     *
     * @param parent - Componente pai
     * @param message - Mensagem exibida
     */
    public static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Exibe mensagem de erro.
     *
     * @param message - Mensagem exibida
     */
    public static void error(String message) {
        error(null, message);
    }

    /**
     * Exibe confirmação ao usuário.
     *
     * @param parent - Componente pai
     * @param message - Mensagem exibida
     * @return boolean - Estado da confirmação
     */
    public static boolean confirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(
                parent,
                message,
                "Confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION;
    }

    /**
     * Exibe confirmação ao usuário.
     *
     * @param message - Mensagem exibida
     * @return boolean - Estado da confirmação
     */
    public static boolean confirm(String message) {
        return confirm(null, message);
    }

}
