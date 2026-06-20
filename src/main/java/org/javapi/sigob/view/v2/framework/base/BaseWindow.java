package org.javapi.sigob.view.v2.framework.base;

import javax.swing.JFrame;

/**
 * Representa janela base da aplicação.
 */
public abstract class BaseWindow {

    /**
     * Janela raiz.
     */
    private JFrame root;

    /**
     * Estado de inicialização.
     */
    private boolean initialized;

    /**
     * Inicializa janela.
     */
    public final void initialize() {
        if (initialized) {
            return;
        }

        root = build();
        setup();
        initialized = true;
    }
    /**
     * Realiza setup interno.
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
     * Atualiza conteúdo dinâmico.
     */
    protected void onUpdate() {
    }

    /**
     * Constrói janela.
     *
     * @return JFrame - Janela criada
     */
    protected abstract JFrame build();

    /**
     * Exibe janela.
     */
    public final void show() {
        root().setVisible(true);
        onShow();
    }

    /**
     * Oculta janela.
     */
    public final void hide() {
        onHide();
        root().setVisible(false);
    }

    /**
     * Alterna visibilidade da janela.
     */
    public final void toggle() {
        if (root().isVisible()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * Atualiza janela.
     */
    public final void update() {
        onUpdate();
    }

    /**
     * Fecha janela.
     */
    public final void dispose() {
        root().dispose();
    }

    /**
     * Retorna janela raiz.
     *
     * @return JFrame - Janela raiz
     */
    public final JFrame root() {
        initialize();
        return root;
    }

    /**
     * Retorna estado de inicialização.
     *
     * @return boolean - Estado atual
     */
    public final boolean initialized() {
        return initialized;
    }

}
