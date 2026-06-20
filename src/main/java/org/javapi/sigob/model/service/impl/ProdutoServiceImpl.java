package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.model.entity.Produto;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.ProdutoRepositoryImpl;
import org.javapi.sigob.model.repository.impl.ProdutosEstoquesRepositoryImpl;
import org.javapi.sigob.model.service.ProdutoService;
import org.javapi.sigob.model.validation.entity.ProdutoValidator;

public class ProdutoServiceImpl implements ProdutoService {

    /**
     * Cria um novo ProdutoService
     */
    public ProdutoServiceImpl() {
    }

    /**
     * Salva um Produto.
     *
     * @param produto - Produto a ser salvo.
     */
    @Override
    public void save(Produto produto) {
        ProdutoValidator.validate(produto);
        Database.write(
                ProdutoRepositoryImpl::new,
                repo -> repo.save(produto)
        );
    }

    /**
     * Atualiza um Produto.
     *
     * @param produto - Produto a ser atualizado.
     */
    @Override
    public void update(Produto produto) {
        ProdutoValidator.validate(produto);
        Database.write(
                ProdutoRepositoryImpl::new,
                repo -> repo.update(produto)
        );
    }

    /**
     * Remove um Produto.
     *
     * @param produto - Produto a ser removido.
     */
    @Override
    public void delete(Produto produto) {
        if (validateDeleteProduto(produto)) {
            Database.write(
                    ProdutoRepositoryImpl::new,
                    repo -> repo.deleteById(produto.getId())
            );
        } else {
            throw new SigobException(
                    "O Produto possuí vínculo com ProdutosEstoques, não podendo ser removido!"
            );
        }
    }

    /**
     * Verifica se um produto existe.
     *
     * @param id - ID do produto.
     * @return boolean - True se existir, false caso contrário.
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                ProdutoRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Retorna todos os produtos.
     *
     * @return List<Produto> - Lista de produtos.
     */
    @Override
    public List<Produto> findAll() {
        return Database.read(
                ProdutoRepositoryImpl::new,
                ProdutoRepositoryImpl::findAll
        );
    }

    /**
     * Busca um produto pelo ID.
     *
     * @param id - ID utilizado na busca.
     * @return Optional<Produto> - Produto encontrado, se existir.
     */
    @Override
    public Optional<Produto> findById(Integer id) {
        return Database.read(
                ProdutoRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca um produto pelo código.
     *
     * @param codigo - Código utilizado na busca.
     * @return Optional<Produto> - Produto encontrado, se existir.
     */
    @Override
    public Optional<Produto> findByCodigo(String codigo) {
        ProdutoValidator.validateCodigo(codigo);
        return Database.read(
                ProdutoRepositoryImpl::new,
                repo -> repo.findByCodigo(codigo)
        );
    }

    /**
     * Busca produtos pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Produto> - Lista de produtos encontrados.
     */
    @Override
    public List<Produto> findByNome(String nome) {
        ProdutoValidator.validateNome(nome);
        return Database.read(
                ProdutoRepositoryImpl::new,
                repo -> repo.findByNome(nome)
        );
    }

    /**
     * Busca produtos pelo nome da categoria.
     *
     * @param nomeCategoria - Nome da categoria utilizada na busca.
     * @return List<Produto> - Lista de produtos encontrados.
     */
    @Override
    public List<Produto> findByCategoria(String nomeCategoria) {
        ProdutoValidator.validateNome(nomeCategoria);
        return Database.read(
                ProdutoRepositoryImpl::new,
                repo -> repo.findByCategoriaNome(nomeCategoria)
        );
    }

    /**
     * Busca produtos pelo nome da moeda.
     *
     * @param moeda - Nome da moeda utilizada na busca.
     * @return List<Produto> - Lista de produtos encontrados.
     */
    @Override
    public List<Produto> findByMoeda(String moeda) {
        ProdutoValidator.validateMoeda(moeda);
        return Database.read(
                ProdutoRepositoryImpl::new,
                repo -> repo.findByMoedaNome(moeda)
        );
    }

    /**
     * Valida se um Produto está vinculado a uma ProdutosEstoques antes de
     * deletar.
     *
     * @param produto - Produto a ser validado.
     * @return boolean - True se é possível deletar o registro de forma segura.
     */
    private boolean validateDeleteProduto(Produto produto) {
        return Database.read(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.findByProduto(produto.getId()).isEmpty()
        );
    }
}
