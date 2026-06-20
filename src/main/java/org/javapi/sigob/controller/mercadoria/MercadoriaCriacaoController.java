package org.javapi.sigob.controller.mercadoria;

import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.model.service.ProdutosEstoquesService;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.mercadoria.MercadoriaCriacaoScreen;

/**
 * Controller de criação de mercadorias.
 */
public final class MercadoriaCriacaoController {

    /**
     * Tela de criação de mercadorias.
     */
    private final MercadoriaCriacaoScreen SCREEN;

    /**
     * Serviço de produtos e estoques.
     */
    private final ProdutosEstoquesService service;

    public MercadoriaCriacaoController(
            MercadoriaCriacaoScreen screen
        ) {

        this.SCREEN = screen;
        this.service = ServiceFactory.produtosEstoques();

        setup();
        refresh();
    }

    private void setup() {
        SCREEN.onUpdate(this::refresh);
        SCREEN.onCriar(
                this::criar);
    }

    private void refresh() {
        SCREEN.setProdutos(
                ServiceFactory.produtos()
                        .findAll());

        SCREEN.setEstoques(
                ServiceFactory.estoques()
                        .findAll());
    }

    private void criar() {

        try {

            if (SCREEN.produto() == null) {
                UIDialogs.warn(
                        "Selecione um produto!");
                        return;
            }

            if (SCREEN.estoque() == null) {
                UIDialogs.warn(
                        "Selecione um estoque!");
                        return;
            }

            int quantidade = parseQuantidade();

            ProdutosEstoques produtoEstoque = new ProdutosEstoques(
                    0,
                    quantidade,
                    SCREEN.produto(),
                    SCREEN.estoque()
                );

            service.adicionarEstoque(
                    produtoEstoque);

            SCREEN.clearQuantidade();

            UIDialogs.info(
                    "Mercadoria criada!");

        } catch (Exception e) {

            UIDialogs.error(
                    e.getMessage());
        }
    }

    private int parseQuantidade() {

        String valor = SCREEN.quantidade();

        if (!UIValidation.notBlank(
                valor,
                "Informe a quantidade!")) {

            throw new IllegalArgumentException(
                            "Quantidade inválida!");
        }

        int quantidade = Integer.parseInt(valor);

        if (quantidade <= 0) {
                UIDialogs.warn(
                        "Quantidade deve ser maior que zero!");
        }

        return quantidade;
    }
}
