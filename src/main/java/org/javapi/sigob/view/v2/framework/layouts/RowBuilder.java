package org.javapi.sigob.view.v2.framework.layouts;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.layouts.base.BoxLayoutBuilder;

/**
 * Builder para layouts horizontais.
 */
public final class RowBuilder extends BoxLayoutBuilder<RowBuilder> {

    /**
     * Cria layout em linha.
     */
    public RowBuilder() {
        this(new JPanel());
    }

    /**
     * Cria linha usando painel existente.
     *
     * @param panel - Painel utilizado
     */
    public RowBuilder(JPanel panel) {
        super(panel);
        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.X_AXIS
                )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createGap(int size) {
        return Box.createRigidArea(
                new Dimension(size, 0)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createGlue() {
        return Box.createHorizontalGlue();
    }

}
