package org.javapi.sigob.model.persistence;

import org.flywaydb.core.Flyway;
import org.javapi.sigob.config.DatabaseConfig;

/**
 * Responsável pela execução das migrações do banco de dados.
 */
public final class DatabaseMigrator {
    /**
     * Indica se as migrações foram executadas.
     */
    private static Boolean HAS_MIGRATED = false;

    /**
     * Construtor privado para evitar instanciamento.
     */
    private DatabaseMigrator() {
        
    }

    /**
     * Cria uma nova instância do Flyway.
     *
     * @return Flyway - Nova instância de Flyway
     */
    private static Flyway create() {
        return Flyway.configure()
            .dataSource(
                DatabaseConfig.URL,
                DatabaseConfig.USERNAME,
                DatabaseConfig.PASSWORD
            )
            .baselineOnMigrate(true)
            .load();
    }

    /**
     * Executa as migrações pendentes do banco de dados.
     * Após executado com sucesso, não será possível migrar novamente.
     */
    public static void migrate() {
        if (HAS_MIGRATED) return;

        Flyway flyway = create();
        flyway.repair();
        flyway.migrate();
        HAS_MIGRATED = true;
    }
}
