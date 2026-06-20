package org.javapi.sigob.model.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Caixa;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.CaixaRepositoryImpl;
import org.javapi.sigob.model.service.CaixaService;
import org.javapi.sigob.model.validation.entity.CaixaValidator;

public class CaixaServiceImpl implements CaixaService {

    /**
     * Salva um novo caixa.
     *
     * @param caixa - Caixa a ser salvo
     */
    @Override
    public void save(Caixa caixa) {
        CaixaValidator.validate(caixa);
        Database.write(
                CaixaRepositoryImpl::new,
                repo -> repo.save(caixa)
        );
    }

    /**
     * Atualiza um caixa existente.
     *
     * @param caixa - Caixa a ser atualizado
     */
    @Override
    public void update(Caixa caixa) {
        CaixaValidator.validate(caixa);
        Database.write(
                CaixaRepositoryImpl::new,
                repo -> repo.update(caixa)
        );
    }

    /**
     * Remove um caixa.
     *
     * @param caixa - Caixa a ser removido
     */
    @Override
    public void delete(Caixa caixa) {
        Database.write(
                CaixaRepositoryImpl::new,
                repo -> repo.deleteById(caixa.getId())
        );
    }

    /**
     * Verifica se existe um caixa com o ID informado.
     *
     * @param id - ID do caixa
     * @return boolean - True caso exista, false caso contrário
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                CaixaRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca todos os caixas cadastrados.
     *
     * @return List<Caixa> - Lista de caixas encontrados
     */
    @Override
    public List<Caixa> findAll() {
        return Database.read(
                CaixaRepositoryImpl::new,
                CaixaRepositoryImpl::findAll
        );
    }

    /**
     * Busca um caixa pelo ID.
     *
     * @param id - ID do caixa
     * @return Optional<Caixa> - Caixa encontrado, se existir
     */
    @Override
    public Optional<Caixa> findById(Integer id) {
        return Database.read(
                CaixaRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca caixas pelo status.
     *
     * @param status - Status utilizado na busca.
     * @return List<Caixa> - Lista de caixas encontradas.
     */
    @Override
    public List<Caixa> findByStatus(String status) {
        CaixaValidator.validateStatus(status);
        return Database.read(
                CaixaRepositoryImpl::new,
                repo -> repo.findByStatus(status)
        );
    }

    /**
     * Busca caixas pela data de abertura.
     *
     * @param data - Data de abertura utilizada na busca.
     * @return List<Caixa> - Lista de caixas encontrados.
     */
    @Override
    public List<Caixa> findByDataAbertura(LocalDate data) {
        CaixaValidator.validateData(data);
        return Database.read(
                CaixaRepositoryImpl::new,
                repo -> repo.findByDataAbertura(data)
        );
    }

    /**
     * Busca caixas pela data de fechamento.
     *
     * @param data - Data de fechamento utilizada na busca.
     * @return List<Caixa> - Lista de caixas encontrados.
     */
    @Override
    public List<Caixa> findByDataFechamento(LocalDate data) {
        CaixaValidator.validateData(data);
        return Database.read(
                CaixaRepositoryImpl::new,
                repo -> repo.findByDataFechamento(data)
        );
    }

    /**
     * Busca todos os caixas abertos.
     *
     * @return List<Caixa> - Lista de caixas abertos.
     */
    @Override
    public List<Caixa> findAbertos() {
        return Database.read(
                CaixaRepositoryImpl::new,
                CaixaRepositoryImpl::findAbertos
        );
    }

    /**
     * Busca todos os caixas fechados.
     *
     * @return List<Caixa> - Lista de caixas fechados.
     */
    @Override
    public List<Caixa> findFechados() {
        return Database.read(
                CaixaRepositoryImpl::new,
                CaixaRepositoryImpl::findFechados
        );
    }
}
