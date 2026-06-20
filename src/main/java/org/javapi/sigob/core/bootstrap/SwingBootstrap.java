package org.javapi.sigob.core.bootstrap;

import javax.swing.UIManager;

import org.javapi.sigob.view.v2.framework.styles.Colors;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

/**
 * Responsável pela inicialização da aplicação Swing.
 */
public final class SwingBootstrap {

    /**
     * Indica se o bootstrapping foi realizado.
     */
    private static boolean HAS_BOOTSTRAPPED = false;

    /**
     * Realiza boostrap de Swing.
     * Faz setup do LAF e aplica estilos globais.
     */
    public static void bootstrap() {
        if (HAS_BOOTSTRAPPED) {
            return;
        }

        HAS_BOOTSTRAPPED = true;
        FlatMacDarkLaf.setup();

        UIManager.put(
                "Component.arc",
                14);

        UIManager.put(
                "Component.focusWidth",
                0.5);

        UIManager.put(
                "Component.innerFocusWidth",
                1);

        UIManager.put(
                "Component.focusColor",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "TextComponent.arc",
                14);

        UIManager.put(
                "TextField.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "TextArea.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "PasswordField.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "EditorPane.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "TextPane.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "ComboBox.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "ComboBox.selectionForeground",
                Colors.FG_PRIMARY);

                UIManager.put(
                "Table.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "Table.selectionForeground",
                Colors.FG_PRIMARY);

        UIManager.put(
                "TableHeader.background",
                Colors.BG_SURFACE);

        UIManager.put(
                "TableHeader.foreground",
                Colors.FG_PRIMARY);

        UIManager.put(
                "List.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "List.selectionForeground",
                Colors.FG_PRIMARY);

        UIManager.put(
                "Menu.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "Menu.selectionForeground",
                Colors.FG_PRIMARY);

        UIManager.put(
                "MenuItem.selectionBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "MenuItem.selectionForeground",
                Colors.FG_PRIMARY);

        UIManager.put(
            "CheckBox.icon.checkmarkColor",
            Colors.FG_PRIMARY);

        UIManager.put(
                "CheckBox.icon.selectedBorderColor",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "CheckBox.icon.selectedBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "CheckBox.icon.checkmarkColor",
                Colors.FG_PRIMARY);

        UIManager.put(
                "RadioButton.icon.selectedBorderColor",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "RadioButton.icon.selectedBackground",
                Colors.ACCENT_SECONDARY);

        UIManager.put(
                "RadioButton.icon.checkmarkColor",
                Colors.FG_PRIMARY);
    }
}
