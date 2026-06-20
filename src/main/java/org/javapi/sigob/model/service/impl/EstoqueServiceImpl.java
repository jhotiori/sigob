package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.EstoqueRepositoryImpl;
import org.javapi.sigob.model.repository.impl.ProdutosEstoquesRepositoryImpl;
import org.javapi.sigob.model.service.EstoqueService;
import org.javapi.sigob.model.validation.entity.EstoqueValidator;

public class EstoqueServiceImpl implements EstoqueService {

    /**
     * Salva um novo estoque.
     *
     * @param estoque - Estoque a ser salvo.
     */
    @Override
    public void save(Estoque estoque) {
        EstoqueValidator.validate(estoque);
        Database.write(
                EstoqueRepositoryImpl::new,
                repo -> repo.save(estoque)
        );
    }

    /**
     * Atualiza um estoque existente.
     *
     * @param estoque - Estoque a ser atualizado.
     */
    @Override
    public void update(Estoque estoque) {
        EstoqueValidator.validate(estoque);
        Database.write(
                EstoqueRepositoryImpl::new,
                repo -> repo.update(estoque)
        );
    }

    /**
     * Remove um estoque.
     *
     * @param estoque - Estoque a ser removido.
     */
    @Override
    public void delete(Estoque estoque) {
        if (validateDeleteEstoque(estoque)) {
            Database.write(
                    EstoqueRepositoryImpl::new,
                    repo -> repo.deleteById(estoque.getId())
            );
        } else {
            throw new SigobException(
                    "O Estoque possuí vínculo com ProdutosEstoques, não podendo ser removido!"
            );
        }
    }

    /**
     * Verifica se existe um estoque com o ID informado.
     *
     * @param id - ID do estoque.
     * @return boolean - True caso exista, false caso contrário.
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                EstoqueRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca todos os estoques cadastrados.
     *
     * @return List<Estoque> - Lista de estoques encontrados.
     */
    @Override
    public List<Estoque> findAll() {
        return Database.read(
                EstoqueRepositoryImpl::new,
                EstoqueRepositoryImpl::findAll
        );
    }

    /**
     * Busca um estoque pelo ID.
     *
     * @param id - ID do estoque.
     * @return Optional<Estoque> - Estoque encontrado, se existir.
     */
    @Override
    public Optional<Estoque> findById(Integer id) {
        return Database.read(
                EstoqueRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca estoques pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Estoque> - Lista de estoques encontrados.
     */
    @Override
    public List<Estoque> findByNome(String nome) {
        EstoqueValidator.validateNome(nome);
        return Database.read(
                EstoqueRepositoryImpl::new,
                repo -> repo.findByNome(nome)
        );
    }

    /**
     * Busca estoques pelo código.
     *
     * @param codigo - Código utilizado na busca.
     * @return List<Estoque> - Lista de estoques encontrados.
     */
    @Override
    public List<Estoque> findByCodigo(String codigo) {
        EstoqueValidator.validateCodigo(codigo);
        return Database.read(
                EstoqueRepositoryImpl::new,
                repo -> repo.findByCodigo(codigo)
        );
    }

    /**
     * Valida se um estoque está vinculado a um produto em estoque antes de
     * deletar.
     *
     * @param estoque - Estoque a ser validado.
     * @return boolean - True se é possível deletar o registro de forma segura.
     */
    private boolean validateDeleteEstoque(Estoque estoque) {
        return Database.read(
                ProdutosEstoquesRepositoryImpl::new,
                repo -> repo.findByEstoque(estoque.getId()).isEmpty()
        );
    }
}
