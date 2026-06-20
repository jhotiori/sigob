package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.DocumentoRepositoryImpl;
import org.javapi.sigob.model.repository.impl.FuncionarioRepositoryImpl;
import org.javapi.sigob.model.repository.impl.VendaRepositoryImpl;
import org.javapi.sigob.model.service.FuncionarioService;
import org.javapi.sigob.model.validation.entity.FuncionarioValidator;

public class FuncionarioServiceImpl implements FuncionarioService {

    /**
     * Salva um novo funcionário.
     *
     * @param funcionario - Funcionário a ser salvo.
     */
    @Override
    public void save(Funcionario funcionario) {
        FuncionarioValidator.validate(funcionario);
        Database.write(
                FuncionarioRepositoryImpl::new,
                repo -> repo.save(funcionario)
        );
    }

    /**
     * Atualiza um funcionário existente.
     *
     * @param funcionario - Funcionário a ser atualizado.
     */
    @Override
    public void update(Funcionario funcionario) {
        FuncionarioValidator.validate(funcionario);
        Database.write(
                FuncionarioRepositoryImpl::new,
                repo -> repo.update(funcionario)
        );
    }

    /**
     * Remove um funcionário.
     *
     * @param funcionario - Funcionário a ser removido.
     */
    @Override
    public void delete(Funcionario funcionario) {
        if (validateDeleteFuncionario(funcionario)) {
            int documentoId = funcionario.getDocumento().getId();

            Database.write(
                    FuncionarioRepositoryImpl::new,
                    repo -> repo.deleteById(funcionario.getId())
            );

            if (documentoId > 0) {
                Database.write(
                        DocumentoRepositoryImpl::new,
                        repo -> repo.deleteById(documentoId)
                );
            }
        } else {
            throw new SigobException(
                    "O Funcionário possuí vínculo com Vendas, não podendo ser removido!"
            );
        }
    }

    /**
     * Verifica se existe um funcionário com o ID informado.
     *
     * @param id - ID do funcionário.
     * @return boolean - True caso exista, false caso contrário.
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                FuncionarioRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca todos os funcionários cadastrados.
     *
     * @return List<Funcionario> - Lista de funcionários encontrados.
     */
    @Override
    public List<Funcionario> findAll() {
        return Database.read(
                FuncionarioRepositoryImpl::new,
                FuncionarioRepositoryImpl::findAll
        );
    }

    /**
     * Busca um funcionário pelo ID.
     *
     * @param id - ID do funcionário.
     * @return Optional<Funcionario> - Funcionário encontrado, se existir.
     */
    @Override
    public Optional<Funcionario> findById(Integer id) {
        return Database.read(
                FuncionarioRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca funcionários pelo nome.
     *
     * @param nome - Nome utilizado na busca.
     * @return List<Funcionario> - Lista de funcionários encontrados.
     */
    @Override
    public List<Funcionario> findByNome(String nome) {
        FuncionarioValidator.validateNome(nome);
        return Database.read(
                FuncionarioRepositoryImpl::new,
                repo -> repo.findByNome(nome)
        );
    }

    /**
     * Busca um funcionário pelo código.
     *
     * @param codigo - Código utilizado na busca.
     * @return Optional<Funcionario> - Funcionário encontrado, se existir.
     */
    @Override
    public Optional<Funcionario> findByCodigo(String codigo) {
        FuncionarioValidator.validateCodigo(codigo);
        return Database.read(
                FuncionarioRepositoryImpl::new,
                repo -> repo.findByCodigo(codigo)
        );
    }

    /**
     * Busca funcionários pelo documento.
     *
     * @param documento - Documento utilizado na busca.
     * @return List<Funcionario> - Lista de funcionários encontrados.
     */
    @Override
    public List<Funcionario> findByDocumento(String documento) {
        FuncionarioValidator.validateDocumento(documento);
        return Database.read(
                FuncionarioRepositoryImpl::new,
                repo -> repo.findByDocumento(documento)
        );
    }

    /**
     * Busca funcionários pelo acesso.
     *
     * @param acesso - Acesso utilizado na busca.
     * @return List<Funcionario> - Lista de funcionários encontrados.
     */
    @Override
    public List<Funcionario> findByAcesso(String acesso) {
        FuncionarioValidator.validateAcesso(acesso);
        return Database.read(
                FuncionarioRepositoryImpl::new,
                repo -> repo.findByAcesso(acesso)
        );
    }

    /**
     * Valida se um funcionário está vinculado a uma venda antes de deletar.
     *
     * @param funcionario - Funcionário a ser validado.
     * @return boolean - True se é possível deletar o registro de forma segura.
     */
    private boolean validateDeleteFuncionario(Funcionario funcionario) {
        return Database.read(
                VendaRepositoryImpl::new,
                repo -> repo.findByFuncionarioId(funcionario.getId()).isEmpty()
        );
    }
}
