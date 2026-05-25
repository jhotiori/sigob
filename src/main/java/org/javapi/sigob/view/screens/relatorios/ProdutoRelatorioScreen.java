package org.javapi.sigob.view.screens.relatorios;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.view.Actions;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.base.BaseRelatorioScreen;
import org.javapi.sigob.view.base.BaseTableModel;
import org.javapi.sigob.view.models.ProdutoTableModel;
import org.javapi.sigob.view.popups.PopupInputs;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIEvents;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de relatório de produtos.
 */
public final class ProdutoRelatorioScreen
        extends BaseRelatorioScreen<Produto> {

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
     * @return BaseTableModel<Produto> - Model da tabela
     */
    @Override
    protected BaseTableModel<Produto> tableModel() {
        return tableModel;
    }

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    @Override
    protected String entityNameSingular() {
        return "produto";
    }

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    @Override
    protected String entityNamePlural() {
        return "produtos";
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
                buscarCategoriaButton,
                this::buscarPorCategoria
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
                                "Relatório de Produtos"
                        ),
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
        if (hasAdminAccess()) {
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
        }

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

    /**
     * Busca produto por ID.
     */
    private void buscarPorId() {
        Integer id = PopupInputs.integer(
                "Buscar Produto",
                "ID do produto:"
        );

        if (id == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar produto!",
                () -> {
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
                }
        );
    }

    /**
     * Busca produtos por nome.
     */
    private void buscarPorNome() {
        String nome = PopupInputs.requiredText(
                "Buscar Produto",
                "Nome do produto:"
        );

        if (nome == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar produtos!",
                () -> setResultados(
                        produtoService.findByNome(nome)
                )
        );
    }

    /**
     * Busca produto por código.
     */
    private void buscarPorCodigo() {
        String codigo = PopupInputs.requiredText(
                "Buscar Produto",
                "Código do produto:"
        );

        if (codigo == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar produto!",
                () -> {
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
                }
        );
    }

    /**
     * Busca produtos por categoria.
     */
    private void buscarPorCategoria() {
        String categoria = PopupInputs.requiredText(
                "Buscar Produto",
                "Nome da categoria:"
        );

        if (categoria == null) {
            return;
        }

        Actions.safe(
                "Erro ao buscar produtos por categoria!",
                () -> setResultados(
                        produtoService.findByCategoria(categoria)
                )
        );
    }

    /**
     * Lista todos os produtos.
     */
    private void listarTodos() {
        Actions.safe(
                "Erro ao listar produtos!",
                () -> setResultados(
                        produtoService.findAll()
                )
        );
    }

    /**
     * Remove produto selecionado.
     */
    private void removerSelecionado() {
        Produto produto = selectedRow();

        if (produto == null) {
            Popups.warn(
                    "Selecione um produto para remover!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                "Deseja remover o produto '%s'?"
                        .formatted(produto.getNome())
        );

        if (!confirmacao) {
            return;
        }

        Actions.safe(
                "Erro ao remover produto!",
                () -> {
                    produtoService.delete(produto);

                    Popups.success(
                            "Produto removido com sucesso!"
                    );

                    listarTodos();
                }
        );
    }

}
