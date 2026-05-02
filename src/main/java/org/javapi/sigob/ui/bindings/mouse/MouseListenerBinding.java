package org.javapi.sigob.ui.bindings.mouse;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Builder fluente para associação de eventos de mouse em componentes Swing.
 */
public final class MouseListenerBinding {

    /**
     * Evento ao clicar com o botão do mouse.
     * @see {@link MouseEvent}
     */
    private Consumer<MouseEvent> onClicked = e -> {};

    /**
     * Evento ao pressionar o botão do mouse.
     * @see {@link MouseEvent}
     */
    private Consumer<MouseEvent> onPressed = e -> {};

    /**
     * Evento ao soltar o botão do mouse.
     * @see {@link MouseEvent}
     */
    private Consumer<MouseEvent> onReleased = e -> {};

    /**
     * Evento ao entrar com o cursor no componente.
     * @see {@link MouseEvent}
     */
    private Consumer<MouseEvent> onEntered = e -> {};

    /**
     * Evento ao sair com o cursor do componente.
     * @see {@link MouseEvent}
     */
    private Consumer<MouseEvent> onExited = e -> {};

    /**
     * Evento ao mover o mouse.
     * @see {@link MouseEvent}
     */
    private Consumer<MouseEvent> onMoved = e -> {};

    /**
     * Evento ao arrastar o mouse.
     * @see {@link MouseEvent}
     */
    private Consumer<MouseEvent> onDragged = e -> {};

    /**
     * Adaptador de eventos atualmente sendo usado.
     * @see {@link MouseAdapter}
     */
    private MouseAdapter adapter;

    public MouseListenerBinding() {
    }

    /**
     * Define comportamento para clique do mouse.
     *
     * @param handler - Ação executada ao clicar
     * @return MouseListenerBinding - Instância atual para encadeamento
     */
    public MouseListenerBinding onClicked(Consumer<MouseEvent> handler) {
        if (handler != null) {
            this.onClicked = this.onClicked.andThen(handler);
        }
        return this;
    }

    /**
     * Define comportamento para pressionar botão do mouse.
     *
     * @param handler - Ação executada ao pressionar
     * @return MouseListenerBinding - Instância atual para encadeamento
     */
    public MouseListenerBinding onPressed(Consumer<MouseEvent> handler) {
        if (handler != null) {
            this.onPressed = this.onPressed.andThen(handler);
        }
        return this;
    }

    /**
     * Define comportamento para soltar botão do mouse.
     *
     * @param handler - Ação executada ao soltar
     * @return MouseListenerBinding - Instância atual para encadeamento
     */
    public MouseListenerBinding onReleased(Consumer<MouseEvent> handler) {
        if (handler != null) {
            this.onReleased = this.onReleased.andThen(handler);
        }
        return this;
    }

    /**
     * Define comportamento ao entrar com o cursor no componente.
     *
     * @param handler - Ação executada ao entrar
     * @return MouseListenerBinding - Instância atual para encadeamento
     */
    public MouseListenerBinding onEntered(Consumer<MouseEvent> handler) {
        if (handler != null) {
            this.onEntered = this.onEntered.andThen(handler);
        }
        return this;
    }

    /**
     * Define comportamento ao sair com o cursor do componente.
     *
     * @param handler - Ação executada ao sair
     * @return MouseListenerBinding - Instância atual para encadeamento
     */
    public MouseListenerBinding onExited(Consumer<MouseEvent> handler) {
        if (handler != null) {
            this.onExited = this.onExited.andThen(handler);
        }
        return this;
    }

    /**
     * Define comportamento para movimento do mouse.
     *
     * @param handler - Ação executada ao mover
     * @return MouseListenerBinding - Instância atual para encadeamento
     */
    public MouseListenerBinding onMoved(Consumer<MouseEvent> handler) {
        if (handler != null) {
            this.onMoved = this.onMoved.andThen(handler);
        }
        return this;
    }

    /**
     * Define comportamento para arraste do mouse.
     *
     * @param handler - Ação executada ao arrastar
     * @return MouseListenerBinding - Instância atual para encadeamento
     */
    public MouseListenerBinding onDragged(Consumer<MouseEvent> handler) {
        if (handler != null) {
            this.onDragged = this.onDragged.andThen(handler);
        }
        return this;
    }

    /**
     * Instala os listeners no componente alvo.
     *
     * @param component - Componente que receberá os eventos
     * @return MouseAdapter - Adapter instalado para possível reutilização
     */
    public MouseAdapter install(Component component) {
        if (adapter != null) {
            component.removeMouseListener(adapter);
            component.removeMouseMotionListener(adapter);
        }
        adapter = build();
        component.addMouseListener(adapter);
        component.addMouseMotionListener(adapter);
        return adapter;
    }

    /**
     * Cria um MouseAdapter com os comportamentos configurados.
     *
     * @return MouseAdapter - Adapter com os comportamentos configurados
     */
    private MouseAdapter build() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClicked.accept(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                onPressed.accept(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                onReleased.accept(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                onEntered.accept(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                onExited.accept(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                onMoved.accept(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                onDragged.accept(e);
            }
        };
    }
}
