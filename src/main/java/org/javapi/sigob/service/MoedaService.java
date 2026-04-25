package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.config.JPAConfig;
import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.exception.MoedaException;
import org.javapi.sigob.repository.MoedaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MoedaService {

    /**
     * Cria um novo MoedaService
     *
     * @return MoedaService - O servico de moedas
     */
    public MoedaService() {
    }

    /**
     * Salva uma moeda (cria ou atualiza)
     *
     * @param moeda A moeda para salvar
     */
    public void save(Moeda moeda) {
        validateMoeda(moeda);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        MoedaRepository repository = new MoedaRepository(em);

        try {
            transaction.begin();
            if (moeda.getId() > 0) {
                repository.update(moeda);
            } else {
                repository.save(moeda);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Atualiza uma moeda
     *
     * @param moeda A moeda para atualizar
     * @throws MoedaException Se o ID da moeda for invalido
     */
    public void update(Moeda moeda) {
        validateMoeda(moeda);

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        MoedaRepository repository = new MoedaRepository(em);

        try {
            transaction.begin();
            repository.update(moeda);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Busca uma moeda pelo ID
     *
     * @param id O ID da moeda
     * @return Moeda - A moeda encontrada
     */
    public Moeda findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        MoedaRepository repository = new MoedaRepository(em);

        try {
            return repository.findById(id).orElse(null);
        } finally {
            em.close();
        }
    }

    /**
     * Busca moedas pelo nome
     *
     * @param nome O nome para buscar
     * @return List<Moeda> - A lista de moedas
     */
    public List<Moeda> findByNome(String nome) {
        validateNome(nome);

        EntityManager em = JPAConfig.getEntityManager();
        MoedaRepository repository = new MoedaRepository(em);

        try {
            return repository.findByNome(nome);
        } finally {
            em.close();
        }
    }

    /**
     * Busca uma moeda pelo codigo
     *
     * @param codigo O codigo da moeda
     * @return Moeda - A moeda encontrada
     */
    public Moeda findByCodigo(String codigo) {
        validateCodigo(codigo);

        EntityManager em = JPAConfig.getEntityManager();
        MoedaRepository repository = new MoedaRepository(em);

        try {
            return repository.findBySigla(codigo);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna todas as moedas
     *
     * @return List<Moeda> - A lista de moedas
     */
    public List<Moeda> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        MoedaRepository repository = new MoedaRepository(em);

        try {
            return repository.findAll();
        } finally {
            em.close();
        }
    }

    /**
     * Remove uma moeda
     *
     * @param moeda A moeda para remover
     */
    public void delete(Moeda moeda) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        MoedaRepository repository = new MoedaRepository(em);

        try {
            transaction.begin();
            repository.deleteById(moeda.getId());
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    private void validateMoeda(Moeda moeda) {
        if (moeda == null) {
            throw new MoedaException("Moeda não pode ser nula");
        }
        validateNome(moeda.getNome());
        validateCifrao(moeda.getCifrao());
        validateSigla(moeda.getSigla());
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
