package org.javapi.sigob.view.screens;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;
import org.javapi.sigob.view.styles.Spacing;

public final class DashboardScreen extends BaseScreen {

    public DashboardScreen() {
        super("dashboard");
        init();
    }

    @Override
    protected JPanel build() {
        return UI.border()
            .center(buildPanel())
            .padding(Spacing.MD)
            .build();
    }

    private JPanel buildPanel() {
        return UI.column()
            .add(buildTitle())
            .add(buildSubtitle())
            .glue()
            .add(buildDescription())
            .glue()
            /*.add(buildGridLayout())*/
            .build();
    }

    private JLabel buildTitle() {
        return UI.label(
            "Olá, seja bem-vindo(a)!",
            label -> {
                label.setFont(Fonts.TITLE_BIG);
            }
        );
    }

    private JLabel buildSubtitle() {
        return UI.label(
            "Selecione a operação desejada na barra de menus.",
            label -> {
                label.setForeground(Palette.FG_MUTED);
                label.setFont(Fonts.TITLE_SMALL);
            }
        );
    }

    private JLabel buildDescription() {
        return UI.label(
                """
            <html>
                <body style='width: 800px'>
                    O SIGOB (Sistema Integrado de Gestão Comercial e Operacional
                    para Distribuidoras de Bebidas) é um sistema básico feito
                    como um projeto universitário, visando utilizar tecnologias
                    como PostgreSQL, Hibernate, JPA e Java Swing.
                    Atualmente, este sistema é um protótipo de teste, podendo haver varias mudanças ao decorrer do desenvolvimento.
                </body>
            </html>
            """,
                label -> {
                    label.setForeground(Palette.FG_MUTED);
                    label.setFont(Fonts.MEDIUM_ITALIC);
                }
        );
    }
}
