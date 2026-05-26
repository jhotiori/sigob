package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.repository.AcessoRepository;
import org.javapi.sigob.repository.FuncionarioRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class AcessoService {

    /**
     * Cria um novo AcessoService
     *
     */
    public AcessoService() {
    }

    /**
     * Salva um acesso
     *
     * @param acesso O acesso a ser salvo
     */
    public void save(Acesso acesso) {
        validateAcesso(acesso);
        TransactionExecutor.executeVoid(em -> {
            new AcessoRepository(em).save(acesso);
        });
    }

    /**
     * Atualiza um acesso
     *
     * @param acesso O acesso a ser atualizado
     */
    public void update(Acesso acesso) {
        validateAcesso(acesso);
        TransactionExecutor.executeVoid(em -> {
            new AcessoRepository(em).update(acesso);
        });
    }

    /**
     * Deleta um acesso
     *
     * @param acesso O acesso a ser deletado
     */
    public void delete(Acesso acesso) {

        if(validateDeleteAcesso(acesso)){
            TransactionExecutor.executeVoid(em -> {
                new AcessoRepository(em).deleteById(acesso.getId());
            });
        } else {
            throw new SigobException("O Acesso possuí vínculo com Funcionário, não podendo ser removido!");
        }

    }

    /**
     * Confere se um acesso existe
     *
     * @param acesso O acesso para conferir
     * @return boolean - true se o Acesso existe, false se nao existir
     */
    public boolean contains(Acesso acesso) {
        validateAcesso(acesso);
        return TransactionExecutor.query(em -> {
            return new AcessoRepository(em).contains(acesso.getId());
        });
    }

    /**
     * Retorna uma lista com todos os Acessos
     *
     * @return List<Acesso> - A lista de Acessos
     */
    public List<Acesso> findAll() {
        return TransactionExecutor.query(em -> {
            return new AcessoRepository(em).findAll();
        });
    }

    /**
     * Busca um Acesso pelo seu ID
     *
     * @param id O ID do Acesso
     * @throws IllegalArgumentException Se o ID for invalido
     * @return Optional<Acesso> - O Acesso buscado
     */
    public Optional<Acesso> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new AcessoRepository(em).findById(id);
        });
    }

    /**
     * Retorna uma lista de acessos que possuem o nome informado
     *
     * @param nome O nome para procurar
     * @return List<Acesso> - A lista de Acessos
     */
    public List<Acesso> findByNome(String nome) {
        validateNome(nome);
        return TransactionExecutor.query(em -> {
            return new AcessoRepository(em).findByNome(nome);
        });
    }

    /**
     * Valida um acesso por completo
     *
     * @param acesso O acesso a ser validado
     * @throws IllegalArgumentException Se o acesso for invalido
     */
    private void validateAcesso(Acesso acesso) {
        Validator.start()
                .expectNotNull(acesso, "Acesso nao pode ser nulo!")
                .validate();
        validateNome(acesso.getNome());
    }

    /**
     * Valida o nome de um acesso
     *
     * @param nome O nome a ser validado
     * @throws IllegalArgumentException Se o nome for invalido
     */
    private void validateNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome do acesso não pode ser nulo ou vazio!")
                .validate();
    }

    /**
     * Valida se um acesso está vinculado a um funcionário antes de deletar
     *
     * @param acesso O acesso a ser validado
     * @return true se é possível deletar o registro de forma segura
     * @return false se não é possível deletar este registro
     */
    private boolean validateDeleteAcesso(Acesso acesso){
        return TransactionExecutor.query(em -> {
            return (new FuncionarioRepository(em).findByAcessoId(acesso.getId()).isEmpty() ? true : false);
        });
    }
}
