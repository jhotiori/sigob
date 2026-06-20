package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.AcessoRepositoryImpl;
import org.javapi.sigob.model.service.AcessoService;
import org.javapi.sigob.model.validation.entity.AcessoValidator;

public class AcessoServiceImpl implements AcessoService {

    /**
     * Salva um novo acesso.
     *
     * @param acesso - Acesso a ser salvo
     */
    @Override
    public void save(Acesso acesso) {
        AcessoValidator.validate(acesso);
        Database.write(
                AcessoRepositoryImpl::new,
                repo -> repo.save(acesso)
        );
    }

    /**
     * Atualiza um acesso existente.
     *
     * @param acesso - Acesso a ser atualizado
     */
    @Override
    public void update(Acesso acesso) {
        AcessoValidator.validate(acesso);
        Database.write(
                AcessoRepositoryImpl::new,
                repo -> repo.update(acesso)
        );
    }

    /**
     * Remove um acesso.
     *
     * @param acesso - Acesso a ser removido
     */
    @Override
    public void delete(Acesso acesso) {
        Database.write(
                AcessoRepositoryImpl::new,
                repo -> repo.deleteById(acesso.getId())
        );
    }

    /**
     * Verifica se existe um acesso com o ID informado.
     *
     * @param id - ID do acesso
     * @return boolean - True caso exista, false caso contrário
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                AcessoRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca todos os acessos cadastrados.
     *
     * @return List<Acesso> - Lista de acessos encontrados
     */
    @Override
    public List<Acesso> findAll() {
        return Database.read(
                AcessoRepositoryImpl::new,
                AcessoRepositoryImpl::findAll
        );
    }

    /**
     * Busca um acesso pelo ID.
     *
     * @param id - ID do acesso
     * @return Optional<Acesso> - Acesso encontrado, se existir
     */
    @Override
    public Optional<Acesso> findById(Integer id) {
        return Database.read(
                AcessoRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca acessos pelo nome.
     *
     * @param nome - Nome do acesso
     * @return List<Acesso> - Lista de acessos encontrados
     */
    @Override
    public List<Acesso> findByNome(String nome) {
        AcessoValidator.validateNome(nome);
        return Database.read(
                AcessoRepositoryImpl::new,
                repo -> repo.findByNome(nome)
        );
    }
}
