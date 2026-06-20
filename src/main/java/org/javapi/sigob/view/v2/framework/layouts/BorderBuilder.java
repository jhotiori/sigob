package org.javapi.sigob.view.v2.framework.layouts;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.layouts.base.LayoutBuilder;

/**
 * Builder para BorderLayout.
 */
public class BorderBuilder extends LayoutBuilder<BorderBuilder> {

    /**
     * Cria layout de borda.
     */
    public BorderBuilder() {
        this(new JPanel());
    }

    /**
     * Cria layout de borda utilizando painel existente.
     *
     * @param panel - Painel utilizado
     */
    public BorderBuilder(JPanel panel) {
        super(panel);
        panel.setLayout(new BorderLayout());
    }

    /**
     * Adiciona componente ao topo.
     *
     * @param component - Componente
     * @return BorderBuilder - Builder atual
     */
    public BorderBuilder north(Object component) {
        panel.add(
                resolve(component),
                BorderLayout.NORTH
        );

        return this;
    }

    /**
     * Adiciona componente à esquerda.
     *
     * @param component - Componente
     * @return BorderBuilder - Builder atual
     */
    public BorderBuilder west(Object component) {
        panel.add(
                resolve(component),
                BorderLayout.WEST
        );

        return this;
    }

    /**
     * Adiciona componente ao centro.
     *
     * @param component - Componente
     * @return BorderBuilder - Builder atual
     */
    public BorderBuilder center(Object component) {
        panel.add(
                resolve(component),
                BorderLayout.CENTER
        );

        return this;
    }

    /**
     * Adiciona componente à direita.
     *
     * @param component - Componente
     * @return BorderBuilder - Builder atual
     */
    public BorderBuilder east(Object component) {
        panel.add(
                resolve(component),
                BorderLayout.EAST
        );

        return this;
    }

    /**
     * Adiciona componente à base.
     *
     * @param component - Componente
     * @return BorderBuilder - Builder atual
     */
    public BorderBuilder south(Object component) {
        panel.add(
                resolve(component),
                BorderLayout.SOUTH
        );

        return this;
    }

}
