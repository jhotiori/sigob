package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.ProdutoTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de produtos.
 */
public final class ProdutoRelatorioScreen extends BaseScreen {

    /**
     * Serviço de produtos.
     *
     * @see ProdutoService
     */
    private final ProdutoService produtoService
            = ApplicationContext.getProdutoService();

    /**
     * Modelo da tabela.
     *
     * @see ProdutoTableModel
     */
    private final ProdutoTableModel tableModel
            = new ProdutoTableModel();

    /**
     * Tabela de produtos.
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
     * Botão de busca por código.
     *
     * @see JButton
     */
    private final JButton buscarCodigoButton
            = UI.button("Buscar por Código");

    /**
     * Botão de busca por categoria.
     *
     * @see JButton
     */
    private final JButton buscarCategoriaButton
            = UI.button("Buscar por Categoria");

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
     * Cria tela de relatório de produtos.
     */
    public ProdutoRelatorioScreen() {
        super("relatorio-produto");

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

        Events.mouse(buscarCodigoButton, mouse -> {
            mouse.onClicked(this::buscarPorCodigo);
        });

        Events.mouse(buscarCategoriaButton, mouse -> {
            mouse.onClicked(this::buscarPorCategoria);
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
                        UIScreen.title("Relatório de Produtos"),
                        UIScreen.subtitle(
                                "Consulta e gerenciamento dos produtos cadastrados no sistema."
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
                            buscarCodigoButton,
                            buscarCategoriaButton,
                            listarTodosButton,
                            removerButton
                    )
                    .build();
        } else {
            return UI.grid(2, 3)
                    .add(
                            buscarIdButton,
                            buscarNomeButton,
                            buscarCodigoButton,
                            buscarCategoriaButton,
                            listarTodosButton
                    )
                    .build();
        }
    }

    /**
     * Busca produto por ID.
     */
    private void buscarPorId() {
        try {
            String input = Popups.input(
                    "ID do produto:"
            );

            if (input == null || input.isBlank()) {
                return;
            }

            int id = Integer.parseInt(input);

            Optional<Produto> produto = produtoService
                    .findById(id);

            if (produto.isEmpty()) {
                clearResults();

                Popups.warn(
                        "Produto não encontrado!"
                );

                return;
            }

            setResultados(
                    List.of(produto.get())
            );
        } catch (NumberFormatException e) {
            Popups.error(
                    "ID inválido!"
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar produto: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca produtos por nome.
     */
    private void buscarPorNome() {
        try {
            String nome = Popups.input(
                    "Nome do produto:"
            );

            if (nome == null || nome.isBlank()) {
                return;
            }

            List<Produto> produtos = produtoService
                    .findByNome(nome);

            setResultados(produtos);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar produtos: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca produto por código.
     */
    private void buscarPorCodigo() {
        try {
            String codigo = Popups.input(
                    "Código do produto:"
            );

            if (codigo == null || codigo.isBlank()) {
                return;
            }

            Optional<Produto> produto = produtoService
                    .findByCodigo(codigo);

            if (produto.isEmpty()) {
                clearResults();

                Popups.warn(
                        "Produto não encontrado!"
                );

                return;
            }

            setResultados(
                    List.of(produto.get())
            );
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar produto: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Busca produtos por categoria.
     */
    private void buscarPorCategoria() {
        try {
            String categoria = Popups.input(
                    "Nome da categoria:"
            );

            if (categoria == null || categoria.isBlank()) {
                return;
            }

            List<Produto> produtos = produtoService
                    .findByCategoria(categoria);

            setResultados(produtos);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao buscar produtos por categoria: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Lista todos os produtos.
     */
    private void listarTodos() {
        try {
            List<Produto> produtos = produtoService
                    .findAll();

            setResultados(produtos);
        } catch (Exception e) {
            Popups.error(
                    "Erro ao listar produtos: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Remove produto selecionado.
     */
    private void removerSelecionado() {
        try {
            int row = table.getSelectedRow();

            if (row < 0) {
                Popups.warn(
                        "Selecione um produto para remover!"
                );

                return;
            }

            Produto produto = tableModel
                    .getProduto(row);

            boolean confirmacao = Popups.confirm(
                    "Deseja remover o produto '%s'?"
                            .formatted(produto.getNome())
            );

            if (!confirmacao) {
                return;
            }

            produtoService.delete(produto);

            Popups.success(
                    "Produto removido com sucesso!"
            );

            listarTodos();
        } catch (Exception e) {
            Popups.error(
                    "Erro ao remover produto: %s"
                            .formatted(e.getMessage())
            );
        }
    }

    /**
     * Define resultados da tabela.
     *
     * @param produtos - Lista de produtos
     */
    private void setResultados(
            List<Produto> produtos
    ) {
        if (produtos == null || produtos.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhum produto encontrado!"
            );

            return;
        }

        tableModel.setProdutos(produtos);
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setProdutos(List.of());
    }

}
