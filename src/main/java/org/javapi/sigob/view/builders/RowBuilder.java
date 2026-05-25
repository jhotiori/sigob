package org.javapi.sigob.view.builders;

import javax.swing.*;
import java.awt.*;

/**
 * Builder fluente para layouts em linha.
 */
public class RowBuilder {

    /**
     * Painel interno do builder.
     *
     * @see {@link JPanel}
     */
    private final JPanel panel;

    /**
     * Cria builder com painel vazio.
     */
    public RowBuilder() {
        this(new JPanel());
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     */
    public RowBuilder(JPanel panel) {
        this.panel = panel != null
                ? panel
                : new JPanel();

        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
    }

    /**
     * Define preenchimento interno uniforme.
     *
     * @param padding - Tamanho do preenchimento
     * @return RowBuilder - Instância atual
     */
    public RowBuilder padding(int padding) {
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
     * @return RowBuilder - Instância atual
     */
    public RowBuilder padding(int vertical, int horizontal) {
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
     * @return RowBuilder - Instância atual
     */
    public RowBuilder add(Component... components) {
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
     * Adiciona espaçamento horizontal.
     *
     * @param size - Tamanho do espaçamento
     * @return RowBuilder - Instância atual
     */
    public RowBuilder gap(int size) {
        panel.add(Box.createRigidArea(new Dimension(size, 0)));

        return this;
    }

    /**
     * Adiciona espaçamento flexível horizontal.
     *
     * @return RowBuilder - Instância atual
     */
    public RowBuilder glue() {
        panel.add(Box.createHorizontalGlue());

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
