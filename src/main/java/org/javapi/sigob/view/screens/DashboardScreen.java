package org.javapi.sigob.view.screens;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Settings;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela principal da aplicação.
 */
public final class DashboardScreen extends BaseScreen {

    /**
     * Cria tela principal.
     */
    public DashboardScreen() {
        super("dashboard");

        initialize();
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildPanel())
                .padding(Spacing.MD)
                .build();
    }

    /**
     * Constrói painel principal.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildPanel() {
        return UI.column()
                .add(buildTitle())
                .add(buildSubtitle())
                .glue()
                .add(buildInformationGrid())
                .glue()
                .build();
    }

    /**
     * Constrói título da tela.
     *
     * @return JLabel - Título construído
     */
    private JPanel buildTitle() {
        return UI.row()
            .add(UI.label(UI.icon(Settings.APP_ICON_PATH, 36)))
            .gap(Spacing.XS)
            .add(UIScreen.title("Olá, seja bem-vindo(a) %s!".formatted(ApplicationContext.getFuncionarioLogado().getNome())))
            .build();
    }

    /**
     * Constrói subtítulo da tela.
     *
     * @return JLabel - Subtítulo construído
     */
    private JLabel buildSubtitle() {
        return UIScreen.subtitle(
                "Selecione a operação desejada na barra de menus."
        );
    }

    /**
     * Constrói uma label para grid.
     *
     * @return JLabel - Label construiuda.
     */
    private JLabel buildGridLabel(String text) {
        return UI.label(text, label -> {
            label.setForeground(Palette.FG_DARK);
            label.setFont(Fonts.DEFAULT_ITALIC);
        });
    }

    /**
     * Constrói painel de informações.
     *
     * @return JPanel - Painel construido.
     */
    public JPanel buildInformationGrid() {
        Funcionario funcionarioLogado = ApplicationContext.getFuncionarioLogado();
        String funcionarioAcessos = funcionarioLogado.getAcessos()
            .stream()
            .map(Acesso::getNome)
            .map(acesso -> acesso.toLowerCase())
            .collect(Collectors.joining(", "));

        JPanel idField = UIForm.field(
                UIForm.fieldLabel("ID"),
                buildGridLabel(String.valueOf(funcionarioLogado.getId()))
        );

        JPanel nomeField = UIForm.field(
                UIForm.fieldLabel("Nome"),
                buildGridLabel(funcionarioLogado.getNome())
        );

        JPanel acessosField = UIForm.field(
            UIForm.fieldLabel("Acessos"),
            buildGridLabel(funcionarioAcessos)
        );

        JPanel dataAcessoField = UIForm.field(
            UIForm.fieldLabel("Data de Acesso"),
            buildGridLabel(LocalDate.now().toString())
        );

        JPanel gridLayout = UI.grid(2, 2)
                .add(idField, dataAcessoField, nomeField, acessosField)
                .build();

        return UIScreen.section("Informações de Login", gridLayout);
    }
}
