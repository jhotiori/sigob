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
     * Busca Documento cujo documento (valor dele) inicia com o valor informado
     *
     * @param valor de prefixo
     * @return List<Documento> - As Documento encontradas
     */
    public List<Documento> findByDocumento(String valor) {
        return em.createQuery("SELECT d FROM documentos d WHERE d.documento LIKE :prefix", Documento.class)
                        .setParameter("prefix", valor + "%")
                        .getResultList();
    }

    /**
     * Busca por Documentos cujo tipo inicia com o valor informado
     *
     * @param tipo O tipo do Documento
     * @return List<Documento> - Os Documento encontrados
     */
    public List<Documento> findByTipo(String tipo) {
        return em.createQuery("SELECT d FROM documentos d WHERE d.tipo LIKE :str", Documento.class)
                .setParameter("str", tipo + "%")
                .getResultList();
    }
}
