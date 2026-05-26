package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Moeda;
import org.javapi.sigob.exception.SigobException;
import org.javapi.sigob.repository.AcessoRepository;
import org.javapi.sigob.repository.FuncionarioRepository;
import org.javapi.sigob.repository.MoedaRepository;
import org.javapi.sigob.repository.ProdutoRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

import java.util.List;
import java.util.Optional;

public class MoedaService {

    /**
     * Cria um novo MoedaService
     *
     * @return MoedaService - O servico de moedas
     */
    public MoedaService() {
    }

    /**
     * Salva uma nova moeda
     *
     * @param moeda A moeda para salvar
     * @throws IllegalArgumentException Se a moeda for invalida
     */
    public void save(Moeda moeda) {
        validateMoeda(moeda);

        TransactionExecutor.executeVoid(em -> {
            new MoedaRepository(em).save(moeda);
        });
    }

    /**
     * Atualiza uma moeda existente
     *
     * @param moeda A moeda para atualizar
     * @throws IllegalArgumentException Se a moeda for invalida
     */
    public void update(Moeda moeda) {
        validateMoeda(moeda);

        TransactionExecutor.executeVoid(em -> {
            new MoedaRepository(em).update(moeda);
        });
    }

    /**
     * Remove uma moeda
     *
     * @param moeda A moeda para remover
     * @throws IllegalArgumentException Se a moeda for invalida
     */
    public void delete(Moeda moeda) {
        //validateMoeda(moeda); --acredito nao ser necessario, pois o já tem validação antes

        if(validateDeleteMoeda(moeda)){
            TransactionExecutor.executeVoid(em -> {
                new MoedaRepository(em).deleteById(moeda.getId());
            });
        } else {
            throw new SigobException("A Moeda possuí vínculo com Produto, não podendo ser removido!");
        }
    }

    /**
     * Confere se uma moeda existe
     *
     * @param moeda A moeda para conferir
     * @throws IllegalArgumentException Se a moeda for invalida
     * @return boolean - true se existir, false caso contrario
     */
    public boolean contains(Moeda moeda) {
        validateMoeda(moeda);

        return TransactionExecutor.query(em -> {
            return new MoedaRepository(em).contains(moeda.getId());
        });
    }

    /**
     * Busca uma moeda pelo ID
     *
     * @param id O ID da moeda
     * @throws IllegalArgumentException Se o ID for invalido
     * @return Optional<Moeda> - A moeda encontrada
     */
    public Optional<Moeda> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new MoedaRepository(em).findById(id);
        });
    }

    /**
     * Busca moedas pelo nome
     *
     * @param nome O nome para buscar
     * @throws IllegalArgumentException Se o nome for invalido
     * @return List<Moeda> - A lista de moedas
     */
    public List<Moeda> findByNome(String nome) {
        validateNome(nome);

        return TransactionExecutor.query(em -> {
            return new MoedaRepository(em).findByNome(nome);
        });
    }

    /**
     * Busca uma moeda pela sigla
     *
     * @param sigla A sigla da moeda
     * @throws IllegalArgumentException Se a sigla for invalida
     * @return Optional<Moeda> - A moeda encontrada
     */
    public Optional<Moeda> findBySigla(String sigla) {
        validateSigla(sigla);

        return TransactionExecutor.query(em -> {
            return new MoedaRepository(em).findBySigla(sigla);
        });
    }

    /**
     * Retorna todas as moedas
     *
     * @return List<Moeda> - A lista de moedas
     */
    public List<Moeda> findAll() {
        return TransactionExecutor.query(em -> {
            return new MoedaRepository(em).findAll();
        });
    }

    /**
     * Valida uma moeda por completo
     *
     * @param moeda A moeda a ser validada
     * @throws IllegalArgumentException Se a moeda for invalida
     */
    private void validateMoeda(Moeda moeda) {
        Validator.start()
                .expectNotNull(moeda, "Moeda nao pode ser nula!")
                .validate();

        validateNome(moeda.getNome());
        validateCifrao(moeda.getCifrao());
        validateSigla(moeda.getSigla());
    }

    /**
     * Valida o nome da moeda
     *
     * @param nome O nome a ser validado
     * @throws IllegalArgumentException Se o nome for invalido
     */
    private void validateNome(String nome) {
        Validator.start()
                .expectNotBlank(nome, "Nome da moeda nao pode ser nulo ou vazio!")
                .validate();
    }

    /**
     * Valida o cifrao da moeda
     *
     * @param cifrao O cifrao a ser validado
     * @throws IllegalArgumentException Se o cifrao for invalido
     */
    private void validateCifrao(String cifrao) {
        Validator.start()
                .expectNotBlank(cifrao, "Cifrao da moeda nao pode ser nulo ou vazio!")
                .validate();
    }

    /**
     * Valida a sigla da moeda
     *
     * @param sigla A sigla a ser validada
     * @throws IllegalArgumentException Se a sigla for invalida
     */
    private void validateSigla(String sigla) {
        Validator.start()
                .expectNotBlank(sigla, "Sigla da moeda nao pode ser nula ou vazia!")
                .validate();
    }

    /**
     * Valida se uma Moeda está vinculada a um Produto antes de deletar
     *
     * @param moeda A Moeda a ser validado
     * @return true se é possível deletar o registro de forma segura
     * @return false se não é possível deletar este registro
     */
    private boolean validateDeleteMoeda(Moeda moeda){
        return TransactionExecutor.query(em -> {
            return (new ProdutoRepository(em).findByMoedaId(moeda.getId()).isEmpty() ? true : false);
        });
    }
}
