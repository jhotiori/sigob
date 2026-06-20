package org.javapi.sigob.view.v2.screens.cadastro;

import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

/**
 * Tela de cadastro de produtos.
 */
public final class CadastroProdutoScreen extends BaseCadastroScreen {

    /**
     * Caixa de categorias.
     *
     * @see EntityComboBox
     */
    private final EntityComboBox<Categoria> CATEGORIA_BOX = UI.entityComboBox(Categoria::getNome);

    /**
     * Caixa de moedas.
     *
     * @see EntityComboBox
     */
    private final EntityComboBox<Moeda> MOEDA_BOX = UI.entityComboBox(moeda -> {
        return "%s (%s)".formatted(
                moeda.getCifrao(),
                moeda.getSigla());
    });

    /**
     * Construtor.
     */
    public CadastroProdutoScreen() {
        super(
                "cadastro-produto",
                "Cadastro de Produtos");

        form().field(
                "Código",
                "codigo",
                UI.textField());

        form().field(
                "Nome",
                "nome",
                UI.textField());

        form().field(
                "Valor de Compra",
                "valorCompra",
                UI.textField());

        form().field(
                "Valor de Venda",
                "valorVenda",
                UI.textField());

        form().field(
                "Categoria",
                "categoria",
                CATEGORIA_BOX);

        form().field(
                "Moeda",
                "moeda",
                MOEDA_BOX);
    }

    /**
     * Descrição da tela.
     *
     * @return String - Descrição
     */
    @Override
    protected String description() {
        return """
                Crie um novo Produto para ser Vendido.
                Ao criar, forneça tambem o estoque aonde ele se encontra.
                """;
    }

    /**
     * Retorna caixa de categorias.
     *
     * @return EntityComboBox<Categoria> - Caixa de categorias
     */
    public EntityComboBox<Categoria> categoriaBox() {
        return CATEGORIA_BOX;
    }

    /**
     * Retorna caixa de moedas.
     *
     * @return EntityComboBox<Moeda> - Caixa de moedas
     */
    public EntityComboBox<Moeda> moedaBox() {
        return MOEDA_BOX;
    }
}
