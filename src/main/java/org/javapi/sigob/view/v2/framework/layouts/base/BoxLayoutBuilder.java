package org.javapi.sigob.view.v2.framework.layouts.base;

import java.awt.Component;

import javax.swing.JPanel;

/**
 * Builder base para layouts baseados em BoxLayout.
 *
 * @param <T> - Tipo do builder
 */
public abstract class BoxLayoutBuilder<T extends BoxLayoutBuilder<T>> extends LayoutBuilder<T> {

    /**
     * Cria builder.
     *
     * @param panel - Painel utilizado
     */
    protected BoxLayoutBuilder(JPanel panel) {
        super(panel);
        
    }

    /**
     * Adiciona espaçamento.
     *
     * @param size - Tamanho do espaçamento
     * @return T - Builder atual
     */
    @SuppressWarnings("unchecked")
    public T gap(int size) {
        panel.add(createGap(size));
        return (T) this;
    }

    /**
     * Adiciona componente expansível.
     *
     * @return T - Builder atual
     */
    @SuppressWarnings("unchecked")
    public T glue() {
        panel.add(createGlue());
        return (T) this;
    }

    /**
     * Cria componente de espaçamento.
     *
     * @param size - Tamanho do espaçamento
     * @return Component - Espaçamento criado
     */
    protected abstract Component createGap(int size);

    /**
     * Cria componente expansível.
     *
     * @return Component - Componente criado
     */
    protected abstract Component createGlue();

}
