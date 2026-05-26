package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Categoria;
import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.exception.ValidationException;
import org.javapi.sigob.repository.CategoriaRepository;
import org.javapi.sigob.repository.ProdutoRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class CategoriaService {

    /**
     * Construtor para criar um novo CategoriaService
     *
     */
    public CategoriaService() {
    }

    /**
     * Salva uma nova Categoria
     *
     * @param categoria A Categoria para ser salva
     */
    public void save(Categoria categoria) {
        validateNome(categoria.getNome());

        TransactionExecutor.executeVoid(em -> {
            new CategoriaRepository(em).save(categoria);
        });
    }

    /**
     * Atualiza uma Categoria
     *
     * @param categoria A Categoria para ser atualizada
     */
    public void update(Categoria categoria) {
        validateCategoria(categoria);

        TransactionExecutor.executeVoid(em -> {
            new CategoriaRepository(em).update(categoria);
        });
    }

    /**
     * Remove uma Categoria
     *
     * @param categoria A Categoria para ser removida
     */
    public void delete(Categoria categoria) {

        if (validateDeleteCategoria(categoria)){
            TransactionExecutor.executeVoid(em -> {
                new CategoriaRepository(em).deleteById(categoria.getId());
            });
        } else{
            throw new SigobException("A Categoria possuí vínculo com Produto, não podendo ser removida!");
        }
    }

    /**
     * Confere se uma categoria existe
     *
     * @param categoria A categoria
     * @return boolean - true se a categoria existe, false se nao
     */
    public boolean contains(Categoria categoria) {
        validateCategoria(categoria);

        return TransactionExecutor.query(em -> {
            return new CategoriaRepository(em).contains(categoria.getId());
        });
    }

    /**
     * Retorna uma lista com todas as Categorias
     *
     * @return List<Categoria> - A lista de categorias
     */
    public List<Categoria> findAll() {
        return TransactionExecutor.query(em -> {
            return new CategoriaRepository(em).findAll();
        });
    }

    /**
     * Busca uma Categoria pelo seu ID
     *
     * @param id O ID da Categoria
     * @return Optional<Categoria> - A Categoria buscada
     */
    public Optional<Categoria> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new CategoriaRepository(em).findById(id);
        });
    }

    /**
     * Busca por categorias com base em um nome
     *
     * @param nome O nome a ser buscado
     * @return List<Categoria> - A lista de categorias
     */
    public List<Categoria> findByNome(String nome) {
        validateNome(nome);

        return TransactionExecutor.query(em -> {
            return new CategoriaRepository(em).findByNome(nome);
        });
    }

    /**
     * Valida uma Categoria por completa
     *
     * @param categoria A Categoria para ser validada
     * @throws IllegalArgumentException Se a Categoria for invalida
     */
    private void validateCategoria(Categoria categoria) {
        Validator.start()
                .expectNotNull(categoria, "Categoria não pode ser nula")
                .validate();
        validateNome(categoria.getNome());
    }

    /**
     * Valida o nome de uma Categoria
     *
     * @param nome O nome a ser validado
     * @throws IllegalArgumentException Se o nome for invalido
     */
    private void validateNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome da Categoria não pode ser nulo ou vazio")
                .validate();
    }

    /**
     * Valida se uma Categoria está vinculado a um funcionário antes de deletar
     *
     * @param categoria O acesso a ser validado
     * @return true se é possível deletar o registro de forma segura
     * @return false se não é possível deletar este registro
     */
    private boolean validateDeleteCategoria(Categoria categoria){
        return TransactionExecutor.query(em -> {
            return (new ProdutoRepository(em).findByCategoriaId(categoria.getId()).isEmpty() ? true : false);
        });
    }
}
