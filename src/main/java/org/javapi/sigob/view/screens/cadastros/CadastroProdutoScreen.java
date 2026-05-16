package org.javapi.sigob.view.screens.cadastros;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.BaseScreen;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Tela de cadastro de produtos.
 */
public final class CadastroProdutoScreen extends BaseScreen {

    /**
     * Campo de código do produto.
     *
     * @see {@link JTextField}
     */
    private final JTextField codigoField = UI.textField(field -> {
        field.setColumns(64);
    });

    /**
     * Campo de nome do produto.
     *
     * @see {@link JTextField}
     */
    private final JTextField nomeField = UI.textField(field -> {
        field.setColumns(128);
    });

    /**
     * Campo de valor de compra.
     *
     * @see {@link JTextField}
     */
    private final JTextField valorCompraField = UI.textField();

    /**
     * Campo de valor de venda.
     *
     * @see {@link JTextField}
     */
    private final JTextField valorVendaField = UI.textField();

    /**
     * ComboBox de categorias.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> categoriasBox = UI.comboBox();

    /**
     * ComboBox de moedas.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> moedasBox = UI.comboBox();

    /**
     * Mapa de categorias.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Integer> categoriasMap = new LinkedHashMap<>();

    /**
     * Mapa de moedas.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Integer> moedasMap = new LinkedHashMap<>();

    /**
     * Botão de cadastro.
     *
     * @see {@link JButton}
     */
    private final JButton cadastrarButton = UI.button("Cadastrar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Botão de limpeza.
     *
     * @see {@link JButton}
     */
    private final JButton limparButton = UI.button("Limpar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Cria tela de cadastro de produtos.
     */
    public CadastroProdutoScreen() {
        super("cadastro-produto");
        init();
        setup();
    }

    /**
     * Realiza setup da tela.
     */
    @Override
    protected void setup() {
        update();

        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(() -> {
                try {
                    String codigo = codigoField.getText();
                    String nome = nomeField.getText();

                    BigDecimal compraValor = BigDecimal.valueOf(
                            Double.parseDouble(valorCompraField.getText())
                    );

                    BigDecimal vendaValor = BigDecimal.valueOf(
                            Double.parseDouble(valorVendaField.getText())
                    );

                    Integer categoriaId = categoriasMap.get(
                            categoriasBox.getSelectedItem().toString()
                    );

                    Integer moedaId = moedasMap.get(
                            moedasBox.getSelectedItem().toString()
                    );

                    Categoria categoria = ApplicationContext
                            .getCategoriaService()
                            .findById(categoriaId)
                            .get();

                    Moeda moeda = ApplicationContext
                            .getMoedaService()
                            .findById(moedaId)
                            .get();

                    Produto produto = new Produto(
                            0,
                            codigo,
                            nome,
                            compraValor,
                            vendaValor,
                            categoria,
                            moeda
                    );

                    ProdutoService service = ApplicationContext.getProdutoService();

                    service.save(produto);

                    Messages.success(
                            "Produto '%s' cadastrado com sucesso!"
                                    .formatted(nome)
                    );

                    clearForm();
                } catch (Exception e) {
                    Messages.error(
                            "Erro ao cadastrar produto: %s"
                                    .formatted(e.getMessage())
                    );
                }
            });
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
        });
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildForm())
                .padding(Spacing.MD)
                .build();
    }

    /**
     * Atualiza dados da tela.
     */
    @Override
    public void update() {
        updateCategorias();
        updateMoedas();
    }

    /**
     * Atualiza lista de categorias.
     */
    private void updateCategorias() {
        List<Categoria> categorias
                = ApplicationContext.getCategoriaService().findAll();

        categoriasMap.clear();

        categorias.forEach(categoria -> {
            categoriasMap.put(
                    categoria.getNome(),
                    categoria.getId()
            );
        });

        categoriasBox.setModel(
                new DefaultComboBoxModel<>(
                        categoriasMap.keySet().toArray(new String[0])
                )
        );
    }

    /**
     * Atualiza lista de moedas.
     */
    private void updateMoedas() {
        List<Moeda> moedas
                = ApplicationContext.getMoedaService().findAll();

        moedasMap.clear();

        moedas.forEach(moeda -> {
            moedasMap.put(
                    moeda.getSigla(),
                    moeda.getId()
            );
        });

        moedasBox.setModel(
                new DefaultComboBoxModel<>(
                        moedasMap.keySet().toArray(new String[0])
                )
        );
    }

    /**
     * Constrói formulário principal.
     *
     * @return JPanel - Formulário construído
     */
    private JPanel buildForm() {
        return UI.column()
                .add(buildTitle(), buildSubtitle())
                .glue()
                .add(
                        UI.field(
                                UI.fieldLabel(
                                        "Código [ex: PROD123, FRUT001, ENERG000]"
                                ),
                                codigoField
                        ),
                        UI.field(
                                UI.fieldLabel("Nome"),
                                nomeField
                        ),
                        UI.field(
                                UI.fieldLabel("Valor de Compra"),
                                valorCompraField
                        ),
                        UI.field(
                                UI.fieldLabel("Valor de Venda"),
                                valorVendaField
                        ),
                        UI.field(
                                UI.fieldLabel("Categoria"),
                                categoriasBox
                        ),
                        UI.field(
                                UI.fieldLabel("Moeda"),
                                moedasBox
                        )
                )
                .glue()
                .add(UI.actions(cadastrarButton, limparButton))
                .build();
    }

    /**
     * Constrói título da tela.
     *
     * @return JLabel - Título construído
     */
    private JLabel buildTitle() {
        return UI.label("Cadastro de Produtos", label -> {
            label.setFont(Fonts.TITLE_MEDIUM);
        });
    }

    /**
     * Constrói subtítulo da tela.
     *
     * @return JLabel - Subtítulo construído
     */
    private JLabel buildSubtitle() {
        return UI.subtitle(
                "Gerencia produtos, categorias e valores comerciais do sistema."
        );
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UI.clearFields(
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
