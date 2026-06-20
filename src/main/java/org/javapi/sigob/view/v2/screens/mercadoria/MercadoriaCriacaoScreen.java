package org.javapi.sigob.view.v2.screens.mercadoria;

import javax.swing.JPanel;

import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.entity.Produto;
import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.components.ButtonComponent;
import org.javapi.sigob.view.v2.framework.components.TextFieldComponent;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIText;
import org.javapi.sigob.view.v2.framework.styles.Spacing;

/**
 * Tela de criação de mercadoria.
 */
public final class MercadoriaCriacaoScreen extends BaseScreen {

        /**
         * Caixa de produtos.
         */
        private final EntityComboBox<Produto> PRODUTO_BOX = UI.entityComboBox(produto -> {

                return "%s (%s)"
                                .formatted(
                                                produto.getNome(),
                                                produto.getCodigo());
        });

        /**
         * Caixa de estoques.
         */
        private final EntityComboBox<Estoque> ESTOQUE_BOX = UI.entityComboBox(estoque -> {

                return "%s (%s)"
                                .formatted(
                                                estoque.getNome(),
                                                estoque.getCodigo());
        });

        /**
         * Quantidade.
         */
        private final TextFieldComponent QUANTIDADE_FIELD = UI.textField();

        /**
         * Botão criar.
         */
        private final ButtonComponent CRIAR_BUTTON = UI.button("Criar");

        /**
         * Construtor.
         */
        public MercadoriaCriacaoScreen() {

                super("mercadoria-criacao");
        }

        /**
         * Evento criar.
         *
         * @param action - ação
         */
        public void onCriar(
                        Runnable action) {

                UIEvents.bind(
                                CRIAR_BUTTON).onClick(action);
        }

        /**
         * Produto selecionado.
         *
         * @return Produto
         */
        public Produto produto() {

                return PRODUTO_BOX.getSelectedEntity();
        }

        /**
         * Estoque selecionado.
         *
         * @return Estoque
         */
        public Estoque estoque() {

                return ESTOQUE_BOX.getSelectedEntity();
        }

        /**
         * Quantidade.
         *
         * @return String
         */
        public String quantidade() {

                return QUANTIDADE_FIELD.getText();
        }

        /**
         * Define produtos.
         */
        public void setProdutos(
                        Iterable<Produto> produtos) {

                PRODUTO_BOX.setEntities(produtos);
        }

        /**
         * Define estoques.
         */
        public void setEstoques(
                        Iterable<Estoque> estoques) {

                ESTOQUE_BOX.setEntities(estoques);
        }

        /**
         * Limpa quantidade.
         */
        public void clearQuantidade() {

                QUANTIDADE_FIELD.setText("");
        }

        /**
         * Constrói tela.
         *
         * @return JPanel
         */
        @Override
        protected JPanel build() {

                return UILayouts.border()

                                .add(
                                                UILayouts.column()

                                                                .add(
                                                                                UIText.header(
                                                                                                "Criar Mercadoria"))

                                                                .add(
                                                                                UIText.subtitle(
                                                                                                """
                                                                                                                Registre produtos em estoques
                                                                                                                para disponibilizar mercadorias.
                                                                                                                """))

                                                                .glue()

                                                                .gap(Spacing.MD)

                                                                .add(
                                                                                buildFormulario())

                                                                .glue()

                                                                .gap(Spacing.MD)

                                                                .add(
                                                                                CRIAR_BUTTON)

                                                                .build())

                                .padding(
                                                Spacing.LG)

                                .build();
        }

        /**
         * Constrói formulário.
         *
         * @return JPanel
         */
        private JPanel buildFormulario() {

                return UILayouts.column()
                                                .add(
                                                                UILayouts.column()
                                                                        .add(UIText.subtitle("Produto"))
                                                                        .add(PRODUTO_BOX)
                                                        ).gap(Spacing.SM)


                                                .add(
                                                                UILayouts.column()
                                                                        .add(UIText.subtitle("Estoque"))
                                                                        .add(ESTOQUE_BOX)
                                                        ).gap(Spacing.SM)

                                                .add(
                                                                UILayouts.column()
                                                                        .add(UIText.subtitle("Quantidade"))
                                                                        .add(QUANTIDADE_FIELD)
                                                ).gap(Spacing.SM)

                                                .build();
        }
}
