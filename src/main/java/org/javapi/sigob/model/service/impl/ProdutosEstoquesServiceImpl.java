package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.ProdutosEstoquesRepositoryImpl;
import org.javapi.sigob.model.service.ProdutosEstoquesService;
import org.javapi.sigob.model.validation.Validators;
import org.javapi.sigob.model.validation.entity.ProdutosEstoquesValidator;

public class ProdutosEstoquesServiceImpl implements ProdutosEstoquesService {

    /**
     * Cria um novo ProdutosEstoquesService
     */
    public ProdutosEstoquesServiceImpl() {
    }

    /**
     * Salva um novo ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques a ser salvo
     */
    @Override
    public void save(ProdutosEstoques produtoEstoque) {
        ProdutosEstoquesValidator.validate(produtoEstoque);
        Database.write(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> {
                    int produtoId = produtoEstoque.getProduto().getId();
                    int estoqueId = produtoEstoque.getEstoque().getId();
                    Optional<ProdutosEstoques> existente = repo.findUnique(produtoId, estoqueId);

                    if (existente.isPresent()) {
                        throw new SigobException("Produto já está vinculado a este Estoque!");
                    }

                    repo.save(produtoEstoque);
                }
        );
    }

    /**
     * Atualiza um ProdutosEstoques existente
     *
     * @param produtoEstoque O ProdutosEstoques a ser atualizado
     */
    @Override
    public void update(ProdutosEstoques produtoEstoque) {
        ProdutosEstoquesValidator.validate(produtoEstoque);
        Database.write(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.update(produtoEstoque)
        );
    }

    /**
     * Remove um ProdutosEstoques
     *
     * @param produtoEstoque O ProdutosEstoques a ser removido
     */
    @Override
    public void delete(ProdutosEstoques produtoEstoque) {
        Database.write(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.deleteById(produtoEstoque.getId())
        );
    }

    /**
     * Verifica se um ProdutosEstoques existe
     *
     * @param id O ID do ProdutosEstoques
     * @return boolean - true se existir, false caso contrário
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Transfere uma quantidade de produtos para outro estoque.
     *
     * @param origem - Registro de origem da transferência.
     * @param destino - Estoque de destino.
     * @param quantidade - Quantidade a ser transferida.
     */
    @Override
    public void transferir(
            ProdutosEstoques origem,
            Estoque destino,
            int quantidade
    ) {
        Validators.notNull(origem, "Origem não pode ser nulo!");
        Validators.notNull(destino, "Destino não pode ser nulo!");
        Validators.nonZero(quantidade, "Quantidade não pode ser negativo ou zero!");
        Validators.expect(quantidade <= origem.getQuantidade(), "Quantidade maior que disponível!");

        Database.write(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> {
                    int produtoId = origem.getProduto().getId();
                    int destinoId = destino.getId();
                    Optional<ProdutosEstoques> destinoExistente = repo.findUnique(produtoId, destinoId);

                    int novaQtdOrigem = origem.getQuantidade() - quantidade;

                    if (novaQtdOrigem == 0) {
                        repo.deleteById(origem.getId());
                    } else {
                        origem.setQuantidade(novaQtdOrigem);
                        repo.update(origem);
                    }

                    if (destinoExistente.isPresent()) {
                        ProdutosEstoques peDestino = destinoExistente.get();

                        peDestino.setQuantidade(
                                peDestino.getQuantidade() + quantidade
                        );

                        repo.update(peDestino);
                    } else {
                        repo.save(
                                new ProdutosEstoques(
                                        0,
                                        quantidade,
                                        origem.getProduto(),
                                        destino
                                )
                        );
                    }
                }
        );
    }

    /**
     * Adiciona quantidade a um produto existente no estoque.
     *
     * @param produtoEstoque - Produto estoque
     */
    @Override
    public void adicionarEstoque(
            ProdutosEstoques produtoEstoque) {

        ProdutosEstoquesValidator.validate(produtoEstoque);

        Database.write(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> {

                    Optional<ProdutosEstoques> existente = repo.findUnique(
                            produtoEstoque.getProduto().getId(),
                            produtoEstoque.getEstoque().getId());

                    if (existente.isPresent()) {

                        ProdutosEstoques atual = existente.get();

                        atual.setQuantidade(
                                atual.getQuantidade()
                                        +
                                        produtoEstoque.getQuantidade());

                        repo.update(
                                atual);

                        return;
                    } else {
                        repo.save(
                            produtoEstoque);
                        }

                });
    }

    /**
     * Retorna todos os ProdutosEstoques
     *
     * @return List<ProdutosEstoques> - Lista de ProdutosEstoques
     */
    @Override
    public List<ProdutosEstoques> findAll() {
        return Database.read(
                ProdutosEstoquesRepositoryImpl::new,
                ProdutosEstoquesRepositoryImpl::findAll
        );
    }

    /**
     * Busca um ProdutosEstoques pelo ID
     *
     * @param id O ID do ProdutosEstoques
     * @return Optional<ProdutosEstoques> - O resultado da busca
     */
    @Override
    public Optional<ProdutosEstoques> findById(Integer id) {
        return Database.read(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca registros pelo produto.
     *
     * @param produtoId - Identificador do produto.
     * @return List<ProdutosEstoques> - Lista de registros encontrados.
     */
    @Override
    public List<ProdutosEstoques> findByProduto(int produtoId) {
        return Database.read(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.findByProduto(produtoId)
        );
    }

    /**
     * Busca registros pelo estoque.
     *
     * @param estoqueId - Identificador do estoque.
     * @return List<ProdutosEstoques> - Lista de registros encontrados.
     */
    @Override
    public List<ProdutosEstoques> findByEstoque(int estoqueId) {
        return Database.read(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.findByEstoque(estoqueId)
        );
    }

    /**
     * Busca um registro único pela combinação de produto e estoque.
     *
     * @param produtoId - Identificador do produto.
     * @param estoqueId - Identificador do estoque.
     * @return Optional<ProdutosEstoques> - Registro encontrado, se existir.
     */
    @Override
    public Optional<ProdutosEstoques> findUnique(
            int produtoId,
            int estoqueId
    ) {
        return Database.read(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.findUnique(produtoId, estoqueId)
        );
    }
}
