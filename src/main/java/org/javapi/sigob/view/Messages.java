package org.javapi.sigob.view;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 * Utilitário centralizado para mensagens da aplicação.
 */
public final class Messages {

    /**
     * Impede instanciação utilitária.
     */
    private Messages() {
    }

    /**
     * Exibe mensagem de informação.
     *
     * @param message - Mensagem exibida
     */
    public static void info(String message) {
        show(message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe mensagem de sucesso.
     *
     * @param message - Mensagem exibida
     */
    public static void success(String message) {
        show(message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe mensagem de aviso.
     *
     * @param message - Mensagem exibida
     */
    public static void warn(String message) {
        show(message, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Exibe mensagem de erro.
     *
     * @param message - Mensagem exibida
     */
    public static void error(String message) {
        show(message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Exibe diálogo de confirmação.
     *
     * @param message - Mensagem exibida
     * @return boolean - true caso confirmado
     */
    public static boolean confirm(String message) {
        int option = JOptionPane.showConfirmDialog(
                null,
                message,
                "Confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return option == JOptionPane.YES_OPTION;
    }

    /**
     * Exibe seletor simples de opções.
     *
     * @param title - Título do diálogo
     * @param values - Valores disponíveis
     * @return Object - Valor selecionado
     */
    public static Object select(
            String title,
            Object[] values
    ) {
        return select(
                null,
                title,
                values
        );
    }

    /**
     * Exibe seletor simples de opções.
     *
     * @param parent - Componente pai
     * @param title - Título do diálogo
     * @param values - Valores disponíveis
     * @return Object - Valor selecionado
     */
    public static Object select(
            Component parent,
            String title,
            Object[] values
    ) {
        if (values == null || values.length == 0) {
            return null;
        }

        JComboBox<Object> comboBox = UI.comboBox(values);

        int option = JOptionPane.showConfirmDialog(
                parent,
                comboBox,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return null;
        }

        return comboBox.getSelectedItem();
    }

    /**
     * Exibe diálogo customizado.
     *
     * @param message - Mensagem exibida
     * @param title - Título da janela
     * @param type - Tipo do diálogo
     */
    private static void show(
            String message,
            String title,
            int type
    ) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                type
        );
    }

    /**
     * Exibe diálogo customizado.
     *
     * @param parent - Componente pai
     * @param message - Mensagem exibida
     * @param title - Título da janela
     * @param type - Tipo do diálogo
     * @param icon - Ícone customizado
     */
    public static void show(
            Component parent,
            String message,
            String title,
            int type,
            Icon icon
    ) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                title,
                type,
                icon
        );
    }

}
