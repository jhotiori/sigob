package org.javapi.sigob.view.v2.framework.styles;

import java.awt.Color;

/**
 * Paleta de cores da aplicação.
 */
public final class Colors {

    /**
     * Cores de texto.
     */
    public static final Color FG_PRIMARY = rgb(245, 245, 245);
    public static final Color FG_SECONDARY = darker(FG_PRIMARY, 0.15f);
    public static final Color FG_MUTED = darker(FG_PRIMARY, 0.35f);
    public static final Color FG_DARK = darker(FG_PRIMARY, 0.55f);

    /**
     * Cores de background.
     */
    public static final Color BG_PRIMARY = rgb(24, 24, 24);
    public static final Color BG_SECONDARY = brighter(BG_PRIMARY, 0.15f);
    public static final Color BG_SURFACE = brighter(BG_PRIMARY, 0.30f);
    public static final Color BG_HIGHLIGHT = brighter(BG_PRIMARY, 0.45f);

    /**
     * Cores de acento.
     * Vermelho: rgb(255, 90, 90);
     * Laranja: rgb(255, 140, 90);
     * Verde: rgb(90, 255, 125);
     * Azul: rgb(90, 175, 255);
     * Roxo: rgb(190, 90, 255);
     */
    public static final Color ACCENT_PRIMARY = rgb(255, 140, 90);
    public static final Color ACCENT_SECONDARY = darker(ACCENT_PRIMARY, 0.15f);
    public static final Color ACCENT_MUTED = darker(ACCENT_PRIMARY, 0.30f);
    public static final Color ACCENT_DARK = darker(ACCENT_PRIMARY, 0.50f);
    public static final Color ACCENT_LIGHT = brighter(ACCENT_PRIMARY, 0.15f);
    public static final Color ACCENT_HIGHLIGHT = brighter(ACCENT_PRIMARY, 0.30f);

    /**
     * Cor de sucesso.
     */
    public static final Color SUCCESS_PRIMARY = rgb(120, 200, 140);
    public static final Color SUCCESS_SECONDARY = darker(SUCCESS_PRIMARY, 0.15f);
    public static final Color SUCCESS_MUTED = darker(SUCCESS_PRIMARY, 0.30f);
    public static final Color SUCCESS_DARK = darker(SUCCESS_PRIMARY, 0.50f);

    /**
     * Cor de aviso.
     */
    public static final Color WARNING_PRIMARY = rgb(230, 190, 110);
    public static final Color WARNING_SECONDARY = darker(WARNING_PRIMARY, 0.15f);
    public static final Color WARNING_MUTED = darker(WARNING_PRIMARY, 0.30f);
    public static final Color WARNING_DARK = darker(WARNING_PRIMARY, 0.50f);

    /**
     * Cor de erro.
     */
    public static final Color ERROR_PRIMARY = rgb(220, 120, 120);
    public static final Color ERROR_SECONDARY = darker(ERROR_PRIMARY, 0.15f);
    public static final Color ERROR_MUTED = darker(ERROR_PRIMARY, 0.30f);
    public static final Color ERROR_DARK = darker(ERROR_PRIMARY, 0.50f);

    private Colors() {
    }

    /**
     * Cria cor RGB.
     *
     * @param red - Valor vermelho
     * @param green - Valor verde
     * @param blue - Valor azul
     * @return Color - Cor criada
     */
    private static Color rgb(
            int red,
            int green,
            int blue
    ) {
        return new Color(red, green, blue);
    }

    /**
     * Cria variação com transparência.
     *
     * @param color - Cor base
     * @param alpha - Transparência
     * @return Color - Cor resultante
     */
    private static Color alpha(
            Color color,
            int alpha
    ) {
        return new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                alpha
        );
    }

    /**
     * Clareia uma cor.
     *
     * @param color - Cor base
     * @param factor - Intensidade do efeito
     * @return Color - Cor resultante
     */
    private static Color brighter(
            Color color,
            float factor
    ) {
        int red = Math.min(255, (int) (color.getRed() * (1 + factor)));
        int green = Math.min(255, (int) (color.getGreen() * (1 + factor)));
        int blue = Math.min(255, (int) (color.getBlue() * (1 + factor)));
        return new Color(red, green, blue);
    }

    /**
     * Escurece uma cor.
     *
     * @param color - Cor base
     * @param factor - Intensidade do efeito
     * @return Color - Cor resultante
     */
    private static Color darker(
            Color color,
            float factor
    ) {
        int red = Math.max(0, (int) (color.getRed() * (1 - factor)));
        int green = Math.max(0, (int) (color.getGreen() * (1 - factor)));
        int blue = Math.max(0, (int) (color.getBlue() * (1 - factor)));
        return new Color(red, green, blue);
    }

}
