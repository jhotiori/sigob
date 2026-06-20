package org.javapi.sigob.view.v2.framework.events;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;

import org.javapi.sigob.view.v2.framework.events.base.ComponentEvents;

/**
 * Eventos para botões.
 */
public class ButtonEvents extends ComponentEvents<AbstractButton> {

    /**
     * Cria manipulador de eventos.
     *
     * @param component - Botão alvo
     */
    public ButtonEvents(
            AbstractButton component
    ) {
        super(component);
    }

    /**
     * Registra evento de clique.
     *
     * @param action - Ação executada
     * @return ButtonEvents - Instância atual
     */
    public ButtonEvents onClick(
            Runnable action
    ) {
        component.addActionListener(
                event -> action.run()
        );

        return this;
    }

    /**
     * Registra evento de hover.
     *
     * @param action - Ação executada
     * @return ButtonEvents - Instância atual
     */
    public ButtonEvents onHover(
            Runnable action
    ) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(
                    MouseEvent event
            ) {
                action.run();
            }
        });

        return this;
    }

    /**
     * Registra evento de saída de hover.
     *
     * @param action - Ação executada
     * @return ButtonEvents - Instância atual
     */
    public ButtonEvents onHoverExit(
            Runnable action
    ) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(
                    MouseEvent event
            ) {
                action.run();
            }
        });

        return this;
    }

}
