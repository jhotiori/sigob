package org.javapi.sigob.view.ui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import org.javapi.sigob.view.components.LabelComponent;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Utilitários relacionados a formulários.
 */
public final class UIForm {

    /**
     * Impede instanciação.
     */
    private UIForm() {

    }

    /**
     * Cria campo composto com label textual.
     *
     * @param label - Texto do campo
     * @param component - Componente do campo
     * @return JPanel - Campo criado
     */
    public static JPanel field(String label, JComponent component) {
        return field(UIForm.fieldLabel(label), component);
    }

    /**
     * Cria campo composto com label customizado.
     *
     * @param label - Label do campo
     * @param component - Componente do campo
     * @return JPanel - Campo criado
     */
    public static JPanel field(JLabel label, JComponent component) {
        return UI.column()
                .add(label)
                .gap(Spacing.XS)
                .add(component)
                .build();
    }

    /**
     * Cria label padrão de campos.
     *
     * @param text - Texto do label
     * @return LabelComponent - Label criado
     */
    public static LabelComponent fieldLabel(String text) {
        return UI.label(text, label -> {
            label.setFont(Fonts.MEDIUM);
        });
    }

    /**
     * Limpa campos de texto.
     *
     * @param fields - Campos alvo
     */
    public static void clearFields(JTextComponent... fields) {
        for (JTextComponent field : fields) {
            if (field != null) {
                field.setText("");
            }
        }
    }

    /**
     * Habilita ou desabilita campos de texto.
     *
     * @param enabled - Habilitar ou desabilitar
     * @param fields - Campos alvo
     */
    public static void setFieldsEnabled(boolean enabled, JComponent... fields) {
        for (JComponent field : fields) {
            if (field != null) {
                field.setEnabled(enabled);
            }
        }
    }

    /**
     * Habilita campos de texto.
     *
     * @param fields - Campos alvo
     */
    public static void enableFields(JComponent... fields) {
        setFieldsEnabled(true, fields);
    }

    /**
     * Desabilita campos de texto.
     *
     * @param fields - Campos alvo
     */
    public static void disableFields(JComponent... fields) {
        setFieldsEnabled(false, fields);
    }

}
