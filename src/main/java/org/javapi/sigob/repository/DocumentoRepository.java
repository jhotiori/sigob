package org.javapi.sigob.repository;

import java.util.List;
import java.util.Optional;

import org.javapi.sigob.entity.Documento;

import jakarta.persistence.EntityManager;

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
        return em.createQuery("select d from documentos d", Documento.class)
                .getResultList();
    }

    /**
     * Busca Documento cujo documento (valor dele) inicia com o valor informado
     *
     * @param prefixo O prefixo do nome
     * @return List<Documento> - As Documento encontradas
     */
    public Optional<Documento> findByDocumento(String valor) {
        return Optional.ofNullable(
            em.createQuery("select d from documentos d where d.documento like :prefix", Documento.class)
                .setParameter("prefix", valor + "%")
                .getSingleResultOrNull()
        );
    }

    /**
     * Busca por Documentos cujo tipo inicia com o valor informado
     *
     * @param tipo O tipo do Documento
     * @return List<Documento> - Os Documento encontrados
     */
    public List<Documento> findByTipo(String tipo) {
        return em.createQuery("select d from documentos d where d.tipo like :str", Documento.class)
                .setParameter("str", tipo + "%")
                .getResultList();
    }
}
