package org.javapi.sigob.model.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.VendaRepositoryImpl;
import org.javapi.sigob.model.service.VendaService;
import org.javapi.sigob.model.validation.Validators;
import org.javapi.sigob.model.validation.entity.VendaValidator;

public class VendaServiceImpl implements VendaService {

    /**
     * Cria um novo VendaService
     */
    public VendaServiceImpl() {

    }

    /**
     * Salva um novo registro.
     *
     * @param venda - Entidade a ser salva.
     */
    @Override
    public void save(Venda venda) {
        VendaValidator.validate(venda);
        Database.write(
                VendaRepositoryImpl::new,
                repo -> repo.save(venda)
        );
    }

    /**
     * Atualiza um registro existente.
     *
     * @param venda - Entidade a ser atualizada.
     */
    @Override
    public void update(Venda venda) {
        VendaValidator.validate(venda);
        Database.write(
                VendaRepositoryImpl::new,
                repo -> repo.update(venda)
        );
    }

    /**
     * Remove um registro existente.
     *
     * @param venda - Entidade a ser removida.
     */
    @Override
    public void delete(Venda venda) {
        Database.write(
                VendaRepositoryImpl::new,
                repo -> repo.deleteById(venda.getId())
        );
    }

    /**
     * Verifica se um registro existe pelo identificador.
     *
     * @param id - Identificador do registro.
     * @return boolean - true se existir, false caso contrário.
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca todos os registros.
     *
     * @return List<Venda> - Lista de registros encontrados.
     */
    @Override
    public List<Venda> findAll() {
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findAll()
        );
    }

    /**
     * Busca um registro pelo identificador.
     *
     * @param id - Identificador do registro.
     * @return Optional<Venda> - Registro encontrado, se existir.
     */
    @Override
    public Optional<Venda> findById(Integer id) {
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca vendas abertas.
     *
     * @return List<Venda> - Lista de vendas abertas.
     */
    @Override
    public List<Venda> findAbertas() {
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findAbertas()
        );
    }

    /**
     * Busca vendas finalizadas.
     *
     * @return List<Venda> - Lista de vendas finalizadas.
     */
    @Override
    public List<Venda> findFinalizadas() {
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findFinalizadas()
        );
    }

    /**
     * Busca vendas pelo nome do cliente.
     *
     * @param nome - Nome do cliente utilizado na busca.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    @Override
    public List<Venda> findByClienteNome(String nome) {
        Validators.notBlank(nome, "Nome não pode ser vazio!");
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findByClienteNome(nome)
        );
    }

    /**
     * Busca vendas pelo nome do funcionário.
     *
     * @param nome - Nome do funcionário utilizado na busca.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    @Override
    public List<Venda> findByFuncionarioNome(String nome) {
        Validators.notBlank(nome, "Nome não pode ser vazio");
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findByFuncionarioNome(nome)
        );
    }

    /**
     * Busca vendas pela data de abertura.
     *
     * @param data - Data de abertura utilizada na busca.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    @Override
    public List<Venda> findByDataAbertura(LocalDate data) {
        Validators.notNull(data, "Data não pode ser nula!");
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findByDataAbertura(data)
        );
    }

    /**
     * Busca vendas pela data de finalização.
     *
     * @param data - Data de finalização utilizada na busca.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    @Override
    public List<Venda> findByDataFinalizada(LocalDate data) {
        Validators.notNull(data, "Data não pode ser nula!");
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findByDataFinalizada(data)
        );
    }

    /**
     * Busca vendas dentro de um período.
     *
     * @param inicio - Data inicial do período.
     * @param fim - Data final do período.
     * @return List<Venda> - Lista de vendas encontradas.
     */
    @Override
    public List<Venda> findByPeriodo(LocalDate inicio, LocalDate fim) {
        Validators.notNull(inicio, "Data inicial não pode ser nula");
        Validators.notNull(fim, "Data final não pode ser nula");
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findByPeriodo(inicio, fim)
        );
    }

}
