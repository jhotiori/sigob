package org.javapi.sigob.view.v2.framework.components.table;

import java.util.function.Function;

/**
 * Representa uma coluna de entidade.
 *
 * @param <T> Tipo da entidade
 */
public final class EntityTableColumn<T> {

    /**
     * Nome da coluna.
     */
    private final String NAME;

    /**
     * Extrator de valor.
     */
    private final Function<T, Object> EXTRACTOR;

    /**
     * Construtor.
     *
     * @param name      - Nome da coluna
     * @param extractor - Extrator de valor
     */
    public EntityTableColumn(
            String name,
            Function<T, Object> extractor
        ) {
        this.NAME = name;
        this.EXTRACTOR = extractor;
    }

    /**
     * Retorna nome da coluna.
     *
     * @return String - Nome
     */
    public String name() {
        return NAME;
    }

    /**
     * Extrai valor da entidade.
     *
     * @param entity - Entidade
     * @return Object - Valor
     */
    public Object value(T entity) {
        return EXTRACTOR.apply(entity);
    }
}
