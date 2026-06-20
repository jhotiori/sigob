package org.javapi.sigob.view.v2.framework.events;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import org.javapi.sigob.view.v2.framework.events.base.ComponentEvents;

/**
 * Eventos para tabelas.
 */
public class TableEvents extends ComponentEvents<JTable> {

    /**
     * Cria manipulador de eventos.
     *
     * @param component - Tabela alvo
     */
    public TableEvents(
            JTable component
    ) {
        super(component);
    }

    /**
     * Registra evento de duplo clique.
     *
     * @param action - Ação executada
     * @return TableEvents - Instância atual
     */
    public TableEvents onDoubleClick(
            Runnable action
    ) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(
                    MouseEvent event
            ) {
                if (event.getClickCount() == 2) {
                    action.run();
                }
            }
        });

        return this;
    }

    /**
     * Registra evento de seleção.
     *
     * @param action - Ação executada
     * @return TableEvents - Instância atual
     */
    public TableEvents onSelection(
            Runnable action
    ) {
        component.getSelectionModel()
                .addListSelectionListener(
                        (ListSelectionEvent event) -> {
                            if (!event.getValueIsAdjusting()) {
                                action.run();
                            }
                        }
                );

        return this;
    }

}
