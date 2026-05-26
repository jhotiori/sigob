package org.javapi.sigob.repository;

import java.time.LocalDate;
import java.util.List;

import org.javapi.sigob.entity.Venda;

import jakarta.persistence.EntityManager;

public class VendaRepository extends BaseRepository<Venda, Integer> {

    /**
     * Cria um novo VendaRepository
     *
     * @param em O EntityManager
     */
    public VendaRepository(EntityManager em) {
        super(em, Venda.class);
    }

    /**
     * Busca todas as vendas.
     *
     * @return List<Venda> - Lista de vendas
     */
    public List<Venda> findAll() {
        return em.createQuery("""
                SELECT v FROM vendas v JOIN FETCH v.cliente JOIN FETCH v.funcionario ORDER BY v.id DESC""", Venda.class)
                .getResultList();
    }

    /**
     * Busca vendas pelo prefixo do nome do cliente.
     *
     * @param nome Nome do cliente
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByClienteNome(String nome) {
        return em.createQuery("""
                SELECT v FROM vendas v JOIN FETCH v.cliente JOIN FETCH v.funcionario
                WHERE LOWER(v.cliente.nome) LIKE LOWER(:nome) ORDER BY v.id DESC""", Venda.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    /**
     * Busca vendas pelo prefixo do nome do funcionário.
     *
     * @param nome Nome do funcionário
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByFuncionarioNome(String nome) {
        return em.createQuery("""
                SELECT v FROM vendas v JOIN FETCH v.cliente JOIN FETCH v.funcionario
                WHERE LOWER(v.funcionario.nome) LIKE LOWER(:nome) ORDER BY v.id DESC """, Venda.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    /**
     * Busca vendas pela data de abertura.
     *
     * @param data Data desejada
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByDataAbertura(
            LocalDate data
    ) {
        return em.createQuery("""
            SELECT v FROM vendas v JOIN FETCH v.cliente JOIN FETCH v.funcionario
            WHERE CAST(v.dataAbertura AS DATE) = :data ORDER BY v.dataAbertura DESC """, Venda.class)
                .setParameter("data", data)
                .getResultList();
    }

    /**
     * Busca vendas pela data de fechamento.
     *
     * @param data Data de finalização.
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByDataFinalizada(
            LocalDate data
    ) {
        return em.createQuery("""
                SELECT v FROM vendas v JOIN FETCH v.cliente JOIN FETCH v.funcionario
                WHERE v.dataFinalizada IS NOT NULL AND CAST(v.dataFinalizada AS DATE) = :data 
                ORDER BY v.dataFinalizada DESC """, Venda.class)
                .setParameter("data", data)
                .getResultList();
    }

    /**
     * Busca vendas dentro de um período.
     *
     * @param inicio Data inicial
     * @param fim Data final
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByPeriodo(
            LocalDate inicio,
            LocalDate fim
    ) {
        return em.createQuery("""
            SELECT v FROM vendas v JOIN FETCH v.cliente JOIN FETCH v.funcionario
            WHERE CAST(v.dataAbertura AS DATE) BETWEEN :inicio AND :fim ORDER BY v.dataAbertura DESC """, Venda.class)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();
    }

    /**
     * Busca Venda que tenham status 'ABERTA'
     *
     * @return List<Venda> - Os Venda encontrados
     */
    public List<Venda> findAbertas() {
        return em.createQuery("SELECT v FROM vendas v JOIN FETCH v.cliente JOIN FETCH v.funcionario " +
                        "WHERE v.status = 'aberta'", Venda.class)
                .getResultList();
    }

    /**
     * Busca Venda que tenham status 'FINALIZADA'
     *finalizada
     * @return List<Venda> - Os Venda encontrados
     */
    public List<Venda> findFinalizadas() {
        return em.createQuery("SELECT v FROM vendas v JOIN FETCH v.cliente JOIN FETCH v.funcionario " +
                        "WHERE v.status = 'finalizada'", Venda.class)
                .getResultList();
    }
}
