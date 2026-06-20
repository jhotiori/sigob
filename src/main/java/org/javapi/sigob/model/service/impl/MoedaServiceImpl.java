package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.MoedaRepositoryImpl;
import org.javapi.sigob.model.repository.impl.ProdutoRepositoryImpl;
import org.javapi.sigob.model.service.MoedaService;
import org.javapi.sigob.model.validation.entity.MoedaValidator;

public class MoedaServiceImpl implements MoedaService {

    /**
     * Cria um novo MoedaService
     *
     * @return MoedaService - O servico de moedas
     */
    public MoedaServiceImpl() {
    }

    /**
     * Salva uma moeda.
     *
     * @param moeda - Moeda a ser salva.
     */
    @Override
    public void save(Moeda moeda) {
        MoedaValidator.validate(moeda);
        Database.write(
                MoedaRepositoryImpl::new,
                repo -> repo.save(moeda)
        );
    }

    /**
     * Atualiza uma moeda existente.
     *
     * @param moeda - Moeda a ser atualizada.
     */
    @Override
    public void update(Moeda moeda) {
        MoedaValidator.validate(moeda);
        Database.write(
                MoedaRepositoryImpl::new,
                repo -> repo.update(moeda)
        );
    }

    /**
     * Remove uma moeda.
     *
     * @param moeda - Moeda a ser removida.
     */
    @Override
    public void delete(Moeda moeda) {
        if (validateDeleteMoeda(moeda)) {
            Database.write(
                    MoedaRepositoryImpl::new,
                    repo -> repo.deleteById(moeda.getId())
            );
        } else {
            throw new SigobException(
                    "A Moeda possuí vínculo com Produto, não podendo ser removido!"
            );
        }
    }

    /**
     * Confere se uma moeda existe.
     *
     * @param id - ID da moeda.
     * @return boolean - True se a moeda existir, false caso contrário.
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                MoedaRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca uma moeda pelo ID.
     *
     * @param id - ID da moeda utilizada na busca.
     * @return Optional<Moeda> - Moeda encontrada, se existir.
     */
    @Override
    public Optional<Moeda> findById(Integer id) {
        return Database.read(
                MoedaRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca moedas pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Moeda> - Lista de moedas encontradas.
     */
    @Override
    public List<Moeda> findByNome(String nome) {
        MoedaValidator.validateNome(nome);
        return Database.read(
                MoedaRepositoryImpl::new,
                repo -> repo.findByNome(nome)
        );
    }

    /**
     * Busca uma moeda pela sigla.
     *
     * @param sigla - Sigla utilizada na busca.
     * @return Optional<Moeda> - Moeda encontrada, se existir.
     */
    @Override
    public Optional<Moeda> findBySigla(String sigla) {
        MoedaValidator.validateSigla(sigla);
        return Database.read(
                MoedaRepositoryImpl::new,
                repo -> repo.findBySigla(sigla)
        );
    }

    /**
     * Busca moedas pelo Cifrao.
     *
     * @param cifrao - Cifrao utilizado na busca.
     * @return List<Moeda> - Lista de moedas encontradas.
     */
    @Override
    public List<Moeda> findByCifrao(String cifrao) {
        MoedaValidator.validateCifrao(cifrao);
        return Database.read(
                MoedaRepositoryImpl::new,
                repo -> repo.findByCifrao(cifrao)
        );
    }

    /**
     * Retorna todas as moedas.
     *
     * @return List<Moeda> - Lista de moedas.
     */
    @Override
    public List<Moeda> findAll() {
        return Database.read(
                MoedaRepositoryImpl::new,
                MoedaRepositoryImpl::findAll
        );
    }

    /**
     * Valida se uma Moeda está vinculada a um Produto antes de deletar.
     *
     * @param moeda - Moeda a ser validada.
     * @return boolean - True se é possível deletar o registro de forma segura.
     */
    private boolean validateDeleteMoeda(Moeda moeda) {
        return Database.read(
                ProdutoRepositoryImpl::new,
                repo -> repo.findByMoedaId(moeda.getId()).isEmpty()
        );
    }
}
