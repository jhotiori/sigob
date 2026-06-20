package org.javapi.sigob.model.persistence;

import java.util.function.Consumer;
import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Utilitário para execução de operações de leitura e escrita no banco de dados.
 */
public final class Database {

    /**
     * Construtor privado para evitar instanciamento.
     */
    private Database() {

    }

    /**
     * Executa uma operação de escrita dentro de uma transação.
     *
     * @param action - Função que recebe o EntityManager e executa a operação
     * @return T - Resultado da operação
     */
    public static <T> T write(Function<EntityManager, T> action) {
        EntityManager manager = EntityManagerProvider.get();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();

            T result = action.apply(manager);

            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        } finally {
            manager.close();
        }
    }

    /**
     * Executa uma operação de escrita sem retorno dentro de uma transação.
     *
     * @param action - Consumidor que recebe o EntityManager
     */
    public static void write(Consumer<EntityManager> action) {
        write(em -> {
            action.accept(em);
            return null;
        });
    }

    /**
     * Executa uma operação de escrita sem retorno utilizando um repositório.
     *
     * @param repositoryFactory - Função responsável por criar o repositório
     * @param action - Consumidor que executa a operação utilizando o
     * repositório
     */
    public static <R> void write(
            Function<EntityManager, R> repositoryFactory,
            Consumer<R> action
    ) {
        write(em -> {
            R repository = repositoryFactory.apply(em);
            action.accept(repository);
        });
    }

    /**
     * Executa uma operação de leitura sem transação.
     *
     * @param action - Função que recebe o EntityManager e executa a consulta
     * @return T - Resultado da consulta
     */
    public static <T> T read(Function<EntityManager, T> action) {
        try (EntityManager manager = EntityManagerProvider.get()) {
            return action.apply(manager);
        }
    }

    /**
     * Executa uma operação de leitura utilizando um repositório.
     *
     * @param repositoryFactory - Função responsável por criar o repositório
     * @param action - Função que executa a consulta utilizando o repositório
     * @return T - Resultado da consulta
     */
    public static <R, T> T read(
            Function<EntityManager, R> repositoryFactory,
            Function<R, T> action
    ) {
        return read(em -> {
            R repository = repositoryFactory.apply(em);
            return action.apply(repository);
        });
    }
}
