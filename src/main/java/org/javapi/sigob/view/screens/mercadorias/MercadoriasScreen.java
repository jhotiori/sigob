package org.javapi.sigob.view.screens.mercadorias;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.ProdutosEstoquesTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de gerenciamento de mercadorias.
 */
public final class MercadoriasScreen extends BaseScreen {

    /**
     * Serviço de produtos em estoque.
     *
     * @see ProdutosEstoquesService
     */
    private final ProdutosEstoquesService produtosEstoquesService
            = ApplicationContext.getProdutosEstoquesService();

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
     * @see ProdutosEstoquesTableModel
     */
    private final ProdutosEstoquesTableModel tableModel
            = new ProdutosEstoquesTableModel();

    /**
     * Tabela de produtos em estoque.
     *
     * @see JTable
     */
    private final JTable table = UI.table(tableModel);

    /**
     * Campo de origem.
     *
     * @see JComboBox
     */
    private final JComboBox<String> origemBox
            = UI.comboBox();

    /**
     * Campo de destino.
     *
     * @see JComboBox
     */
    private final JComboBox<String> destinoBox
            = UI.comboBox();

    /**
     * Campo de quantidade.
     *
     * @see JTextField
     */
    private final JTextField quantidadeField
            = UI.textField(field -> {
                field.setColumns(12);
            });

    /**
     * Botão de transferência.
     *
     * @see JButton
     */
    private final JButton transferirButton = UI.button("Transferir");

    /**
     * Mapa de origens.
     */
    private final LinkedHashMap<String, Integer> origensMap
            = new LinkedHashMap<>();

    /**
     * Mapa de destinos.
     */
    private final LinkedHashMap<String, Integer> destinosMap
            = new LinkedHashMap<>();

    /**
     * ID selecionado previamente.
     */
    private Integer selectedProdutoEstoqueId;

    /**
     * Cria tela de mercadorias.
     */
    public MercadoriasScreen() {
        super("mercadorias");

        initialize();
    }

    @Override
    protected JPanel build() {
        configureTable();

        return UIScreen.page(
                UI.column()
                        .add(UIScreen.title("Mercadorias"))
                        .add(
                                UIScreen.subtitle(
                                        "Gerencie transferências entre estoques."
                                )
                        )
                        .gap(Spacing.XS)
                        .glue()
                        .add(buildTableSection())
                        .gap(Spacing.XS)
                        .glue()
                        .add(buildTransferenciaSection())
                        .build()
        );
    }

    @Override
    protected void setup() {
        Events.mouse(transferirButton, mouse -> {
            mouse.onClicked(this::transferir);
        });

        Events.mouse(table, mouse -> {
            mouse.onClicked(event -> {
                if (event.getClickCount() == 2) {
                    autofillFromTable();
                }
            });
        });

        Events.mouse(origemBox, mouse -> {
            mouse.onClicked(() -> updateOrigens());
        });
    }

    @Override
    public void onShow() {
        refresh();
    }

    @Override
    public void refresh() {
        preserveSelection();

        updateTable();
        updateOrigens();
        updateDestinos();

        restoreSelection();
    }

    /**
     * Constrói seção da tabela.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildTableSection() {
        return UIScreen.section(
                "Estoque",
                UI.scroll(table, scroll -> {
                    scroll.setVerticalScrollBarPolicy(
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
                    );
                })
        );
    }

    /**
     * Constrói seção de transferência.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildTransferenciaSection() {
        return UIScreen.section(
                "Transferência",
                UI.column()
                        .add(
                                UIForm.field(
                                        UIForm.fieldLabel("Origem"),
                                        origemBox
                                )
                        )
                        .gap(Spacing.XS)
                        .add(
                                UIForm.field(
                                        UIForm.fieldLabel("Destino"),
                                        destinoBox
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
                        .add(
                                UIScreen.actions(transferirButton)
                        )
                        .build()
        );
    }

    /**
     * Configura tabela.
     */
    private void configureTable() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(32);
    }

    /**
     * Realiza transferência.
     */
    private void transferir() {
        try {
            String origemSelecionada
                    = (String) origemBox.getSelectedItem();

            if (origemSelecionada == null) {
                Popups.warn("Selecione uma origem!");
                return;
            }

            String destinoSelecionado
                    = (String) destinoBox.getSelectedItem();

            if (destinoSelecionado == null) {
                Popups.warn("Selecione um destino!");
                return;
            }

            String quantidadeTexto
                    = quantidadeField.getText();

            if (quantidadeTexto == null
                    || quantidadeTexto.isBlank()) {

                Popups.warn("Informe a quantidade!");
                return;
            }

            int quantidade = Integer.parseInt(quantidadeTexto);

            if (quantidade <= 0) {
                Popups.warn("Quantidade inválida!");
                return;
            }

            Integer origemId
                    = origensMap.get(origemSelecionada);

            Integer destinoId
                    = destinosMap.get(destinoSelecionado);

            if (origemId == null || destinoId == null) {
                Popups.warn("Dados inválidos!");
                return;
            }

            Optional<ProdutosEstoques> origemOpt
                    = produtosEstoquesService.findById(origemId);

            if (origemOpt.isEmpty()) {
                Popups.warn("Origem não encontrada!");
                return;
            }

            Optional<Estoque> destinoOpt
                    = estoqueService.findById(destinoId);

            if (destinoOpt.isEmpty()) {
                Popups.warn("Destino não encontrado!");
                return;
            }

            ProdutosEstoques origem = origemOpt.get();

            if (quantidade > origem.getQuantidade()) {
                Popups.warn(
                        """
                        Quantidade indisponível!
                        Máximo disponível: %d
                        """
                                .formatted(origem.getQuantidade())
                );

                return;
            }

            boolean confirmar = Popups.confirm(
                    """
                    Confirmar transferência?

                    Produto: %s
                    Origem: %s
                    Destino: %s
                    Quantidade: %d
                    """
                            .formatted(
                                    origem.getProduto().getNome(),
                                    origem.getEstoque().getNome(),
                                    destinoOpt.get().getNome(),
                                    quantidade
                            )
            );

            if (!confirmar) {
                return;
            }

            produtosEstoquesService.transferir(
                    origem,
                    destinoOpt.get(),
                    quantidade
            );

            selectedProdutoEstoqueId = origem.getId();

            quantidadeField.setText("");

            refresh();

            Popups.success("Transferência realizada!");

        } catch (NumberFormatException e) {
            Popups.warn("Quantidade inválida!");

        } catch (Exception e) {
            Popups.error(
                    "Erro ao transferir mercadoria: "
                    + e.getMessage()
            );
        }
    }

    /**
     * Atualiza tabela.
     */
    private void updateTable() {
        try {
            List<ProdutosEstoques> itens
                    = produtosEstoquesService.findAll()
                            .stream()
                            .filter(item -> item.getQuantidade() > 0)
                            .toList();

            tableModel.setItens(itens);

        } catch (Exception e) {
            Popups.error(
                    "Erro ao atualizar tabela: "
                    + e.getMessage()
            );
        }
    }

    /**
     * Atualiza origens disponíveis.
     */
    private void updateOrigens() {
        try {
            Object selected = origemBox.getSelectedItem();

            origemBox.removeAllItems();

            origensMap.clear();

            List<ProdutosEstoques> itens
                    = produtosEstoquesService.findAll()
                            .stream()
                            .filter(item -> item.getQuantidade() > 0)
                            .toList();

            for (ProdutosEstoques item : itens) {
                String nome = "%s (Estoque: %s) [%d disponível]"
                        .formatted(
                                item.getProduto().getNome(),
                                item.getEstoque().getNome(),
                                item.getQuantidade()
                        );

                origensMap.put(nome, item.getId());

                origemBox.addItem(nome);
            }

            if (selected != null) {
                origemBox.setSelectedItem(selected);
            }

        } catch (Exception e) {
            Popups.error(
                    "Erro ao atualizar origens: "
                    + e.getMessage()
            );
        }
    }

    /**
     * Atualiza destinos disponíveis.
     */
    private void updateDestinos() {
        try {
            destinoBox.removeAllItems();

            destinosMap.clear();

            String origemSelecionada
                    = (String) origemBox.getSelectedItem();

            if (origemSelecionada == null) {
                return;
            }

            Integer origemId
                    = origensMap.get(origemSelecionada);

            if (origemId == null) {
                return;
            }

            Optional<ProdutosEstoques> origemOpt
                    = produtosEstoquesService.findById(origemId);

            if (origemOpt.isEmpty()) {
                return;
            }

            ProdutosEstoques origem = origemOpt.get();

            List<Estoque> estoques
                    = estoqueService.findAll();

            for (Estoque estoque : estoques) {
                if (estoque.getId()
                        == origem.getEstoque().getId()) {

                    continue;
                }

                String nome = "%s (%s)"
                        .formatted(
                                estoque.getNome(),
                                estoque.getCodigo()
                        );

                destinosMap.put(nome, estoque.getId());

                destinoBox.addItem(nome);
            }

        } catch (Exception e) {
            Popups.error(
                    "Erro ao atualizar destinos: "
                    + e.getMessage()
            );
        }
    }

    /**
     * Realiza autofill baseado na tabela.
     */
    private void autofillFromTable() {
        int row = table.getSelectedRow();

        if (row < 0) {
            return;
        }

        ProdutosEstoques item
                = tableModel.getItem(row);

        if (item == null) {
            return;
        }

        selectedProdutoEstoqueId = item.getId();

        restoreSelection();
    }

    /**
     * Preserva seleção atual.
     */
    private void preserveSelection() {
        String origemSelecionada
                = (String) origemBox.getSelectedItem();

        if (origemSelecionada == null) {
            return;
        }

        selectedProdutoEstoqueId
                = origensMap.get(origemSelecionada);
    }

    /**
     * Restaura seleção atual.
     */
    private void restoreSelection() {
        if (selectedProdutoEstoqueId == null) {
            return;
        }

        for (String key : origensMap.keySet()) {
            Integer id = origensMap.get(key);

            if (id == null) {
                continue;
            }

            if (id.equals(selectedProdutoEstoqueId)) {
                origemBox.setSelectedItem(key);
                return;
            }
        }
    }

}
