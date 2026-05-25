package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.service.FuncionarioService;
import org.javapi.sigob.view.Actions;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.base.BaseRelatorioScreen;
import org.javapi.sigob.view.base.BaseTableModel;
import org.javapi.sigob.view.models.FuncionarioTableModel;
import org.javapi.sigob.view.popups.PopupInputs;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIEvents;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de funcionários.
 */
public final class FuncionarioRelatorioScreen
        extends BaseRelatorioScreen<Funcionario> {

    /**
     * Serviço de funcionários.
     *
     * @see FuncionarioService
     */
    private final FuncionarioService service
            = ApplicationContext.getFuncionarioService();

    /**
     * Modelo da tabela.
     *
     * @see FuncionarioTableModel
     */
    private final FuncionarioTableModel tableModel
            = new FuncionarioTableModel();

    /**
     * Tabela de funcionários.
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
     * @return BaseTableModel<Funcionario> - Model da tabela
     */
    @Override
    protected BaseTableModel<Funcionario> tableModel() {
        return tableModel;
    }

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    @Override
    protected String entityNameSingular() {
        return "funcionário";
    }

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    @Override
    protected String entityNamePlural() {
        return "funcionários";
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
                buscarIdButton,
                this::buscarPorId
        );

        UIEvents.onClick(
                buscarNomeButton,
                this::buscarPorNome
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
        if (hasAdminAccess()) {
            return UI.grid(2, 4)
                    .add(
                            buscarIdButton,
                            buscarNomeButton,
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        }

        return UI.grid(2, 3)
                .add(
                        buscarIdButton,
                        buscarNomeButton,
                        listarTodosButton
                )
                .build();
    }

    /**
     * Busca funcionário por ID.
     */
    private void buscarPorId() {
        Integer id = PopupInputs.integer(
                "Buscar Funcionário",
                "ID do funcionário:"
        );

        if (id == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar funcionário!",
                () -> {
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
                }
        );
    }

    /**
     * Busca funcionários por nome.
     */
    private void buscarPorNome() {
        String nome = PopupInputs.requiredText(
                "Buscar Funcionário",
                "Nome do funcionário:"
        );

        if (nome == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar funcionários!",
                () -> setResultados(
                        service.findByNome(nome)
                )
        );
    }

    /**
     * Lista todos os funcionários.
     */
    private void listarTodos() {
        Actions.safe(
                "Erro ao listar funcionários!",
                () -> setResultados(
                        service.findAll()
                )
        );
    }

    /**
     * Remove funcionário selecionado.
     */
    private void removerSelecionado() {
        Funcionario funcionario = selectedRow();

        if (funcionario == null) {
            Popups.warn(
                    "Selecione um funcionário para remover!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                "Deseja remover o funcionário '%s'?"
                        .formatted(funcionario.getNome())
        );

        if (!confirmacao) {
            return;
        }

        Actions.safe(
                "Erro ao remover funcionário!",
                () -> {
                    service.delete(funcionario);

                    Popups.success(
                            "Funcionário removido com sucesso!"
                    );

                    listarTodos();
                }
        );
    }

}
