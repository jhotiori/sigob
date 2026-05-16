package org.javapi.sigob.view.screens;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Tela de criação de venda.
 */
public class NovaVendaScreen extends BaseScreen {

    /**
     * Serviço de vendas.
     *
     * @see {@link VendaService}
     */
    private final VendaService vendaService
            = ApplicationContext.getVendaService();

    /**
     * Serviço de clientes.
     *
     * @see {@link ClienteService}
     */
    private final ClienteService clienteService
            = ApplicationContext.getClienteService();

    /**
     * Serviço de funcionários.
     *
     * @see {@link FuncionarioService}
     */
    private final FuncionarioService funcionarioService
            = ApplicationContext.getFuncionarioService();

    /**
     * Campo de clientes.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> clientesBox = UI.comboBox();

    /**
     * Campo de funcionários.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> funcionariosBox = UI.comboBox();

    /**
     * Botão de iniciar venda.
     *
     * @see {@link JButton}
     */
    private final JButton iniciarButton = UI.button("Iniciar Venda", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Botão de voltar.
     *
     * @see {@link JButton}
     */
    private final JButton voltarButton = UI.button("Voltar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Mapa de clientes.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Integer> clientesMap
            = new LinkedHashMap<>();

    /**
     * Mapa de funcionários.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Integer> funcionariosMap
            = new LinkedHashMap<>();

    /**
     * Cria tela de nova venda.
     */
    public NovaVendaScreen() {
        super("nova-venda");

        init();
        setup();
    }

    @Override
    protected void setup() {
        Events.mouse(iniciarButton, mouse -> {
            mouse.onClicked(() -> {
                try {
                    String clienteNome
                            = (String) clientesBox.getSelectedItem();

                    String funcionarioNome
                            = (String) funcionariosBox.getSelectedItem();

                    if (clienteNome == null) {
                        Messages.error("Selecione um cliente!");
                        return;
                    }

                    if (funcionarioNome == null) {
                        Messages.error("Selecione um funcionário!");
                        return;
                    }

                    Integer clienteId
                            = clientesMap.get(clienteNome);

                    Integer funcionarioId
                            = funcionariosMap.get(funcionarioNome);

                    if (clienteId == null) {
                        Messages.error("Cliente inválido!");
                        return;
                    }

                    if (funcionarioId == null) {
                        Messages.error("Funcionário inválido!");
                        return;
                    }

                    Cliente cliente = clienteService.findById(clienteId)
                            .orElseThrow(()
                                    -> new IllegalArgumentException(
                                    "Cliente não encontrado!"
                            ));

                    Funcionario funcionario
                            = funcionarioService.findById(funcionarioId)
                                    .orElseThrow(()
                                            -> new IllegalArgumentException(
                                            "Funcionário não encontrado!"
                                    ));

                    Venda venda = new Venda();

                    venda.setCliente(cliente);
                    venda.setFuncionario(funcionario);
                    venda.setStatus("aberta");
                    venda.setDataAbertura(OffsetDateTime.now());
                    venda.setValorTotal(BigDecimal.ZERO);

                    vendaService.save(venda);

                    ApplicationContext.getWindow()
                            .showScreen(new VendaEditorScreen(venda));

                    Messages.success("Venda iniciada com sucesso!");

                } catch (Exception e) {
                    Messages.error(e.getMessage());
                }
            });
        });

        Events.mouse(voltarButton, mouse -> {
            mouse.onClicked(() -> {
                ApplicationContext.getWindow().showScreen("vendas");
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

    @Override
    public void update() {
        updateClientes();
        updateFuncionarios();
    }

    private JPanel buildPanel() {
        return UI.column()
                .add(buildTitle())
                .add(buildSubtitle())
                .glue()
                .add(buildForm())
                .glue()
                .add(UI.actions(iniciarButton, voltarButton))
                .build();
    }

    private JLabel buildTitle() {
        return UI.label("Nova Venda", label -> {
            label.setFont(Fonts.TITLE_MEDIUM);
        });
    }

    private JLabel buildSubtitle() {
        return UI.label(
                "Selecione cliente e funcionário para iniciar a venda.",
                label -> {
                    label.setForeground(Palette.FG_MUTED);
                    label.setFont(Fonts.TITLE_SMALL);
                }
        );
    }

    private JPanel buildForm() {
        return UI.column()
                .add(
                        UI.field(
                                UI.fieldLabel("Funcionário"),
                                funcionariosBox
                        )
                )
                .add(
                        UI.field(
                                UI.fieldLabel("Cliente"),
                                clientesBox
                        )
                )
                .build();
    }

    /**
     * Atualiza clientes disponíveis.
     */
    private void updateClientes() {
        clientesMap.clear();

        clientesBox.removeAllItems();

        List<Cliente> clientes = clienteService.findAll();

        for (Cliente cliente : clientes) {
            clientesMap.put(cliente.getNome(), cliente.getId());

            clientesBox.addItem(cliente.getNome());
        }
    }

    /**
     * Atualiza funcionários disponíveis.
     */
    private void updateFuncionarios() {
        funcionariosMap.clear();

        funcionariosBox.removeAllItems();

        List<Funcionario> funcionarios
                = funcionarioService.findAll();

        Funcionario logado
                = ApplicationContext.getFuncionarioLogado();

        String selecionado = null;

        for (Funcionario funcionario : funcionarios) {
            funcionariosMap.put(
                    funcionario.getNome(),
                    funcionario.getId()
            );

            funcionariosBox.addItem(funcionario.getNome());

            if (funcionario.getId() == logado.getId()) {
                selecionado = funcionario.getNome();
            }
        }

        if (selecionado != null) {
            funcionariosBox.setSelectedItem(selecionado);
        }
    }

}
