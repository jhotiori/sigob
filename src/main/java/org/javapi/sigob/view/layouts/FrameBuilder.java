package org.javapi.sigob.view.layouts;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * Builder fluente para JFrame.
 */
public class FrameBuilder {

    /**
     * Frame interno do builder.
     *
     * @see {@link JFrame}
     */
    private final JFrame frame;

    /**
     * Cria builder de frame.
     */
    public FrameBuilder() {
        this(new JFrame());
    }

    /**
     * Cria builder usando frame existente.
     *
     * @param frame - Frame existente
     */
    public FrameBuilder(JFrame frame) {
        this.frame = frame != null
                ? frame
                : new JFrame();
    }

    /**
     * Define título do frame.
     *
     * @param title - Título do frame
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder title(String title) {
        frame.setTitle(title);

        return this;
    }

    /**
     * Define tamanho do frame.
     *
     * @param width - Largura do frame
     * @param height - Altura do frame
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder size(int width, int height) {
        frame.setSize(width, height);

        return this;
    }

    /**
     * Define icone do frame.
     *
     * @param icon - Icone do frame
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder icon(Image icon) {
        frame.setIconImage(icon);

        return this;
    }

    /**
     * Define tamanho mínimo do frame.
     *
     * @param width - Largura mínima
     * @param height - Altura mínima
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder minimumSize(int width, int height) {
        frame.setMinimumSize(new Dimension(width, height));

        return this;
    }

    /**
     * Define tamanho máximo do frame.
     *
     * @param width - Largura máxima
     * @param height - Altura máxima
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder maximumSize(int width, int height) {
        frame.setMaximumSize(new Dimension(width, height));

        return this;
    }

    /**
     * Define conteúdo principal do frame.
     *
     * @param component - Conteúdo principal
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder content(Component component) {
        if (component != null) {
            frame.setContentPane((java.awt.Container) component);
        }

        return this;
    }

    /**
     * Define barra de menu do frame.
     *
     * @param menuBar - Barra de menu
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder menubar(JMenuBar menuBar) {
        if (menuBar != null) {
            frame.setJMenuBar(menuBar);
        }

        return this;
    }

    /**
     * Centraliza frame na tela.
     *
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder center() {
        frame.setLocationRelativeTo(null);

        return this;
    }

    /**
     * Define redimensionamento do frame.
     *
     * @param resizable - Estado do redimensionamento
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder resizable(boolean resizable) {
        frame.setResizable(resizable);

        return this;
    }

    /**
     * Define visibilidade do frame.
     *
     * @param visible - Estado da visibilidade
     * @return FrameBuilder - Instância atual
     */
    public FrameBuilder visible(boolean visible) {
        frame.setVisible(visible);

        return this;
    }

    /**
     * Retorna frame construído.
     *
     * @return JFrame - Frame construído
     */
    public JFrame build() {
        return frame;
    }

}
