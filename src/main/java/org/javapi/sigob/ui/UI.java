package org.javapi.sigob.ui;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Ponto de entrada para criação de componentes Swing.
 */
public final class UI {

    /**
     * Construtor privado para evitar instanciamento.
     */
    private UI() {
    }

    /**
     * Cria e configura componentes Swing.
     *
     * @param component - Componente alvo
     * @param config - Configuração do componente
     * @return T - Instância configurada
     */
    private static <T extends JComponent> T build(T component, Consumer<T> config) {
        if (config != null) {
            config.accept(component);
        }
        return component;
    }

    /**
     * Adiciona múltiplos componentes filhos a um componente pai.
     *
     * @param parent - Componente pai
     * @param children - Componentes filhos
     * @param <T> - Tipo do componente pai
     * @return T - Componente pai com filhos adicionados
     */
    public static <T extends JComponent> T children(T parent, JComponent... children) {
        if (children != null) {
            for (JComponent child : children) {
                parent.add(child);
            }
        }
        return parent;
    }

    /**
     * Envolve um componente em um JScrollPane.
     *
     * @param component - Componente alvo
     * @return JScrollPane - Componente com scroll
     */
    public static JScrollPane scroll(JComponent component) {
        return new JScrollPane(component);
    }

    /**
     * Cria e configura um JPanel.
     *
     * @param config - Configuração do componente
     * @return JPanel
     */
    public static JPanel panel(Consumer<JPanel> config) {
        return build(new JPanel(), config);
    }

    /**
     * Cria e configura um JPanel com componentes filhos.
     *
     * @param config - Configuração do componente
     * @param children - Componentes filhos
     * @return JPanel
     */
    public static JPanel panel(Consumer<JPanel> config, JComponent... children) {
        JPanel panel = panel(config);
        return children(panel, children);
    }

    /**
     * Cria um JButton com texto.
     *
     * @param text - Texto do botão
     * @return JButton
     */
    public static JButton button(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        return button;
    }

    /**
     * Cria e configura um JButton com texto.
     *
     * @param text - Texto do botão
     * @param config - Configuração do componente
     * @return JButton
     */
    public static JButton button(String text, Consumer<JButton> config) {
        return build(button(text), config);
    }

    /**
     * Cria e configura um JButton.
     *
     * @param config - Configuração do componente
     * @return JButton
     */
    public static JButton button(Consumer<JButton> config) {
        return build(new JButton(), config);
    }

    /**
     * Cria e configura um JLabel com texto.
     *
     * @param text - Texto do label
     * @param config - Configuração do componente
     * @return JLabel
     */
    public static JLabel label(String text, Consumer<JLabel> config) {
        return build(new JLabel(text), config);
    }

    /**
     * Cria e configura um JLabel.
     *
     * @param config - Configuração do componente
     * @return JLabel
     */
    public static JLabel label(Consumer<JLabel> config) {
        return build(new JLabel(), config);
    }

    /**
     * Cria e configura um JTextField com texto inicial.
     *
     * @param text - Texto inicial
     * @param config - Configuração do componente
     * @return JTextField
     */
    public static JTextField textField(String text, Consumer<JTextField> config) {
        return build(new JTextField(text), config);
    }

    /**
     * Cria e configura um JTextField.
     *
     * @param config - Configuração do componente
     * @return JTextField
     */
    public static JTextField textField(Consumer<JTextField> config) {
        return build(new JTextField(), config);
    }

    /**
     * Cria e configura um JTextArea.
     *
     * @param config - Configuração do componente
     * @return JTextArea
     */
    public static JTextArea textArea(Consumer<JTextArea> config) {
        return build(new JTextArea(), config);
    }

    /**
     * Cria e configura um JFrame com título.
     *
     * @param title - Título da janela
     * @param config - Configuração do frame
     * @return JFrame
     */
    public static JFrame frame(String title, Consumer<JFrame> config) {
        JFrame frame = new JFrame(title);

        if (config != null) {
            config.accept(frame);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    /**
     * Cria e configura um JFrame.
     *
     * @param config - Configuração do frame
     * @return JFrame
     */
    public static JFrame frame(Consumer<JFrame> config) {
        return frame("", config);
    }
}
