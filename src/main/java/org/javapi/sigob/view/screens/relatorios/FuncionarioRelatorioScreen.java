package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.FuncionarioTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de funcionários.
 */
public final class FuncionarioRelatorioScreen extends BaseScreen {

    /**
     * Serviço de funcionários.
     *
     * @see {@link FuncionarioService}
     */
    private final FuncionarioService service
            = ApplicationContext.getFuncionarioService();

    /**
     * Modelo da tabela.
     *
     * @see {@link FuncionarioTableModel}
     */
    private final FuncionarioTableModel tableModel
            = new FuncionarioTableModel();

    /**
     * Tabela de funcionários.
     *
     * @see {@link JTable}
     */
    private final JTable table
            = UI.table(tableModel);

    /**
     * Botão de busca por ID.
     *
     * @see {@link JButton}
     */
    private final JButton buscarIdButton
            = UI.button("Buscar por ID");

    /**
     * Botão de busca por nome.
     *
     * @see {@link JButton}
     */
    private final JButton buscarNomeButton
            = UI.button("Buscar por Nome");

    /**
     * Botão de listagem geral.
     *
     * @see {@link JButton}
     */
    private final JButton listarTodosButton
            = UI.button("Listar Todos");

    /**
     * Botão de remoção.
     *
     * @see {@link JButton}
     */
    private final JButton removerButton
            = UI.button("Remover Selecionado");

    /**
     * Cria tela de relatório de funcionários.
     */
    public FuncionarioRelatorioScreen() {
        super("relatorio-funcionario");

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
                        UIScreen.title(
                                "Relatório de Funcionários"
                        ),
                        UIScreen.subtitle(
                                "Consulta e gerenciamento dos funcionários cadastrados no sistema."
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
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        } else {
            return UI.grid(2, 3)
                    .add(
                            buscarIdButton,
                            buscarNomeButton,
                            listarTodosButton
                    )
                    .build();
        }
    }

    /**
     * Busca funcionário por ID.
     */
    private void buscarPorId() {
        try {
            String input = Popups.input(
                    "ID do funcionário:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            int id = Integer.parseInt(input);

            Optional<Funcionario> funcionario = service
                    .findById(id);

            if (funcionario.isEmpty()) {
                clearResults();

                Popups.warn(
                        "Funcionário não encontrado!"
                );

                return;
            }

            setResultados(
                    List.of(funcionario.get())
            );
        } catch (NumberFormatException e) {
            Popups.error(
                    "ID inválido!"
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar funcionário: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca funcionários por nome.
     */
    private void buscarPorNome() {
        try {
            String nome = Popups.input(
                    "Nome do funcionário:"
            );

            if (nome == null || nome.isBlank()) {
                return;
            }

            List<Funcionario> funcionarios = service
                    .findByNome(nome);

            setResultados(funcionarios);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar funcionários: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Lista todos os funcionários.
     */
    private void listarTodos() {
        try {
            List<Funcionario> funcionarios = service
                    .findAll();

            setResultados(funcionarios);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao listar funcionários: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Remove funcionário selecionado.
     */
    private void removerSelecionado() {
        try {
            int row = table.getSelectedRow();

            if (row < 0) {
                Popups.warn(
                        "Selecione um funcionário para remover!"
                );

                return;
            }

            Funcionario funcionario = tableModel
                    .getFuncionario(row);

            boolean confirmacao = Popups.confirm(
                    "Deseja remover o funcionário '%s'?"
                            .formatted(funcionario.getNome())
            );

            if (!confirmacao) {
                return;
            }

            service.delete(funcionario);

            Popups.success(
                    "Funcionário removido com sucesso!"
            );

            listarTodos();
        } catch (Exception e) {
            Popups.error(
                    "Erro ao remover funcionário: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param funcionarios - Lista de funcionários
     */
    private void setResultados(
            List<Funcionario> funcionarios
    ) {
        if (funcionarios == null
                || funcionarios.isEmpty()) {

            clearResults();

            Popups.warn(
                    "Nenhum funcionário encontrado!"
            );

            return;
        }

        tableModel.setFuncionarios(
                funcionarios
        );
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setFuncionarios(
                List.of()
        );
    }

}
