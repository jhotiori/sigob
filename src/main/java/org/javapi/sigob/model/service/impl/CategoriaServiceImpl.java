package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.CategoriaRepositoryImpl;
import org.javapi.sigob.model.repository.impl.ProdutoRepositoryImpl;
import org.javapi.sigob.model.service.CategoriaService;
import org.javapi.sigob.model.validation.entity.CategoriaValidator;

public class CategoriaServiceImpl implements CategoriaService {

    /**
     * Salva uma nova categoria.
     *
     * @param categoria - Categoria a ser salva
     */
    @Override
    public void save(Categoria categoria) {
        CategoriaValidator.validate(categoria);
        Database.write(
                CategoriaRepositoryImpl::new,
                repo -> repo.save(categoria)
        );
    }

    /**
     * Atualiza uma categoria existente.
     *
     * @param categoria - Categoria a ser atualizada
     */
    @Override
    public void update(Categoria categoria) {
        CategoriaValidator.validate(categoria);
        Database.write(
                CategoriaRepositoryImpl::new,
                repo -> repo.update(categoria)
        );
    }

    /**
     * Remove uma categoria.
     *
     * @param categoria - Categoria a ser removida
     */
    @Override
    public void delete(Categoria categoria) {
        if (validateDeleteCategoria(categoria)) {
            Database.write(
                    CategoriaRepositoryImpl::new,
                    repo -> repo.deleteById(categoria.getId())
            );
        } else {
            throw new SigobException(
                    "A Categoria possuí vínculo com Produto, não podendo ser removida!"
            );
        }
    }

    /**
     * Verifica se existe uma categoria com o ID informado.
     *
     * @param id - ID da categoria
     * @return boolean - True caso exista, false caso contrário
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                CategoriaRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca todas as categorias cadastradas.
     *
     * @return List<Categoria> - Lista de categorias encontradas
     */
    @Override
    public List<Categoria> findAll() {
        return Database.read(
                CategoriaRepositoryImpl::new,
                CategoriaRepositoryImpl::findAll
        );
    }

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id - ID da categoria
     * @return Optional<Categoria> - Categoria encontrada, se existir
     */
    @Override
    public Optional<Categoria> findById(Integer id) {
        return Database.read(
                CategoriaRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca categorias pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Categoria> - Lista de categorias encontradas.
     */
    @Override
    public List<Categoria> findByNome(String nome) {
        CategoriaValidator.validateNome(nome);
        return Database.read(
                CategoriaRepositoryImpl::new,
                repo -> repo.findByNome(nome)
        );
    }

    /**
     * Valida se uma categoria está vinculada a produtos antes de deletar.
     *
     * @param categoria - Categoria a ser validada
     * @return boolean - True caso possa ser removida, false caso contrário
     */
    private boolean validateDeleteCategoria(Categoria categoria) {
        return Database.read(
                ProdutoRepositoryImpl::new,
                repo -> repo.findByCategoriaId(categoria.getId()).isEmpty()
        );
    }
}
