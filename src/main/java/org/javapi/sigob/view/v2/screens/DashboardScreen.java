package org.javapi.sigob.view.v2.screens;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.context.SessionContext;
import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.styles.Spacing;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIText;

public class DashboardScreen extends BaseScreen {
    /**
     * Label de Titulo
     *
     * @see LabelComponent
     */
    private final LabelComponent TITLE_LABEL = UIText.header("Olá!");

    /**
     * Label de Sub-Titulo
     *
     * @see LabelComponent
     */
    private final LabelComponent SUBTITLE_LABEL = UIText.title(
        "Sejá bem-vindo(a), %s.".formatted(
            SessionContext.getFuncionarioLogado().getNome()
        )
    );

    /**
     * Construtor da tela de Dashboard.
     */
    public DashboardScreen() {
        super("dashboard");
    }

    @Override
    protected JPanel build() {
        return UILayouts.border()
            .add(buildTitles())
            .padding(Spacing.MD)
            .build();
    }

    private JPanel buildTitles() {
        return UILayouts.column()
            .add(TITLE_LABEL)
            .gap(Spacing.SM)
            .add(SUBTITLE_LABEL)
            .gap(Spacing.SM)
            .build();
    }
}
