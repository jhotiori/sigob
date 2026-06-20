package org.javapi.sigob.view.v2.framework.events;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;

import org.javapi.sigob.view.v2.framework.events.base.ComponentEvents;

/**
 * Eventos de foco para componentes.
 *
 * @param <T> - Tipo do componente
 */
public class FocusEvents<T extends JComponent> extends ComponentEvents<T> {

    /**
     * Cria manipulador de eventos.
     *
     * @param component - Componente alvo
     */
    public FocusEvents(T component) {
        super(component);
    }

    /**
     * Registra evento ao receber foco.
     *
     * @param action - Ação executada
     * @return FocusEvents<T> - Instância atual
     */
    public FocusEvents<T> onFocus(Runnable action) {
        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event) {
                action.run();
            }
        });

        return this;
    }

    /**
     * Registra evento ao perder foco.
     *
     * @param action - Ação executada
     * @return FocusEvents<T> - Instância atual
     */
    public FocusEvents<T> onFocusLost(Runnable action) {
        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                action.run();
            }
        });

        return this;
    }

}
