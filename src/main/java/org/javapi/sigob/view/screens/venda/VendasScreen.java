package org.javapi.sigob.view.screens.venda;

import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;
import org.javapi.sigob.view.windows.ApplicationWindow;

/**
 * Tela principal do módulo de vendas.
 */
public final class VendasScreen extends BaseScreen {

    /**
     * Serviço de vendas.
     *
     * @see {@link VendaService}
     */
    private final VendaService vendaService
            = ApplicationContext.getVendaService();

    /**
     * Mapa de vendas.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Venda> vendasMap = new LinkedHashMap<>();

    /**
     * Botão de iniciar venda.
     *
     * @see {@link JButton}
     */
    private final JButton iniciarButton = UI.button("Iniciar");

    /**
     * Botão de continuar venda.
     *
     * @see {@link JButton}
     */
    private final JButton continuarButton = UI.button("Continuar");

    /**
     * Cria tela do módulo de vendas.
     */
    public VendasScreen() {
        super("vendas");

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        Events.mouse(iniciarButton, mouse -> {
            mouse.onClicked(() -> {
                ApplicationContext.getWindow()
                        .showScreen("venda-nova");
            });
        });

        Events.mouse(continuarButton, mouse -> {
            mouse.onClicked(this::continuarVenda);
        });
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected JPanel build() {
        return UIScreen.page(buildContent());
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel - Conteúdo construído
     */
    private JPanel buildContent() {
        return UI.column()
                .add(
                        UIScreen.title("Módulo de Vendas"),
                        UIScreen.subtitle(
                                "Selecione uma operação relacionada a vendas."
                        )
                )
                .glue()
                .add(buildActionsGrid())
                .build();
    }

    /**
     * Constrói grid de ações.
     *
     * @return JPanel - Grid construído
     */
    private JPanel buildActionsGrid() {
        return UI.grid(2, 2)
                .add(
                        iniciarButton,
                        continuarButton
                )
                .build();
    }

    /**
     * Continua venda aberta.
     */
    private void continuarVenda() {
        try {
            List<Venda> abertas = vendaService.findAbertas();

            if (abertas.isEmpty()) {
                Popups.warn("Nenhuma venda aberta encontrada!");
                return;
            }

            refreshVendasAbertas();
            String vendaId = (String) Popups.select(
                    "Selecione a venda",
                    vendasMap.keySet().toArray()
            );

            if (vendaId == null) {
                return;
            }

            Venda venda = vendasMap.get(vendaId);

            if (venda == null) {
                return;
            }

            openVendaEditor(venda);

        } catch (Exception e) {
            Popups.error(
                    "Erro ao continuar venda: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Abre editor da venda.
     *
     * @param venda - Venda aberta
     */
    private void openVendaEditor(Venda venda) {
        ApplicationWindow window
                = ApplicationContext.getWindow();

        String screenId = "venda-editor-" + venda.getId();

        if (!window.containsScreen(screenId)) {
            window.registerScreen(new VendaEditorScreen(venda));
        }

        window.showScreen(screenId);
    }

    /**
     * Popula mapa de vendas.
     */
    private void refreshVendasAbertas() {
        List<Venda> abertas = vendaService.findAbertas();
        vendasMap.clear();

        for (Venda venda : abertas) {
            Cliente cliente = venda.getCliente();
            Funcionario funcionario = venda.getFuncionario();
            String id = "%d | Funcionario (%s) | Cliente (%s)".formatted(
                venda.getId(),
                funcionario.getNome(),
                cliente.getNome()
            );

            vendasMap.put(id, venda);
        }
    }

}
