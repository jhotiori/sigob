package org.javapi.sigob.view.v2.framework.events.base;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Classe base para eventos de componentes.
 *
 * @param <T> - Tipo do componente
 */
public abstract class ComponentEvents<T extends JComponent> {

    /**
     * Componente alvo.
     */
    protected final T component;

    /**
     * Cria manipulador de eventos.
     *
     * @param component - Componente alvo
     */
    protected ComponentEvents(T component) {
        this.component = component;
    }

    /**
     * Registra evento ao pressionar tecla.
     *
     * @param keyCode - Código da tecla
     * @param action - Ação executada
     * @return ComponentEvents<T> - Instância atual
     */
    public ComponentEvents<T> onKeyPressed(
            int keyCode,
            Runnable action
    ) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == keyCode) {
                    action.run();
                }
            }
        });

        return this;
    }

    /**
     * Registra atalho de teclado.
     *
     * @param shortcut - Atalho
     * @param action - Ação executada
     * @return ComponentEvents<T> - Instância atual
     */
    public ComponentEvents<T> onShortcut(
            String shortcut,
            Runnable action
    ) {
        return onShortcut(
                KeyStroke.getKeyStroke(shortcut),
                action
        );
    }

    /**
     * Registra atalho de teclado.
     *
     * @param keystroke - Atalho
     * @param action - Ação executada
     * @return ComponentEvents<T> - Instância atual
     */
    public ComponentEvents<T> onShortcut(
            KeyStroke keystroke,
            Runnable action
    ) {
        String actionKey = "shortcut@" + System.nanoTime();

        component.getInputMap(
                JComponent.WHEN_IN_FOCUSED_WINDOW
        ).put(
                keystroke,
                actionKey
        );

        component.getActionMap().put(
                actionKey,
                new AbstractAction() {
            @Override
            public void actionPerformed(
                    ActionEvent event
            ) {
                action.run();
            }
        }
        );

        return this;
    }

}
