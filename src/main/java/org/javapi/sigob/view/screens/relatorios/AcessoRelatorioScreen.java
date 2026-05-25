package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.service.AcessoService;
import org.javapi.sigob.view.Actions;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.base.BaseRelatorioScreen;
import org.javapi.sigob.view.base.BaseTableModel;
import org.javapi.sigob.view.models.AcessoTableModel;
import org.javapi.sigob.view.popups.PopupInputs;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIEvents;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de acessos.
 */
public final class AcessoRelatorioScreen
        extends BaseRelatorioScreen<Acesso> {

    /**
     * Serviço de acessos.
     */
    private final AcessoService acessoService
            = ApplicationContext.getAcessoService();

    /**
     * Modelo da tabela.
     */
    private final AcessoTableModel tableModel
            = new AcessoTableModel();

    /**
     * Tabela de acessos.
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
     * Cria tela de relatório de acessos.
     */
    public AcessoRelatorioScreen() {
        super("relatorio-acesso");

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
     * @return BaseTableModel<Acesso> - Model da tabela
     */
    @Override
    protected BaseTableModel<Acesso> tableModel() {
        return tableModel;
    }

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    @Override
    protected String entityNameSingular() {
        return "acesso";
    }

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    @Override
    protected String entityNamePlural() {
        return "acessos";
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
                        UIScreen.title("Relatório de Acessos"),
                        UIScreen.subtitle(
                                "Consulta e gerenciamento dos acessos cadastrados no sistema."
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
     * Busca acesso por ID.
     */
    private void buscarPorId() {
        Integer id = PopupInputs.integer(
                "Buscar Acesso",
                "ID do acesso:"
        );

        if (id == null) {
            return;
        }

        Optional<Acesso> acesso = Actions.safe(
                "Erro ao buscar acesso!",
                () -> acessoService.findById(id),
                Optional.empty()
        );

        if (acesso.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Acesso não encontrado!"
            );

            return;
        }

        setResultados(
                List.of(acesso.get())
        );
    }

    /**
     * Busca acessos por nome.
     */
    private void buscarPorNome() {
        String nome = PopupInputs.requiredText(
                "Buscar Acesso",
                "Nome do acesso:"
        );

        if (nome == null) {
            return;
        }

        List<Acesso> acessos = Actions.safe(
                "Erro ao buscar acessos!",
                () -> acessoService.findByNome(nome),
                List.of()
        );

        setResultados(acessos);
    }

    /**
     * Lista todos os acessos.
     */
    private void listarTodos() {
        List<Acesso> acessos = Actions.safe(
                "Erro ao listar acessos!",
                acessoService::findAll,
                List.of()
        );

        setResultados(acessos);
    }

    /**
     * Remove acesso selecionado.
     */
    private void removerSelecionado() {
        Acesso acesso = selectedRow();

        if (acesso == null) {
            Popups.warn(
                    "Selecione um acesso para remover!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                "Deseja remover o acesso '%s'?"
                        .formatted(acesso.getNome())
        );

        if (!confirmacao) {
            return;
        }

        Actions.safe(
                "Erro ao remover acesso!",
                () -> {
                    acessoService.delete(acesso);

                    Popups.success(
                            "Acesso removido com sucesso!"
                    );

                    listarTodos();
                }
        );
    }

}
