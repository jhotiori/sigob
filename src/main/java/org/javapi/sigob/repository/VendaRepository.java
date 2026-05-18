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
                select v
                from vendas v
                join fetch v.cliente
                join fetch v.funcionario
                order by v.id desc
                """, Venda.class)
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
                select v
                from vendas v
                join fetch v.cliente
                join fetch v.funcionario
                where lower(v.cliente.nome) like lower(:nome)
                order by v.id desc
                """, Venda.class)
                .setParameter("nome", nome + "%")
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
                select v
                from vendas v
                join fetch v.cliente
                join fetch v.funcionario
                where lower(v.funcionario.nome) like lower(:nome)
                order by v.id desc
                """, Venda.class)
                .setParameter("nome", nome + "%")
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
            select v
            from vendas v
            join fetch v.cliente
            join fetch v.funcionario
            where cast(v.dataAbertura as date) = :data
            order by v.dataAbertura desc
            """, Venda.class)
                .setParameter("data", data)
                .getResultList();
    }

    /**
     * Busca vendas pela data de fechamento.
     *
     * @param inicio Início do período
     * @param fim Fim do período
     * @return List<Venda> - Lista encontrada
     */
    public List<Venda> findByDataFinalizada(
            LocalDate data
    ) {
        return em.createQuery("""
                select v
                from vendas v
                join fetch v.cliente
                join fetch v.funcionario
                where v.dataFinalizada is not null
                where cast(v.dataFinalizada as date) = :data
                order by v.dataFinalizada desc
                """, Venda.class)
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
            select v
            from vendas v
            join fetch v.cliente
            join fetch v.funcionario
            where cast(v.dataAbertura as date) between :inicio and :fim
            order by v.dataAbertura desc
            """, Venda.class)
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
        return em.createQuery("select v from vendas v join fetch v.cliente join fetch v.funcionario where v.status = 'aberta'", Venda.class)
                .getResultList();
    }

    /**
     * Busca Venda que tenham status 'FINALIZADA'
     *
     * @return List<Venda> - Os Venda encontrados
     */
    public List<Venda> findFinalizadas() {
        return em.createQuery("select v from vendas v join fetch v.cliente join fetch v.funcionario where v.status = 'finalizada'", Venda.class)
                .getResultList();
    }
}
