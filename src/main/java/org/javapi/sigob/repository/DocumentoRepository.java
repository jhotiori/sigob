package org.javapi.sigob.repository;

import jakarta.persistence.EntityManager;
import org.javapi.sigob.entity.Documento;

import java.util.List;
import java.util.Optional;

public class DocumentoRepository extends BaseRepository<Documento, Integer> {

    /**
     * Cria um novo DocumentoRepository
     *
     * @param em O EntityManager
     */
    public DocumentoRepository(EntityManager em) {
        super(em, Documento.class);
    }

    /**
     * Busca todos os Documento disponíveis
     *
     * @return List<Documento> - Todos os Documento
     */
    public List<Documento> findAll() {
        return em.createQuery("SELECT d FROM documentos d", Documento.class)
                .getResultList();
    }

    /**
     * Busca Documento no banco de dados com base em um documento
     *
     * @param documento string informada para busca
     * @return List<Documento> - As Documento encontradas
     */
    public List<Documento> findByDocumento(String documento) {
        return em.createQuery("SELECT d FROM documentos d WHERE LOWER (d.documento) LIKE LOWER (:str)", Documento.class)
                        .setParameter("str", "%" + documento + "%")
                        .getResultList();
    }

    /**
     * Busca Documento no banco de dados com base em um tipo
     *
     * @param tipo string informada para busca
     * @return List<Documento> - Os Documento encontrados
     */
    public List<Documento> findByTipo(String tipo) {
        return em.createQuery("SELECT d FROM documentos d WHERE LOWER (d.tipo) LIKE LOWER (:str)", Documento.class)
                .setParameter("str", "%" + tipo + "%")
                .getResultList();
    }
}
