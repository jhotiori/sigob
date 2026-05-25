package org.javapi.sigob.view.screens.relatorios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.view.Actions;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.base.BaseRelatorioScreen;
import org.javapi.sigob.view.base.BaseTableModel;
import org.javapi.sigob.view.models.VendaTableModel;
import org.javapi.sigob.view.popups.PopupInputs;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIEvents;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de vendas.
 */
public final class VendaRelatorioScreen
        extends BaseRelatorioScreen<Venda> {

    /**
     * Formatter de datas.
     */
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Serviço de vendas.
     *
     * @see VendaService
     */
    private final VendaService vendaService
            = ApplicationContext.getVendaService();

    /**
     * Modelo da tabela.
     *
     * @see VendaTableModel
     */
    private final VendaTableModel tableModel
            = new VendaTableModel();

    /**
     * Tabela de vendas.
     *
     * @see JTable
     */
    private final JTable table
            = UI.table(tableModel);

    /**
     * Botão de busca por cliente.
     *
     * @see JButton
     */
    private final JButton buscarClienteButton
            = UI.button("Buscar por Cliente");

    /**
     * Botão de busca por funcionário.
     *
     * @see JButton
     */
    private final JButton buscarFuncionarioButton
            = UI.button("Buscar por Funcionário");

    /**
     * Botão de busca por data de abertura.
     *
     * @see JButton
     */
    private final JButton buscarDataAberturaButton
            = UI.button("Buscar por Data Abertura");

    /**
     * Botão de busca por data de fechamento.
     *
     * @see JButton
     */
    private final JButton buscarDataFechamentoButton
            = UI.button("Buscar por Data Fechamento");

    /**
     * Botão de busca por período.
     *
     * @see JButton
     */
    private final JButton buscarPeriodoButton
            = UI.button("Buscar por Período");

    /**
     * Botão de listagem geral.
     *
     * @see JButton
     */
    private final JButton listarTodosButton
            = UI.button("Listar Todos");

    /**
     * Cria tela de relatório de vendas.
     */
    public VendaRelatorioScreen() {
        super("relatorio-venda");

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
     * @return BaseTableModel<Venda> - Model da tabela
     */
    @Override
    protected BaseTableModel<Venda> tableModel() {
        return tableModel;
    }

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    @Override
    protected String entityNameSingular() {
        return "venda";
    }

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    @Override
    protected String entityNamePlural() {
        return "vendas";
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
        UIEvents.onClick(
                buscarClienteButton,
                this::buscarPorCliente
        );

        UIEvents.onClick(
                buscarFuncionarioButton,
                this::buscarPorFuncionario
        );

        UIEvents.onClick(
                buscarDataAberturaButton,
                this::buscarPorDataAbertura
        );

        UIEvents.onClick(
                buscarDataFechamentoButton,
                this::buscarPorDataFechamento
        );

        UIEvents.onClick(
                buscarPeriodoButton,
                this::buscarPorPeriodo
        );

        UIEvents.onClick(
                listarTodosButton,
                this::listarTodos
        );
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel - Conteúdo construído
     */
    private JPanel buildContent() {
        return UI.column()
                .add(
                        UIScreen.title(
                                "Relatório de Vendas"
                        ),
                        UIScreen.subtitle(
                                "Consulta e visualização das vendas cadastradas no sistema."
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
        return UI.grid(2, 3)
                .add(
                        buscarClienteButton,
                        buscarFuncionarioButton,
                        buscarDataAberturaButton,
                        buscarDataFechamentoButton,
                        buscarPeriodoButton,
                        listarTodosButton
                )
                .build();
    }

    /**
     * Busca vendas por cliente.
     */
    private void buscarPorCliente() {
        String nome = PopupInputs.requiredText(
                "Buscar Vendas",
                "Nome do cliente:"
        );

        if (nome == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar vendas por cliente!",
                () -> setResultados(
                        vendaService.findByClienteNome(nome)
                )
        );
    }

    /**
     * Busca vendas por funcionário.
     */
    private void buscarPorFuncionario() {
        String nome = PopupInputs.requiredText(
                "Buscar Vendas",
                "Nome do funcionário:"
        );

        if (nome == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar vendas por funcionário!",
                () -> setResultados(
                        vendaService.findByFuncionarioNome(nome)
                )
        );
    }

    /**
     * Busca vendas por data de abertura.
     */
    private void buscarPorDataAbertura() {
        String input = PopupInputs.requiredText(
                "Buscar Vendas",
                "Data de abertura [DD-MM-YYYY]:"
        );

        if (input == null) {
            return;
        }

        try {
            LocalDate data = LocalDate.parse(
                    input,
                    DATE_FORMATTER
            );

            Actions.safe(
                    "Erro ao buscar vendas por data de abertura!",
                    () -> setResultados(
                            vendaService.findByDataAbertura(data)
                    )
            );
        } catch (DateTimeParseException e) {
            Popups.error(
                    "Data inválida! Utilize o formato DD-MM-YYYY."
            );
        }
    }

    /**
     * Busca vendas por data de fechamento.
     */
    private void buscarPorDataFechamento() {
        String input = PopupInputs.requiredText(
                "Buscar Vendas",
                "Data de fechamento [DD-MM-YYYY]:"
        );

        if (input == null) {
            return;
        }

        try {
            LocalDate data = LocalDate.parse(
                    input,
                    DATE_FORMATTER
            );

            Actions.safe(
                    "Erro ao buscar vendas por data de fechamento!",
                    () -> setResultados(
                            vendaService.findByDataFinalizada(data)
                    )
            );
        } catch (DateTimeParseException e) {
            Popups.error(
                    "Data inválida! Utilize o formato DD-MM-YYYY."
            );
        }
    }

    /**
     * Busca vendas por período de abertura.
     */
    private void buscarPorPeriodo() {
        String inicioInput = PopupInputs.requiredText(
                "Buscar Vendas",
                "Data inicial [DD-MM-YYYY]:"
        );

        if (inicioInput == null) {
            return;
        }

        String fimInput = PopupInputs.requiredText(
                "Buscar Vendas",
                "Data final [DD-MM-YYYY]:"
        );

        if (fimInput == null) {
            return;
        }

        try {
            LocalDate dataInicio = LocalDate.parse(
                    inicioInput,
                    DATE_FORMATTER
            );

            LocalDate dataFim = LocalDate.parse(
                    fimInput,
                    DATE_FORMATTER
            );

            if (dataInicio.isAfter(dataFim)) {
                Popups.warn(
                        "A data inicial não pode ser maior que a data final!"
                );

                return;
            }

            Actions.safe(
                    "Erro ao buscar vendas por período!",
                    () -> setResultados(
                            vendaService.findByPeriodo(
                                    dataInicio,
                                    dataFim
                            )
                    )
            );
        } catch (DateTimeParseException e) {
            Popups.error(
                    "Data inválida! Utilize o formato DD-MM-YYYY."
            );
        }
    }

    /**
     * Lista todas as vendas.
     */
    private void listarTodos() {
        Actions.safe(
                "Erro ao listar vendas!",
                () -> setResultados(
                        vendaService.findAll()
                )
        );
    }

}
