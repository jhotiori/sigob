package org.javapi.sigob.view.styles;

import java.awt.Font;

/**
 * Fontes centralizadas da interface.
 */
public final class Fonts {

    /**
     * Nome da fonte padrão.
     */
    public static final String DEFAULT_FONT_NAME = "Segoe UI";

    /**
     * Fonte padrão e variantes.
     */
    public static final Font DEFAULT = new Font(DEFAULT_FONT_NAME, Font.PLAIN, 14);
    public static final Font DEFAULT_BOLD = new Font(DEFAULT_FONT_NAME, Font.BOLD, 14);
    public static final Font DEFAULT_ITALIC = new Font(DEFAULT_FONT_NAME, Font.ITALIC, 14);

    /**
     * Fonte média e variantes.
     */
    public static final Font MEDIUM = new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18);
    public static final Font MEDIUM_BOLD = new Font(DEFAULT_FONT_NAME, Font.BOLD, 18);
    public static final Font MEDIUM_ITALIC = new Font(DEFAULT_FONT_NAME, Font.ITALIC, 18);

    /**
     * Fonte pequena e variantes.
     */
    public static final Font SMALL = new Font(DEFAULT_FONT_NAME, Font.PLAIN, 12);
    public static final Font SMALL_BOLD = new Font(DEFAULT_FONT_NAME, Font.BOLD, 12);
    public static final Font SMALL_ITALIC = new Font(DEFAULT_FONT_NAME, Font.ITALIC, 12);

    /**
     * Fonte de título e variantes.
     */
    public static final Font TITLE_BIG = new Font("Inter", Font.BOLD, 48);
    public static final Font TITLE_MEDIUM = new Font("Inter", Font.BOLD, 32);
    public static final Font TITLE_SMALL = new Font("Inter", Font.BOLD, 24);

    public static final Font TITLE_BIG_ITALIC = new Font("Inter", Font.ITALIC, 48);
    public static final Font TITLE_MEDIUM_ITALIC = new Font("Inter", Font.ITALIC, 32);
    public static final Font TITLE_SMALL_ITALIC = new Font("Inter", Font.ITALIC, 24);

    /**
     * Impede instanciação.
     */
    private Fonts() {

    }

}
