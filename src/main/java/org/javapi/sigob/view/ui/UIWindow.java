package org.javapi.sigob.view.ui;

import javax.swing.JFrame;

import org.javapi.sigob.view.builders.FrameBuilder;

/**
 * Utilitários relacionados a janelas.
 */
public final class UIWindow {

    /**
     * Impede instanciação.
     */
    private UIWindow() {

    }

    /**
     * Cria builder de frame.
     *
     * @return FrameBuilder - Builder criado
     */
    public static FrameBuilder create() {
        return new FrameBuilder();
    }

    /**
     * Cria builder usando frame existente.
     *
     * @param frame - Frame existente
     * @return FrameBuilder - Builder criado
     */
    public static FrameBuilder create(JFrame frame) {
        return new FrameBuilder(frame);
    }
}
