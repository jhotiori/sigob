package org.javapi.sigob.view.layouts;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Builder fluente para BorderLayout.
 */
public class BorderBuilder {

    /**
     * Painel interno do builder.
     *
     * @see {@link JPanel}
     */
    private final JPanel panel;

    /**
     * Cria builder com painel vazio.
     */
    public BorderBuilder() {
        this(new JPanel());
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     */
    public BorderBuilder(JPanel panel) {
        this.panel = panel != null
                ? panel
                : new JPanel();

        this.panel.setLayout(new BorderLayout());
    }

    /**
     * Define preenchimento interno uniforme.
     *
     * @param padding - Tamanho do preenchimento
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder padding(int padding) {
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
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder padding(int vertical, int horizontal) {
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
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder hgap(int gap) {
        ((BorderLayout) panel.getLayout()).setHgap(gap);

        return this;
    }

    /**
     * Define espaçamento vertical.
     *
     * @param gap - Espaçamento vertical
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder vgap(int gap) {
        ((BorderLayout) panel.getLayout()).setVgap(gap);

        return this;
    }

    /**
     * Adiciona componente ao norte.
     *
     * @param component - Componente alvo
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder north(JComponent component) {
        if (component != null) {
            panel.add(component, BorderLayout.NORTH);
        }

        return this;
    }

    /**
     * Adiciona componente ao sul.
     *
     * @param component - Componente alvo
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder south(JComponent component) {
        if (component != null) {
            panel.add(component, BorderLayout.SOUTH);
        }

        return this;
    }

    /**
     * Adiciona componente ao centro.
     *
     * @param component - Componente alvo
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder center(JComponent component) {
        if (component != null) {
            panel.add(component, BorderLayout.CENTER);
        }

        return this;
    }

    /**
     * Adiciona componente ao oeste.
     *
     * @param component - Componente alvo
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder west(JComponent component) {
        if (component != null) {
            panel.add(component, BorderLayout.WEST);
        }

        return this;
    }

    /**
     * Adiciona componente ao leste.
     *
     * @param component - Componente alvo
     * @return BorderBuilder - Instância atual
     */
    public BorderBuilder east(JComponent component) {
        if (component != null) {
            panel.add(component, BorderLayout.EAST);
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
