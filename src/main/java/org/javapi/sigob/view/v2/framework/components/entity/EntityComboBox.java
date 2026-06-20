package org.javapi.sigob.view.v2.framework.components.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.javapi.sigob.view.v2.framework.components.ComboBoxComponent;

public class EntityComboBox<T> extends ComboBoxComponent<String> implements EntityContainer<T> {
    /**
     * Mapeamento de entidades com seus respectivos textos.
     *
     * @see LinkedHashMap
     */
    private final Map<String, T> ENTITIES = new LinkedHashMap<>();

    /**
     * Formata entidade.
     *
     * @see Function
     */
    private final Function<T, String> FORMATTER;

    /**
     * Construtor da ComboBox de Entidades.
     *
     * @return EntityComboBox - ComboBox de Entidades
     */
    public EntityComboBox(Function<T, String> formatter) {
        super();
        this.FORMATTER = formatter;
    }

    /**
     * Define entidades a serem exibidas.
     *
     * @param entities - Entidades
     */
    @Override
    public void setEntities(Iterable<T> entities) {
        clearEntities();

        entities.forEach(entity -> {
            String text = FORMATTER.apply(entity);
            ENTITIES.put(text, entity);
            addItem(text);
        });
    }

    /**
     * Limpa entidades.
     */
    public void clearEntities() {
        ENTITIES.clear();
        removeAllItems();
    }

    /**
     * Define entidade selecionada.
     *
     * @param entity - Entidade
     */
    @Override
    public void setSelectedEntity(T entity) {
        if (entity == null) {
            setSelectedItem(null);
            return;
        }

        String text = FORMATTER.apply(entity);

        if (!ENTITIES.containsKey(text)) {
            return;
        }

        setSelectedItem(text);
    }

    /**
     * Obtem entidade selecionada.
     *
     * @return T - Entidade selecionada
     */
    public T getSelectedEntity() {
        return ENTITIES.get(
            (String) getSelectedItem()
        );
    }
}
