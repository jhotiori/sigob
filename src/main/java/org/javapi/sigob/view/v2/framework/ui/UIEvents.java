package org.javapi.sigob.view.v2.framework.ui;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.javapi.sigob.view.v2.framework.events.ButtonEvents;
import org.javapi.sigob.view.v2.framework.events.ComboBoxEvents;
import org.javapi.sigob.view.v2.framework.events.FocusEvents;
import org.javapi.sigob.view.v2.framework.events.ListEvents;
import org.javapi.sigob.view.v2.framework.events.TableEvents;
import org.javapi.sigob.view.v2.framework.events.TextEvents;

/**
 * Fábrica de manipuladores de eventos.
 */
public final class UIEvents {

    /**
     * Construtor privado para evitar instanciação.
     */
    private UIEvents() {
    }

    /**
     * Associa eventos a um botão.
     *
     * @param component - Componente alvo
     * @return ButtonEvents - Manipulador criado
     */
    public static ButtonEvents bind(
            AbstractButton component
    ) {
        return new ButtonEvents(component);
    }

    /**
     * Associa eventos a um campo de texto.
     *
     * @param component - Componente alvo
     * @return TextEvents - Manipulador criado
     */
    public static TextEvents bind(
            JTextField component
    ) {
        return new TextEvents(component);
    }

    /**
     * Associa eventos a uma tabela.
     *
     * @param component - Componente alvo
     * @return TableEvents - Manipulador criado
     */
    public static TableEvents bind(
            JTable component
    ) {
        return new TableEvents(component);
    }

    /**
     * Associa eventos de foco.
     *
     * @param component - Componente alvo
     * @return FocusEvents<JComponent> - Manipulador criado
     */
    public static FocusEvents<JComponent> bind(
            JComponent component
    ) {
        return new FocusEvents<>(component);
    }

    /**
     * Associa eventos a um ComboBox.
     *
     * @param component - Componente alvo
     * @param <T> - Tipo dos itens
     * @return ComboBoxEvents<T> - Manipulador criado
     */
    public static <T> ComboBoxEvents<T> bind(
            JComboBox<T> component
    ) {
        return new ComboBoxEvents<>(component);
    }

    /**
     * Associa eventos a uma lista.
     *
     * @param component - Componente alvo
     * @param <T> - Tipo dos itens
     * @return ListEvents<T> - Manipulador criado
     */
    public static <T> ListEvents<T> bind(
            JList<T> component
    ) {
        return new ListEvents<>(component);
    }

}
