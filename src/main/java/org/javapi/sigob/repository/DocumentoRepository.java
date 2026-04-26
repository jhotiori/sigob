package org.javapi.sigob.repository;

import java.util.List;

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
     * Busca Documento cujo nome inicia com o valor informado
     *
     * @param prefixo O prefixo do nome
     * @return List<Documento> - As Documento encontradas
     */
    public List<Documento> findByNome(String prefixo) {
        return em.createQuery("select d from documentos d where d.nome like :prefix", Documento.class)
                .setParameter("prefix", prefixo + "%")
                .getResultList();
    }

    /**
     * Busca um Documento cujo tipo inicia com o valor informado
     *
     * @param tipo O tipo do Documento
     * @return Documento - A Documento encontrada (pode ser null)
     */
    public Documento findByTipo(String tipo) {
        return em.createQuery("select c from documentos c where c.tipo like :str", Documento.class)
                .setParameter("str", tipo + "%")
                .getSingleResultOrNull();
    }
}
