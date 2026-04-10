package org.javapi.sigob.service;

import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.exception.FuncionarioException;
import org.javapi.sigob.repository.FuncionarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class FuncionarioService {
    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public void save(Funcionario funcionario, EntityManager em) {
        validateFuncionario(funcionario);

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int idFuncionario = funcionario.getIdFuncionario();

            if (idFuncionario > 0) {
                this.repository.update(funcionario);
            } else {
                this.repository.create(funcionario);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void create(Funcionario funcionario) {
        validateFuncionario(funcionario);
        this.repository.create(funcionario);
    }

    public void update(Funcionario funcionario) {
        validateFuncionario(funcionario);
        if (funcionario.getIdFuncionario() <= 0) {
            throw new FuncionarioException("ID do funcionário deve ser maior que zero para atualizar");
        }
        this.repository.update(funcionario);
    }

    public Funcionario getById(int id) {
        if (id <= 0) {
            throw new FuncionarioException("ID do funcionário não pode ser menor ou igual a zero");
        }
        return this.repository.findById(id);
    }

    public List<Funcionario> getByNome(String nome) {
        validateNome(nome);
        return this.repository.findByName(nome);
    }

    public Funcionario getByCodigo(String codigo) {
        validateCodigo(codigo);
        return this.repository.findByCodigo(codigo);
    }

    public List<Funcionario> getAll() {
        return this.repository.findAll();
    }

    public void delete(Funcionario funcionario) {
        this.repository.delete(funcionario);
    }

    private void validateFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new FuncionarioException("Funcionário não pode ser nulo");
        }
        validateNome(funcionario.getNmFuncionario());
        validateCodigo(funcionario.getCdFuncionario());
        validateAcesso(funcionario.getAcesso());
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