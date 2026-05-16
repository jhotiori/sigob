package org.javapi.sigob.service;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.repository.DocumentoRepository;
import org.javapi.sigob.transaction.TransactionExecutor;
import org.javapi.sigob.util.Validator;

public class DocumentoService {

    /**
     * Cria um novo DocumentoService
     *
     * @return DocumentoService - O service
     */
    public DocumentoService() {

    }

    /**
     * Salva um Documento
     *
     * @param documento O Documento para ser salvado
     */
    public void save(Documento documento) {
        validateDocumento(documento);
        TransactionExecutor.executeVoid(em -> {
            new DocumentoRepository(em).save(documento);
        });
    }

    /**
     * Atualiza um Documento
     *
     * @param documento O Documento para ser atualizado
     */
    public void update(Documento documento) {
        validateDocumento(documento);
        TransactionExecutor.executeVoid(em -> {
            new DocumentoRepository(em).update(documento);
        });
    }

    /**
     * Deleta um Documento
     *
     * @param documento O Documento para ser deletado
     */
    public void delete(Documento documento) {
        TransactionExecutor.executeVoid(em -> {
            new DocumentoRepository(em).deleteById(documento.getId());
        });
    }

    /**
     * Confere se um Documento existe no Banco de Dados
     *
     * @param documento O Documento para conferir
     * @return boolean - true se o Documento existir, false se nao
     */
    public boolean contains(Documento documento) {
        validateDocumento(documento);
        return TransactionExecutor.query(em -> {
            return new DocumentoRepository(em).contains(documento.getId());
        });
    }

    /**
     * Busca um Documento pelo seu ID
     *
     * @param id O ID do Documento
     * @return Optional<Documento> - O Documento buscado
     */
    public Optional<Documento> findById(int id) {
        return TransactionExecutor.query(em -> {
            return new DocumentoRepository(em).findById(id);
        });
    }

    /**
     * Busca todos os Documento disponíveis
     *
     * @return List<Documento> - Todos os Documento
     */
    public List<Documento> findAll() {
        return TransactionExecutor.query(em -> {
            return new DocumentoRepository(em).findAll();
        });
    }

    /**
     * Busca Documento cujo documento (valor dele) inicia com o valor informado
     *
     * @param prefixo O prefixo do nome
     * @return List<Documento> - As Documento encontradas
     */
    public Optional<Documento> findByDocumento(String prefixo) {
        return TransactionExecutor.query(em -> {
            return new DocumentoRepository(em).findByDocumento(prefixo);
        });
    }

    /**
     * Busca por Documentos cujo tipo inicia com o valor informado
     *
     * @param tipo O tipo do Documento
     * @return List<Documento> - Os Documento encontrados
     */
    public List<Documento> findByTipo(String tipo) {
        return TransactionExecutor.query(em -> {
            return new DocumentoRepository(em).findByTipo(tipo);
        });
    }

    /**
     * Valida o documento de um serviço
     *
     * @param documento O Documento
     */
    private void validateDocumento(Documento documento) {
        Validator.start()
                .expectNotNull(documento, "Documento não pode ser nulo")
                .expectNotBlank(documento.getDocumento(), "Documento do documento não pode ser nulo ou vazio")
                .expectNotBlank(documento.getTipo(), "Tipo do Documento não pode ser nulo ou vazio")
                .validate();
    }
}
