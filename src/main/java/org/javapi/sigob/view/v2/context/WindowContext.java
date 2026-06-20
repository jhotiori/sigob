package org.javapi.sigob.view.v2.context;

import org.javapi.sigob.view.v2.framework.base.BaseWindow;

/**
 * Contexto de janela da aplicação.
 */
public final class WindowContext {
    /**
     * Janela atual da aplicação.
     *
     * @see BaseWindow
     */
    private static BaseWindow window;

    /**
     * Define janela atual da aplicação.
     *
     * @param window - Janela atual
     */
    public static void setCurrentWindow(BaseWindow window) {
        WindowContext.window = window;
    }

    /**
     * Retorna janela atual da aplicação.
     *
     * @return BaseWindow - Janela atual
     */
    public static BaseWindow getCurrentWindow() {
        return window;
    }

    /**
     * Verifica se janela atual da aplicação foi definida.
     *
     * @return boolean - Se janela atual da aplicação foi definida
     */
    public static boolean hasCurrentWindow() {
        return window != null;
    }

    /**
     * Fecha janela atual da aplicação.
     */
    public static void disposeCurrentWindow() {
        if (!hasCurrentWindow()) {
            return;
        }

        window.dispose();
        window = null;
    }

    /**
     * Construtor privado.
     */
    private WindowContext() {

    }
}
