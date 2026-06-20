package org.javapi.sigob.view.v2.framework.layouts;

import java.awt.CardLayout;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.layouts.base.LayoutBuilder;

/**
 * Builder para CardLayout.
 */
public class CardBuilder extends LayoutBuilder<CardBuilder> {

    /**
     * Layout utilizado.
     */
    private final CardLayout layout;

    /**
     * Cria conjunto de cards.
     */
    public CardBuilder() {
        this(new JPanel());
    }

    /**
     * Cria conjunto de cards utilizando painel existente.
     *
     * @param panel - Painel utilizado
     */
    public CardBuilder(JPanel panel) {
        super(panel);
        this.layout = new CardLayout();
        panel.setLayout(layout);
    }

    /**
     * Adiciona card.
     *
     * @param id - Identificador do card
     * @param component - Componente
     * @return CardBuilder - Builder atual
     */
    public CardBuilder card(
            String id,
            Object component
    ) {
        if (component == null) {
            return this;
        }

        panel.add(
                resolve(component),
                id
        );

        return this;
    }

    /**
     * Exibe card.
     *
     * @param id - Identificador do card
     */
    public void show(String id) {
        layout.show(
                panel,
                id
        );
    }

    /**
     * Exibe próximo card.
     */
    public void next() {
        layout.next(panel);
    }

    /**
     * Exibe card anterior.
     */
    public void previous() {
        layout.previous(panel);
    }

}
