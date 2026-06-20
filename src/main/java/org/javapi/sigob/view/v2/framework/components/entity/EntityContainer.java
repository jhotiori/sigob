package org.javapi.sigob.view.v2.framework.components.entity;

public interface EntityContainer<T> {

    /**
     * Define entidades.
     *
     * @param entities - Entidades
     */
    void setEntities(Iterable<T> entities);

    /**
     * Define entidade selecionada.
     *
     * @param entity - Entidade
     */
    void setSelectedEntity(T entity);

}
