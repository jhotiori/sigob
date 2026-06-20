package org.javapi.sigob.view.v2.framework.ui;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.javapi.sigob.view.v2.framework.components.entity.EntityCheckList;

/**
 * Utilidades para UI.
 */
public final class UIUtils {
    /**
     * Desabilita um botão.
     *
     * @param button - Botão a ser desabilitado
     */
    public static void disableButton(JButton button) {
        button.setEnabled(false);
    }

    /**
     * Habilita um botão.
     *
     * @param button - Botão a ser habilitado
     */
    public static void enableButton(JButton button) {
        button.setEnabled(true);
    }

    /**
     * Desabilita varios botões.
     *
     * @param buttons - Botões a serem desabilitados
     */
    public static void disableButton(JButton ...buttons) {
        for (JButton button : buttons) {
            disableButton(button);
        }
    }

    /**
     * Habilita varios botões.
     *
     * @param buttons - Botões a serem habilitados
     */
    public static void enableButton(JButton ...buttons) {
        for (JButton button : buttons) {
            enableButton(button);
        }
    }

    /**
     * Limpa um campo de texto.
     *
     * @param field - Campo a ser limpo
     */
    public static void clearField(JComponent field) {
        if (field instanceof JTextField textField) {
            textField.setText("");
        } else if (field instanceof JPasswordField passwordField) {
            passwordField.setText("");
        } else if (field instanceof EntityCheckList<?> checkList) {
            checkList.clearSelection();
        }
    }

    /**
     * Limpa varios campos de texto.
     *
     * @param fields - Campos a serem limpos
     */
    public static void clearFields(JTextField ...fields) {
        for (JTextField field : fields) {
            clearField(field);
        }
    }
}
