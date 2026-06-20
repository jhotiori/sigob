package org.javapi.sigob.view.v2.framework.base;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Representa tela base da aplicação.
 */
public abstract class BaseScreen {

    /**
     * Identificador da tela.
     */
    private final String id;

    /**
     * Painel raiz.
     */
    private JPanel root;

    /**
     * Estado de inicialização.
     */
    private boolean initialized;

    /**
     * Listeners de exibição.
     */
    private final List<Runnable> SHOW_LISTENERS = new ArrayList<>();

    /**
     * Listeners de ocultação.
     */
    private final List<Runnable> HIDE_LISTENERS = new ArrayList<>();

    /**
     * Listeners de atualização.
     */
    private final List<Runnable> UPDATE_LISTENERS = new ArrayList<>();

    /**
     * Cria tela base.
     *
     * @param id - Identificador da tela
     */
    protected BaseScreen(String id) {
        this.id = id;
    }

    /**
     * Inicializa tela.
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
     * Mostra a tela.
     */
    public final void show() {
        root.setVisible(true);
        notifyShow();
    }

    /**
     * Oculta a tela.
     */
    public final void hide() {
        notifyHide();
        root.setVisible(false);
    }

    /**
     * Alterna visibilidade da tela.
     */
    public final void toggle() {
        if (root.isVisible()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * Atualiza conteúdo dinâmico.
     */
    public final void update() {
        notifyUpdate();
    }

    /**
     * Registra listener de exibição.
     *
     * @param listener - Listener
     */
    public final void onShow(Runnable listener) {
        if (listener == null) {
            return;
        }

        SHOW_LISTENERS.add(listener);
    }

    /**
     * Registra listener de ocultação.
     *
     * @param listener - Listener
     */
    public final void onHide(Runnable listener) {
        if (listener == null) {
            return;
        }

        HIDE_LISTENERS.add(listener);
    }

    /**
     * Registra listener de atualização.
     *
     * @param listener - Listener
     */
    public final void onUpdate(Runnable listener) {
        if (listener == null) {
            return;
        }

        UPDATE_LISTENERS.add(listener);
    }

    /**
     * Executa listeners de exibição.
     */
    private void notifyShow() {
        SHOW_LISTENERS.forEach(Runnable::run);
    }

    /**
     * Executa listeners de ocultação.
     */
    private void notifyHide() {
        HIDE_LISTENERS.forEach(Runnable::run);
    }

    /**
     * Executa listeners de atualização.
     */
    private void notifyUpdate() {
        UPDATE_LISTENERS.forEach(Runnable::run);
    }

    /**
     * Realiza setup interno.
     */
    protected void setup() {

    }

    /**
     * Constrói tela.
     *
     * @return JPanel - Painel raiz
     */
    protected abstract JPanel build();

    /**
     * Retorna identificador.
     *
     * @return String - Identificador
     */
    public final String id() {
        return id;
    }

    /**
     * Retorna painel raiz.
     *
     * @return JPanel - Painel raiz
     */
    public final JPanel panel() {
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
