package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.EstoqueTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de estoques.
 */
public final class EstoqueRelatorioScreen extends BaseScreen {

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
                        UIScreen.title("Relatório de Estoques"),
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
     * Busca estoque por ID.
     */
    private void buscarPorId() {
        try {
            String input = Popups.input(
                    "ID do estoque:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            int id = Integer.parseInt(input);

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
        } catch (NumberFormatException e) {
            Popups.error(
                    "ID inválido!"
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar estoque: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca estoques por nome.
     */
    private void buscarPorNome() {
        try {
            String nome = Popups.input(
                    "Nome do estoque:"
            );

            if (nome == null || nome.isBlank()) {
                return;
            }

            List<Estoque> estoques = estoqueService
                    .findByNome(nome);

            setResultados(estoques);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar estoques: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Lista todos os estoques.
     */
    private void listarTodos() {
        try {
            List<Estoque> estoques = estoqueService
                    .findAll();

            setResultados(estoques);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao listar estoques: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Remove estoque selecionado.
     */
    private void removerSelecionado() {
        try {
            int row = table.getSelectedRow();

            if (row < 0) {
                Popups.warn(
                        "Selecione um estoque para remover!"
                );

                return;
            }

            Estoque estoque = tableModel
                    .getEstoque(row);

            boolean confirmacao = Popups.confirm(
                    "Deseja remover o estoque '%s'?"
                            .formatted(estoque.getNome())
            );

            if (!confirmacao) {
                return;
            }

            estoqueService.delete(estoque);

            Popups.success(
                    "Estoque removido com sucesso!"
            );

            listarTodos();
        } catch (Exception e) {
            String message = e.getMessage();

            if (message != null
                    && message.contains("violates foreign key constraint")) {
                Popups.error(
                        "Não é possível remover este estoque porque ele está vinculado a outros registros."
                );

                return;
            }

            Popups.error(
                    "Erro ao remover estoque: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param estoques - Lista de estoques
     */
    private void setResultados(
            List<Estoque> estoques
    ) {
        if (estoques == null || estoques.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhum estoque encontrado!"
            );

            return;
        }

        tableModel.setEstoques(estoques);
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setEstoques(List.of());
    }

}
