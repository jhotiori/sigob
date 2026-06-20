package org.javapi.sigob.model.repository;

import java.util.List;

import org.javapi.sigob.model.entity.Documento;

public interface DocumentoRepository extends JpaCrudRepository<Documento, Integer> {

    /**
     * Busca Documentos pelo número do documento.
     *
     * @param documento - Documento para busca
     * @return List<Documento> - Documentos encontrados
     */
    List<Documento> findByDocumento(String documento);

    /**
     * Busca Documentos pelo tipo.
     *
     * @param tipo - Tipo para busca
     * @return List<Documento> - Documentos encontrados
     */
    List<Documento> findByTipo(String tipo);
}
