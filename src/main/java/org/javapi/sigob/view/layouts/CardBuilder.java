package org.javapi.sigob.view.layouts;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Builder fluente para CardLayout.
 */
public class CardBuilder {

    /**
     * Layout interno.
     *
     * @see {@link CardLayout}
     */
    private final CardLayout layout;

    /**
     * Painel interno.
     *
     * @see {@link JPanel}
     */
    private final JPanel panel;

    /**
     * Cria builder vazio.
     */
    public CardBuilder() {
        this.layout = new CardLayout();
        this.panel = new JPanel(layout);
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     */
    public CardBuilder(JPanel panel) {
        this.layout = new CardLayout();
        this.panel = panel != null
                ? panel
                : new JPanel();

        this.panel.setLayout(layout);
    }

    /**
     * Define preenchimento interno uniforme.
     *
     * @param padding - Tamanho do preenchimento
     * @return CardBuilder - Instância atual
     */
    public CardBuilder padding(int padding) {
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
     * Define preenchimento interno uniforme.
     *
     * @param padding - Tamanho do preenchimento
     * @return CardBuilder - Instância atual
     */
    public CardBuilder padding(int vertical, int horizontal) {
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
     * Adiciona card ao layout.
     *
     * @param name - Nome do card
     * @param component - Componente do card
     * @return CardBuilder - Instância atual
     */
    public CardBuilder add(String name, Component component) {
        if (name == null || component == null) {
            return this;
        }

        panel.add(component, name);

        return this;
    }

    /**
     * Exibe card.
     *
     * @param name - Nome do card
     * @return CardBuilder - Instância atual
     */
    public CardBuilder show(String name) {
        if (name == null) {
            return this;
        }

        layout.show(panel, name);

        return this;
    }

    /**
     * Avança para próximo card.
     *
     * @return CardBuilder - Instância atual
     */
    public CardBuilder next() {
        layout.next(panel);

        return this;
    }

    /**
     * Retorna para card anterior.
     *
     * @return CardBuilder - Instância atual
     */
    public CardBuilder previous() {
        layout.previous(panel);

        return this;
    }

    /**
     * Retorna primeiro card.
     *
     * @return CardBuilder - Instância atual
     */
    public CardBuilder first() {
        layout.first(panel);

        return this;
    }

    /**
     * Retorna último card.
     *
     * @return CardBuilder - Instância atual
     */
    public CardBuilder last() {
        layout.last(panel);

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
