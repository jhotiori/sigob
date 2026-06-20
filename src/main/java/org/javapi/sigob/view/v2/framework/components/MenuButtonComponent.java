package org.javapi.sigob.view.v2.framework.components;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuButtonComponent extends JMenu {

    /**
     * Cria um botão de menu.
     *
     * @param text - Texto do botão
     * @param action - Ação executada ao clicar
     * @return MenuButtonComponent - Botão de menu
     */
    public MenuButtonComponent(
            String text,
            Runnable action
    ) {
        super(text);

        addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                action.run();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        }
        );
    }
}
