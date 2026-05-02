package org.javapi.sigob.ui.bindings.key;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * Builder fluente para associação de eventos de teclado via KeyListener.
 */
public final class KeyListenerBinding {

    /**
     * Evento ao pressionar tecla.
     *
     * @see {@link KeyEvent}
     */
    private Consumer<KeyEvent> onPressed = e -> {
    };

    /**
     * Evento ao liberar tecla.
     *
     * @see {@link KeyEvent}
     */
    private Consumer<KeyEvent> onReleased = e -> {
    };

    /**
     * Evento ao digitar tecla.
     *
     * @see {@link KeyEvent}
     */
    private Consumer<KeyEvent> onTyped = e -> {
    };

    /**
     * Adaptador de eventos atualmente sendo usado.
     * @see {@link KeyAdapter}
     */
    private KeyAdapter adapter;

    public KeyListenerBinding() {
    }

    /**
     * Define comportamento para tecla pressionada.
     *
     * @param handler - Ação executada ao pressionar
     * @return KeyListenerBinding - Instância atual para encadeamento
     */
    public KeyListenerBinding onPressed(Consumer<KeyEvent> handler) {
        if (handler != null) {
            this.onPressed = this.onPressed.andThen(handler);
        }
        return this;
    }

    /**
     * Define comportamento para tecla liberada.
     *
     * @param handler - Ação executada ao liberar
     * @return KeyListenerBinding - Instância atual para encadeamento
     */
    public KeyListenerBinding onReleased(Consumer<KeyEvent> handler) {
        if (handler != null) {
            this.onReleased = this.onReleased.andThen(handler);
        }
        return this;
    }

    /**
     * Define comportamento para tecla digitada.
     *
     * @param handler - Ação executada ao digitar
     * @return KeyListenerBinding - Instância atual para encadeamento
     */
    public KeyListenerBinding onTyped(Consumer<KeyEvent> handler) {
        if (handler != null) {
            this.onTyped = this.onTyped.andThen(handler);
        }
        return this;
    }

    /**
     * Instala o listener no componente alvo.
     *
     * @param component - Componente que receberá os eventos
     * @return KeyAdapter - Adapter instalado para possível reutilização
     */
    public KeyAdapter install(Component component) {
        if (adapter != null) {
            component.removeKeyListener(adapter);
        }
        adapter = build();
        component.addKeyListener(adapter);
        component.setFocusable(true);
        component.requestFocusInWindow();
        return adapter;
    }

    /**
     * Cria um KeyAdapter com os comportamentos configurados.
     *
     * @return KeyAdapter - Adapter com os comportamentos configurados
     */
    private KeyAdapter build() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                onPressed.accept(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                onReleased.accept(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                onTyped.accept(e);
            }
        };
    }
}
