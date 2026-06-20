package org.javapi.sigob.view.v2.framework.ui;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Utilitário para carregamento de recursos.
 */
public final class UIResources {

    private UIResources() {
    }

    /**
     * Carrega ícone do classpath.
     *
     * @param path - Caminho do recurso
     * @return ImageIcon - Ícone carregado
     */
    public static ImageIcon icon(String path) {
        URL resource = resource(path);
        return new ImageIcon(resource);
    }

    /**
     * Carrega ícone redimensionado.
     *
     * @param path - Caminho do recurso
     * @param width - Largura desejada
     * @param height - Altura desejada
     * @return ImageIcon - Ícone carregado
     */
    public static ImageIcon icon(
            String path,
            int width,
            int height
    ) {
        Image image = image(path).getScaledInstance(
            width,
            height,
            Image.SCALE_SMOOTH
        );

        return new ImageIcon(image);
    }

    /**
     * Carrega ícone redimensionado.
     *
     * @param path - Caminho do recurso
     * @param size - Tamanho desejado
     * @return ImageIcon - Ícone carregado
     */
    public static ImageIcon icon(
        String path,
        int size
    ) {
        return icon(
            path,
            size,
            size
        );
    }

    /**
     * Carrega imagem do classpath.
     *
     * @param path - Caminho do recurso
     * @return Image - Imagem carregada
     */
    public static Image image(String path) {
        return icon(path).getImage();
    }

    /**
     * Carrega imagem redimensionada.
     *
     * @param path - Caminho do recurso
     * @param width - Largura desejada
     * @param height - Altura desejada
     * @return Image - Imagem carregada
     */
    public static Image image(
            String path,
            int width,
            int height
    ) {
        return icon(
                path,
                width,
                height
        ).getImage();
    }

    /**
     * Localiza recurso no classpath.
     *
     * @param path - Caminho do recurso
     * @return URL - Recurso encontrado
     */
    public static URL resource(String path) {
        URL resource = UIResources.class.getResource(path);

        if (resource == null) {
            throw new IllegalArgumentException(
                    "Recurso não encontrado: " + path
            );
        }

        return resource;
    }

}
