package org.javapi.sigob.config;

/**
 * Configuração do banco de dados.
 */
public final class DatabaseConfig {
    /**
     * Host do banco de dados
     */
    public static final String HOST = "localhost";

    /**
     * Porta do banco de dados
     */
    public static final Integer PORT = 5432;

    /**
     * Nome do banco de dados
     */
    public static final String DATABASE = "sigob";

    /**
     * Usuário default do banco de dados
     */
    public static final String USERNAME = "postgres";

    /**
     * Senha default do banco de dados
     */
    public static final String PASSWORD = "postgres";

    /**
     * URL do banco de dados
     */
    public static final String URL = "jdbc:postgresql://%s:%d/%s".formatted(
        HOST,
        PORT,
        DATABASE
    );
}
