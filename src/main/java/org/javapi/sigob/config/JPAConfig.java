package org.javapi.sigob.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {

    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("sigob");
        }
        return factory.createEntityManager();
    }

    public static void closeEntityFactory() {
        if (factory != null) {
            factory.close();
            factory = null;
        }
    }
}
