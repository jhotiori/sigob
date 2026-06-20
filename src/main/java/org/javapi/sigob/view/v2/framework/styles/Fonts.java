package org.javapi.sigob.view.v2.framework.styles;

import java.awt.Font;

/**
 * Fontes da aplicação.
 */
public final class Fonts {

    /**
     * Fontes de cabecalho.
     */
    public static final Font HEADER = inter(Font.PLAIN, 28);
    public static final Font HEADER_BOLD = inter(Font.BOLD, 28);

    /**
     * Fontes de titulo.
     */
    public static final Font TITLE = inter(Font.PLAIN, 22);
    public static final Font TITLE_BOLD = inter(Font.BOLD, 22);

    /**
     * Fontes de subtitulo.
     */
    public static final Font SUBTITLE = inter(Font.PLAIN, 18);
    public static final Font SUBTITLE_BOLD = inter(Font.BOLD, 18);

    /**
     * Fontes de corpo. (padrão)
     */
    public static final Font DEFAULT = segoe(Font.PLAIN, 14);
    public static final Font DEFAULT_BOLD = segoe(Font.BOLD, 14);

    /**
     * Fontes pequenas.
     */
    public static final Font SMALL = segoe(Font.PLAIN, 12);
    public static final Font SMALL_BOLD = segoe(Font.BOLD, 12);

    /**
     * Construtor privado.
     */
    private Fonts() {
    }

    /**
     * Cria fonte Inter.
     *
     * @param style - Estilo da fonte
     * @param size - Tamanho da fonte
     * @return Font - Fonte criada
     */
    private static Font inter(
            int style,
            int size
    ) {
        return new Font("Inter", style, size);
    }

    /**
     * Cria fonte Segoe UI.
     *
     * @param style - Estilo da fonte
     * @param size - Tamanho da fonte
     * @return Font - Fonte criada
     */
    private static Font segoe(
            int style,
            int size
    ) {
        return new Font("Segoe UI", style, size);
    }

}
