package org.javapi.sigob.view.v2.framework.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.javapi.sigob.view.v2.framework.layouts.base.LayoutBuilder;

/**
 * Builder para construção de janelas.
 */
public class FrameBuilder {

    /**
     * Janela construída.
     */
    private final JFrame frame;

    /**
     * Cria builder vazio.
     */
    public FrameBuilder() {
        this.frame = new JFrame();
    }

    /**
     * Define título da janela.
     *
     * @param title - Título da janela
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder title(String title) {
        frame.setTitle(title);
        return this;
    }

    /**
     * Define tamanho da janela.
     *
     * @param width - Largura
     * @param height - Altura
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder size(
            int width,
            int height
    ) {
        frame.setSize(width, height);
        return this;
    }

    /**
     * Define tamanho da janela.
     *
     * @param size - Tamanho desejado
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder size(Dimension size) {
        frame.setSize(size);
        return this;
    }

    /**
     * Define tamanho mínimo.
     *
     * @param width - Largura
     * @param height - Altura
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder minimumSize(
            int width,
            int height
    ) {
        frame.setMinimumSize(
                new Dimension(width, height)
        );

        return this;
    }

    /**
     * Define tamanho mínimo.
     *
     * @param size - Tamanho mínimo
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder minimumSize(
            Dimension size
    ) {
        frame.setMinimumSize(size);
        return this;
    }

    /**
     * Define tamanho máximo.
     *
     * @param width - Largura
     * @param height - Altura
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder maximumSize(
            int width,
            int height
    ) {
        frame.setMaximumSize(
                new Dimension(width, height)
        );

        return this;
    }

    /**
     * Define tamanho máximo.
     *
     * @param size - Tamanho máximo
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder maximumSize(
            Dimension size
    ) {
        frame.setMaximumSize(size);
        return this;
    }

    /**
     * Define redimensionamento.
     *
     * @param resizable - Estado desejado
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder resizable(
            boolean resizable
    ) {
        frame.setResizable(resizable);
        return this;
    }

    /**
     * Define ícone da janela.
     *
     * @param icon - Ícone da janela
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder icon(Image icon) {
        frame.setIconImage(icon);
        return this;
    }

    /**
     * Define barra de menu.
     *
     * @param menuBar - Barra de menu
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder menuBar(
            JMenuBar menuBar
    ) {
        frame.setJMenuBar(menuBar);
        return this;
    }

    /**
     * Define conteúdo da janela.
     *
     * @param component - Conteúdo principal
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder content(
            Component component
    ) {
        frame.setContentPane((Container) component);
        return this;
    }

    /**
     * Define conteúdo da janela.
     *
     * @param layout - Layout principal
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder content(
            LayoutBuilder<?> layout
    ) {
        return content(layout.build());
    }

    /**
     * Define operação de fechamento.
     *
     * @param operation - Operação desejada
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder closeOperation(
            int operation
    ) {
        frame.setDefaultCloseOperation(operation);
        return this;
    }

    /**
     * Centraliza a janela.
     *
     * @return FrameBuilder - Builder atual
     */
    public FrameBuilder center() {
        frame.setLocationRelativeTo(null);
        return this;
    }

    /**
     * Constrói janela.
     *
     * @return JFrame - Janela construída
     */
    public JFrame build() {
        return frame;
    }

}
