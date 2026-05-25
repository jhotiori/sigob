package org.javapi.sigob.view.ui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javapi.sigob.view.builders.RowBuilder;
import org.javapi.sigob.view.components.LabelComponent;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Utilitários semânticos para telas.
 */
public final class UIScreen {

    /**
     * Impede instanciação.
     */
    private UIScreen() {

    }

    /**
     * Cria título padrão.
     *
     * @param text - Texto do título
     * @return LabelComponent - Label criado
     */
    public static LabelComponent title(String text) {
        return UI.label(text, label -> {
            label.setFont(Fonts.TITLE_MEDIUM);
        });
    }

    /**
     * Cria subtítulo padrão.
     *
     * @param text - Texto do subtítulo
     * @return LabelComponent - Label criado
     */
    public static LabelComponent subtitle(String text) {
        return UI.label(text, label -> {
            label.setForeground(Palette.FG_MUTED);
            label.setFont(Fonts.MEDIUM_ITALIC);
        });
    }

    /**
     * Cria painel padrão de ações.
     *
     * @param components - Componentes das ações
     * @return JPanel - Painel criado
     */
    public static JPanel actions(JComponent... components) {
        RowBuilder builder = UI.row();

        for (JComponent component : components) {
            if (component != null) {
                builder.add(component);
                builder.gap(Spacing.XS);
            }
        }

        return builder.build();
    }

    /**
     * Cria seção semântica.
     *
     * @param title - Título da seção
     * @param components - Componentes internos
     * @return JPanel - Painel criado
     */
    public static JPanel section(
            String title,
            JComponent... components
    ) {
        return UI.column()
                .add(UIScreen.sectionLabel(title))
                .gap(Spacing.XS)
                .add(components)
                .build();
    }

    /**
     * Cria label padrão de seções.
     *
     * @param title - Título da seção
     * @return JLabel - Label criado
     */
    public static JLabel sectionLabel(String title) {
        return UI.label(title, label -> {
            label.setFont(Fonts.MEDIUM_BOLD);
        });
    }

    /**
     * Centraliza conteúdo da tela.
     *
     * @param component - Conteúdo central
     * @return JPanel - Painel criado
     */
    public static JPanel centered(JComponent component) {
        return UI.border()
                .center(component)
                .build();
    }

    /**
     * Cria página padrão.
     *
     * @param component - Conteúdo principal
     * @return JPanel - Painel criado
     */
    public static JPanel page(JComponent component) {
        return UI.border()
                .center(component)
                .padding(Spacing.MD)
                .build();
    }
}
