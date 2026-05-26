package org.javapi.sigob.service;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.entity.ProdutosEstoques;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

import java.util.List;
import java.util.Optional;

public class ProdutosEstoquesService {

    /**
     * Cria um novo ProdutosEstoquesService
     *
     * @return ProdutosEstoquesService - O serviço
     */
    public ProdutosEstoquesService() {
    }

    /**
     * Salva um novo ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques a ser salvo
     * @throws IllegalArgumentException Se o ProdutosEstoques for inválido
     * @throws IllegalArgumentException Se o vínculo já existir
     */
    public void save(ProdutosEstoques produtoEstoque) {
        validateProdutoEstoque(produtoEstoque);

        TransactionExecutor.executeVoid(em -> {
            ProdutosEstoquesRepository repository = new ProdutosEstoquesRepository(em);

            int produtoId = produtoEstoque.getProduto().getId();
            int estoqueId = produtoEstoque.getEstoque().getId();

            Optional<ProdutosEstoques> existente = repository.findUnique(produtoId, estoqueId);
            if (existente.isPresent()) {
                throw new IllegalArgumentException("Produto já vinculado a este estoque");
            }

            repository.save(produtoEstoque);
        });
    }

    /**
     * Atualiza um ProdutosEstoques existente
     *
     * @param produtoEstoque O ProdutosEstoques a ser atualizado
     * @throws IllegalArgumentException Se o ProdutosEstoques for inválido
     */
    public void update(ProdutosEstoques produtoEstoque) {
        validateProdutoEstoque(produtoEstoque);

        TransactionExecutor.executeVoid(em -> {
            new ProdutosEstoquesRepository(em).update(produtoEstoque);
        });
    }

    /**
     * Remove um ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques a ser removido
     * @throws IllegalArgumentException Se o ProdutosEstoques for inválido
     */
    public void delete(ProdutosEstoques produtoEstoque) {
        validateProdutoEstoque(produtoEstoque);

        TransactionExecutor.executeVoid(em -> {
            new ProdutosEstoquesRepository(em).deleteById(produtoEstoque.getId());
        });
    }

    /**
     * Verifica se um ProdutosEstoques existe
     *
     * @param produtoEstoque O ProdutosEstoques a ser verificado
     * @return boolean - true se existir, false caso contrário
     * @throws IllegalArgumentException Se o ProdutosEstoques for inválido
     */
    public boolean contains(ProdutosEstoques produtoEstoque) {
        validateProdutoEstoque(produtoEstoque);

        return TransactionExecutor.query(em -> {
            return new ProdutosEstoquesRepository(em).contains(produtoEstoque.getId());
        });
    }

    /**
     * Transfere quantidade de um produto entre estoques.
     *
     * @param origem O vínculo origem (Produto + Estoque)
     * @param destino O estoque destino
     * @param quantidade A quantidade a transferir
     *
     * @throws IllegalArgumentException Se parâmetros forem inválidos
     */
    public void transferir(ProdutosEstoques origem, Estoque destino, int quantidade) {
        Validator.start()
                .expectNotNull(origem, "Origem não pode ser nula!")
                .expectNotNull(destino, "Destino não pode ser nulo!")
                .expect(quantidade, q -> q > 0, "Quantidade deve ser maior que zero!")
                .expect(quantidade, q -> q <= origem.getQuantidade(), "Quantidade maior que disponível!")
                .validate();

        TransactionExecutor.executeVoid(em -> {
            ProdutosEstoquesRepository repo = new ProdutosEstoquesRepository(em);
            int produtoId = origem.getProduto().getId();
            int destinoId = destino.getId();

            // Verifica se já existe vínculo no destino
            Optional<ProdutosEstoques> destinoExistente = repo.findUnique(produtoId, destinoId);
            int novaQtdOrigem = origem.getQuantidade() - quantidade;

            // Atualiza origem
            if (novaQtdOrigem == 0) {
                repo.deleteById(origem.getId());
            } else {
                origem.setQuantidade(novaQtdOrigem);
                repo.update(origem);
            }

            // Atualiza ou cria destino
            if (destinoExistente.isPresent()) {
                ProdutosEstoques peDestino = destinoExistente.get();
                peDestino.setQuantidade(
                        peDestino.getQuantidade() + quantidade
                );

                repo.update(peDestino);
            } else {
                repo.save(new ProdutosEstoques(
                        0,
                        quantidade,
                        origem.getProduto(),
                        destino
                ));
            }
        });
    }

    /**
     * Retorna todos os ProdutosEstoques
     *
     * @return List<ProdutosEstoques> - Lista de ProdutosEstoques
     */
    public List<ProdutosEstoques> findAll() {
        return TransactionExecutor.query(em -> {
            return new ProdutosEstoquesRepository(em).findAll();
        });
    }

    /**
     * Busca um ProdutosEstoques pelo ID
     *
     * @param id O ID do ProdutosEstoques
     * @return Optional<ProdutosEstoques> - O resultado da busca
     * @throws IllegalArgumentException Se o ID for inválido
     */
    public Optional<ProdutosEstoques> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new ProdutosEstoquesRepository(em).findById(id);
        });
    }

    /**
     * Busca ProdutosEstoques pelo ID do Produto
     *
     * @param produtoId O ID do Produto
     * @return List<ProdutosEstoques> - Lista de ProdutosEstoques
     * @throws IllegalArgumentException Se o ID for inválido
     */
    public List<ProdutosEstoques> findByProduto(int produtoId) {
        return TransactionExecutor.query(em -> {
            return new ProdutosEstoquesRepository(em).findByProduto(produtoId);
        });
    }

    /**
     * Busca ProdutosEstoques pelo ID do Estoque
     *
     * @param estoqueId O ID do Estoque
     * @return List<ProdutosEstoques> - Lista de ProdutosEstoques
     * @throws IllegalArgumentException Se o ID for inválido
     */
    public List<ProdutosEstoques> findByEstoque(int estoqueId) {
        return TransactionExecutor.query(em -> {
            return new ProdutosEstoquesRepository(em).findByEstoque(estoqueId);
        });
    }

    /**
     * Busca o vínculo único entre Produto e Estoque
     *
     * @param produtoId O ID do Produto
     * @param estoqueId O ID do Estoque
     * @return Optional<ProdutosEstoques> - O vínculo encontrado
     * @throws IllegalArgumentException Se algum ID for inválido
     */
    public Optional<ProdutosEstoques> findUnique(int produtoId, int estoqueId) {
        return TransactionExecutor.query(em -> {
            return new ProdutosEstoquesRepository(em).findUnique(produtoId, estoqueId);
        });
    }

    /**
     * Valida um ProdutosEstoques por completo
     *
     * @param produtoEstoque O ProdutosEstoques a ser validado
     * @throws IllegalArgumentException Se for inválido
     */
    private void validateProdutoEstoque(ProdutosEstoques produtoEstoque) {
        Validator.start()
                .expectNotNull(produtoEstoque, "ProdutosEstoques não pode ser nulo!")
                .validate();

        validateProduto(produtoEstoque);
        validateEstoque(produtoEstoque);
    }

    /**
     * Valida o Produto dentro de ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     * @throws IllegalArgumentException Se inválido
     */
    private void validateProduto(ProdutosEstoques produtoEstoque) {
        Validator.start()
                .expectNotNull(produtoEstoque.getProduto(), "Produto não pode ser nulo!")
                .validate();
    }

    /**
     * Valida o Estoque dentro de ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques
     * @throws IllegalArgumentException Se inválido
     */
    private void validateEstoque(ProdutosEstoques produtoEstoque) {
        Validator.start()
                .expectNotNull(produtoEstoque.getEstoque(), "Estoque não pode ser nulo!")
                .validate();
    }
}
