package org.javapi.sigob.view.screens;

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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.javapi.sigob.entity.ItemVenda;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.entity.Venda;
import org.javapi.sigob.service.ItemVendaService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.service.VendaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.components.ScrollComponent;
import org.javapi.sigob.view.components.TableComponent;
import org.javapi.sigob.view.models.ItemVendaTableModel;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Palette;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Tela de edição de venda.
 */
public class VendaEditorScreen extends BaseScreen {

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
     * @see {@link TableComponent}
     */
    private final TableComponent carrinhoTable = UI.table(carrinhoModel);

    /**
     * Scroll da tabela.
     *
     * @see {@link ScrollComponent}
     */
    private final ScrollComponent carrinhoScroll = UI.scroll(carrinhoTable);

    /**
     * Campo de produtos disponíveis.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> produtosBox = UI.comboBox();

    /**
     * Campo de quantidade.
     *
     * @see {@link JTextField}
     */
    private final JTextField quantidadeField = UI.textField();

    /**
     * Label de total atual.
     *
     * @see {@link JLabel}
     */
    private final JLabel totalLabel = UI.label("Total atual: R$ 0");

    /**
     * Botão de adicionar item.
     *
     * @see {@link JButton}
     */
    private final JButton adicionarButton = UI.button("Adicionar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Botão de remover item.
     *
     * @see {@link JButton}
     */
    private final JButton removerButton = UI.button("Remover", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Botão de finalizar venda.
     *
     * @see {@link JButton}
     */
    private final JButton finalizarButton = UI.button("Finalizar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

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

        init();
        setup();
    }

    @Override
    protected JPanel build() {
        configureTable();

        return UI.border()
                .center(buildPanel())
                .padding(Spacing.MD)
                .build();
    }

    @Override
    protected void setup() {
        adicionarButton.addActionListener(event -> {
            adicionarItem();
        });

        removerButton.addActionListener(event -> {
            removerItemSelecionado();
        });

        finalizarButton.addActionListener(event -> {
            finalizarVenda();
        });

        Events.mouse(carrinhoTable, mouse -> {
            mouse.onClicked(event -> {
                if (event.getClickCount() == 2) {
                    removerItemSelecionado();
                }
            });
        });
    }

    @Override
    public void update() {
        updateProdutos();
        updateCarrinho();
        updateReadonlyState();
    }

    private JPanel buildPanel() {
        return UI.column()
                .add(buildTitle())
                .add(buildSubtitle())
                .glue()
                .add(buildMetadataPanel())
                .glue()
                .add(buildAdicionarPanel())
                .glue()
                .add(buildCarrinhoPanel())
                .glue()
                .add(totalLabel)
                .glue()
                .add(UI.actions(removerButton, finalizarButton))
                .build();
    }

    private JLabel buildTitle() {
        return UI.label("Editor de Venda", label -> {
            label.setFont(Fonts.TITLE_MEDIUM);
        });
    }

    private JLabel buildSubtitle() {
        return UI.label(
                "Gerencie itens, estoque e finalização da venda.",
                label -> {
                    label.setForeground(Palette.FG_MUTED);
                    label.setFont(Fonts.TITLE_SMALL);
                }
        );
    }

    private JPanel buildMetadataPanel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return UI.grid(2, 2)
                .hgap(Spacing.MD)
                .vgap(Spacing.SM)
                .add(
                        UI.field(
                                UI.fieldLabel("Funcionário"),
                                UI.label(venda.getFuncionario().getNome())
                        ),
                        UI.field(
                                UI.fieldLabel("Cliente"),
                                UI.label(venda.getCliente().getNome())
                        ),
                        UI.field(
                                UI.fieldLabel("Status"),
                                UI.label(venda.getStatus())
                        ),
                        UI.field(
                                UI.fieldLabel("Data de abertura"),
                                UI.label(venda.getDataAbertura().format(formatter))
                        )
                )
                .build();
    }

    private JPanel buildAdicionarPanel() {
        return UI.column()
                .add(UI.subtitle("Adicionar item ao carrinho"))
                .add(
                        UI.field(
                                UI.fieldLabel("Produto"),
                                produtosBox
                        )
                )
                .add(
                        UI.field(
                                UI.fieldLabel("Quantidade"),
                                quantidadeField
                        )
                )
                .add(UI.actions(adicionarButton))
                .build();
    }

    private JPanel buildCarrinhoPanel() {
        return UI.column()
                .add(UI.subtitle("Carrinho"))
                .add(carrinhoScroll)
                .build();
    }

    /**
     * Configura tabela do carrinho.
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
                Messages.warn("Selecione um produto!");
                return;
            }

            String quantidadeTexto = quantidadeField.getText();

            if (quantidadeTexto == null || quantidadeTexto.isBlank()) {
                Messages.warn("Informe a quantidade!");
                return;
            }

            int quantidade = Integer.parseInt(quantidadeTexto);

            if (quantidade <= 0) {
                Messages.warn("Quantidade inválida!");
                return;
            }

            Integer produtoEstoqueId = produtosMap.get(produtoSelecionado);

            if (produtoEstoqueId == null) {
                Messages.warn("Produto inválido!");
                return;
            }

            Optional<ProdutosEstoques> produtoOpt = estoqueService.findById(produtoEstoqueId);

            if (produtoOpt.isEmpty()) {
                Messages.warn("Produto não encontrado!");
                return;
            }

            ProdutosEstoques produtoEstoque = produtoOpt.get();

            if (quantidade > produtoEstoque.getQuantidade()) {
                Messages.warn(
                        "Quantidade indisponível! Máximo disponível: %d"
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

            quantidadeField.setText("");

            update();

        } catch (NumberFormatException e) {
            Messages.warn("Quantidade inválida!");

        } catch (Exception e) {
            Messages.error("Erro ao adicionar item: " + e.getMessage());
        }
    }

    /**
     * Atualiza quantidade de item existente.
     *
     * @param item - Item existente
     * @param produtoEstoque - Produto do estoque
     * @param quantidadeAdicional - Quantidade adicionada
     */
    private void atualizarQuantidadeExistente(
            ItemVenda item,
            ProdutosEstoques produtoEstoque,
            int quantidadeAdicional
    ) {
        int novaQuantidade = item.getQuantidade() + quantidadeAdicional;

        if (novaQuantidade > produtoEstoque.getQuantidade()) {
            Messages.warn(
                    "Quantidade total excede estoque disponível! Máximo: %d"
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

        Messages.success("Quantidade atualizada!");
    }

    /**
     * Cria novo item no carrinho.
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

        Messages.success("Item adicionado!");
    }

    /**
     * Remove item selecionado da tabela.
     */
    private void removerItemSelecionado() {
        try {
            int linha = carrinhoTable.getSelectedRow();

            if (linha < 0) {
                Messages.warn("Selecione um item!");
                return;
            }

            ItemVenda item = carrinhoModel.getItem(linha);

            boolean confirmar = Messages.confirm(
                    "Deseja remover o item \"%s\"?"
                            .formatted(item.getProdutoEstoque().getProduto().getNome())
            );

            if (!confirmar) {
                return;
            }

            itemVendaService.delete(item);

            update();

            Messages.success("Item removido!");

        } catch (Exception e) {
            Messages.error("Erro ao remover item: " + e.getMessage());
        }
    }

    /**
     * Finaliza venda atual.
     */
    private void finalizarVenda() {
        try {
            List<ItemVenda> itens = itemVendaService.findByVenda(venda.getId());

            if (itens.isEmpty()) {
                Messages.warn("Carrinho vazio!");
                return;
            }

            BigDecimal total = itens.stream()
                    .map(ItemVenda::getValorSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            boolean confirmar = Messages.confirm(
                    "Confirmar pagamento da venda no valor de R$ %s?"
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

            update();

            Messages.success("Venda finalizada!");

        } catch (Exception e) {
            Messages.error("Erro ao finalizar venda: " + e.getMessage());
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
            String nome = "%s | %s | Disponível: %d"
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
     * Atualiza tabela do carrinho.
     */
    private void updateCarrinho() {
        try {
            List<ItemVenda> itens = itemVendaService.findByVenda(venda.getId());

            carrinhoModel.setItens(itens);

            BigDecimal total = itens.stream()
                    .map(ItemVenda::getValorSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            totalLabel.setText(
                    "Total atual: R$ %s".formatted(total)
            );

        } catch (Exception e) {
            Messages.error(e.getMessage());
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
