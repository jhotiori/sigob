package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.ClientesTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de clientes.
 */
public final class ClienteRelatorioScreen extends BaseScreen {

    /**
     * Serviço de clientes.
     *
     * @see ClienteService
     */
    private final ClienteService clienteService
            = ApplicationContext.getClienteService();

    /**
     * Modelo da tabela.
     *
     * @see ClientesTableModel
     */
    private final ClientesTableModel tableModel
            = new ClientesTableModel();

    /**
     * Tabela de clientes.
     *
     * @see JTable
     */
    private final JTable table
            = UI.table(tableModel);

    /**
     * Botão de busca por ID.
     *
     * @see JButton
     */
    private final JButton buscarIdButton
            = UI.button("Buscar por ID");

    /**
     * Botão de busca por nome.
     *
     * @see JButton
     */
    private final JButton buscarNomeButton
            = UI.button("Buscar por Nome");

    /**
     * Botão de busca por documento.
     *
     * @see JButton
     */
    private final JButton buscarDocumentoButton
            = UI.button("Buscar por Documento");

    /**
     * Botão de listagem geral.
     *
     * @see JButton
     */
    private final JButton listarTodosButton
            = UI.button("Listar Todos");

    /**
     * Botão de remoção.
     *
     * @see JButton
     */
    private final JButton removerButton
            = UI.button("Remover Selecionado");

    /**
     * Cria tela de relatório de clientes.
     */
    public ClienteRelatorioScreen() {
        super("relatorio-cliente");

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        registerEvents();

        listarTodos();
    }

    /**
     * Atualiza tela.
     */
    @Override
    public void refresh() {
        listarTodos();
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildContent())
                .padding(Spacing.XL)
                .build();
    }

    /**
     * Registra eventos da tela.
     */
    private void registerEvents() {
        Events.mouse(buscarIdButton, mouse -> {
            mouse.onClicked(this::buscarPorId);
        });

        Events.mouse(buscarNomeButton, mouse -> {
            mouse.onClicked(this::buscarPorNome);
        });

        Events.mouse(buscarDocumentoButton, mouse -> {
            mouse.onClicked(this::buscarPorDocumento);
        });

        Events.mouse(listarTodosButton, mouse -> {
            mouse.onClicked(this::listarTodos);
        });

        Events.mouse(removerButton, mouse -> {
            mouse.onClicked(this::removerSelecionado);
        });
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel - Conteúdo construído
     */
    private JPanel buildContent() {
        return UI.column()
                .add(
                        UIScreen.title("Relatório de Clientes"),
                        UIScreen.subtitle(
                                "Consulta e gerenciamento dos clientes cadastrados no sistema."
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Operações",
                                buildActions()
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Resultados",
                                UI.scroll(table)
                        )
                )
                .build();
    }

    /**
     * Constrói painel de ações.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildActions() {
        if (ApplicationContext.hasFuncionarioAcesso("admin")) {
            return UI.grid(2, 4)
                    .add(
                            buscarIdButton,
                            buscarNomeButton,
                            buscarDocumentoButton,
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        } else {
                return UI.grid(2, 3)
                .add(
                        buscarIdButton,
                        buscarNomeButton,
                        buscarDocumentoButton,
                        listarTodosButton
                )
                .build();
    }
    }

    /**
     * Busca cliente por ID.
     */
    private void buscarPorId() {
        try {
            String input = Popups.input(
                    "ID do cliente:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            int id = Integer.parseInt(input);

            Optional<Cliente> cliente = clienteService
                    .findById(id);

            if (cliente.isEmpty()) {
                clearResults();

                Popups.warn(
                        "Cliente não encontrado!"
                );

                return;
            }

            setResultados(
                    List.of(cliente.get())
            );
        } catch (NumberFormatException e) {
            Popups.error(
                    "ID inválido!"
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar cliente: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca clientes por nome.
     */
    private void buscarPorNome() {
        try {
            String nome = Popups.input(
                    "Nome do cliente:"
            );

            if (nome == null || nome.isBlank()) {
                return;
            }

            List<Cliente> clientes = clienteService
                    .findByNome(nome);

            setResultados(clientes);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar clientes: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca cliente por documento.
     */
    private void buscarPorDocumento() {
        try {
            String documento = Popups.input(
                    "Documento do cliente:"
            );

            if (documento == null || documento.isBlank()) {
                return;
            }

            List<Cliente> clientes = clienteService
                    .findByDocumento(documento);

            if (clientes.isEmpty()) {
                clearResults();

                Popups.warn(
                        "Cliente não encontrado!"
                );

                return;
            }

            setResultados(
                    clientes
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar cliente: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Lista todos os clientes.
     */
    private void listarTodos() {
        try {
            List<Cliente> clientes = clienteService
                    .findAll();

            setResultados(clientes);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao listar clientes: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Remove cliente selecionado.
     */
    private void removerSelecionado() {
        try {
            int row = table.getSelectedRow();

            if (row < 0) {
                Popups.warn(
                        "Selecione um cliente para remover!"
                );

                return;
            }

            Cliente cliente = tableModel
                    .getCliente(row);

            boolean confirmacao = Popups.confirm(
                    "Deseja remover o cliente '%s'?"
                            .formatted(cliente.getNome())
            );

            if (!confirmacao) {
                return;
            }

            clienteService.delete(cliente);

            Popups.success(
                    "Cliente removido com sucesso!"
            );

            listarTodos();
        } catch (Exception e) {
            String message = e.getMessage();

            if (message != null
                    && message.contains("violates foreign key constraint")) {

                Popups.error(
                        "Não é possível remover este cliente pois existem vendas vinculadas."
                );

                return;
            }

            Popups.error(
                    "Erro ao remover cliente: %s"
                            .formatted(message)
            );
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param clientes - Lista de clientes
     */
    private void setResultados(
            List<Cliente> clientes
    ) {
        if (clientes == null || clientes.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhum cliente encontrado!"
            );

            return;
        }

        tableModel.setClientes(clientes);
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setClientes(List.of());
    }

}
