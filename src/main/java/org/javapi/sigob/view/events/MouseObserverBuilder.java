package org.javapi.sigob.view.events;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;

/**
 * Builder fluente para eventos de mouse.
 */
public class MouseObserverBuilder {

    /**
     * Componente alvo.
     *
     * @see {@link JComponent}
     */
    private final JComponent component;

    /**
     * Cria builder de eventos.
     *
     * @param component - Componente alvo
     */
    public MouseObserverBuilder(JComponent component) {
        this.component = component;
    }

    /**
     * Adiciona evento de clique.
     *
     * @param callback - Callback do evento
     */
    public void onClicked(Runnable callback) {
        if (callback == null) {
            return;
        }

        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent event) {
                callback.run();
            }

        });
    }

    /**
     * Adiciona evento de clique.
     *
     * @param callback - Callback do evento
     */
    public void onClicked(Consumer<MouseEvent> callback) {
        if (callback == null) {
            return;
        }

        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent event) {
                callback.accept(event);
            }

        });
    }

    /**
     * Adiciona evento de hover.
     *
     * @param callback - Callback do evento
     */
    public void onHover(Runnable callback) {
        if (callback == null) {
            return;
        }

        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent event) {
                callback.run();
            }

        });
    }

    /**
     * Adiciona evento de hover.
     *
     * @param callback - Callback do evento
     */
    public void onHover(Consumer<MouseEvent> callback) {
        if (callback == null) {
            return;
        }

        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent event) {
                callback.accept(event);
            }

        });
    }

    /**
     * Adiciona evento de saída do mouse.
     *
     * @param callback - Callback do evento
     */
    public void onLeave(Runnable callback) {
        if (callback == null) {
            return;
        }

        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent event) {
                callback.run();
            }

        });
    }

    /**
     * Adiciona evento de saída do mouse.
     *
     * @param callback - Callback do evento
     */
    public void onLeave(Consumer<MouseEvent> callback) {
        if (callback == null) {
            return;
        }

        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent event) {
                callback.accept(event);
            }

        });
    }

}
