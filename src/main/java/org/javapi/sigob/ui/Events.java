package org.javapi.sigob.ui;

import java.awt.Component;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.javapi.sigob.ui.bindings.key.KeyBinder;
import org.javapi.sigob.ui.bindings.key.KeyListenerBinding;
import org.javapi.sigob.ui.bindings.mouse.MouseListenerBinding;
import org.javapi.sigob.ui.bindings.text.DocumentListenerBinding;

/**
 * Ponto de entrada para criação de bindings de eventos.
 */
public final class Events {

    private Events() {
    }

    /**
     * Cria e configura bindings de mouse.
     *
     * @param component - Componente alvo
     * @param config - Configuração dos handlers
     * @return MouseListenerBinding - Instância configurada
     */
    public static MouseListenerBinding mouse(Component component, Consumer<MouseListenerBinding> config) {
        MouseListenerBinding binding = new MouseListenerBinding();

        if (config != null) {
            config.accept(binding);
        }

        binding.install(component);
        return binding;
    }

    /**
     * Cria e configura bindings de teclado via KeyListener.
     *
     * @param component - Componente alvo
     * @param config - Configuração dos handlers
     * @return KeyListenerBinding - Instância configurada
     */
    public static KeyListenerBinding key(Component component, Consumer<KeyListenerBinding> config) {
        KeyListenerBinding binding = new KeyListenerBinding();

        if (config != null) {
            config.accept(binding);
        }

        binding.install(component);
        return binding;
    }

    /**
     * Cria e configura bindings de texto via DocumentListener.
     *
     * @param component - Componente alvo
     * @param config - Configuração dos handlers
     * @return DocumentListenerBinding - Instância configurada
     */
    public static DocumentListenerBinding text(JTextComponent component, Consumer<DocumentListenerBinding> config) {
        DocumentListenerBinding binding = new DocumentListenerBinding();

        if (config != null) {
            config.accept(binding);
        }

        binding.install(component);
        return binding;
    }

    /**
     * Cria bindings de atalhos de teclado via InputMap/ActionMap.
     *
     * @param component - Componente alvo
     * @return KeyBind - Builder de atalhos
     */
    public static KeyBinder keyBinder(JComponent component, Consumer<KeyBinder> config) {
        KeyBinder binding = new KeyBinder(component);

        if (config != null) {
            config.accept(binding);
        }

        return binding;
    }
}
