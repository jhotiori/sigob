package org.javapi.sigob.view.v2.framework.events;

import java.util.function.Consumer;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.javapi.sigob.view.v2.framework.events.base.ComponentEvents;

/**
 * Eventos para campos de texto.
 */
public class TextEvents
        extends ComponentEvents<JTextField> {

    /**
     * Cria manipulador de eventos.
     *
     * @param component - Campo alvo
     */
    public TextEvents(
            JTextField component
    ) {
        super(component);
    }

    /**
     * Registra evento de alteração.
     *
     * @param action - Ação executada
     * @return TextEvents - Instância atual
     */
    public TextEvents onChange(
            Consumer<String> action
    ) {
        component.getDocument().addDocumentListener(
                new DocumentListener() {
            @Override
            public void insertUpdate(
                    DocumentEvent event
            ) {
                notifyChange();
            }

            @Override
            public void removeUpdate(
                    DocumentEvent event
            ) {
                notifyChange();
            }

            @Override
            public void changedUpdate(
                    DocumentEvent event
            ) {
                notifyChange();
            }

            private void notifyChange() {
                action.accept(
                        component.getText()
                );
            }
        }
        );

        return this;
    }

    /**
     * Registra evento ao pressionar Enter.
     *
     * @param action - Ação executada
     * @return TextEvents - Instância atual
     */
    public TextEvents onEnter(
            Runnable action
    ) {
        component.addActionListener(
                event -> action.run()
        );

        return this;
    }

}
