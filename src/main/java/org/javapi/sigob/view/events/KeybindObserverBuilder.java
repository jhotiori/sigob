package org.javapi.sigob.view.events;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Builder fluente para atalhos de teclado.
 */
public class KeybindObserverBuilder {

    /**
     * Componente alvo.
     *
     * @see {@link JComponent}
     */
    private final JComponent component;

    /**
     * Cria builder de atalhos.
     *
     * @param component - Componente alvo
     */
    public KeybindObserverBuilder(JComponent component) {
        this.component = component;
    }

    /**
     * Adiciona atalho de teclado.
     *
     * @param key - Atalho configurado
     * @param callback - Callback do atalho
     */
    public void onKeypress(String key, Runnable callback) {
        if (key == null || callback == null) {
            return;
        }

        String actionKey = "sigob-keybind-" + key + "-" + System.nanoTime();

        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), actionKey);
        component.getActionMap().put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                callback.run();
            }
        });
    }

}
