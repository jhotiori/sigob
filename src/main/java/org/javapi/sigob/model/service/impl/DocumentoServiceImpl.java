package org.javapi.sigob.model.service.impl;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.model.persistence.Database;
import org.javapi.sigob.model.repository.impl.DocumentoRepositoryImpl;
import org.javapi.sigob.model.service.DocumentoService;
import org.javapi.sigob.model.validation.entity.DocumentoValidator;

public class DocumentoServiceImpl implements DocumentoService {

    /**
     * Salva um novo documento.
     *
     * @param documento - Documento a ser salvo.
     */
    @Override
    public void save(Documento documento) {
        DocumentoValidator.validate(documento);
        Database.write(
                DocumentoRepositoryImpl::new,
                repo -> repo.save(documento)
        );
    }

    /**
     * Atualiza um documento existente.
     *
     * @param documento - Documento a ser atualizado.
     */
    @Override
    public void update(Documento documento) {
        DocumentoValidator.validate(documento);
        Database.write(
                DocumentoRepositoryImpl::new,
                repo -> repo.update(documento)
        );
    }

    /**
     * Remove um documento.
     *
     * @param documento - Documento a ser removido.
     */
    @Override
    public void delete(Documento documento) {
        Database.write(
                DocumentoRepositoryImpl::new,
                repo -> repo.deleteById(documento.getId())
        );
    }

    /**
     * Verifica se existe um documento com o ID informado.
     *
     * @param id - ID do documento.
     * @return boolean - True caso exista, false caso contrário.
     */
    @Override
    public boolean existsById(Integer id) {
        return Database.read(
                DocumentoRepositoryImpl::new,
                repo -> repo.existsById(id)
        );
    }

    /**
     * Busca um documento pelo ID.
     *
     * @param id - ID do documento.
     * @return Optional<Documento> - Documento encontrado, se existir.
     */
    @Override
    public Optional<Documento> findById(Integer id) {
        return Database.read(
                DocumentoRepositoryImpl::new,
                repo -> repo.findById(id)
        );
    }

    /**
     * Busca todos os documentos cadastrados.
     *
     * @return List<Documento> - Lista de documentos encontrados.
     */
    @Override
    public List<Documento> findAll() {
        return Database.read(
                DocumentoRepositoryImpl::new,
                DocumentoRepositoryImpl::findAll
        );
    }

    /**
     * Busca documentos pelo número ou identificação do documento.
     *
     * @param documento - Documento utilizado na busca.
     * @return List<Documento> - Lista de documentos encontrados.
     */
    @Override
    public List<Documento> findByDocumento(String documento) {
        DocumentoValidator.validateDocumento(documento);
        return Database.read(
                DocumentoRepositoryImpl::new,
                repo -> repo.findByDocumento(documento)
        );
    }

    /**
     * Busca documentos pelo tipo.
     *
     * @param tipo - Tipo utilizado na busca.
     * @return List<Documento> - Lista de documentos encontrados.
     */
    @Override
    public List<Documento> findByTipo(String tipo) {
        DocumentoValidator.validateTipo(tipo);
        return Database.read(
                DocumentoRepositoryImpl::new,
                repo -> repo.findByTipo(tipo)
        );
    }
}
