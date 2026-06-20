package org.javapi.sigob.view.v2.framework.ui;

import java.awt.Component;
import java.util.List;

import javax.swing.JOptionPane;

import org.javapi.sigob.view.v2.dialogs.base.BaseCustomDialog;
import org.javapi.sigob.view.v2.framework.layouts.base.LayoutBuilder;

/**
 * Utilitário para exibição de diálogos.
 */
public final class UIDialogs {

    /**
     * Construtor privado para evitar instanciação.
     */
    private UIDialogs() {
    }

    /**
     * Exibe diálogo de informação.
     *
     * @param message - Mensagem exibida
     */
    public static void info(String message) {
        info(message, "Informação");
    }

    /**
     * Exibe diálogo de informação.
     *
     * @param message - Mensagem exibida
     * @param title - Título da janela
     */
    public static void info(
            String message,
            String title
    ) {
        message(
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Exibe diálogo de aviso.
     *
     * @param message - Mensagem exibida
     */
    public static void warn(String message) {
        warn(message, "Aviso");
    }

    /**
     * Exibe diálogo de aviso.
     *
     * @param message - Mensagem exibida
     * @param title - Título da janela
     */
    public static void warn(
            String message,
            String title
    ) {
        message(
                message,
                title,
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Exibe diálogo de erro.
     *
     * @param message - Mensagem exibida
     */
    public static void error(String message) {
        error(message, "Erro");
    }

    /**
     * Exibe diálogo de erro.
     *
     * @param message - Mensagem exibida
     * @param title - Título da janela
     */
    public static void error(
            String message,
            String title
    ) {
        message(
                message,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Exibe diálogo de erro.
     *
     * @param throwable - Exceção exibida
     */
    public static void error(Throwable throwable) {
        error(
                throwable == null
                        ? "Erro desconhecido"
                        : throwable.getMessage()
        );
    }

    /**
     * Exibe diálogo de confirmação.
     *
     * @param message - Mensagem exibida
     * @return boolean - Resultado da confirmação
     */
    public static boolean confirm(String message) {
        return confirm(
                message,
                "Confirmação"
        );
    }

    /**
     * Exibe diálogo de confirmação.
     *
     * @param message - Mensagem exibida
     * @param title - Título da janela
     * @return boolean - Resultado da confirmação
     */
    public static boolean confirm(
            String message,
            String title
    ) {
        return JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION;
    }

    /**
     * Solicita valor textual.
     *
     * @param message - Mensagem exibida
     * @return String - Valor informado ou null
     */
    public static String prompt(String message) {
        return prompt(
                message,
                "Entrada",
                null
        );
    }

    /**
     * Solicita valor textual.
     *
     * @param message - Mensagem exibida
     * @param defaultValue - Valor inicial
     * @return String - Valor informado ou null
     */
    public static String prompt(
            String message,
            String defaultValue
    ) {
        return prompt(
                message,
                "Entrada",
                defaultValue
        );
    }

    /**
     * Solicita valor textual.
     *
     * @param message - Mensagem exibida
     * @param title - Título da janela
     * @param defaultValue - Valor inicial
     * @return String - Valor informado ou null
     */
    public static String prompt(
            String message,
            String title,
            String defaultValue
    ) {
        return (String) JOptionPane.showInputDialog(
                null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                defaultValue
        );
    }

    /**
     * Solicita seleção de item.
     *
     * @param message - Mensagem exibida
     * @param options - Opções disponíveis
     * @param <T> - Tipo da opção
     * @return T - Opção selecionada ou null
     */
    public static <T> T select(
            String message,
            List<T> options
    ) {
        return select(
                message,
                options,
                options.isEmpty()
                ? null
                : options.get(0)
        );
    }

    /**
     * Solicita seleção de item.
     *
     * @param message - Mensagem exibida
     * @param options - Opções disponíveis
     * @param selected - Opção pré-selecionada
     * @param <T> - Tipo da opção
     * @return T - Opção selecionada ou null
     */
    @SuppressWarnings("unchecked")
    public static <T> T select(
            String message,
            List<T> options,
            T selected
    ) {
        return (T) JOptionPane.showInputDialog(
                null,
                message,
                "Seleção",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options.toArray(),
                selected
        );
    }

    /**
     * Solicita seleção de item.
     *
     * @param message - Mensagem exibida
     * @param options - Opções disponíveis
     * @param <T> - Tipo da opção
     * @return T - Opção selecionada ou null
     */
    public static <T> T select(
            String message,
            T[] options
    ) {
        return select(
                message,
                options,
                options.length == 0
                        ? null
                        : options[0]
        );
    }

    /**
     * Solicita seleção de item.
     *
     * @param message - Mensagem exibida
     * @param options - Opções disponíveis
     * @param selected - Opção pré-selecionada
     * @param <T> - Tipo da opção
     * @return T - Opção selecionada ou null
     */
    @SuppressWarnings("unchecked")
    public static <T> T select(
            String message,
            T[] options,
            T selected
    ) {
        return (T) JOptionPane.showInputDialog(
                null,
                message,
                "Seleção",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                selected
        );
    }

    /**
     * Exibe diálogo customizado.
     *
     * @param title - Título da janela
     * @param component - Componente exibido
     * @return boolean - Resultado da confirmação
     */
    public static boolean custom(
            String title,
            Component component
    ) {
        return JOptionPane.showConfirmDialog(
                null,
                component,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        ) == JOptionPane.OK_OPTION;
    }

    /**
     * Exibe diálogo customizado.
     *
     * @param title - Título da janela
     * @param layout - Layout exibido
     * @return boolean - Resultado da confirmação
     */
    public static boolean custom(
            String title,
            LayoutBuilder<?> layout
    ) {
        return custom(
                title,
                layout.build()
        );
    }

    /**
     * Exibe diálogo customizado.
     *
     * @param title - Título da janela
     * @param dialog - Dialogo exibido
     * @return boolean - Resultado da confirmação
     */
    public static boolean custom(
        String title,
        BaseCustomDialog dialog
    ) {
        return custom(
                title,
                dialog.build()
        );
    }

    /**
     * Exibe diálogo de mensagem.
     *
     * @param message - Mensagem exibida
     * @param title - Título da janela
     * @param type - Tipo da mensagem
     */
    private static void message(
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
     * Solicita opção.
     *
     * @param message - Mensagem exibida
     * @param title - Título da janela
     * @param options - Opções disponíveis
     * @return String - Opção selecionada ou null
     */
    public static String option(
                String message,
                String title,
                String... options
        ) {
            int index = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options.length > 0
                                ? options[0]
                                : null
            );

            return index < 0 ? null : options[index];
    }

}
