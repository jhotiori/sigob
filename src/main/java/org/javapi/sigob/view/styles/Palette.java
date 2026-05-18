package org.javapi.sigob.view.styles;

import java.awt.Color;

/**
 * Paleta centralizada de cores da interface.
 */
public final class Palette {

    /**
     * Cor base de fundo.
     */
    private static final Color BG_BASE = rgb(15);

    /**
     * Cor base de texto.
     */
    private static final Color FG_BASE = rgb(235);

    /**
     * Cor base de destaque.
     */
    private static final Color ACCENT_BASE = new Color(65, 135, 255);

    /**
     * Cor principal de fundo.
     */
    public static final Color BG_PRIMARY = BG_BASE;

    /**
     * Cor secundária de fundo.
     */
    public static final Color BG_SECONDARY = brighter(BG_BASE, 8);

    /**
     * Cor de superfície.
     */
    public static final Color BG_SURFACE = brighter(BG_BASE, 14);

    /**
     * Cor de destaque de fundo.
     */
    public static final Color BG_HIGHLIGHT = brighter(BG_BASE, 24);

    /**
     * Cor principal de texto.
     */
    public static final Color FG_PRIMARY = FG_BASE;

    /**
     * Cor secundária de texto.
     */
    public static final Color FG_SECONDARY = darker(FG_BASE, 30);

    /**
     * Cor suavizada de texto.
     */
    public static final Color FG_MUTED = darker(FG_BASE, 55);

    /**
     * Cor escura de texto.
     */
    public static final Color FG_DARK = darker(FG_BASE, 90);

    /**
     * Cor principal de destaque.
     */
    public static final Color ACCENT_PRIMARY = ACCENT_BASE;

    /**
     * Cor secundária de destaque.
     */
    public static final Color ACCENT_SECONDARY = darker(ACCENT_BASE, 18);

    /**
     * Cor suavizada de destaque.
     */
    public static final Color ACCENT_MUTED = darker(ACCENT_BASE, 42);

    /**
     * Cor escura de destaque.
     */
    public static final Color ACCENT_DARK = darker(ACCENT_BASE, 75);

    /**
     * Cor principal de borda.
     */
    public static final Color BORDER_PRIMARY = alpha(FG_PRIMARY, 25);

    /**
     * Cor secundária de borda.
     */
    public static final Color BORDER_SECONDARY = alpha(FG_PRIMARY, 14);

    /**
     * Cor de erro.
     */
    public static final Color ERROR = rgb(255, 80, 80);

    /**
     * Cor de sucesso.
     */
    public static final Color SUCCESS = rgb(110, 255, 110);

    /**
     * Cor de aviso.
     */
    public static final Color WARNING = rgb(255, 180, 110);

    /**
     * Cor de informação.
     */
    public static final Color INFO = rgb(110, 110, 255);

    /**
     * Impede instanciação.
     */
    private Palette() {

    }

    /**
     * Cria cor RGB simplificada.
     *
     * @param value - Valor RGB
     * @return Color - Cor criada
     */
    private static Color rgb(int value) {
        return new Color(value, value, value);
    }

    /**
     * Cria cor RGB simplificada.
     *
     * @param r - Valor vermelho
     * @param g - Valor verde
     * @param b - Valor azul
     * @return Color - Cor criada
     */
    private static Color rgb(int r, int g, int b) {
        return new Color(r, g, b);
    }

    /**
     * Clareia cor.
     *
     * @param color - Cor base
     * @param amount - Intensidade aplicada
     * @return Color - Cor clareada
     */
    public static Color brighter(Color color, int amount) {
        return new Color(
                clamp(color.getRed() + amount),
                clamp(color.getGreen() + amount),
                clamp(color.getBlue() + amount)
        );
    }

    /**
     * Escurece cor.
     *
     * @param color - Cor base
     * @param amount - Intensidade aplicada
     * @return Color - Cor escurecida
     */
    public static Color darker(Color color, int amount) {
        return new Color(
                clamp(color.getRed() - amount),
                clamp(color.getGreen() - amount),
                clamp(color.getBlue() - amount)
        );
    }

    /**
     * Aplica transparência sobre cor.
     *
     * @param color - Cor base
     * @param alpha - Transparência aplicada
     * @return Color - Cor resultante
     */
    public static Color alpha(Color color, int alpha) {
        return new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                clamp(alpha)
        );
    }

    /**
     * Limita valor RGB.
     *
     * @param value - Valor analisado
     * @return int - Valor ajustado
     */
    public static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

}
