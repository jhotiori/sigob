package org.javapi.sigob.service;

import java.util.List;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.exception.AcessoException;
import org.javapi.sigob.repository.AcessoRepository;

public class AcessoService {
    private final AcessoRepository repository;

    /**
     * Cria um novo AcessoService
     *
     * @param repository O repositorio de Acesso
     * @return AcessoService - O servico
     */
    public AcessoService(AcessoRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva um acesso
     *
     * @param acesso O acesso a ser salvo
     * @throws AcessoException Se o acesso for invalido
     */
    public void save(Acesso acesso) {
        validateAcesso(acesso);
        this.repository.save(acesso);
    }

    /**
     * Atualiza um acesso
     *
     * @param acesso O acesso a ser atualizado
     * @throws AcessoException Se o acesso for invalido
     */
    public void update(Acesso acesso) {
        validateAcesso(acesso);
        this.repository.update(acesso);
    }

    /**
     * Deleta um acesso
     *
     * @param acesso O acesso a ser deletado
     */
    public void delete(Acesso acesso) {
        if (this.repository.contains(acesso)) {
            this.repository.delete(acesso);
        }
    }

    /**
     * Confere se um acesso existe
     *
     * @param acesso O acesso para conferir
     * @return boolean - true se o Acesso existe, false se nao existir
     */
    public boolean contains(Acesso acesso) {
        return this.repository.contains(acesso);
    }

    /**
     * Retorna uma lista com todos os Acessos
     *
     * @return List<Acesso> - A lista de Acessos
     */
    public List<Acesso> findAll() {
        return this.repository.findAll();
    }

    /**
     * Busca um Acesso pelo seu ID
     *
     * @param id O ID do Acesso
     * @return Acesso - O Acesso buscado
     */
    public Acesso findById(int id) {
        validateId(id);
        return this.repository.findById(id);
    }

    /**
     * Retorna uma lista de acessos que possuem o nome informado
     *
     * @param nome O nome para procurar
     * @return List<Acesso> - A lista de Acessos
     */
    public List<Acesso> findByNome(String nome) {
        validateNome(nome);
        return this.repository.findByNome(nome);
    }

    /**
     * Busca um Acesso pelo seu codigo
     *
     * @param codigo O codigo do Acesso
     * @return Acesso - O Acesso buscado
     */
    public Acesso findByCodigo(String codigo) {
        validateCodigo(codigo);
        return this.repository.findByCodigo(codigo);
    }

    private void validateAcesso(Acesso acesso) {
        if (acesso == null) {
            throw new AcessoException("Acesso não pode ser nulo");
        }
        validateId(acesso.getIdAcesso());
        validateNome(acesso.getNmAcesso());
        validateCodigo(acesso.getCdAcesso());
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new AcessoException("ID do acesso deve ser maior que zero");
        }
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
