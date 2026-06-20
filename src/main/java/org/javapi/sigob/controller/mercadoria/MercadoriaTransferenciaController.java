package org.javapi.sigob.controller.mercadoria;

import java.util.List;

import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.model.service.EstoqueService;
import org.javapi.sigob.model.service.ProdutosEstoquesService;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.mercadoria.MercadoriaTransferenciaScreen;

/**
 * Controller de transferência de mercadorias.
 */
public final class MercadoriaTransferenciaController {

    /**
     * Tela controlada.
     *
     * @see MercadoriaTransferenciaScreen
     */
    private final MercadoriaTransferenciaScreen SCREEN;

    /**
     * Serviço de produtos em estoque.
     *
     * @see ProdutosEstoquesService
     */
    private final ProdutosEstoquesService produtosEstoquesService;

    /**
     * Serviço de estoques.
     *
     * @see EstoqueService
     */
    private final EstoqueService estoqueService;

    /**
     * Construtor.
     *
     * @param screen - Tela
     */
    public MercadoriaTransferenciaController(
            MercadoriaTransferenciaScreen screen) {

        this.SCREEN = screen;

        this.produtosEstoquesService = ServiceFactory.produtosEstoques();

        this.estoqueService = ServiceFactory.estoques();

        setup();

        refresh();
    }

    /**
     * Configura eventos.
     */
    private void setup() {
        SCREEN.onUpdate(this::refresh);
        SCREEN.onTransferir(
                this::transferir);
    }

    /**
     * Atualiza dados da tela.
     */
    private void refresh() {
        loadProdutos();
        loadDestinos();
    }

    /**
     * Carrega produtos disponíveis.
     */
    private void loadProdutos() {

        List<ProdutosEstoques> produtos = produtosEstoquesService.findAll()
                .stream()
                .filter(
                        produto -> produto.getQuantidade() > 0)
                .toList();

        SCREEN.setProdutos(
                produtos);
    }

    /**
     * Carrega estoques destino.
     */
    private void loadDestinos() {

        SCREEN.setDestinos(
                estoqueService.findAll());
    }

    /**
     * Realiza transferência.
     */
    private void transferir() {

        try {

            ProdutosEstoques origem = SCREEN.origem();

            if (origem == null) {

                UIDialogs.error(
                        "Selecione uma origem!");

                return;
            }

            Estoque destino = SCREEN.destino();

            if (destino == null) {

                UIDialogs.error(
                        "Selecione um destino!");

                return;
            }

            if (origem.getEstoque()
                    .getId() == destino.getId()) {

                UIDialogs.error(
                        "Origem e destino não podem ser iguais!");

                return;
            }

            int quantidade = parseQuantidade();

            validarQuantidade(
                    origem,
                    quantidade);

            boolean confirmar = UIDialogs.confirm(
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
                                    destino.getNome(),
                                    quantidade));

            if (!confirmar) {

                return;
            }

            produtosEstoquesService.transferir(
                    origem,
                    destino,
                    quantidade);

            SCREEN.clearQuantidade();

            refresh();

            UIDialogs.info(
                    "Transferência realizada!");

        } catch (Exception e) {

            UIDialogs.error(
                    "Erro ao transferir mercadoria: "
                            + e.getMessage());
        }
    }

    /**
     * Obtém quantidade informada.
     *
     * @return Quantidade
     */
    private int parseQuantidade() {

        String quantidade = SCREEN.quantidade();

        boolean valido = UIValidation.notBlank(
                quantidade,
                "Informe a quantidade!");

        if (!valido) {

            throw new IllegalArgumentException(
                    "Quantidade inválida!");
        }

        try {

            return Integer.parseInt(
                    quantidade);

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Quantidade deve ser numérica!");
        }
    }

    /**
     * Valida quantidade disponível.
     *
     * @param produto    - Produto estoque
     * @param quantidade - Quantidade
     */
    private void validarQuantidade(
            ProdutosEstoques produto,
            int quantidade) {

        if (quantidade <= 0) {

            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero!");
        }

        if (quantidade > produto.getQuantidade()) {

            throw new IllegalArgumentException(
                    """
                            Quantidade indisponível!

                            Máximo disponível: %d
                            """
                            .formatted(
                                    produto.getQuantidade()));
        }
    }
}
