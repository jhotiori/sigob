package org.javapi.sigob.view.events;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * Builder fluente para eventos de texto.
 */
public class TextObserverBuilder {

    /**
     * Campo de texto alvo.
     *
     * @see {@link JTextComponent}
     */
    private final JTextComponent component;

    /**
     * Cria builder de eventos.
     *
     * @param component - Campo de texto alvo
     */
    public TextObserverBuilder(JTextComponent component) {
        this.component = component;
    }

    /**
     * Adiciona evento de alteração de texto.
     *
     * @param callback - Callback do evento
     */
    public void onChanged(Runnable callback) {
        if (callback == null) {
            return;
        }

        component.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                callback.run();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                callback.run();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                callback.run();
            }
        });
    }

}
