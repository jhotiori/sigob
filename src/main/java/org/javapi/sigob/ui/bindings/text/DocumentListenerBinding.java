package org.javapi.sigob.ui.bindings.text;

import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * Builder fluente para associação de eventos de texto em componentes Swing.
 */
public final class DocumentListenerBinding {

    /**
     * Evento ao alterar o conteúdo do texto.
     */
    private Consumer<String> onChange = text -> {
    };

    /**
     * Listener atualmente instalado.
     *
     * @see {@link DocumentListener}
     */
    private DocumentListener listener;

    public DocumentListenerBinding() {
    }

    /**
     * Define comportamento ao alterar o texto.
     *
     * @param handler - Ação executada ao modificar o conteúdo
     * @return DocumentListenerBinding - Instância atual para encadeamento
     */
    public DocumentListenerBinding onChange(Consumer<String> handler) {
        if (handler != null) {
            this.onChange = this.onChange.andThen(handler);
        }
        return this;
    }

    /**
     * Instala o listener no componente alvo.
     *
     * @param component - Componente de texto que receberá os eventos
     * @return DocumentListener - Listener instalado para possível reutilização
     */
    public DocumentListener install(JTextComponent component) {
        Document document = component.getDocument();
        if (listener != null) {
            document.removeDocumentListener(listener);
        }
        listener = build();
        document.addDocumentListener(listener);
        return listener;
    }

    /**
     * Cria um DocumentListener com os comportamentos configurados.
     *
     * @return DocumentListener - Listener com os comportamentos configurados
     */
    private DocumentListener build() {
        return new DocumentListener() {
            private void update(DocumentEvent event) {
                try {
                    Document document = event.getDocument();
                    String text = document.getText(0, document.getLength());
                    onChange.accept(text);
                } catch (Throwable exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void insertUpdate(DocumentEvent event) {
                update(event);
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                update(event);
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                update(event);
            }
        };
    }
}
