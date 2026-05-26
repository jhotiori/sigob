package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Estoque;
import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.repository.EstoqueRepository;
import org.javapi.sigob.repository.ProdutosEstoquesRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

import java.util.List;
import java.util.Optional;

public class EstoqueService {

    /**
     * Cria um novo EstoqueService
     *
     * @return EstoqueService - O serviço de estoques
     */
    public EstoqueService() {
    }

    /**
     * Salva um estoque
     *
     * @param estoque O estoque para ser salvo
     * @throws IllegalArgumentException Se o estoque for invalido
     */
    public void save(Estoque estoque) {
        validateEstoque(estoque);

        TransactionExecutor.executeVoid(em -> {
            new EstoqueRepository(em).save(estoque);
        });
    }

    /**
     * Atualiza um estoque
     *
     * @param estoque O estoque para ser atualizado
     * @throws IllegalArgumentException Se o estoque for invalido
     */
    public void update(Estoque estoque) {
        validateEstoque(estoque);

        TransactionExecutor.executeVoid(em -> {
            new EstoqueRepository(em).update(estoque);
        });
    }

    /**
     * Deleta um estoque
     *
     * @param estoque O estoque para ser deletado
     * @throws IllegalArgumentException Se o estoque for invalido
     */
    public void delete(Estoque estoque) {
        if(validateDeleteEstoque(estoque)){
            TransactionExecutor.executeVoid(em -> {
                new EstoqueRepository(em).deleteById(estoque.getId());
            });
        } else{
            throw new SigobException("O Estoque possuí vínculo com Produtos_Estoques, não podendo ser removido!");
        }
    }

    /**
     * Confere se um estoque existe
     *
     * @param estoque O estoque para conferir
     * @return boolean - true se o estoque existe, false se nao
     * @throws IllegalArgumentException Se o estoque for invalido
     */
    public boolean contains(Estoque estoque) {
        validateEstoque(estoque);

        return TransactionExecutor.query(em -> {
            return new EstoqueRepository(em).contains(estoque.getId());
        });
    }

    /**
     * Retorna uma lista com todos os estoques
     *
     * @return List<Estoque> - A lista de estoques
     */
    public List<Estoque> findAll() {
        return TransactionExecutor.query(em -> {
            return new EstoqueRepository(em).findAll();
        });
    }

    /**
     * Busca um estoque pelo id
     *
     * @param id O id do estoque
     * @return Optional<Estoque> - O estoque encontrado
     * @throws IllegalArgumentException Se o id for invalido
     */
    public Optional<Estoque> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new EstoqueRepository(em).findById(id);
        });
    }

    /**
     * Busca estoques pelo nome
     *
     * @param nome O nome do estoque
     * @return List<Estoque> - A lista de estoques encontrados
     * @throws IllegalArgumentException Se o nome for invalido
     */
    public List<Estoque> findByNome(String nome) {
        validateNome(nome);

        return TransactionExecutor.query(em -> {
            return new EstoqueRepository(em).findByNome(nome);
        });
    }

    /**
     * Busca estoques pelo codigo
     *
     * @param codigo O codigo a ser buscado
     * @return List<Estoque> - A lista de estoques encontrados
     * @throws IllegalArgumentException Se o codigo for invalido
     */
    public List<Estoque> findByCodigo(String codigo) {
        validateCodigo(codigo);

        return TransactionExecutor.query(em -> {
            return new EstoqueRepository(em).findByCodigo(codigo);
        });
    }

    /**
     * Valida um estoque por completo
     *
     * @param estoque O estoque a ser validado
     * @throws IllegalArgumentException Se o estoque for invalido
     */
    private void validateEstoque(Estoque estoque) {
        Validator.start()
                .expectNotNull(estoque, "Estoque não pode ser nulo")
                .validate();

        validateCodigo(estoque.getCodigo());
        validateNome(estoque.getNome());
    }

    /**
     * Valida o código de um estoque
     *
     * @param codigo O código a ser validado
     * @throws IllegalArgumentException Se o código for invalido
     */
    private void validateCodigo(String codigo) {
        Validator.start()
                .expectNotBlank(codigo, "Código do estoque não pode ser nulo ou vazio")
                .validate();
    }

    /**
     * Valida o nome de um estoque
     *
     * @param nome O nome a ser validado
     * @throws IllegalArgumentException Se o nome for invalido
     */
    private void validateNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome do estoque não pode ser nulo ou vazio")
                .validate();
    }

    /**
     * Valida se um Estoque está vinculado a um ProdutoEstoque antes de deletar
     *
     * @param estoque O Estoque a ser validado
     * @return true se é possível deletar o registro de forma segura
     * @return false se não é possível deletar este registro
     */
    private boolean validateDeleteEstoque(Estoque estoque){
        return TransactionExecutor.query(em -> {
            return (new ProdutosEstoquesRepository(em).findByEstoque(estoque.getId()).isEmpty() ? true : false);
        });
    }
}
