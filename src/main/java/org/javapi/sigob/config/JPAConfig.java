package org.javapi.sigob.config;

import org.hibernate.cfg.Configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class JPAConfig {
    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory = new Configuration()
                .configure()
                .buildSessionFactory();
        }
        return factory.createEntityManager();
    }

    public static void closeEntityManager() {
        if (factory != null) {
            factory.close();
            factory = null;
        }
    }
}
