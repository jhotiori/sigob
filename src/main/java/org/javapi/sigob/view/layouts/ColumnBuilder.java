package org.javapi.sigob.view.layouts;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Builder fluente para layouts em coluna.
 */
public class ColumnBuilder {

    /**
     * Painel interno do builder.
     *
     * @see {@link JPanel}
     */
    private final JPanel panel;

    /**
     * Cria builder com painel vazio.
     */
    public ColumnBuilder() {
        this(new JPanel());
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     */
    public ColumnBuilder(JPanel panel) {
        this.panel = panel != null
                ? panel
                : new JPanel();

        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
    }

    /**
     * Adiciona espaçamento flexível vertical.
     *
     * @return ColumnBuilder - Instância atual
     */
    public ColumnBuilder glue() {
        panel.add(Box.createVerticalGlue());

        return this;
    }

    /**
     * Define preenchimento interno uniforme.
     *
     * @param padding - Tamanho do preenchimento
     * @return ColumnBuilder - Instância atual
     */
    public ColumnBuilder padding(int padding) {
        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        padding,
                        padding,
                        padding,
                        padding
                )
        );

        return this;
    }

    /**
     * Define preenchimento vertical e horizontal.
     *
     * @param vertical - Preenchimento vertical
     * @param horizontal - Preenchimento horizontal
     * @return ColumnBuilder - Instância atual
     */
    public ColumnBuilder padding(int vertical, int horizontal) {
        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        vertical,
                        horizontal,
                        vertical,
                        horizontal
                )
        );

        return this;
    }

    /**
     * Adiciona componentes ao painel.
     *
     * @param components - Componentes adicionados
     * @return ColumnBuilder - Instância atual
     */
    public ColumnBuilder add(Component... components) {
        if (components == null) {
            return this;
        }

        for (Component component : components) {
            if (component != null) {

                if (component instanceof JComponent swingComponent) {
                    swingComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
                }

                panel.add(component);
            }
        }

        return this;
    }

    /**
     * Adiciona espaçamento vertical.
     *
     * @param size - Tamanho do espaçamento
     * @return ColumnBuilder - Instância atual
     */
    public ColumnBuilder gap(int size) {
        panel.add(Box.createRigidArea(new Dimension(0, size)));

        return this;
    }

    /**
     * Retorna painel construído.
     *
     * @return JPanel - Painel construído
     */
    public JPanel build() {
        return panel;
    }

}
