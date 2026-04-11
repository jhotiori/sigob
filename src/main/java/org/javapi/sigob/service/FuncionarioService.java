package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.exception.FuncionarioException;
import org.javapi.sigob.repository.FuncionarioRepository;

public class FuncionarioService {
    private final FuncionarioRepository repository;

    /**
     * Cria uma novo FuncionarioService
     *
     * @param repository O repositorio de funcionarios
     * @return FuncionarioService - O servico de funcionarios
     */
    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva um novo Funcionario
     *
     * @param funcionario O funcionario para salvar
     */
    public void save(Funcionario funcionario) {
        validateFuncionario(funcionario);
        this.repository.save(funcionario);
    }

    /**
     * Atualiza um Funcionario
     *
     * @param funcionario O funcionario para atualizar
     */
    public void update(Funcionario funcionario) {
        validateNome(funcionario.getNmFuncionario());
        validateCodigo(funcionario.getCdFuncionario());
        validateAcesso(funcionario.getAcesso());
        this.repository.update(funcionario);
    }

    /**
     * Deleta um Funcionario
     *
     * @param funcionario O funcionario para deletar
     */
    public void delete(Funcionario funcionario) {
        if (this.repository.contains(funcionario)) {
            this.repository.delete(funcionario);
        }
    }

    /**
     * Confere se um funcionario existe
     *
     * @param funcionario O funcionario para conferir
     * @return boolean - true se o funcionario existe, false se nao
     */
    public boolean contains(Funcionario funcionario) {
        return this.repository.contains(funcionario);
    }

    /**
     * Retorna uma lista com todos os funcionarios
     *
     * @return List<Funcionario> - A lista de funcionarios
     */
    public List<Funcionario> findAll() {
        return this.repository.findAll();
    }

    /**
     * Busca um funcionario pelo id
     *
     * @param id O ID do funcionario
     * @return Funcionario - O funcionario
     */
    public Funcionario findById(int id) {
        validateId(id);
        return this.repository.findById(id);
    }

    /**
     * Busca um por funcionarios que contem o nome
     *
     * @param nome O nome para procurar
     * @return List<Funcionario> - A lista de funcionarios
     */
    public List<Funcionario> findByNome(String nome) {
        validateNome(nome);
        return this.repository.findByNome(nome);
    }

    /**
     * Busca pelo Funcionario que contem o codigo
     *
     * @param codigo O codigo para procurar
     * @return Funcionario - O funcionario
     */
    public Funcionario findByCodigo(String codigo) {
        validateCodigo(codigo);
        return this.repository.findByCodigo(codigo);
    }

    private void validateFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new FuncionarioException("Funcionário não pode ser nulo");
        }
        validateId(funcionario.getIdFuncionario());
        validateNome(funcionario.getNmFuncionario());
        validateCodigo(funcionario.getCdFuncionario());
        validateAcesso(funcionario.getAcesso());
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new FuncionarioException("ID do funcionário deve ser maior que zero");
        }
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new FuncionarioException("Nome do funcionário não pode ser nulo ou vazio");
        }
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new FuncionarioException("Código do funcionário não pode ser nulo ou vazio");
        }
    }

    private void validateAcesso(Acesso acesso) {
        if (acesso == null) {
            throw new FuncionarioException("Acesso do funcionário não pode ser nulo");
        }
    }
}
