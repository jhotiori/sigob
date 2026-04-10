package org.javapi.sigob.service;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.exception.AcessoException;
import org.javapi.sigob.repository.AcessoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class AcessoService {
    private final AcessoRepository repository;

    public AcessoService(AcessoRepository repository) {
        this.repository = repository;
    }

    public void save(Acesso acesso, EntityManager em) {
        validateAcesso(acesso);

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int idAcesso = acesso.getIdAcesso();

            if (idAcesso > 0) {
                this.repository.update(acesso);
            } else {
                this.repository.create(acesso);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void create(Acesso acesso) {
        validateAcesso(acesso);
        this.repository.create(acesso);
    }

    public void update(Acesso acesso) {
        validateAcesso(acesso);
        if (acesso.getIdAcesso() <= 0) {
            throw new AcessoException("ID do acesso deve ser maior que zero para atualizar");
        }
        this.repository.update(acesso);
    }

    public Acesso getById(int id) {
        if (id <= 0) {
            throw new AcessoException("ID do acesso não pode ser menor ou igual a zero");
        }
        return this.repository.findById(id);
    }

    public List<Acesso> getByNome(String nome) {
        validateNome(nome);
        return this.repository.findByName(nome);
    }

    public Acesso getByCodigo(String codigo) {
        validateCodigo(codigo);
        return this.repository.findByCodigo(codigo);
    }

    public List<Acesso> getAll() {
        return this.repository.findAll();
    }

    public void delete(Acesso acesso) {
        this.repository.delete(acesso);
    }

    private void validateAcesso(Acesso acesso) {
        if (acesso == null) {
            throw new AcessoException("Acesso não pode ser nulo");
        }
        validateNome(acesso.getNmAcesso());
        validateCodigo(acesso.getCdAcesso());
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new AcessoException("Nome do acesso não pode ser nulo ou vazio");
        }
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new AcessoException("Código do acesso não pode ser nulo ou vazio");
        }
    }
}