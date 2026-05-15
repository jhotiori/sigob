package org.javapi.sigob.view.windows;

import javax.swing.JFrame;

/**
 * Representa janela base da aplicação.
 */
public abstract class BaseWindow {

    /**
     * Janela raiz.
     *
     * @see {@link JFrame}
     */
    private JFrame root;

    /**
     * Cria janela base.
     */
    protected BaseWindow() {
    }

    /**
     * Inicializa janela.
     */
    protected final void init() {
        this.root = build();
    }

    /**
     * Constrói janela.
     *
     * @return JFrame - Janela construída
     */
    protected abstract JFrame build();

    /**
     * Exibe janela.
     */
    public void show() {
        root.setVisible(true);
    }

    /**
     * Oculta janela.
     */
    public void hide() {
        root.setVisible(false);
    }

    /**
     * Fecha janela.
     */
    public void dispose() {
        root.dispose();
    }

    /**
     * Retorna janela raiz.
     *
     * @return JFrame - Janela raiz
     */
    public JFrame root() {
        return root;
    }

}
