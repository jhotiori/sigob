package org.javapi.sigob.view.v2.dialogs.base;

import javax.swing.JPanel;

/**
 * Classe base para dialogos.
 */
public abstract class BaseCustomDialog {
    /**
     * Título do dialogo.
     */
    private final String title;

    /**
     * Construtor.
     *
     * @param title - Título do dialogo
     * @return BaseDialog - Base da classe
     */
    public BaseCustomDialog(String title) {
        this.title = title;
    }

    /**
     * Obtem título do dialogo.
     *
     * @return String - Título do dialogo
     */
    public String title() {
        return title;
    }

    /**
     * Constrói dialogo.
     *
     * @return JPanel - Painel raiz
     */
    public abstract JPanel build();
}
