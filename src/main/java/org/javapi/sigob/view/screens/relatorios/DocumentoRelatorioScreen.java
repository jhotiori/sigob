package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.service.DocumentoService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.DocumentoTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de documentos.
 */
public final class DocumentoRelatorioScreen extends BaseScreen {

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

        Events.mouse(buscarDocumentoButton, mouse -> {
            mouse.onClicked(this::buscarPorDocumento);
        });

        Events.mouse(buscarTipoButton, mouse -> {
            mouse.onClicked(this::buscarPorTipo);
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
                                "Relatório de Documentos"
                        ),
                        UIScreen.subtitle(
                                "Consulta e gerenciamento dos documentos cadastrados no sistema."
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
                            buscarDocumentoButton,
                            buscarTipoButton,
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        } else {
            return UI.grid(2, 3)
                    .add(
                            buscarIdButton,
                            buscarDocumentoButton,
                            buscarTipoButton,
                            listarTodosButton
                    )
                    .build();
        }
    }

    /**
     * Busca documento por ID.
     */
    private void buscarPorId() {
        try {
            String input = Popups.input(
                    "ID do documento:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            int id = Integer.parseInt(input);

            Optional<Documento> documento = documentoService
                    .findById(id);

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
        } catch (NumberFormatException e) {
            Popups.error(
                    "ID inválido!"
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar documento: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca documentos por documento.
     */
    private void buscarPorDocumento() {
        try {
            String documento = Popups.input(
                    "Documento:"
            );

            if (documento == null || documento.isBlank()) {
                return;
            }

            List<Documento> documentos = documentoService
                    .findByDocumento(documento);

            setResultados(documentos);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar documentos: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca documentos por tipo.
     */
    private void buscarPorTipo() {
        try {
            String tipo = Popups.input(
                    "Tipo do documento:"
            );

            if (tipo == null || tipo.isBlank()) {
                return;
            }

            List<Documento> documentos = documentoService
                    .findByTipo(tipo);

            setResultados(documentos);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar documentos por tipo: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Lista todos os documentos.
     */
    private void listarTodos() {
        try {
            List<Documento> documentos = documentoService
                    .findAll();

            setResultados(documentos);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao listar documentos: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Remove documento selecionado.
     */
    private void removerSelecionado() {
        try {
            int row = table.getSelectedRow();

            if (row < 0) {
                Popups.warn(
                        "Selecione um documento para remover!"
                );

                return;
            }

            Documento documento = tableModel
                    .getDocumento(row);

            boolean confirmacao = Popups.confirm(
                    "Deseja remover o documento '%s'?"
                            .formatted(documento.getDocumento())
            );

            if (!confirmacao) {
                return;
            }

            documentoService.delete(documento);

            Popups.success(
                    "Documento removido com sucesso!"
            );

            listarTodos();
        } catch (Exception e) {
            String message = e.getMessage();

            if (message != null
                    && message.contains("violates foreign key constraint")) {

                Popups.error(
                        "Não é possível remover este documento porque ele está vinculado a outros registros."
                );

                return;
            }

            Popups.error(
                    "Erro ao remover documento: %s"
                            .formatted(message)
            );
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param documentos - Lista de documentos
     */
    private void setResultados(
            List<Documento> documentos
    ) {
        if (documentos == null
                || documentos.isEmpty()) {

            clearResults();

            Popups.warn(
                    "Nenhum documento encontrado!"
            );

            return;
        }

        tableModel.setDocumentos(
                documentos
        );
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setDocumentos(
                List.of()
        );
    }

}
