package org.javapi.sigob.view.v2.framework.layouts;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.layouts.base.LayoutBuilder;

/**
 * Builder para layouts em grade.
 */
public class GridBuilder extends LayoutBuilder<GridBuilder> {

    /**
     * Cria grade.
     *
     * @param rows - Quantidade de linhas
     * @param cols - Quantidade de colunas
     */
    public GridBuilder(
            int rows,
            int cols
    ) {
        this(
                new JPanel(),
                rows,
                cols
        );
    }

    /**
     * Cria grade utilizando painel existente.
     *
     * @param panel - Painel utilizado
     * @param rows - Quantidade de linhas
     * @param cols - Quantidade de colunas
     */
    public GridBuilder(
            JPanel panel,
            int rows,
            int cols
    ) {
        super(panel);

        panel.setLayout(
                new GridLayout(
                        rows,
                        cols
                )
        );
    }

}
