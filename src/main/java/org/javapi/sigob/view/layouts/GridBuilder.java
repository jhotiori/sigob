package org.javapi.sigob.view.layouts;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Builder fluente para GridLayout.
 */
public class GridBuilder {

    /**
     * Painel interno do builder.
     *
     * @see {@link JPanel}
     */
    private final JPanel panel;

    /**
     * Layout interno do builder.
     *
     * @see {@link GridLayout}
     */
    private final GridLayout layout;

    /**
     * Cria builder de grid.
     *
     * @param rows - Quantidade de linhas
     * @param cols - Quantidade de colunas
     */
    public GridBuilder(int rows, int cols) {
        this(new JPanel(), rows, cols);
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     * @param rows - Quantidade de linhas
     * @param cols - Quantidade de colunas
     */
    public GridBuilder(JPanel panel, int rows, int cols) {
        this.panel = panel != null
                ? panel
                : new JPanel();

        this.layout = new GridLayout(rows, cols);

        this.panel.setLayout(layout);
    }

    /**
     * Define preenchimento interno uniforme.
     *
     * @param padding - Tamanho do preenchimento
     * @return GridBuilder - Instância atual
     */
    public GridBuilder padding(int padding) {
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
     * @return GridBuilder - Instância atual
     */
    public GridBuilder padding(int vertical, int horizontal) {
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
     * @return GridBuilder - Instância atual
     */
    public GridBuilder hgap(int gap) {
        layout.setHgap(gap);

        return this;
    }

    /**
     * Define espaçamento vertical.
     *
     * @param gap - Espaçamento vertical
     * @return GridBuilder - Instância atual
     */
    public GridBuilder vgap(int gap) {
        layout.setVgap(gap);

        return this;
    }

    /**
     * Adiciona componentes ao painel.
     *
     * @param components - Componentes adicionados
     * @return GridBuilder - Instância atual
     */
    public GridBuilder add(Component... components) {
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
