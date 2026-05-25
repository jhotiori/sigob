package org.javapi.sigob.view.screens.cadastros;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.ProdutoTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de cadastro e gerenciamento de produtos.
 */
public final class CadastroProdutoScreen extends BaseScreen {

    /**
     * Campo de busca.
     */
    private final JTextField buscaField = UI.textField(field -> {
        field.setColumns(32);
    });

    /**
     * Campo de código do produto.
     */
    private final JTextField codigoField = UI.textField(field -> {
        field.setColumns(32);
    });

    /**
     * Campo de nome do produto.
     */
    private final JTextField nomeField = UI.textField(field -> {
        field.setColumns(64);
    });

    /**
     * Campo de valor de compra.
     */
    private final JTextField valorCompraField = UI.textField();

    /**
     * Campo de valor de venda.
     */
    private final JTextField valorVendaField = UI.textField();

    /**
     * ComboBox de categorias.
     */
    private final JComboBox<String> categoriasBox = UI.comboBox();

    /**
     * ComboBox de moedas.
     */
    private final JComboBox<String> moedasBox = UI.comboBox();

    /**
     * Modelo da tabela.
     */
    private final ProdutoTableModel tableModel
            = new ProdutoTableModel();

    /**
     * Tabela de produtos.
     */
    private final JTable produtosTable = UI.table(this.tableModel);


    /**
     * Ordenador/filtro da tabela.
     */
    private final TableRowSorter<ProdutoTableModel> tableSorter
            = new TableRowSorter<>(tableModel);

    /**
     * Botão de cadastro.
     */
    private final JButton cadastrarButton = UI.button("Cadastrar");

    /**
     * Botão de atualização.
     */
    private final JButton atualizarButton = UI.button("Atualizar");

    /**
     * Botão de remoção.
     */
    private final JButton removerButton = UI.button("Remover");

    /**
     * Botão de limpeza.
     */
    private final JButton limparButton = UI.button("Limpar");

    /**
     * Mapa de categorias.
     */
    private final LinkedHashMap<String, Integer> categoriasMap
            = new LinkedHashMap<>();

    /**
     * Mapa de moedas.
     */
    private final LinkedHashMap<String, Integer> moedasMap
            = new LinkedHashMap<>();

    /**
     * Produto atualmente selecionado.
     */
    private Produto selectedProduto;

    /**
     * Cria tela de cadastro de produtos.
     */
    public CadastroProdutoScreen() {
        super("cadastro-produto");

        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setup() {
        configureTable();

        refresh();

        setupSearch();
        setupTableSelection();
        setupCadastrar();
        setupAtualizar();
        setupRemover();
        setupLimpar();
    }

    /**
     * Configura tabela.
     */
    private void configureTable() {
        produtosTable.setModel(tableModel);
        produtosTable.setRowSorter(tableSorter);
        produtosTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        produtosTable.getTableHeader()
                .setReorderingAllowed(false);
    }

    /**
     * Configura busca dinâmica.
     */
    private void setupSearch() {
        Events.text(buscaField, document -> {
            document.onChanged(() -> {
                String busca = buscaField.getText();

                if (busca == null || busca.isBlank()) {
                    tableSorter.setRowFilter(null);
                    return;
                }

                tableSorter.setRowFilter(
                        RowFilter.regexFilter(
                                "(?i)" + busca.trim()
                        )
                );
            });
        });
    }

    /**
     * Configura seleção da tabela.
     */
    private void setupTableSelection() {
        produtosTable.getSelectionModel()
                .addListSelectionListener(event -> {
                    if (event.getValueIsAdjusting()) {
                        return;
                    }

                    int selectedRow = produtosTable.getSelectedRow();

                    if (selectedRow < 0) {
                        selectedProduto = null;
                        return;
                    }

                    int modelRow = produtosTable
                            .convertRowIndexToModel(selectedRow);

                    Produto produto = tableModel
                            .getProduto(modelRow);

                    selectedProduto = produto;

                    fillForm(produto);
                });
    }

    /**
     * Configura ação de cadastro.
     */
    private void setupCadastrar() {
        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(() -> {
                try {
                    Produto produto = buildProdutoFromForm(false);

                    ProdutoService service = ApplicationContext
                            .getProdutoService();

                    Optional<Produto> existente = service
                            .findByCodigo(produto.getCodigo());

                    if (existente.isPresent()) {
                        Popups.warn(
                                "Já existe um produto com este código!"
                        );

                        return;
                    }

                    service.save(produto);

                    Popups.success(
                            "Produto '%s' cadastrado com sucesso!"
                                    .formatted(produto.getNome())
                    );

                    refreshTable();
                    clearForm();
                } catch (IllegalArgumentException e) {
                    Popups.warn(e.getMessage());
                } catch (Exception e) {
                    Popups.error(
                            "Erro ao cadastrar produto: %s"
                                    .formatted(e.getMessage())
                    );
                }
            });
        });
    }

    /**
     * Configura ação de atualização.
     */
    private void setupAtualizar() {
        Events.mouse(atualizarButton, mouse -> {
            mouse.onClicked(() -> {
                try {
                    if (selectedProduto == null) {
                        Popups.warn(
                                "Selecione um produto para atualizar!"
                        );

                        return;
                    }

                    Produto atualizado = buildProdutoFromForm(true);

                    ProdutoService service = ApplicationContext
                            .getProdutoService();

                    String novoCodigo = atualizado.getCodigo();

                    if (!novoCodigo.equalsIgnoreCase(
                            selectedProduto.getCodigo()
                    )) {
                        Optional<Produto> existente = service
                                .findByCodigo(novoCodigo);

                        if (existente.isPresent()) {
                            Popups.warn(
                                    "Já existe um produto com este código!"
                            );

                            return;
                        }
                    }

                    selectedProduto.setCodigo(
                            atualizado.getCodigo()
                    );

                    selectedProduto.setNome(
                            atualizado.getNome()
                    );

                    selectedProduto.setValorCompra(
                            atualizado.getValorCompra()
                    );

                    selectedProduto.setValorVenda(
                            atualizado.getValorVenda()
                    );

                    selectedProduto.setCategoria(
                            atualizado.getCategoria()
                    );

                    selectedProduto.setMoeda(
                            atualizado.getMoeda()
                    );

                    int produtoId = selectedProduto.getId();

                    service.update(selectedProduto);

                    Popups.success(
                            "Produto atualizado com sucesso!"
                    );

                    refreshTable();

                    restoreSelection(produtoId);
                } catch (IllegalArgumentException e) {
                    Popups.warn(e.getMessage());
                } catch (Exception e) {
                    Popups.error(
                            "Erro ao atualizar produto: %s"
                                    .formatted(e.getMessage())
                    );
                }
            });
        });
    }

    /**
     * Configura ação de remoção.
     */
    private void setupRemover() {
        Events.mouse(removerButton, mouse -> {
            mouse.onClicked(() -> {
                try {
                    if (selectedProduto == null) {
                        Popups.warn(
                                "Selecione um produto para remover!"
                        );

                        return;
                    }

                    String nome = selectedProduto.getNome();

                    ApplicationContext
                            .getProdutoService()
                            .delete(selectedProduto);

                    Popups.success(
                            "Produto '%s' removido com sucesso!"
                                    .formatted(nome)
                    );

                    refreshTable();
                    clearForm();
                } catch (Exception e) {
                    Popups.error(
                            "Erro ao remover produto: %s"
                                    .formatted(e.getMessage())
                    );
                }
            });
        });
    }

    /**
     * Configura limpeza do formulário.
     */
    private void setupLimpar() {
        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        refreshCategorias();
        refreshMoedas();
        refreshTable();
    }

    /**
     * Atualiza tabela.
     */
    private void refreshTable() {
        List<Produto> produtos = ApplicationContext
                .getProdutoService()
                .findAll();

        tableModel.setProdutos(produtos);
    }

    /**
     * Atualiza categorias.
     */
    private void refreshCategorias() {
        List<Categoria> categorias = ApplicationContext
                .getCategoriaService()
                .findAll();

        categoriasMap.clear();

        categorias.forEach(categoria -> {
            categoriasMap.put(
                    categoria.getNome(),
                    categoria.getId()
            );
        });

        categoriasBox.setModel(
                new DefaultComboBoxModel<>(
                        categoriasMap.keySet()
                                .toArray(new String[0])
                )
        );
    }

    /**
     * Atualiza moedas.
     */
    private void refreshMoedas() {
        List<Moeda> moedas = ApplicationContext
                .getMoedaService()
                .findAll();

        moedasMap.clear();

        moedas.forEach(moeda -> {
            moedasMap.put(
                    moeda.getSigla(),
                    moeda.getId()
            );
        });

        moedasBox.setModel(
                new DefaultComboBoxModel<>(
                        moedasMap.keySet()
                                .toArray(new String[0])
                )
        );
    }

    /**
     * Preenche formulário com produto.
     *
     * @param produto Produto selecionado
     */
    private void fillForm(
            Produto produto
    ) {
        codigoField.setText(produto.getCodigo());
        nomeField.setText(produto.getNome());

        valorCompraField.setText(
                produto.getValorCompra().toPlainString()
        );

        valorVendaField.setText(
                produto.getValorVenda().toPlainString()
        );

        if (produto.getCategoria() != null) {
            categoriasBox.setSelectedItem(
                    produto.getCategoria().getNome()
            );
        }

        if (produto.getMoeda() != null) {
            moedasBox.setSelectedItem(
                    produto.getMoeda().getSigla()
            );
        }
    }

    /**
     * Cria produto baseado no formulário.
     *
     * @param allowKeepValues true para manter campos vazios
     * @return Produto Produto criado
     */
    private Produto buildProdutoFromForm(
            boolean allowKeepValues
    ) {
        Produto produto = new Produto();

        String codigo = codigoField.getText();
        String nome = nomeField.getText();

        String compraText = valorCompraField.getText();
        String vendaText = valorVendaField.getText();

        if (!allowKeepValues || !isBlank(codigo)) {
            produto.setCodigo(codigo.trim());
        } else {
            produto.setCodigo(selectedProduto.getCodigo());
        }

        if (!allowKeepValues || !isBlank(nome)) {
            produto.setNome(nome.trim());
        } else {
            produto.setNome(selectedProduto.getNome());
        }

        if (!allowKeepValues || !isBlank(compraText)) {
            produto.setValorCompra(parseMoney(
                    compraText,
                    "valor de compra"
            ));
        } else {
            produto.setValorCompra(
                    selectedProduto.getValorCompra()
            );
        }

        if (!allowKeepValues || !isBlank(vendaText)) {
            produto.setValorVenda(parseMoney(
                    vendaText,
                    "valor de venda"
            ));
        } else {
            produto.setValorVenda(
                    selectedProduto.getValorVenda()
            );
        }

        String categoriaNome = (String) categoriasBox
                .getSelectedItem();

        Integer categoriaId = categoriasMap.get(categoriaNome);

        Categoria categoria = ApplicationContext
                .getCategoriaService()
                .findById(categoriaId)
                .orElseThrow(()
                        -> new IllegalArgumentException(
                        "Categoria inválida!"
                )
                );

        produto.setCategoria(categoria);

        String moedaSigla = (String) moedasBox
                .getSelectedItem();

        Integer moedaId = moedasMap.get(moedaSigla);

        Moeda moeda = ApplicationContext
                .getMoedaService()
                .findById(moedaId)
                .orElseThrow(()
                        -> new IllegalArgumentException(
                        "Moeda inválida!"
                )
                );

        produto.setMoeda(moeda);

        return produto;
    }

    /**
     * Converte texto monetário.
     *
     * @param text Texto
     * @param field Campo
     * @return BigDecimal Valor convertido
     */
    private BigDecimal parseMoney(
            String text,
            String field
    ) {
        try {
            return new BigDecimal(
                    text.trim()
                            .replace(",", ".")
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Informe um %s válido!"
                            .formatted(field)
            );
        }
    }

    /**
     * Restaura seleção após atualização.
     *
     * @param produtoId ID do produto
     */
    private void restoreSelection(
            int produtoId
    ) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Produto produto = tableModel.getProduto(i);

            if (produto.getId() == produtoId) {
                int viewIndex = produtosTable
                        .convertRowIndexToView(i);

                produtosTable.setRowSelectionInterval(
                        viewIndex,
                        viewIndex
                );

                break;
            }
        }
    }

    /**
     * Verifica se texto está vazio.
     *
     * @param value Valor
     * @return boolean true se vazio
     */
    private boolean isBlank(
            String value
    ) {
        return value == null || value.isBlank();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildContent())
                .padding(Spacing.MD)
                .build();
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel Painel principal
     */
    private JPanel buildContent() {
        return UI.column()
                .add(
                        UIScreen.title("Cadastro de Produtos"),
                        UIScreen.subtitle(
                                "Gerencia produtos, categorias e valores comerciais do sistema."
                        )
                )
                .glue()
                .add(
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Buscar produtos"
                                ),
                                buscaField
                        )
                )
                .glue()
                .add(
                        buildTable()
                )
                .glue()
                .add(
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Código [obrigatorio]"
                                ),
                                codigoField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Nome [obrigatorio]"
                                ),
                                nomeField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Valor de Compra [obrigatorio]"
                                ),
                                valorCompraField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Valor de Venda [obrigatorio]"
                                ),
                                valorVendaField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Categoria"),
                                categoriasBox
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Moeda"),
                                moedasBox
                        )
                )
                .glue()
                .add(
                        UIScreen.actions(
                                cadastrarButton,
                                atualizarButton,
                                removerButton,
                                limparButton
                        )
                )
                .build();
    }

    /**
     * Constrói tabela.
     *
     * @return JScrollPane Scroll da tabela
     */
    private JScrollPane buildTable() {
        JScrollPane scroll = UI.scroll(produtosTable);

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        return scroll;
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        selectedProduto = null;

        produtosTable.clearSelection();

        UIForm.clearFields(
                codigoField,
                nomeField,
                valorCompraField,
                valorVendaField
        );

        if (categoriasBox.getItemCount() > 0) {
            categoriasBox.setSelectedIndex(0);
        }

        if (moedasBox.getItemCount() > 0) {
            moedasBox.setSelectedIndex(0);
        }
    }

}
