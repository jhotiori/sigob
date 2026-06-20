package org.javapi.sigob.model.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Responsável pela criação e gerenciamento da fábrica de EntityManager.
 */
public final class EntityManagerProvider {
    /**
     * Fábrica de EntityManager
     *
     * @see EntityManagerFactory
     */
    private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("sigob");

    /**
     * Construtor privado para evitar criação de instancias.
     */
    private EntityManagerProvider() {

    }

    /**
     * Cria uma nova instância de EntityManager.
     *
     * @return EntityManager - Nova instância de EntityManager
     */
    public static EntityManager get() {
        if (FACTORY == null) {
            FACTORY = Persistence.createEntityManagerFactory("sigob");
        }

        return FACTORY.createEntityManager();
    }

    /**
     * Encerra a fábrica de EntityManager.
     */
    public static void shutdown() {
        if (FACTORY != null) {
            FACTORY.close();
            FACTORY = null;
        }
    }
}
