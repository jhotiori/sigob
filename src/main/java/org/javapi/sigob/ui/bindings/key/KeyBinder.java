package org.javapi.sigob.ui.bindings.key;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Builder para associação de atalhos de teclado via InputMap e ActionMap.
 */
public final class KeyBinder {

    /**
     * Componente associado aos atalhos
     *
     * @see {@link JComponent}
     */
    private final JComponent component;

    /**
     * Cria uma nova instância de KeyBind.
     *
     * @param component - Componente associado aos atalhos
     * @return KeyBinder - A instância criada
     */
    public KeyBinder(JComponent component) {
        this.component = component;
    }

    /**
     * Associa uma tecla a uma ação simples.
     *
     * @param key - Representação da tecla (ex: "ENTER", "ctrl S")
     * @param handler - Ação executada ao acionar a tecla
     * @return KeyBinder - Instância atual para encadeamento
     */
    public KeyBinder on(String key, Consumer<ActionEvent> handler) {
        if (handler == null) {
            return this;
        }

        KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
        if (keyStroke == null) {
            throw new IllegalArgumentException("Key (KeyStroke) inválida: " + key);
        }

        String id = normalizeKey(key);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, id);
        component.getActionMap().put(id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.accept(e);
            }
        });

        return this;
    }

    /**
     * Associa uma tecla a uma ação simples.
     *
     * @param key - Representação da tecla (ex: "ENTER", "ctrl S")
     * @param handler - Ação executada ao acionar a tecla
     * @return KeyBinder - Instância atual para encadeamento
     */
    public KeyBinder on(String key, Runnable handler) {
        if (handler == null) {
            return this;
        }
        return on(key, event -> handler.run());
    }

    /**
     * Desassocia uma tecla de uma ação.
     *
     * @param key - Representação da tecla (ex: "ENTER", "ctrl S")
     * @return KeyBind - Instância atual para encadeamento
     */
    public KeyBinder off(String key) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
        if (keyStroke == null) {
            return this;
        }

        String id = normalizeKey(key);
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(keyStroke);
        component.getActionMap().remove(id);

        return this;
    }

    /**
     * Normaliza a representação da tecla para uso no InputMap e ActionMap.
     *
     * @param key - Representação da tecla (ex: "ENTER", "ctrl S")
     * @return String - Representação normalizada
     */
    private String normalizeKey(String key) {
        key = key.toUpperCase().trim();
        return "KEYBIND@%s".formatted(key);
    }
}
