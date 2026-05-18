package org.javapi.sigob.view.screens.relatorios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.VendaTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de vendas.
 */
public final class VendaRelatorioScreen extends BaseScreen {

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
        Events.mouse(buscarClienteButton, mouse -> {
            mouse.onClicked(this::buscarPorCliente);
        });

        Events.mouse(buscarFuncionarioButton, mouse -> {
            mouse.onClicked(this::buscarPorFuncionario);
        });

        Events.mouse(buscarDataAberturaButton, mouse -> {
            mouse.onClicked(this::buscarPorDataAbertura);
        });

        Events.mouse(buscarDataFechamentoButton, mouse -> {
            mouse.onClicked(this::buscarPorDataFechamento);
        });

        Events.mouse(buscarPeriodoButton, mouse -> {
            mouse.onClicked(this::buscarPorPeriodo);
        });

        Events.mouse(listarTodosButton, mouse -> {
            mouse.onClicked(this::listarTodos);
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
                        UIScreen.title("Relatório de Vendas"),
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
        try {
            String nome = Popups.input(
                    "Nome do cliente:"
            );

            if (nome == null || nome.isBlank()) {
                return;
            }

            List<Venda> vendas = vendaService
                    .findByClienteNome(nome);

            setResultados(vendas);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar vendas por cliente: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca vendas por funcionário.
     */
    private void buscarPorFuncionario() {
        try {
            String nome = Popups.input(
                    "Nome do funcionário:"
            );

            if (nome == null || nome.isBlank()) {
                return;
            }

            List<Venda> vendas = vendaService
                    .findByFuncionarioNome(nome);

            setResultados(vendas);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar vendas por funcionário: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca vendas por data de abertura.
     */
    private void buscarPorDataAbertura() {
        try {
            String input = Popups.input(
                    "Data de abertura [DD-MM-YYYY]:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            LocalDate data = LocalDate.parse(
                    input,
                    DATE_FORMATTER
            );

            List<Venda> vendas = vendaService
                    .findByDataAbertura(data);

            setResultados(vendas);
        } catch (DateTimeParseException e) {
            Popups.error(
                    "Data inválida! Utilize o formato DD-MM-YYYY."
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar vendas por data de abertura: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca vendas por data de fechamento.
     */
    private void buscarPorDataFechamento() {
        try {
            String input = Popups.input(
                    "Data de fechamento [DD-MM-YYYY]:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            LocalDate data = LocalDate.parse(
                    input,
                    DATE_FORMATTER
            );

            List<Venda> vendas = vendaService
                    .findByDataFinalizada(data);

            setResultados(vendas);
        } catch (DateTimeParseException e) {
            Popups.error(
                    "Data inválida! Utilize o formato DD-MM-YYYY."
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar vendas por data de fechamento: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca vendas por período de abertura.
     */
    private void buscarPorPeriodo() {
        try {
            String inicioInput = Popups.input(
                    "Data inicial [DD-MM-YYYY]:"
            );

            if (inicioInput == null || inicioInput.isBlank()) {
                return;
            }

            String fimInput = Popups.input(
                    "Data final [DD-MM-YYYY]:"
            );

            if (fimInput == null || fimInput.isBlank()) {
                return;
            }

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

            List<Venda> vendas = vendaService.findByPeriodo(dataInicio, dataFim);
            setResultados(vendas);
        } catch (DateTimeParseException e) {
            Popups.error(
                    "Data inválida! Utilize o formato DD-MM-YYYY."
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar vendas por período: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Lista todas as vendas.
     */
    private void listarTodos() {
        try {
            List<Venda> vendas = vendaService
                    .findAll();

            setResultados(vendas);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao listar vendas: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param vendas Lista de vendas
     */
    private void setResultados(
            List<Venda> vendas
    ) {
        if (vendas == null || vendas.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhuma venda encontrada!"
            );

            return;
        }

        tableModel.setVendas(vendas);
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setVendas(List.of());
    }

}
