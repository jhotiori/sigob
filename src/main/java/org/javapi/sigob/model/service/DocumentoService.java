package org.javapi.sigob.model.service;

import java.util.List;

import org.javapi.sigob.model.entity.Documento;

public interface DocumentoService extends JpaCrudService<Documento, Integer> {

    /**
     * Busca documentos pelo número ou identificação do documento.
     *
     * @param documento - Documento utilizado na busca.
     * @return List<Documento> - Lista de documentos encontrados.
     */
    List<Documento> findByDocumento(String documento);

    /**
     * Busca documentos pelo tipo.
     *
     * @param tipo - Tipo utilizado na busca.
     * @return List<Documento> - Lista de documentos encontrados.
     */
    List<Documento> findByTipo(String tipo);
}
