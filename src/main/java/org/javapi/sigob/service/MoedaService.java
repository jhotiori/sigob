package org.javapi.sigob.service;

import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.exception.MoedaException;
import org.javapi.sigob.repository.MoedaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class MoedaService {
    private final MoedaRepository repository;

    public MoedaService(MoedaRepository repository) {
        this.repository = repository;
    }

    public void save(Moeda moeda, EntityManager em) {
        validateMoeda(moeda);

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int idMoeda = moeda.getIdMoeda();

            if (idMoeda > 0) {
                this.repository.update(moeda);
            } else {
                this.repository.create(moeda);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void create(Moeda moeda) {
        validateMoeda(moeda);
        this.repository.create(moeda);
    }

    public void update(Moeda moeda) {
        validateMoeda(moeda);
        if (moeda.getIdMoeda() <= 0) {
            throw new MoedaException("ID da moeda deve ser maior que zero para atualizar");
        }
        this.repository.update(moeda);
    }

    public Moeda getById(int id) {
        if (id <= 0) {
            throw new MoedaException("ID da moeda não pode ser menor ou igual a zero");
        }
        return this.repository.findById(id);
    }

    public List<Moeda> getByNome(String nome) {
        validateNome(nome);
        return this.repository.findByNome(nome);
    }

    public Moeda getByCodigo(String codigo) {
        validateCodigo(codigo);
        return this.repository.findByCodigo(codigo);
    }

    public List<Moeda> getAll() {
        return this.repository.findAll();
    }

    public void delete(Moeda moeda) {
        this.repository.delete(moeda);
    }

    private void validateMoeda(Moeda moeda) {
        if (moeda == null) {
            throw new MoedaException("Moeda não pode ser nula");
        }
        validateNome(moeda.getNmMoeda());
        validateCifrao(moeda.getDsCifrao());
        validateSigla(moeda.getDsSigla());
    }

    private void validateNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new MoedaException("Nome da moeda não pode ser nulo ou vazio");
        }
    }

    private void validateCifrao(String cifrao) {
        if (cifrao == null || cifrao.isBlank()) {
            throw new MoedaException("Cifrao da moeda não pode ser nulo ou vazio");
        }
    }

    private void validateSigla(String sigla) {
        if (sigla == null || sigla.isBlank()) {
            throw new MoedaException("Sigla da moeda não pode ser nula ou vazia");
        }
    }

    private void validateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new MoedaException("Código da moeda não pode ser nulo ou vazio");
        }
    }
}