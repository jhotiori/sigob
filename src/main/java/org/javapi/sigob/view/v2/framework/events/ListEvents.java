package org.javapi.sigob.view.v2.framework.events;

import java.util.function.Consumer;

import javax.swing.JList;

import org.javapi.sigob.view.v2.framework.events.base.ComponentEvents;

/**
 * Eventos para listas.
 *
 * @param <T> - Tipo dos itens
 */
public class ListEvents<T> extends ComponentEvents<JList<T>> {

    /**
     * Cria manipulador de eventos.
     *
     * @param component - Componente alvo
     */
    public ListEvents(
            JList<T> component
    ) {
        super(component);
    }

    /**
     * Registra evento de seleção.
     *
     * @param action - Ação executada
     * @return ListEvents<T> - Instância atual
     */
    public ListEvents<T> onSelection(
            Consumer<T> action
    ) {
        component.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                action.accept(
                        component.getSelectedValue()
                );
            }
        });

        return this;
    }

}
