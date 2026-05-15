package org.javapi.sigob.view.layouts;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Builder fluente para FlowLayout.
 */
public class FlowBuilder {

    /**
     * Painel interno do builder.
     *
     * @see {@link JPanel}
     */
    private final JPanel panel;

    /**
     * Cria builder com painel vazio.
     */
    public FlowBuilder() {
        this(new JPanel());
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     */
    public FlowBuilder(JPanel panel) {
        this.panel = panel != null
                ? panel
                : new JPanel();

        this.panel.setLayout(new FlowLayout());
    }

    /**
     * Define preenchimento interno uniforme.
     *
     * @param padding - Tamanho do preenchimento
     * @return FlowBuilder - Instância atual
     */
    public FlowBuilder padding(int padding) {
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
     * @return FlowBuilder - Instância atual
     */
    public FlowBuilder padding(int vertical, int horizontal) {
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
     * Define espaçamento horizontal.
     *
     * @param gap - Espaçamento horizontal
     * @return FlowBuilder - Instância atual
     */
    public FlowBuilder hgap(int gap) {
        ((FlowLayout) panel.getLayout()).setHgap(gap);

        return this;
    }

    /**
     * Define espaçamento vertical.
     *
     * @param gap - Espaçamento vertical
     * @return FlowBuilder - Instância atual
     */
    public FlowBuilder vgap(int gap) {
        ((FlowLayout) panel.getLayout()).setVgap(gap);

        return this;
    }

    /**
     * Adiciona componentes ao painel.
     *
     * @param components - Componentes adicionados
     * @return FlowBuilder - Instância atual
     */
    public FlowBuilder add(Component... components) {
        if (components == null) {
            return this;
        }

        for (Component component : components) {
            if (component != null) {
                panel.add(component);
            }
        }

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
