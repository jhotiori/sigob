package org.javapi.sigob.view.screens;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.windows.ApplicationWindow;

public class VendasScreen extends BaseScreen {

    /**
     * Serviço de vendas.
     *
     * @see {@link VendaService}
     */
    private final VendaService vendaService = ApplicationContext.getVendaService();

    /**
     * Botão de listar vendas
     *
     * @see {@link JButton}
     */
    private final JButton listarButton = UI.button("Listar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Botão de cadastrar venda
     *
     * @see {@link JButton}
     */
    private final JButton iniciarButton = UI.button("Iniciar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Botão de continuar venda
     *
     * @see {@link JButton}
     */
    private final JButton continuarButton = UI.button("Continuar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    public VendasScreen() {
        super("vendas");
        init();
        setup();
    }

    @Override
    protected void setup() {
        Events.mouse(iniciarButton, mouse -> {
            mouse.onClicked(() -> {
                ApplicationContext.getWindow().showScreen("nova-venda");
            });
        });

        Events.mouse(continuarButton, mouse -> {
            mouse.onClicked(() -> {
                continuarVenda();
            });
        });
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
                .add(buildGrid())
                .build();
    }

    private JLabel buildTitle() {
        return UI.label("Módulo de Vendas", label -> {
            label.setFont(Fonts.TITLE_MEDIUM);
        });
    }

    private JLabel buildSubtitle() {
        return UI.label("Selecione a operação relacionada a vendas abaixo.", label -> {
            label.setForeground(Palette.FG_MUTED);
            label.setFont(Fonts.TITLE_SMALL);
        });
    }

    private JPanel buildGrid() {
        return UI.grid(2, 2)
                .add(listarButton, iniciarButton, continuarButton)
                .build();
    }

    /**
     * Continua venda aberta existente.
     */
    private void continuarVenda() {
        try {
            List<Venda> abertas = vendaService.findAbertas();

            if (abertas.isEmpty()) {
                Messages.warn("Nenhuma venda aberta encontrada!");
                return;
            }

            Venda venda = (Venda) Messages.select(
                    "Selecione a venda",
                    abertas.toArray()
            );

            if (venda == null) {
                return;
            }

            openVendaEditor(venda);

        } catch (Exception e) {
            Messages.error(
                    "Erro ao continuar venda: " + e.getMessage()
            );
        }
    }

    /**
     * Abre editor da venda.
     *
     * @param venda - Venda aberta
     */
    private void openVendaEditor(Venda venda) {
        ApplicationWindow window = ApplicationContext.getWindow();

        String screenId = "venda-editor-" + venda.getId();

        if (!window.containsScreen(screenId)) {
            window.register(new VendaEditorScreen(venda));
        }

        window.showScreen(screenId);
    }
}
