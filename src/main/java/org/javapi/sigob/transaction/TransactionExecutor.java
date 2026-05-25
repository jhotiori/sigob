package org.javapi.sigob.transaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.javapi.sigob.config.JPAConfig;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Executor transacional
 */
public final class TransactionExecutor {

    /**
     * Executa uma operação que retorna valor dentro de uma transação.
     *
     * @param action função que recebe o EntityManager
     * @return T - Resultado da operação
     */
    public static <T> T execute(Function<EntityManager, T> action) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            T result = action.apply(em);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Executa uma operação sem retorno dentro de uma transação.
     *
     * @param action Consumidor que recebe o EntityManager
     */
    public static void executeVoid(Consumer<EntityManager> action) {
        execute(em -> {
            action.accept(em);
            return null;
        });
    }

    /**
     * Executa uma operação que retorna valor sem transação.
     *
     * @param action Função que recebe o EntityManager
     * @return T - Resultado da operação
     */
    public static <T> T query(Function<EntityManager, T> action) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return action.apply(em);
        } finally {
            em.close();
        }
    }

    /**
     * Executa uma operação sem retorno sem transação.
     *
     * @param action Consumidor que recebe o EntityManager
     */
    public static void queryVoid(Consumer<EntityManager> action) {
        query(em -> {
            action.accept(em);
            return null;
        });
    }
}
