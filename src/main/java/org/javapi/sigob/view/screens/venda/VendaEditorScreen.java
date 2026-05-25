package org.javapi.sigob.view.screens.venda;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import org.javapi.sigob.entity.ItemVenda;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.ItemVendaService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.ItemVendaTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de edição de venda.
 */
public final class VendaEditorScreen extends BaseScreen {

    /**
     * Formatter de data.
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Venda editada.
     *
     * @see {@link Venda}
     */
    private final Venda venda;

    /**
     * Serviço de itens da venda.
     *
     * @see {@link ItemVendaService}
     */
    private final ItemVendaService itemVendaService = ApplicationContext.getItemVendaService();

    /**
     * Serviço de estoque.
     *
     * @see {@link ProdutosEstoquesService}
     */
    private final ProdutosEstoquesService estoqueService = ApplicationContext.getProdutosEstoquesService();

    /**
     * Serviço de vendas.
     *
     * @see {@link VendaService}
     */
    private final VendaService vendaService = ApplicationContext.getVendaService();

    /**
     * Modelo da tabela do carrinho.
     *
     * @see {@link ItemVendaTableModel}
     */
    private final ItemVendaTableModel carrinhoModel = new ItemVendaTableModel();

    /**
     * Tabela do carrinho.
     *
     * @see {@link JTable}
     */
    private final JTable carrinhoTable = UI.table(carrinhoModel);

    /**
     * Campo de produtos.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> produtosBox = UI.comboBox(combo -> {
        combo.setMaximumRowCount(12);
    });

    /**
     * Campo de quantidade.
     *
     * @see {@link JTextField}
     */
    private final JTextField quantidadeField = UI.textField(field -> {
        field.setColumns(12);
    });

    /**
     * Label do total.
     *
     * @see {@link JLabel}
     */
    private final JLabel totalLabel = UI.label("Total: R$0.00", label -> {
        label.setFont(Fonts.DEFAULT_BOLD);
    });

    /**
     * Botão de adicionar item.
     *
     * @see {@link JButton}
     */
    private final JButton adicionarButton = UI.button("Adicionar");

    /**
     * Botão de remover item.
     *
     * @see {@link JButton}
     */
    private final JButton removerButton = UI.button("Remover");

    /**
     * Botão de finalizar venda.
     *
     * @see {@link JButton}
     */
    private final JButton finalizarButton = UI.button("Finalizar");

    /**
     * Mapa de produtos disponíveis.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Integer> produtosMap = new LinkedHashMap<>();

    /**
     * Cria tela de edição de venda.
     *
     * @param venda - Venda editada
     */
    public VendaEditorScreen(Venda venda) {
        super("venda-editor-" + venda.getId());

        this.venda = venda;

        initialize();
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel construído
     */
    @Override
    protected JPanel build() {
        configureTable();
        return UIScreen.page(
                UI.column()
                        .add(UIScreen.title("Edição de Venda"))
                        .add(
                                UIScreen.subtitle(
                                        "Gerencie itens do carrinho, estoque e finalização da venda."
                                )
                        )
                        .glue()
                        .gap(Spacing.XS)
                        .add(buildMetadataSection())
                        .glue()
                        .gap(Spacing.XS)
                        .add(buildAdicionarSection())
                        .glue()
                        .gap(Spacing.XS)
                        .add(buildCarrinhoSection())
                        .glue()
                        .gap(Spacing.XS)
                        .add(totalLabel)
                        .glue()
                        .gap(Spacing.XS)
                        .add(UIScreen.actions(removerButton, finalizarButton))
                        .build()
        );
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        Events.mouse(adicionarButton, mouse -> {
            mouse.onClicked(this::adicionarItem);
        });

        Events.mouse(removerButton, mouse -> {
            mouse.onClicked(this::removerItemSelecionado);
        });

        Events.mouse(finalizarButton, mouse -> {
            mouse.onClicked(this::finalizarVenda);
        });

        Events.mouse(carrinhoTable, mouse -> {
            mouse.onClicked(event -> {
                if (event.getClickCount() == 2) {
                    removerItemSelecionado();
                }
            });
        });
    }

    /**
     * Atualiza dados dinâmicos da tela.
     */
    @Override
    public void refresh() {
        updateProdutos();
        updateCarrinho();
        updateReadonlyState();
    }

    /**
     * Constrói seção de metadados.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildMetadataSection() {
        return UIScreen.section(
                "Informações da Venda",
                UI.grid(2, 2)
                        .add(
                                UIForm.field(
                                        UIForm.fieldLabel("Funcionário"),
                                        UI.label(venda.getFuncionario().getNome())
                                ),
                                UIForm.field(
                                        UIForm.fieldLabel("Cliente"),
                                        UI.label(venda.getCliente().getNome())
                                ),
                                UIForm.field(
                                        UIForm.fieldLabel("Status"),
                                        UI.label(venda.getStatus())
                                ),
                                UIForm.field(
                                        UIForm.fieldLabel("Data Abertura"),
                                        UI.label(venda.getDataAbertura().format(DATE_FORMATTER))
                                )
                        )
                        .build()
        );
    }

    /**
     * Constrói seção de adição.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildAdicionarSection() {
        return UIScreen.section(
                "Adicionar Item",
                UI.column()
                        .add(
                                UIForm.field(
                                        UIForm.fieldLabel("Produto"),
                                        produtosBox
                                )
                        )
                        .gap(Spacing.XS)
                        .add(
                                UIForm.field(
                                        UIForm.fieldLabel("Quantidade"),
                                        quantidadeField
                                )
                        )
                        .gap(Spacing.XS)
                        .add(UIScreen.actions(adicionarButton))
                        .build()
        );
    }

    /**
     * Constrói seção do carrinho.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildCarrinhoSection() {
        return UIScreen.section(
                "Carrinho",
                UI.scroll(carrinhoTable, scroll -> {
                    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                })
        );
    }

    /**
     * Configura tabela.
     */
    private void configureTable() {
        carrinhoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carrinhoTable.setRowHeight(32);
    }

    /**
     * Adiciona item ao carrinho.
     */
    private void adicionarItem() {
        try {
            String produtoSelecionado = (String) produtosBox.getSelectedItem();
            if (produtoSelecionado == null || produtoSelecionado.isBlank()) {
                Popups.warn("Selecione um produto!");
                return;
            }

            String quantidadeTexto = quantidadeField.getText();
            if (quantidadeTexto == null || quantidadeTexto.isBlank()) {
                Popups.warn("Informe a quantidade!");
                return;
            }

            int quantidade = Integer.parseInt(quantidadeTexto);
            if (quantidade <= 0) {
                Popups.warn("Quantidade inválida!");
                return;
            }

            Integer produtoEstoqueId = produtosMap.get(produtoSelecionado);
            if (produtoEstoqueId == null) {
                Popups.warn("Produto inválido!");
                return;
            }

            Optional<ProdutosEstoques> produtoOpt = estoqueService.findById(produtoEstoqueId);
            if (produtoOpt.isEmpty()) {
                Popups.warn("Produto não encontrado!");
                return;
            }

            ProdutosEstoques produtoEstoque = produtoOpt.get();
            if (quantidade > produtoEstoque.getQuantidade()) {
                Popups.warn(
                        "Quantidade indisponível!\nMáximo disponível: %d"
                                .formatted(produtoEstoque.getQuantidade())
                );
                return;
            }

            Optional<ItemVenda> itemExistente = itemVendaService.findByVendaAndProdutoEstoque(
                    venda.getId(),
                    produtoEstoque.getId()
            );

            if (itemExistente.isPresent()) {
                atualizarQuantidadeExistente(
                        itemExistente.get(),
                        produtoEstoque,
                        quantidade
                );

            } else {
                criarNovoItem(produtoEstoque, quantidade);
            }

            UIForm.clearFields(quantidadeField);

            refresh();

        } catch (NumberFormatException e) {
            Popups.warn("Quantidade inválida!");

        } catch (Exception e) {
            Popups.error("Erro ao adicionar item: " + e.getMessage());
        }
    }

    /**
     * Atualiza quantidade de item existente.
     *
     * @param item - Item existente
     * @param produtoEstoque - Produto do estoque
     * @param quantidadeAdicional - Quantidade adicional
     */
    private void atualizarQuantidadeExistente(
            ItemVenda item,
            ProdutosEstoques produtoEstoque,
            int quantidadeAdicional
    ) {
        int novaQuantidade = item.getQuantidade() + quantidadeAdicional;

        if (novaQuantidade > produtoEstoque.getQuantidade()) {
            Popups.warn(
                    "Quantidade total excede estoque disponível!\nMáximo disponível: %d"
                            .formatted(produtoEstoque.getQuantidade())
            );

            return;
        }

        BigDecimal total = produtoEstoque.getProduto()
                .getValorVenda()
                .multiply(BigDecimal.valueOf(novaQuantidade));

        item.setQuantidade(novaQuantidade);
        item.setValorSaldo(total);

        itemVendaService.update(item);
    }

    /**
     * Cria novo item.
     *
     * @param produtoEstoque - Produto do estoque
     * @param quantidade - Quantidade adicionada
     */
    private void criarNovoItem(
            ProdutosEstoques produtoEstoque,
            int quantidade
    ) {
        BigDecimal total = produtoEstoque.getProduto()
                .getValorVenda()
                .multiply(BigDecimal.valueOf(quantidade));

        ItemVenda item = new ItemVenda(
                0,
                quantidade,
                total,
                produtoEstoque,
                venda
        );

        itemVendaService.save(item);
    }

    /**
     * Remove item selecionado.
     */
    private void removerItemSelecionado() {
        try {
            int linha = carrinhoTable.getSelectedRow();

            if (linha < 0) {
                Popups.warn("Selecione um item!");
                return;
            }

            ItemVenda item = carrinhoModel.getItem(linha);

            boolean confirmar = Popups.confirm(
                    "Deseja remover o item \"%s\"?"
                            .formatted(item.getProdutoEstoque().getProduto().getNome())
            );

            if (!confirmar) {
                return;
            }

            itemVendaService.delete(item);

            refresh();

            Popups.success("Item removido!");

        } catch (Exception e) {
            Popups.error("Erro ao remover item: " + e.getMessage());
        }
    }

    /**
     * Finaliza venda atual.
     */
    private void finalizarVenda() {
        try {
            List<ItemVenda> itens = itemVendaService.findByVenda(venda.getId());

            if (itens.isEmpty()) {
                Popups.warn("Carrinho vazio!");
                return;
            }

            BigDecimal total = itens.stream()
                    .map(ItemVenda::getValorSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            boolean confirmar = Popups.confirm(
                    "Confirmar pagamento da venda no valor de R$%s?"
                            .formatted(total)
            );

            if (!confirmar) {
                return;
            }

            for (ItemVenda item : itens) {
                ProdutosEstoques produtoEstoque = item.getProdutoEstoque();

                produtoEstoque.setQuantidade(
                        produtoEstoque.getQuantidade() - item.getQuantidade()
                );

                estoqueService.update(produtoEstoque);
            }

            venda.setStatus("finalizada");
            venda.setValorTotal(total);
            venda.setDataFinalizada(OffsetDateTime.now());

            vendaService.update(venda);

            refresh();

            Popups.success("Venda finalizada!");

        } catch (Exception e) {
            Popups.error("Erro ao finalizar venda: " + e.getMessage());
        }
    }

    /**
     * Atualiza produtos disponíveis.
     */
    private void updateProdutos() {
        produtosMap.clear();

        produtosBox.removeAllItems();

        List<ProdutosEstoques> produtos = estoqueService.findAll()
                .stream()
                .filter(produto -> produto.getQuantidade() > 0)
                .toList();

        for (ProdutosEstoques produto : produtos) {
            String nome = "%s (Estoque: %s) [%d disponível]"
                    .formatted(
                            produto.getProduto().getNome(),
                            produto.getEstoque().getNome(),
                            produto.getQuantidade()
                    );

            produtosMap.put(nome, produto.getId());

            produtosBox.addItem(nome);
        }
    }

    /**
     * Atualiza carrinho.
     */
    private void updateCarrinho() {
        try {
            List<ItemVenda> itens = itemVendaService.findByVenda(venda.getId());

            carrinhoModel.setItens(itens);

            BigDecimal total = itens.stream()
                    .map(ItemVenda::getValorSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            totalLabel.setText(
                    "Total: R$%s".formatted(total)
            );

        } catch (Exception e) {
            Popups.error("Erro ao atualizar carrinho: " + e.getMessage());
        }
    }

    /**
     * Atualiza estado visual da venda.
     */
    private void updateReadonlyState() {
        boolean aberta = "aberta".equalsIgnoreCase(venda.getStatus());

        produtosBox.setEnabled(aberta);
        quantidadeField.setEnabled(aberta);

        adicionarButton.setEnabled(aberta);
        removerButton.setEnabled(aberta);
        finalizarButton.setEnabled(aberta);
    }

}
