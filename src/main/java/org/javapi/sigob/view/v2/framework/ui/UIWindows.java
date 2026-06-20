package org.javapi.sigob.view.v2.framework.ui;

import org.javapi.sigob.view.v2.framework.layouts.FrameBuilder;

public final class UIWindows {
    /**
     * Cria um builder para construção de janelas.
     *
     * @return FrameBuilder - Builder criado
     */
    public static FrameBuilder create() {
        return new FrameBuilder();
    }
}
