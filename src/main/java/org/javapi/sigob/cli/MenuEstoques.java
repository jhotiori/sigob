package org.javapi.sigob.cli;

import java.util.List;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.exception.ProdutosEstoquesException;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

public class MenuEstoques extends Menu {

    /**
     * Inicializa o menu de estoques e registra as entradas disponíveis.
     */
    public MenuEstoques() {
        super("Estoque");
        adicionarEntrada("Listar (ESTOQUE)", this::listarPorEstoque);
        adicionarEntrada("Cadastrar", this::adicionarItem);
        adicionarEntrada("Transferir", this::transferir);
    }

    private final ProdutosEstoquesService service = new ProdutosEstoquesService();
    private final ProdutoService produtoService = new ProdutoService();
    private final EstoqueService estoqueService = new EstoqueService();

    /**
     * Lista itens de um estoque específico.
     */
    private void listarPorEstoque() {
        try {
            List<Estoque> estoques = estoqueService.findAll();

            if (estoques.isEmpty()) {
                Logger.warn("Nenhum estoque cadastrado!");
                return;
            }

            for (Estoque e : estoques) {
                System.out.println("[" + e.getIdEstoque() + "] " + e.getNmEstoque());
            }

            int idEstoque = Inputter.lerInt("Insira o ID do Estoque: ");

            List<ProdutosEstoques> itens = service.findAll();

            boolean encontrou = false;
            for (ProdutosEstoques pe : itens) {
                if (pe.getEstoque().getIdEstoque() == idEstoque) {
                    System.out.println(pe);
                    encontrou = true;
                }
            }

            if (!encontrou) {
                Logger.warn("Nenhum item encontrado neste estoque!");
            }

        } catch (Exception e) {
            Logger.error("Erro ao listar itens do estoque: " + e.getMessage());
        }
    }

    /**
     * Adiciona um item a um estoque.
     */
    private void adicionarItem() {
        try {
            List<Produto> produtos = produtoService.findAll();

            if (produtos.isEmpty()) {
                Logger.warn("Nenhum produto cadastrado!");
                return;
            }

            for (Produto p : produtos) {
                System.out.println("[" + p.getIdProduto() + "] " + p.getNmProduto());
            }

            int idProduto = Inputter.lerInt("Insira o ID do Produto: ");

            List<Estoque> estoques = estoqueService.findAll();

            if (estoques.isEmpty()) {
                Logger.warn("Nenhum estoque cadastrado!");
                return;
            }

            for (Estoque e : estoques) {
                System.out.println("[" + e.getIdEstoque() + "] " + e.getNmEstoque());
            }

            int idEstoque = Inputter.lerInt("Insira o ID do Estoque: ");
            int quantidade = Inputter.lerInt("Quantidade: ");
            String observacao = Inputter.lerString("Observação: ");

            Produto produto = produtoService.findById(idProduto);
            if (produto == null) {
                Logger.warn("Produto não encontrado!");
                return;
            }

            Estoque estoque = estoqueService.findById(idEstoque);
            if (estoque == null) {
                Logger.warn("Estoque não encontrado!");
                return;
            }

            try {
                service.save(new ProdutosEstoques(0, quantidade, observacao, produto, estoque));
                Logger.success("Item adicionado ao estoque com sucesso!");
            } catch (ProdutosEstoquesException e) {
                Logger.error("Erro ao adicionar item: " + e.getMessage());
            }

        } catch (Exception e) {
            Logger.error("Erro ao cadastrar item no estoque: " + e.getMessage());
        }
    }

    /**
     * Transfere itens entre estoques.
     */
    private void transferir() {
        try {
            List<ProdutosEstoques> itens = service.findAll();

            if (itens.isEmpty()) {
                Logger.warn("Nenhum item em estoque!");
                return;
            }

            for (ProdutosEstoques pe : itens) {
                System.out.println(
                        "[" + pe.getIdProdutosEstoque() + "] " +
                                pe.getProduto().getNmProduto() +
                                " | Qtde: " + pe.getNrQuantidade() +
                                " | Estoque: " + pe.getEstoque().getNmEstoque());
            }

            int idItem = Inputter.lerInt("Insira o ID do Item: ");
            ProdutosEstoques origem = service.findById(idItem);

            if (origem == null) {
                Logger.warn("Item não encontrado!");
                return;
            }

            List<Estoque> estoques = estoqueService.findAll();

            for (Estoque e : estoques) {
                if (e.getIdEstoque() != origem.getEstoque().getIdEstoque()) {
                    System.out.println("[" + e.getIdEstoque() + "] " + e.getNmEstoque());
                }
            }

            int idDestino = Inputter.lerInt("Insira o ID do Estoque destino: ");
            int quantidade = Inputter.lerInt("Quantidade a transferir: ");

            if (quantidade <= 0 || quantidade > origem.getNrQuantidade()) {
                Logger.warn("Quantidade inválida! Disponível: " + origem.getNrQuantidade());
                return;
            }

            Estoque destino = estoqueService.findById(idDestino);
            if (destino == null) {
                Logger.warn("Estoque destino não encontrado!");
                return;
            }

            origem.setNrQuantidade(origem.getNrQuantidade() - quantidade);

            if (origem.getNrQuantidade() == 0) {
                service.delete(origem);
            } else {
                try {
                    service.update(origem);
                } catch (ProdutosEstoquesException e) {
                    Logger.error("Erro ao atualizar origem: " + e.getMessage());
                    return;
                }
            }

            try {
                service.save(new ProdutosEstoques(
                        0,
                        quantidade,
                        "Transferido de: " + origem.getEstoque().getNmEstoque(),
                        origem.getProduto(),
                        destino));
            } catch (ProdutosEstoquesException e) {
                Logger.error("Erro ao criar destino: " + e.getMessage());
                return;
            }

            Logger.success("Transferência realizada com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro ao transferir item: " + e.getMessage());
        }
    }
}
