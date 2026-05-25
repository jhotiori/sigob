package org.javapi.sigob.view.ui;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;

import org.javapi.sigob.view.Events;

/**
 * Utilitários relacionados aos eventos da UI.
 */
public final class UIEvents {
    /**
     * Previne instanciamento.
     */
    private UIEvents() {

    }

    /**
     * Adiciona evento de clique.
     *
     * @param component - Componente alvo
     * @param callback - Callback do evento
     */
    public static void onClick(JComponent component, Runnable callback) {
        Events.mouse(component, mouse -> {
            mouse.onClicked(() -> callback.run());
        });
    }

    /**
     * Adiciona evento de clique.
     *
     * @param component - Componente alvo
     * @param callback - Callback do evento
     */
    public static void onClick(JComponent component, Consumer<MouseEvent> callback) {
        Events.mouse(component, mouse -> {
            mouse.onClicked(callback);
        });
    }

}
