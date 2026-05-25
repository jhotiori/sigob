package org.javapi.sigob.view.base;

import javax.swing.JFrame;

/**
 * Representa janela base da aplicação.
 */
public abstract class BaseWindow {

    /**
     * Janela raiz.
     *
     * @see JFrame
     */
    private JFrame root;

    /**
     * Estado de inicialização.
     */
    private boolean initialized;

    /**
     * Cria janela base.
     */
    protected BaseWindow() {

    }

    /**
     * Inicializa janela.
     */
    public final void initialize() {
        if (initialized) {
            return;
        }

        this.root = build();

        setup();

        this.initialized = true;
    }

    /**
     * Realiza setup interno da janela.
     */
    protected void setup() {

    }

    /**
     * Executado ao exibir janela.
     */
    protected void onShow() {

    }

    /**
     * Executado ao ocultar janela.
     */
    protected void onHide() {

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
        onShow();

        root.setVisible(true);
    }

    /**
     * Oculta janela.
     */
    public void hide() {
        onHide();

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

    /**
     * Retorna estado de inicialização.
     *
     * @return boolean - Estado da janela
     */
    public boolean initialized() {
        return initialized;
    }

}
