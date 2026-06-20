package org.javapi.sigob.view.v2.framework.ui;

import org.javapi.sigob.view.v2.framework.layouts.FormBuilder;

/**
 * Utilitário para criação de formulários.
 */
public final class UIForms {

    /**
     * Construtor privado para evitar instanciação.
     */
    private UIForms() {
    }

    /**
     * Cria construtor de formulário.
     *
     * @return FormBuilder - Construtor criado
     */
    public static FormBuilder create() {
        return new FormBuilder();
    }

}
