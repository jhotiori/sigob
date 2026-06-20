package org.javapi.sigob.view.v2.framework.events;

import java.util.function.Consumer;

import javax.swing.JComboBox;

import org.javapi.sigob.view.v2.framework.events.base.ComponentEvents;

/**
 * Eventos para ComboBox.
 *
 * @param <T> - Tipo dos itens
 */
public class ComboBoxEvents<T> extends ComponentEvents<JComboBox<T>> {

    /**
     * Cria manipulador de eventos.
     *
     * @param component - Componente alvo
     */
    public ComboBoxEvents(
            JComboBox<T> component
    ) {
        super(component);
    }

    /**
     * Registra evento de seleção.
     *
     * @param action - Ação executada
     * @return ComboBoxEvents<T> - Instância atual
     */
    @SuppressWarnings("unchecked")
    public ComboBoxEvents<T> onSelection(
            Consumer<T> action
    ) {
        component.addActionListener(event
                -> action.accept(
                        (T) component.getSelectedItem()
                )
        );

        return this;
    }

}
