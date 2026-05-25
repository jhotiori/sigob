package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.view.Actions;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.base.BaseRelatorioScreen;
import org.javapi.sigob.view.base.BaseTableModel;
import org.javapi.sigob.view.models.EstoqueTableModel;
import org.javapi.sigob.view.popups.PopupInputs;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIEvents;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de estoques.
 */
public final class EstoqueRelatorioScreen
        extends BaseRelatorioScreen<Estoque> {

    /**
     * Serviço de estoques.
     *
     * @see EstoqueService
     */
    private final EstoqueService estoqueService
            = ApplicationContext.getEstoqueService();

    /**
     * Modelo da tabela.
     *
     * @see EstoqueTableModel
     */
    private final EstoqueTableModel tableModel
            = new EstoqueTableModel();

    /**
     * Tabela de estoques.
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
     * Botão de busca por codigo.
     *
     * @see JButton
     */
    private final JButton buscarCodigoButton
            = UI.button("Buscar por Codigo");

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
     * Cria tela de relatório de estoques.
     */
    public EstoqueRelatorioScreen() {
        super("relatorio-estoque");

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
     * @return BaseTableModel<Estoque> - Model da tabela
     */
    @Override
    protected BaseTableModel<Estoque> tableModel() {
        return tableModel;
    }

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    @Override
    protected String entityNameSingular() {
        return "estoque";
    }

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    @Override
    protected String entityNamePlural() {
        return "estoques";
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
                buscarCodigoButton,
                this::buscarPorCodigo
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
                                "Relatório de Estoques"
                        ),
                        UIScreen.subtitle(
                                "Consulta e gerenciamento dos estoques cadastrados no sistema."
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
     * Busca estoque por ID.
     */
    private void buscarPorId() {
        Integer id = PopupInputs.integer(
                "Buscar Estoque",
                "ID do estoque:"
        );

        if (id == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar estoque!",
                () -> {
                    Optional<Estoque> estoque = estoqueService
                            .findById(id);

                    if (estoque.isEmpty()) {
                        clearResults();

                        Popups.warn(
                                "Estoque não encontrado!"
                        );

                        return;
                    }

                    setResultados(
                            List.of(estoque.get())
                    );
                }
        );
    }

    /**
     * Busca estoques por nome.
     */
    private void buscarPorNome() {
        String nome = PopupInputs.requiredText(
                "Buscar Estoque",
                "Nome do estoque:"
        );

        if (nome == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar estoques!",
                () -> setResultados(
                        estoqueService.findByNome(nome)
                )
        );
    }

    /**
     * Busca estoques por codigo.
     */
    private void buscarPorCodigo() {
        String codigo = PopupInputs.requiredText(
                "Buscar Estoque",
                "Codigo do estoque:"
        );

        if (codigo == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar estoques!",
                () -> setResultados(
                        estoqueService.findByCodigo(codigo)
                )
        );
    }

    /**
     * Lista todos os estoques.
     */
    private void listarTodos() {
        Actions.safe(
                "Erro ao listar estoques!",
                () -> setResultados(
                        estoqueService.findAll()
                )
        );
    }

    /**
     * Remove estoque selecionado.
     */
    private void removerSelecionado() {
        Estoque estoque = selectedRow();

        if (estoque == null) {
            Popups.warn(
                    "Selecione um estoque para remover!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                "Deseja remover o estoque '%s'?"
                        .formatted(estoque.getNome())
        );

        if (!confirmacao) {
            return;
        }

        Actions.safe(
                "Erro ao remover estoque!",
                () -> {
                    estoqueService.delete(estoque);

                    Popups.success(
                            "Estoque removido com sucesso!"
                    );

                    listarTodos();
                }
        );
    }

}
