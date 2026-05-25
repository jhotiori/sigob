package org.javapi.sigob.view.base;

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
     * Painel raiz da tela.
     *
     * @see JPanel
     */
    private JPanel root;

    /**
     * Estado de inicialização.
     */
    private boolean initialized;

    /**
     * Cria tela base.
     *
     * @param id - Identificador da tela
     */
    protected BaseScreen(String id) {
        this.id = id;
    }

    /**
     * Inicializa estrutura da tela.
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
     * Realiza setup interno da tela.
     *
     * Executado apenas uma vez após a construção.
     */
    protected void setup() {

    }

    /**
     * Executado ao exibir a tela.
     */
    public void onShow() {

    }

    /**
     * Executado ao ocultar a tela.
     */
    public void onHide() {

    }

    /**
     * Atualiza dados dinâmicos da tela.
     */
    public void refresh() {

    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz
     */
    protected abstract JPanel build();

    /**
     * Retorna identificador da tela.
     *
     * @return String - Identificador da tela
     */
    public String id() {
        return id;
    }

    /**
     * Retorna painel raiz.
     *
     * @return JPanel - Painel raiz
     */
    public JPanel root() {
        return root;
    }

    /**
     * Retorna estado de inicialização.
     *
     * @return boolean - Estado da tela
     */
    public boolean initialized() {
        return initialized;
    }

}
