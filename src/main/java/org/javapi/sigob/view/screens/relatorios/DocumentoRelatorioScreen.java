package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.service.DocumentoService;
import org.javapi.sigob.view.Actions;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.base.BaseRelatorioScreen;
import org.javapi.sigob.view.base.BaseTableModel;
import org.javapi.sigob.view.models.DocumentoTableModel;
import org.javapi.sigob.view.popups.PopupInputs;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIEvents;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de documentos.
 */
public final class DocumentoRelatorioScreen
        extends BaseRelatorioScreen<Documento> {

    /**
     * Serviço de documentos.
     *
     * @see DocumentoService
     */
    private final DocumentoService documentoService
            = ApplicationContext.getDocumentoService();

    /**
     * Modelo da tabela.
     *
     * @see DocumentoTableModel
     */
    private final DocumentoTableModel tableModel
            = new DocumentoTableModel();

    /**
     * Tabela de documentos.
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
     * Botão de busca por documento.
     *
     * @see JButton
     */
    private final JButton buscarDocumentoButton
            = UI.button("Buscar por Documento");

    /**
     * Botão de busca por tipo.
     *
     * @see JButton
     */
    private final JButton buscarTipoButton
            = UI.button("Buscar por Tipo");

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
     * Cria tela de relatório de documentos.
     */
    public DocumentoRelatorioScreen() {
        super("relatorio-documento");

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
     * @return BaseTableModel<Documento> - Model da tabela
     */
    @Override
    protected BaseTableModel<Documento> tableModel() {
        return tableModel;
    }

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    @Override
    protected String entityNameSingular() {
        return "documento";
    }

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    @Override
    protected String entityNamePlural() {
        return "documentos";
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
                buscarDocumentoButton,
                this::buscarPorDocumento
        );

        UIEvents.onClick(
                buscarTipoButton,
                this::buscarPorTipo
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
                                "Relatório de Documentos"
                        ),
                        UIScreen.subtitle(
                                """
                                Consulta e gerenciamento dos documentos
                                cadastrados no sistema.
                                """
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
                            buscarDocumentoButton,
                            buscarTipoButton,
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        }

        return UI.grid(2, 3)
                .add(
                        buscarIdButton,
                        buscarDocumentoButton,
                        buscarTipoButton,
                        listarTodosButton
                )
                .build();
    }

    /**
     * Busca documento por ID.
     */
    private void buscarPorId() {
        Integer id = PopupInputs.integer(
                "Buscar Documento",
                "ID do documento:"
        );

        if (id == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar documento!",
                () -> {
                    Optional<Documento> documento
                    = documentoService.findById(id);

                    if (documento.isEmpty()) {
                        clearResults();

                        Popups.warn(
                                "Documento não encontrado!"
                        );

                        return;
                    }

                    setResultados(
                            List.of(documento.get())
                    );
                }
        );
    }

    /**
     * Busca documentos por documento.
     */
    private void buscarPorDocumento() {
        String documento = PopupInputs.requiredText(
                "Buscar Documento",
                "Documento:"
        );

        if (documento == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar documentos!",
                () -> {
                    setResultados(
                            documentoService.findByDocumento(
                                    documento
                            )
                    );
                }
        );
    }

    /**
     * Busca documentos por tipo.
     */
    private void buscarPorTipo() {
        String tipo = PopupInputs.requiredText(
                "Buscar Documento",
                "Tipo do documento:"
        );

        if (tipo == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar documentos por tipo!",
                () -> {
                    setResultados(
                            documentoService.findByTipo(tipo)
                    );
                }
        );
    }

    /**
     * Lista todos os documentos.
     */
    private void listarTodos() {
        Actions.safe(
                "Erro ao listar documentos!",
                () -> {
                    setResultados(
                            documentoService.findAll()
                    );
                }
        );
    }

    /**
     * Remove documento selecionado.
     */
    private void removerSelecionado() {
        Documento documento = selectedRow();

        if (documento == null) {
            Popups.warn(
                    "Selecione um documento para remover!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                """
                Deseja remover o documento '%s'?
                """
                        .formatted(
                                documento.getDocumento()
                        )
        );

        if (!confirmacao) {
            return;
        }

        Actions.safe(
                "Erro ao remover documento!",
                () -> {
                    documentoService.delete(documento);

                    Popups.success(
                            "Documento removido com sucesso!"
                    );

                    listarTodos();
                }
        );

    }

}
