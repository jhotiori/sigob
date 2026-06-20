package org.javapi.sigob.view.v2.framework.ui;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.layouts.BorderBuilder;
import org.javapi.sigob.view.v2.framework.layouts.CardBuilder;
import org.javapi.sigob.view.v2.framework.layouts.ColumnBuilder;
import org.javapi.sigob.view.v2.framework.layouts.GridBuilder;
import org.javapi.sigob.view.v2.framework.layouts.RowBuilder;

/**
 * Utilitário para criação de layouts.
 */
public final class UILayouts {

    /**
     * Construtor privado para evitar instanciação.
     */
    private UILayouts() {
    }

    /**
     * Cria layout em linha.
     *
     * @return RowBuilder - Builder criado
     */
    public static RowBuilder row() {
        return new RowBuilder();
    }

    /**
     * Cria layout em linha utilizando painel existente.
     *
     * @param panel - Painel base
     * @return RowBuilder - Builder criado
     */
    public static RowBuilder row(JPanel panel) {
        return new RowBuilder(panel);
    }

    /**
     * Cria layout em coluna.
     *
     * @return ColumnBuilder - Builder criado
     */
    public static ColumnBuilder column() {
        return new ColumnBuilder();
    }

    /**
     * Cria layout em coluna utilizando painel existente.
     *
     * @param panel - Painel base
     * @return ColumnBuilder - Builder criado
     */
    public static ColumnBuilder column(JPanel panel) {
        return new ColumnBuilder(panel);
    }

    /**
     * Cria layout em grade.
     *
     * @param rows - Quantidade de linhas
     * @param columns - Quantidade de colunas
     * @return GridBuilder - Builder criado
     */
    public static GridBuilder grid(
            int rows,
            int columns
    ) {
        return new GridBuilder(rows, columns);
    }

    /**
     * Cria layout em grade utilizando painel existente.
     *
     * @param panel - Painel base
     * @param rows - Quantidade de linhas
     * @param columns - Quantidade de colunas
     * @return GridBuilder - Builder criado
     */
    public static GridBuilder grid(
            JPanel panel,
            int rows,
            int columns
    ) {
        return new GridBuilder(
                panel,
                rows,
                columns
        );
    }

    /**
     * Cria layout de borda.
     *
     * @return BorderBuilder - Builder criado
     */
    public static BorderBuilder border() {
        return new BorderBuilder();
    }

    /**
     * Cria layout de borda utilizando painel existente.
     *
     * @param panel - Painel base
     * @return BorderBuilder - Builder criado
     */
    public static BorderBuilder border(JPanel panel) {
        return new BorderBuilder(panel);
    }

    /**
     * Cria layout de cartões.
     *
     * @return CardBuilder - Builder criado
     */
    public static CardBuilder card() {
        return new CardBuilder();
    }

    /**
     * Cria layout de cartões utilizando painel existente.
     *
     * @param panel - Painel base
     * @return CardBuilder - Builder criado
     */
    public static CardBuilder card(JPanel panel) {
        return new CardBuilder(panel);
    }

}
