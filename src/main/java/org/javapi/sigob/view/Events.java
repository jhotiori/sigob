package org.javapi.sigob.view;

import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.javapi.sigob.view.events.KeybindObserverBuilder;
import org.javapi.sigob.view.events.MouseObserverBuilder;
import org.javapi.sigob.view.events.TextObserverBuilder;

/**
 * Utilitário para eventos Swing.
 */
public final class Events {

    /**
     * Impede instanciação.
     */
    private Events() {
    }

    /**
     * Configura eventos de mouse.
     *
     * @param component - Componente alvo
     * @param config - Configuração dos eventos
     */
    public static void mouse(JComponent component, Consumer<MouseObserverBuilder> config) {
        if (component == null || config == null) {
            return;
        }

        config.accept(new MouseObserverBuilder(component));
    }

    /**
     * Configura atalhos de teclado.
     *
     * @param component - Componente alvo
     * @param config - Configuração dos atalhos
     */
    public static void keybind(JComponent component, Consumer<KeybindObserverBuilder> config) {
        if (component == null || config == null) {
            return;
        }

        config.accept(new KeybindObserverBuilder(component));
    }

    /**
     * Configura eventos de texto.
     *
     * @param component - Campo de texto alvo
     * @param config - Configuração dos eventos
     */
    public static void text(JTextComponent component, Consumer<TextObserverBuilder> config) {
        if (component == null || config == null) {
            return;
        }

        config.accept(new TextObserverBuilder(component));
    }
}
