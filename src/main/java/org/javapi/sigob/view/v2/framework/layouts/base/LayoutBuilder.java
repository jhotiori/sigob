package org.javapi.sigob.view.v2.framework.layouts.base;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Builder base para layouts.
 *
 * @param <T> - Tipo do builder
 */
public abstract class LayoutBuilder<T extends LayoutBuilder<T>> {

    /**
     * Painel interno.
     */
    protected final JPanel panel;

    /**
     * Cria builder.
     *
     * @param panel - Painel utilizado
     */
    protected LayoutBuilder(JPanel panel) {
        this.panel = panel;
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * Adiciona componentes.
     *
     * @param items - Componentes
     * @return T - Builder atual
     */
    @SuppressWarnings("unchecked")
    public T add(Object... items) {
        for (Object item : items) {
            if (item == null) {
                continue;
            }

            panel.add(resolve(item));
        }

        return (T) this;
    }

    /**
     * Adiciona componente.
     *
     * @param component - Componente
     * @return T - Builder atual
     */
    @SuppressWarnings("unchecked")
    public T add(Component component) {
        if (component == null) {
            return (T) this;
        }

        panel.add(resolve(component));
        return (T) this;
    }

    /**
     * Adiciona layout.
     *
     * @param builder - Layout
     * @return T - Builder atual
     */
    @SuppressWarnings("unchecked")
    public T add(LayoutBuilder<?> builder) {
        if (builder == null) {
            return (T) this;
        }

        panel.add(resolve(builder));
        return (T) this;
    }

    /**
     * Define preenchimento.
     *
     * @param size - Tamanho do preenchimento
     * @return T - Builder atual
     */
    public T padding(int size) {
        return padding(
                size,
                size,
                size,
                size
        );
    }

    /**
     * Define preenchimento.
     *
     * @param vertical - Preenchimento vertical
     * @param horizontal - Preenchimento horizontal
     * @return T - Builder atual
     */
    public T padding(
            int vertical,
            int horizontal
    ) {
        return padding(
                vertical,
                horizontal,
                vertical,
                horizontal
        );
    }

    /**
     * Define preenchimento.
     *
     * @param top - Preenchimento superior
     * @param right - Preenchimento direito
     * @param bottom - Preenchimento inferior
     * @param left - Preenchimento esquerdo
     * @return T - Builder atual
     */
    @SuppressWarnings("unchecked")
    public T padding(
            int top,
            int right,
            int bottom,
            int left
    ) {
        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        top,
                        left,
                        bottom,
                        right
                )
        );
        return (T) this;
    }

    /**
     * Retorna painel construído.
     *
     * @return JPanel - Painel construído
     */
    public JPanel build() {
        return panel;
    }

    /**
     * Resolve objeto para componente Swing.
     *
     * @param object - Objeto a resolver
     * @return Component - Componente resolvido
     */
    protected Component resolve(Object object) {
        Component component = null;

        if (object instanceof Component value) {
            component = value;
        }

        if (object instanceof LayoutBuilder<?> builder) {
            component = builder.build();
        }

        if (component instanceof JComponent swingComponent) {
            swingComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        return component;
    }

}
