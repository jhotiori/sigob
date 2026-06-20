package org.javapi.sigob.view.v2.framework.layouts;

import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.components.PasswordFieldComponent;
import org.javapi.sigob.view.v2.framework.components.TextFieldComponent;
import org.javapi.sigob.view.v2.framework.components.entity.EntityCheckList;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.components.entity.EntityContainer;
import org.javapi.sigob.view.v2.framework.layouts.base.LayoutBuilder;
import org.javapi.sigob.view.v2.framework.styles.Fonts;
import org.javapi.sigob.view.v2.framework.styles.Spacing;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIUtils;

/**
 * Construtor de formulários verticais.
 */
public class FormBuilder {

    /**
     * Layout interno.
     */
    private final ColumnBuilder LAYOUT;

    /**
     * Campos do formulário.
     */
    private final Map<String, JComponent> FIELDS = new LinkedHashMap<>();

    /**
     * Cria construtor.
     */
    public FormBuilder() {
        this.LAYOUT = UILayouts.column();
    }

    /**
     * Adiciona seção.
     *
     * @param title - Título da seção
     * @return FormBuilder - Própria instância
     */
    public FormBuilder section(String title) {
        LAYOUT.add(
                UI.label(
                        title,
                        label -> label.setFont(
                                Fonts.SUBTITLE_BOLD
                        )
                )
        );

        LAYOUT.gap(Spacing.SM);
        return this;
    }

    /**
     * Adiciona seção.
     *
     * @param title - Título da seção
     * @param config - Configuração da label
     * @return FormBuilder - Própria instância
     */
    public FormBuilder section(
            String title,
            Consumer<LabelComponent> config
    ) {
        LabelComponent label = UI.label(title);

        if (config != null) {
            config.accept(label);
        }

        LAYOUT.add(label);
        LAYOUT.gap(Spacing.SM);

        return this;
    }

    /**
     * Adiciona campo.
     *
     * @param label - Texto da label
     * @param component - Componente do campo
     * @return FormBuilder - Própria instância
     */
    public FormBuilder field(
            String label,
            String identifier,
            JComponent component
    ) {
        FIELDS.put(identifier, component);
        LAYOUT.add(
                UILayouts.column()
                        .add(UI.label(label))
                        .add(component)
                        .build()
        );
        LAYOUT.gap(Spacing.XS);

        return this;
    }

    /**
     * Adiciona campo.
     *
     * @param label - Texto da label
     * @param component - Componente do campo
     * @param config - Configuração da label
     * @return FormBuilder - Própria instância
     */
    public FormBuilder field(
            String label,
            String identifier,
            JComponent component,
            Consumer<LabelComponent> config
    ) {
        LabelComponent fieldLabel = UI.label(label);

        if (config != null) {
            config.accept(fieldLabel);
        }

        FIELDS.put(identifier, component);
        LAYOUT.add(
                UILayouts.column()
                        .add(fieldLabel)
                        .add(component)
                        .build()
        );
        LAYOUT.gap(Spacing.XS);

        return this;
    }

    /**
     * Adiciona campo baseado em layout.
     *
     * @param label - Texto da label
     * @param layout - Layout do campo
     * @return FormBuilder - Própria instância
     */
    public FormBuilder field(
            String label,
            String identifier,
            LayoutBuilder<?> layout
    ) {
        return field(
                label,
                identifier,
                layout.build()
        );
    }

    /**
     * Adiciona campo baseado em layout.
     *
     * @param label - Texto da label
     * @param layout - Layout do campo
     * @param config - Configuração da label
     * @return FormBuilder - Própria instância
     */
    public FormBuilder field(
            String label,
            String identifier,
            LayoutBuilder<?> layout,
            Consumer<LabelComponent> config
    ) {
        return field(
                label,
                identifier,
                layout.build(),
                config
        );
    }

    /**
     * Adiciona componente.
     *
     * @param component - Componente
     * @return FormBuilder - Própria instância
     */
    public FormBuilder add(Component component) {
        LAYOUT.add(component);

        return this;
    }

    /**
     * Adiciona layout.
     *
     * @param layout - Layout
     * @return FormBuilder - Própria instância
     */
    public FormBuilder add(
            LayoutBuilder<?> layout
    ) {
        LAYOUT.add(layout);

        return this;
    }

    /**
     * Limpa campos.
     *
     * @return FormBuilder - Própria instância
     */
    public FormBuilder clear() {
        FIELDS.forEach((key, component) -> UIUtils.clearField(component));
        return this;
    }

    /**
     * Adiciona espaço.
     *
     * @param size - Tamanho do espaço
     * @return FormBuilder - Própria instância
     */
    public FormBuilder gap(int size) {
        LAYOUT.gap(size);

        return this;
    }

    /**
     * Define valor do campo.
     *
     * @param identifier - Identificador do campo
     * @param value - Valor do campo
     */
    public void set(String identifier, String value) {
        JComponent component = FIELDS.get(identifier);

        if (component instanceof TextFieldComponent textField) {
            textField.setText(value);
        } else if (component instanceof PasswordFieldComponent passwordField) {
            passwordField.setText(value);
        }
    }

    /**
     * Define valor do campo de entidade.
     *
     * @param identifier - Identificador do campo
     * @param value - Valor do campo
     */
    @SuppressWarnings("unchecked")
    public <T> void setEntity(
            String identifier,
            T value
    ) {
        JComponent component = FIELDS.get(identifier);

        if (component instanceof EntityContainer<?> container) {
            ((EntityContainer<T>) container).setSelectedEntity(value);
        }
    }

    /**
     * Retorna valor do campo.
     *
     * @param identifier - Identificador do campo
     * @return String - Valor do campo
     */
    public String get(String identifier) {
        JComponent component = FIELDS.get(identifier);

        if (component instanceof TextFieldComponent textField) {
            return textField.getText();
        } else if (component instanceof PasswordFieldComponent passwordField) {
            return new String(passwordField.getPassword());
        }

        return null;
    }

    /**
     * Retorna valor do campo de entidade.
     *
     * @param identifier - Identificador do campo
     * @return String - Valor do campo
     */
    @SuppressWarnings("unchecked")
    public <T> T getEntity(String identifier) {
        JComponent component = FIELDS.get(identifier);

        if (component instanceof EntityComboBox<?> comboBox) {
            return (T) comboBox.getSelectedEntity();
        }

        return null;
    }

    /**
     * Retorna componente.
     *
     * @param identifier - Identificador do campo
     * @return <T> - Componente
     */
    @SuppressWarnings("unchecked")
    public <T> T getComponent(String identifier) {
        return (T) FIELDS.get(identifier);
    }

    /**
    * Retorna entidades selecionadas.
    *
    * @param identifier - Identificador
    * @return List<T> - Entidades selecionadas
    */
    @SuppressWarnings("unchecked")
    public <T> List<T> getEntities(String identifier) {
        JComponent component = FIELDS.get(identifier);

        if (component instanceof EntityCheckList<?> checkList) {
            return (List<T>) checkList.getSelectedEntities();
        }

        return List.of();
    }


    /**
     * Retorna painel construído.
     *
     * @return PanelComponent - Painel do formulário
     */
    public JPanel build() {
        return LAYOUT.build();
    }

}
