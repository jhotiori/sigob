package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.service.ClienteService;
import org.javapi.sigob.view.Actions;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.base.BaseRelatorioScreen;
import org.javapi.sigob.view.errors.ErrorsDatabase;
import org.javapi.sigob.view.models.ClientesTableModel;
import org.javapi.sigob.view.popups.PopupInputs;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIEvents;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de clientes.
 */
public final class ClienteRelatorioScreen
        extends BaseRelatorioScreen<Cliente> {

    /**
     * Serviço de clientes.
     */
    private final ClienteService clienteService
            = ApplicationContext.getClienteService();

    /**
     * Modelo da tabela.
     */
    private final ClientesTableModel tableModel
            = new ClientesTableModel();

    /**
     * Tabela principal.
     */
    private final JTable table
            = UI.table(tableModel);

    /**
     * Botão de busca por ID.
     */
    private final JButton buscarIdButton
            = UI.button("Buscar por ID");

    /**
     * Botão de busca por nome.
     */
    private final JButton buscarNomeButton
            = UI.button("Buscar por Nome");

    /**
     * Botão de busca por documento.
     */
    private final JButton buscarDocumentoButton
            = UI.button("Buscar por Documento");

    /**
     * Botão de listagem geral.
     */
    private final JButton listarTodosButton
            = UI.button("Listar Todos");

    /**
     * Botão de remoção.
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
     * Retorna tabela principal.
     *
     * @return JTable - Tabela principal
     */
    @Override
    protected JTable table() {
        return table;
    }

    /**
     * Retorna model da tabela.
     *
     * @return ClientesTableModel - Model da tabela
     */
    @Override
    protected ClientesTableModel tableModel() {
        return tableModel;
    }

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    @Override
    protected String entityNameSingular() {
        return "cliente";
    }

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    @Override
    protected String entityNamePlural() {
        return "clientes";
    }

    /**
     * Registra eventos da tela.
     */
    private void registerEvents() {
        UIEvents.onClick(
                buscarIdButton,
                this::buscarPorId
        );

        UIEvents.onClick(
                buscarNomeButton,
                this::buscarPorNome
        );

        UIEvents.onClick(
                buscarDocumentoButton,
                this::buscarPorDocumento
        );

        UIEvents.onClick(
                listarTodosButton,
                this::listarTodos
        );

        UIEvents.onClick(
                removerButton,
                this::removerSelecionado
        );
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel - Conteúdo principal
     */
    private JPanel buildContent() {
        return UI.column()
                .add(
                        buildHeader()
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
     * Constrói cabeçalho da tela.
     *
     * @return JPanel - Cabeçalho construído
     */
    private JPanel buildHeader() {
        return UI.column()
                .add(
                        UIScreen.title("Relatório de Clientes"),
                        UIScreen.subtitle(
                                "Consulta e gerenciamento dos clientes cadastrados no sistema."
                        )
                )
                .build();
    }

    /**
     * Constrói painel de ações.
     *
     * @return JPanel - Painel de ações
     */
    private JPanel buildActions() {
        if (hasAdminAccess()) {
            return UI.grid(2, 4)
                    .add(
                            buscarIdButton,
                            buscarNomeButton,
                            buscarDocumentoButton,
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        }

        return UI.grid(2, 3)
                .add(
                        buscarIdButton,
                        buscarNomeButton,
                        buscarDocumentoButton,
                        listarTodosButton
                )
                .build();
    }

    /**
     * Busca cliente por ID.
     */
    private void buscarPorId() {
        Integer id = PopupInputs.integer(
                "Buscar Cliente",
                "ID do cliente:"
        );

        if (id == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar cliente!",
                () -> {
                    Optional<Cliente> cliente = clienteService.findById(id);

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
                }
        );
    }

    /**
     * Busca clientes por nome.
     */
    private void buscarPorNome() {
        String nome = PopupInputs.requiredText(
                "Buscar Cliente",
                "Nome do cliente:"
        );

        if (nome == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar clientes!",
                () -> setResultados(
                        clienteService.findByNome(nome)
                )
        );
    }

    /**
     * Busca clientes por documento.
     */
    private void buscarPorDocumento() {
        String documento = PopupInputs.requiredText(
                "Buscar Cliente",
                "Documento do cliente:"
        );

        if (documento == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar cliente!",
                () -> setResultados(
                        clienteService.findByDocumento(documento)
                )
        );
    }

    /**
     * Lista todos os clientes.
     */
    private void listarTodos() {
        Actions.safe(
                "Erro ao listar clientes!",
                () -> setResultados(
                        clienteService.findAll()
                )
        );
    }

    /**
     * Remove cliente selecionado.
     */
    private void removerSelecionado() {
        Cliente cliente = selectedRow();

        if (cliente == null) {
            Popups.warn(
                    "Selecione um cliente para remover!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                "Deseja remover o cliente '%s'?"
                        .formatted(cliente.getNome())
        );

        if (!confirmacao) {
            return;
        }

        Actions.safe(
                "Erro ao remover cliente!",
                () -> {
                    try {
                        clienteService.delete(cliente);

                        Popups.success(
                                "Cliente removido com sucesso!"
                        );

                        listarTodos();
                    } catch (Exception e) {
                        if (ErrorsDatabase.isForeignKey(e)) {
                            Popups.error(
                                    "Não é possível remover este cliente pois existem vendas vinculadas."
                            );

                            return;
                        }

                        throw e;
                    }
                }
        );
    }

}
