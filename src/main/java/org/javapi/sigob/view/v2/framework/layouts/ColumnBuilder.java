package org.javapi.sigob.view.v2.framework.layouts;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.layouts.base.BoxLayoutBuilder;

/**
 * Builder para layouts verticais.
 */
public class ColumnBuilder extends BoxLayoutBuilder<ColumnBuilder> {

    /**
     * Cria coluna.
     */
    public ColumnBuilder() {
        this(new JPanel());
    }

    /**
     * Cria coluna usando painel existente.
     *
     * @param panel - Painel utilizado
     */
    public ColumnBuilder(JPanel panel) {
        super(panel);
        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createGap(int size) {
        return Box.createRigidArea(
                new Dimension(
                        0,
                        size
                )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createGlue() {
        return Box.createVerticalGlue();
    }

}
