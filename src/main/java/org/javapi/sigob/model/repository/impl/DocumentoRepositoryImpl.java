package org.javapi.sigob.model.repository.impl;

import java.util.List;

import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.model.repository.DocumentoRepository;

import jakarta.persistence.EntityManager;

/**
 * Implementação de repositório para Documento.
 */
public final class DocumentoRepositoryImpl extends JpaCrudRepositoryImpl<Documento, Integer> implements DocumentoRepository {

    /**
     * Cria um novo DocumentoRepository.
     *
     * @param entityManager - EntityManager do repositório
     */
    public DocumentoRepositoryImpl(EntityManager entityManager) {
        super(
                entityManager,
                Documento.class
            );
    }

    /**
     * Busca documentos pelo número do documento.
     *
     * @param documento - Documento informado para busca
     * @return List<Documento> - Documentos encontrados
     */
    @Override
    public List<Documento> findByDocumento(String documento) {
        return query("""
                SELECT d
                FROM %s d
                WHERE LOWER(d.documento) LIKE LOWER(:str)
                """)
                .param("str", like(documento))
                .list();
    }

    /**
     * Busca documentos pelo tipo.
     *
     * @param tipo - Tipo informado para busca
     * @return List<Documento> - Documentos encontrados
     */
    @Override
    public List<Documento> findByTipo(String tipo) {
        return query("""
                SELECT d
                FROM %s d
                WHERE LOWER(d.tipo) LIKE LOWER(:str)
                """)
                .param("str", like(tipo))
                .list();
    }
}
