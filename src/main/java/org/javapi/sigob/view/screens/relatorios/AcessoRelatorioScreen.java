package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.AcessoTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de acessos.
 */
public final class AcessoRelatorioScreen extends BaseScreen {

    /**
     * Modelo da tabela.
     *
     * @see {@link AcessoTableModel}
     */
    private final AcessoTableModel tableModel
            = new AcessoTableModel();

    /**
     * Tabela de acessos.
     *
     * @see {@link JTable}
     */
    private final JTable table = UI.table(tableModel);

    /**
     * Botão de listagem por ID.
     *
     * @see {@link JButton}
     */
    private final JButton buscarIdButton
            = UI.button("Buscar por ID");

    /**
     * Botão de listagem por nome.
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
     * Atualiza tela.
     */
    @Override
    public void refresh() {
        listarTodos();
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
     * Busca acesso por ID.
     */
    private void buscarPorId() {
        try {
            String input = Popups.input(
                    "ID do acesso:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            int id = Integer.parseInt(input);

            Optional<Acesso> acesso = ApplicationContext
                    .getAcessoService()
                    .findById(id);

            if (acesso.isEmpty()) {
                clearResults();

                Popups.warn("Acesso não encontrado!");

                return;
            }

            setResultados(
                    List.of(acesso.get())
            );
        } catch (NumberFormatException e) {
            Popups.error("ID inválido!");
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar acesso: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca acessos por nome.
     */
    private void buscarPorNome() {
        try {
            String nome = Popups.input(
                    "Nome do acesso:"
            );

            if (nome == null || nome.isBlank()) {
                return;
            }

            List<Acesso> acessos = ApplicationContext
                    .getAcessoService()
                    .findByNome(nome);

            setResultados(acessos);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar acessos: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Lista todos os acessos.
     */
    private void listarTodos() {
        try {
            List<Acesso> acessos = ApplicationContext
                    .getAcessoService()
                    .findAll();

            setResultados(acessos);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao listar acessos: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Remove acesso selecionado.
     */
    private void removerSelecionado() {
        try {
            int row = table.getSelectedRow();

            if (row < 0) {
                Popups.warn(
                        "Selecione um acesso para remover!"
                );

                return;
            }

            Acesso acesso = tableModel.getAcesso(row);

            boolean confirmacao = Popups.confirm(
                    "Deseja remover o acesso '%s'?"
                            .formatted(acesso.getNome())
            );

            if (!confirmacao) {
                return;
            }

            ApplicationContext
                    .getAcessoService()
                    .delete(acesso);

            Popups.success(
                    "Acesso removido com sucesso!"
            );

            listarTodos();
        } catch (Exception e) {
            Popups.error(
                    "Erro ao remover acesso: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param acessos - Lista de acessos
     */
    private void setResultados(
            List<Acesso> acessos
    ) {
        if (acessos == null || acessos.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhum acesso encontrado!"
            );

            return;
        }

        tableModel.setAcessos(acessos);
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setAcessos(List.of());
    }

}
