package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.repository.DocumentoRepository;
import org.javapi.sigob.repository.FuncionarioRepository;
import org.javapi.sigob.repository.VendaRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class FuncionarioService {

    /**
     * Cria uma novo FuncionarioService
     *
     */
    public FuncionarioService() {
    }

    /**
     * Salva um novo Funcionario
     *
     * @param funcionario O funcionario para salvar
     * @throws IllegalArgumentException Se o funcionario for invalido
     */
    public void save(Funcionario funcionario) {
        validateFuncionario(funcionario);

        TransactionExecutor.executeVoid(em -> {
            new FuncionarioRepository(em).save(funcionario);
        });
    }

    /**
     * Atualiza um Funcionario
     *
     * @param funcionario O funcionario para atualizar
     * @throws IllegalArgumentException Se o funcionario for invalido
     */
    public void update(Funcionario funcionario) {
        validateFuncionario(funcionario);

        TransactionExecutor.executeVoid(em -> {
            new FuncionarioRepository(em).update(funcionario);
        });
    }

    /**
     * Deleta um Funcionario
     *
     * @param funcionario O funcionario para deletar
     * @throws IllegalArgumentException Se o funcionario for invalido
     */
    public void delete(Funcionario funcionario) {
        if (validateDeleteFuncionario(funcionario)) {
            int documento_id = funcionario.getDocumento().getId();

            TransactionExecutor.executeVoid(em -> {
                new FuncionarioRepository(em).deleteById(funcionario.getId());
            });

            if (documento_id > 0){
                TransactionExecutor.executeVoid(em -> {
                    new DocumentoRepository(em).deleteById(documento_id);
                });
            }

        } else {
            throw new SigobException("O Funcionário possuí vínculo com Vendas, não podendo ser removido!");
        }
    }

    /**
     * Confere se um funcionario existe
     *
     * @param funcionario O funcionario para conferir
     * @return boolean - true se o funcionario existe, false se nao
     * @throws IllegalArgumentException Se o funcionario for invalido
     */
    public boolean contains(Funcionario funcionario) {
        validateFuncionario(funcionario);

        return TransactionExecutor.query(em -> {
            return new FuncionarioRepository(em).contains(funcionario.getId());
        });
    }

    /**
     * Retorna uma lista com todos os funcionarios
     *
     * @return List<Funcionario> - A lista de funcionarios
     */
    public List<Funcionario> findAll() {
        return TransactionExecutor.query(em -> {
            return new FuncionarioRepository(em).findAll();
        });
    }

    /**
     * Busca um funcionario pelo id
     *
     * @param id O ID do funcionario
     * @return Optional<Funcionario> - O funcionario encontrado
     * @throws IllegalArgumentException Se o id for invalido
     */
    public Optional<Funcionario> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new FuncionarioRepository(em).findById(id);
        });
    }

    /**
     * Busca funcionarios que contem o nome
     *
     * @param nome O nome para procurar
     * @return List<Funcionario> - A lista de funcionarios
     * @throws IllegalArgumentException Se o nome for invalido
     */
    public List<Funcionario> findByNome(String nome) {
        validateNome(nome);

        return TransactionExecutor.query(em -> {
            return new FuncionarioRepository(em).findByNome(nome);
        });
    }

    /**
     * Busca um Funcionario pelo codigo
     *
     * @param codigo O codigo para procurar
     * @return Optional<Funcionario> - O funcionario encontrado
     * @throws IllegalArgumentException Se o codigo for invalido
     */
    public Optional<Funcionario> findByCodigo(String codigo) {
        validateCodigo(codigo);

        return TransactionExecutor.query(em -> {
            return new FuncionarioRepository(em).findByCodigo(codigo);
        });
    }

    /**
     * Valida um Funcionario por completo
     *
     * @param funcionario O funcionario a ser validado
     * @throws IllegalArgumentException Se o funcionario for invalido
     */
    private void validateFuncionario(Funcionario funcionario) {
        Validator.start()
                .expectNotNull(funcionario, "Funcionário não pode ser nulo")
                .validate();

        validateNome(funcionario.getNome());
        validateCodigo(funcionario.getCodigo());
        validateAcessos(funcionario.getAcessos());
    }

    /**
     * Valida o nome de um Funcionario
     *
     * @param nome O nome a ser validado
     * @throws IllegalArgumentException Se o nome for invalido
     */
    private void validateNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome do funcionário não pode ser nulo ou vazio")
                .validate();
    }

    /**
     * Valida o codigo de um Funcionario
     *
     * @param codigo O codigo a ser validado
     * @throws IllegalArgumentException Se o codigo for invalido
     */
    private void validateCodigo(String codigo) {
        Validator.start()
                .expectNotBlank(codigo, "Código do funcionário não pode ser nulo ou vazio")
                .validate();
    }

    /**
     * Valida os acessos de um Funcionario
     *
     * @param acessos O conjunto de acessos
     * @throws IllegalArgumentException Se os acessos forem invalidos
     */
    private void validateAcessos(Set<Acesso> acessos) {
        Validator.start()
                .expect(acessos, a -> a != null && !a.isEmpty(),
                        "Funcionário deve possuir ao menos um acesso")
                .validate();
    }
    /**
     * Valida se um Funcionario está vinculado a uma Venda antes de deletar
     *
     * @param funcionario O Funcionario a ser validado
     * @return true se é possível deletar o registro de forma segura
     * @return false se não é possível deletar este registro
     */
    private boolean validateDeleteFuncionario(Funcionario funcionario){
        return TransactionExecutor.query(em -> {
            return (new VendaRepository(em).findByFuncionarioId(funcionario.getId()).isEmpty() ? true : false);
        });
    }
}
