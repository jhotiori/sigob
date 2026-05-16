package org.javapi.sigob.view.screens;

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
     * @see {@link JPanel}
     */
    private JPanel root;

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
    protected final void init() {
        this.root = build();
    }

    /**
     * Realiza setup de forma interna.
     */
    protected void setup() {}

    /**
     * Realiza update de forma interna.
     */
    public void update() {}

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

}
