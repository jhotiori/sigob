package org.javapi.sigob.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig {
    public static void migrate() {
        Flyway flyway = Flyway.configure().dataSource(
            "jdbc:postgresql://localhost:5432/sigob",
            "postgres",
            "postgres"
        ).load();
        flyway.migrate();
    }
}
