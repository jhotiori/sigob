package org.javapi.sigob.view.v2.framework.components.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.components.CheckBoxComponent;

/**
 * Lista de seleção múltipla de entidades.
 *
 * @param <T> - Tipo da entidade
 */
public class EntityCheckList<T> extends JPanel implements EntityContainer<T> {

    /**
     * Entidades registradas.
     */
    private final Map<CheckBoxComponent, T> ENTITIES = new LinkedHashMap<>();

    /**
     * Formatador de texto.
     */
    private final Function<T, String> FORMATTER;

    /**
     * Construtor.
     *
     * @param formatter - Formatador de entidade
     */
    public EntityCheckList(Function<T, String> formatter) {
        this.FORMATTER = formatter;

        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS
                    )
                    );
    }

    /**
     * Define entidades exibidas.
     *
     * @param entities - Entidades
     */
    public void setEntities(Iterable<T> entities) {
        clearEntities();

        entities.forEach(entity -> {
            CheckBoxComponent checkBox = new CheckBoxComponent(FORMATTER.apply(entity));
            ENTITIES.put(checkBox, entity);
            add(checkBox);
        });

        revalidate();
        repaint();
    }

    /**
     * Limpa entidades.
     */
    public void clearEntities() {
        ENTITIES.clear();
        removeAll();
    }

    /**
     * Define entidade selecionada.
     *
     * @param entity - Entidade
     */
    public void setSelectedEntity(T entity) {

    }

    /**
     * Define entidades selecionadas.
     *
     * @param entities - Entidades
     */
    public void setSelectedEntities(Iterable<T> entities) {
        clearSelection();
        
        entities.forEach(entity -> {
            ENTITIES.keySet().forEach(checkBox -> {
                if (entity.equals(ENTITIES.get(checkBox))) {
                    checkBox.setSelected(true);
                }
            });
        });
    }

    /**
     * Retorna entidades selecionadas.
     *
     * @return List<T> - Entidades selecionadas
     */
    public List<T> getSelectedEntities() {
        List<T> selected = new ArrayList<>();

        ENTITIES.forEach((checkBox, entity) -> {
            if (checkBox.isSelected()) {
                selected.add(entity);
            }
        });

        return selected;
    }

    /**
     * Remove seleções atuais.
     */
    public void clearSelection() {
        ENTITIES.keySet().forEach(checkBox -> checkBox.setSelected(false));
    }
}
