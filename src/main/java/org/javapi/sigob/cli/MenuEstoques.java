package org.javapi.sigob.cli;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.entity.Produto;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.service.EstoqueService;
import org.javapi.sigob.service.ProdutoService;
import org.javapi.sigob.service.ProdutosEstoquesService;
import org.javapi.sigob.util.Inputter;
import org.javapi.sigob.util.Logger;

/**
 * Menu responsável pela gestão de itens em estoque (ProdutosEstoques).
 */
public class MenuEstoques extends Menu {

    private final ProdutosEstoquesService service;
    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;

    /**
     * Inicializa o menu de movimentação de estoque.
     *
     * @param service Serviço de ProdutosEstoques
     * @param produtoService Serviço de Produtos
     * @param estoqueService Serviço de Estoques
     */
    public MenuEstoques(ProdutosEstoquesService service, ProdutoService produtoService, EstoqueService estoqueService) {
        super("Movimentação de Estoque");
        add("Listar por Estoque", this::listarPorEstoque);
        add("Adicionar Item", this::adicionarItem);
        add("Transferir", this::transferir);

        this.service = service;
        this.produtoService = produtoService;
        this.estoqueService = estoqueService;
    }

    /**
     * Lista todos os itens vinculados a um estoque específico.
     */
    private void listarPorEstoque() {
        List<Estoque> estoques = estoqueService.findAll();

        if (estoques.isEmpty()) {
            Logger.warn("Nenhum estoque cadastrado!");
            return;
        }

        estoques.forEach(e -> System.out.printf("[%d] %s%n", e.getId(), e.getNome()));

        int id = Inputter.readInt("ID do Estoque: ");
        Optional<Estoque> estoque = estoqueService.findById(id);

        if (estoque.isEmpty()) {
            Logger.warn("Estoque não encontrado!");
            return;
        }

        List<ProdutosEstoques> itens = service.findByEstoque(id);

        if (itens.isEmpty()) {
            Logger.warn("Nenhum item neste estoque!");
            return;
        }

        itens.forEach(System.out::println);
    }

    /**
     * Adiciona (ou incrementa) um produto em um estoque.
     */
    private void adicionarItem() {
        List<Produto> produtos = produtoService.findAll();

        if (produtos.isEmpty()) {
            Logger.warn("Nenhum produto cadastrado!");
            return;
        }

        produtos.forEach(p
                -> System.out.printf("[%d] %s%n", p.getId(), p.getNome())
        );

        int produtoId = Inputter.readInt("ID do Produto: ");
        Optional<Produto> produto = produtoService.findById(produtoId);

        if (produto.isEmpty()) {
            Logger.warn("Produto não encontrado!");
            return;
        }

        List<Estoque> estoques = estoqueService.findAll();

        if (estoques.isEmpty()) {
            Logger.warn("Nenhum estoque cadastrado!");
            return;
        }

        estoques.forEach(e
                -> System.out.printf("[%d] %s%n", e.getId(), e.getNome())
        );

        int estoqueId = Inputter.readInt("ID do Estoque: ");
        Optional<Estoque> estoque = estoqueService.findById(estoqueId);

        if (estoque.isEmpty()) {
            Logger.warn("Estoque não encontrado!");
            return;
        }

        int quantidade = Inputter.readInt("Quantidade: ");

        if (quantidade <= 0) {
            Logger.warn("Quantidade deve ser maior que zero!");
            return;
        }

        try {
            Optional<ProdutosEstoques> existente = service.findUnique(produtoId, estoqueId);

            if (existente.isPresent()) {
                ProdutosEstoques pe = existente.get();
                pe.setQuantidade(pe.getQuantidade() + quantidade);
                
                service.update(pe);
                Logger.success("Quantidade atualizada com sucesso!");
            } else {
                service.save(new ProdutosEstoques(
                        0,
                        quantidade,
                        produto.get(),
                        estoque.get()
                ));

                Logger.success("Item adicionado ao estoque!");
            }

        } catch (Exception e) {
            Logger.error("Erro ao adicionar item: " + e.getMessage());
        }
    }

    /**
     * Transfere quantidade de um produto entre estoques.
     */
    private void transferir() {
        List<ProdutosEstoques> itens = service.findAll();

        if (itens.isEmpty()) {
            Logger.warn("Nenhum item em estoque!");
            return;
        }

        itens.forEach(pe
                -> System.out.printf(
                        "[%d] %s | Qtde: %d | Estoque: %s%n",
                        pe.getId(),
                        pe.getProduto().getNome(),
                        pe.getQuantidade(),
                        pe.getEstoque().getNome()
                )
        );

        int id = Inputter.readInt("ID do Item: ");
        Optional<ProdutosEstoques> origemOpt = service.findById(id);

        if (origemOpt.isEmpty()) {
            Logger.warn("Item não encontrado!");
            return;
        }

        ProdutosEstoques origem = origemOpt.get();

        List<Estoque> estoques = estoqueService.findAll();

        estoques.stream()
                .filter(e -> e.getId() != origem.getEstoque().getId())
                .forEach(e
                        -> System.out.printf("[%d] %s%n", e.getId(), e.getNome())
                );

        int destinoId = Inputter.readInt("ID do Estoque destino: ");
        Optional<Estoque> destinoOpt = estoqueService.findById(destinoId);

        if (destinoOpt.isEmpty()) {
            Logger.warn("Estoque destino não encontrado!");
            return;
        }

        int quantidade = Inputter.readInt("Quantidade: ");

        if (quantidade <= 0 || quantidade > origem.getQuantidade()) {
            Logger.warn("Quantidade inválida!");
            return;
        }

        try {
            service.transferir(origem, destinoOpt.get(), quantidade);
            Logger.success("Transferência realizada com sucesso!");
        } catch (Exception e) {
            Logger.error("Erro na transferência: " + e.getMessage());
        }
    }
}
