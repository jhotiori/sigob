package org.javapi.sigob.view.screens.venda;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de criação de vendas.
 */
public final class VendaNovaScreen extends BaseScreen {

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
    private final JButton iniciarButton = UI.button("Iniciar Venda");

    /**
     * Botão de voltar.
     *
     * @see {@link JButton}
     */
    private final JButton voltarButton = UI.button("Voltar");

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
    public VendaNovaScreen() {
        super("venda-nova");

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        Events.mouse(iniciarButton, mouse -> {
            mouse.onClicked(this::iniciarVenda);
        });

        Events.mouse(voltarButton, mouse -> {
            mouse.onClicked(() -> {
                ApplicationContext.getWindow().showScreen("vendas");
            });
        });
    }

    /**
     * Executa ao exibir tela.
     */
    @Override
    public void onShow() {
        refresh();
    }

    /**
     * Atualiza dados dinâmicos da tela.
     */
    @Override
    public void refresh() {
        refreshClientes();
        refreshFuncionarios();
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
                        UIScreen.title("Nova Venda"),
                        UIScreen.subtitle(
                                "Selecione cliente e funcionário para iniciar a venda."
                        )
                )
                .glue()
                .add(buildForm())
                .glue()
                .add(
                        UIScreen.actions(
                                iniciarButton,
                                voltarButton
                        )
                )
                .build();
    }

    /**
     * Constrói formulário da venda.
     *
     * @return JPanel - Formulário construído
     */
    private JPanel buildForm() {
        return UIScreen.section(
                "Dados da Venda",
                UIForm.field(
                        UIForm.fieldLabel("Funcionário"),
                        funcionariosBox
                ),
                UIForm.field(
                        UIForm.fieldLabel("Cliente"),
                        clientesBox
                )
        );
    }

    /**
     * Inicia nova venda.
     */
    private void iniciarVenda() {
        try {
            String clienteNome
                    = (String) clientesBox.getSelectedItem();

            String funcionarioNome
                    = (String) funcionariosBox.getSelectedItem();

            if (clienteNome == null) {
                Popups.warn("Selecione um cliente!");
                return;
            }

            if (funcionarioNome == null) {
                Popups.warn("Selecione um funcionário!");
                return;
            }

            Integer clienteId = clientesMap.get(clienteNome);

            Integer funcionarioId
                    = funcionariosMap.get(funcionarioNome);

            Cliente cliente = clienteService
                    .findById(clienteId)
                    .orElseThrow();

            Funcionario funcionario = funcionarioService
                    .findById(funcionarioId)
                    .orElseThrow();

            Venda venda = new Venda();

            venda.setCliente(cliente);
            venda.setFuncionario(funcionario);
            venda.setStatus("aberta");
            venda.setValorTotal(BigDecimal.ZERO);
            venda.setDataAbertura(OffsetDateTime.now());

            vendaService.save(venda);

            ApplicationContext.getWindow()
                    .showScreen(new VendaEditorScreen(venda));

            Popups.success("Venda iniciada com sucesso!");

        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Atualiza clientes disponíveis.
     */
    private void refreshClientes() {
        clientesMap.clear();

        clientesBox.removeAllItems();

        List<Cliente> clientes = clienteService.findAll();

        for (Cliente cliente : clientes) {
            clientesMap.put(
                    cliente.getNome(),
                    cliente.getId()
            );

            clientesBox.addItem(cliente.getNome());
        }
    }

    /**
     * Atualiza funcionários disponíveis.
     */
    private void refreshFuncionarios() {
        funcionariosMap.clear();

        funcionariosBox.removeAllItems();

        Funcionario funcionarioLogado
                = ApplicationContext.getFuncionarioLogado();

        String selecionado = null;

        List<Funcionario> funcionarios
                = funcionarioService.findAll();

        for (Funcionario funcionario : funcionarios) {
            funcionariosMap.put(
                    funcionario.getNome(),
                    funcionario.getId()
            );

            funcionariosBox.addItem(funcionario.getNome());

            if (funcionarioLogado != null
                    && funcionario.getId() == funcionarioLogado.getId()) {
                selecionado = funcionario.getNome();
            }
        }

        if (selecionado != null) {
            funcionariosBox.setSelectedItem(selecionado);
        }
    }

}
