package org.javapi.sigob.view.v2.framework.ui;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JComponent;
import javax.swing.table.TableModel;

import org.javapi.sigob.view.v2.framework.components.ButtonComponent;
import org.javapi.sigob.view.v2.framework.components.CheckBoxComponent;
import org.javapi.sigob.view.v2.framework.components.ComboBoxComponent;
import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.components.ListComponent;
import org.javapi.sigob.view.v2.framework.components.MenuComponent;
import org.javapi.sigob.view.v2.framework.components.MenuItemComponent;
import org.javapi.sigob.view.v2.framework.components.PanelComponent;
import org.javapi.sigob.view.v2.framework.components.PasswordFieldComponent;
import org.javapi.sigob.view.v2.framework.components.ScrollPaneComponent;
import org.javapi.sigob.view.v2.framework.components.SeparatorComponent;
import org.javapi.sigob.view.v2.framework.components.TableComponent;
import org.javapi.sigob.view.v2.framework.components.TextAreaComponent;
import org.javapi.sigob.view.v2.framework.components.TextFieldComponent;
import org.javapi.sigob.view.v2.framework.components.entity.EntityCheckList;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.layouts.base.LayoutBuilder;
import org.javapi.sigob.view.v2.framework.layouts.menu.MenuBarBuilder;

/**
 * Utilitário principal de criação de componentes e layouts.
 */
public final class UI {

    private UI() {
    }

    /**
     * Aplica configuração ao objeto.
     *
     * @param object - Objeto alvo
     * @param config - Configuração
     * @return T - Objeto configurado
     */
    private static <T> T configure(
            T object,
            Consumer<T> config
    ) {
        if (config != null) {
            config.accept(object);
        }

        return object;
    }

    /**
     * Cria botão.
     *
     * @return ButtonComponent - Botão criado
     */
    public static ButtonComponent button() {
        return new ButtonComponent();
    }

    /**
     * Cria botão.
     *
     * @param config - Configuração
     * @return ButtonComponent - Botão criado
     */
    public static ButtonComponent button(
            Consumer<ButtonComponent> config
    ) {
        return configure(
                new ButtonComponent(),
                config
        );
    }

    /**
     * Cria botão.
     *
     * @param text - Texto do botão
     * @return ButtonComponent - Botão criado
     */
    public static ButtonComponent button(
            String text
    ) {
        return new ButtonComponent(text);
    }

    /**
     * Cria botão.
     *
     * @param text - Texto do botão
     * @param config - Configuração
     * @return ButtonComponent - Botão criado
     */
    public static ButtonComponent button(
            String text,
            Consumer<ButtonComponent> config
    ) {
        return configure(
                new ButtonComponent(text),
                config
        );
    }

    /**
     * Cria label.
     *
     * @return LabelComponent - Label criada
     */
    public static LabelComponent label() {
        return new LabelComponent();
    }

    /**
     * Cria label.
     *
     * @param config - Configuração
     * @return LabelComponent - Label criada
     */
    public static LabelComponent label(
            Consumer<LabelComponent> config
    ) {
        return configure(
                new LabelComponent(),
                config
        );
    }

    /**
     * Cria label.
     *
     * @param text - Texto da label
     * @return LabelComponent - Label criada
     */
    public static LabelComponent label(
            String text
    ) {
        return new LabelComponent(text);
    }

    /**
     * Cria label.
     *
     * @param text - Texto da label
     * @param config - Configuração
     * @return LabelComponent - Label criada
     */
    public static LabelComponent label(
            String text,
            Consumer<LabelComponent> config
    ) {
        return configure(
                new LabelComponent(text),
                config
        );
    }

    /**
     * Cria campo de texto.
     *
     * @return TextFieldComponent - Campo criado
     */
    public static TextFieldComponent textField() {
        return new TextFieldComponent();
    }

    /**
     * Cria campo de texto.
     *
     * @param config - Configuração
     * @return TextFieldComponent - Campo criado
     */
    public static TextFieldComponent textField(
            Consumer<TextFieldComponent> config
    ) {
        return configure(
                new TextFieldComponent(),
                config
        );
    }

    /**
     * Cria campo de texto.
     *
     * @param placeholder - Placeholder do campo
     * @return TextFieldComponent - Campo criado
     */
    public static TextFieldComponent textField(
        String placeholder
    ) {
        return new TextFieldComponent(placeholder);
    }

    /**
     * Cria campo de texto.
     *
     * @param placeholder - Placeholder do campo
     * @param config - Configuração
     * @return TextFieldComponent - Campo criado
     */
    public static TextFieldComponent textField(
            String placeholder,
            Consumer<TextFieldComponent> config
    ) {
        return configure(
                new TextFieldComponent(placeholder),
                config
        );
    }

    /**
     * Cria campo de senha.
     *
     * @return PasswordFieldComponent - Campo criado
     */
    public static PasswordFieldComponent passwordField() {
        return new PasswordFieldComponent();
    }

    /**
     * Cria campo de senha.
     *
     * @param config - Configuração
     * @return PasswordFieldComponent - Campo criado
     */
    public static PasswordFieldComponent passwordField(
            Consumer<PasswordFieldComponent> config
    ) {
        return configure(
                new PasswordFieldComponent(),
                config
        );
    }

    /**
     * Cria área de texto.
     *
     * @return TextAreaComponent - Área criada
     */
    public static TextAreaComponent area() {
        return new TextAreaComponent();
    }

    /**
     * Cria área de texto.
     *
     * @param config - Configuração
     * @return TextAreaComponent - Área criada
     */
    public static TextAreaComponent area(
            Consumer<TextAreaComponent> config
    ) {
        return configure(
                new TextAreaComponent(),
                config
        );
    }

    /**
     * Cria checkbox.
     *
     * @return CheckBoxComponent - Checkbox criada
     */
    public static CheckBoxComponent checkbox() {
        return new CheckBoxComponent();
    }

    /**
     * Cria checkbox.
     *
     * @param config - Configuração
     * @return CheckBoxComponent - Checkbox criada
     */
    public static CheckBoxComponent checkbox(
            Consumer<CheckBoxComponent> config
    ) {
        return configure(
                new CheckBoxComponent(),
                config
        );
    }

    /**
     * Cria checkbox.
     *
     * @param text - Texto da checkbox
     * @return CheckBoxComponent - Checkbox criada
     */
    public static CheckBoxComponent checkbox(
            String text
    ) {
        return new CheckBoxComponent(text);
    }

    /**
     * Cria checkbox.
     *
     * @param text - Texto da checkbox
     * @param config - Configuração
     * @return CheckBoxComponent - Checkbox criada
     */
    public static CheckBoxComponent checkbox(
            String text,
            Consumer<CheckBoxComponent> config
    ) {
        return configure(
                new CheckBoxComponent(text),
                config
        );
    }

    /**
     * Cria ComboBox vazio.
     *
     * @param <T> - Tipo dos itens
     * @return ComboBoxComponent<T> - ComboBox criada
     */
    public static <T> ComboBoxComponent<T> combobox() {
        return new ComboBoxComponent<>();
    }

    /**
     * Cria ComboBox vazio.
     *
     * @param <T> - Tipo dos itens
     * @param config - Configuração
     * @return ComboBoxComponent<T> - ComboBox criada
     */
    public static <T> ComboBoxComponent<T> combobox(
            Consumer<ComboBoxComponent<T>> config
    ) {
        return configure(
                new ComboBoxComponent<>(),
                config
        );
    }

    /**
     * Cria ComboBox com itens.
     *
     * @param <T> - Tipo dos itens
     * @param items - Itens da ComboBox
     * @return ComboBoxComponent<T> - ComboBox criada
     */
    @SafeVarargs
    public static <T> ComboBoxComponent<T> combobox(
            T... items
    ) {
        return new ComboBoxComponent<>(items);
    }

    /**
     * Cria ComboBox com itens.
     *
     * @param <T> - Tipo dos itens
     * @param items - Itens da ComboBox
     * @param config - Configuração
     * @return ComboBoxComponent<T> - ComboBox criada
     */
    public static <T> ComboBoxComponent<T> combobox(
            T[] items,
            Consumer<ComboBoxComponent<T>> config
    ) {
        return configure(
                new ComboBoxComponent<>(items),
                config
        );
    }

    /**
     * Cria ComboBox de entidades.
     *
     * @param <T> - Tipo da entidade
     * @param formatter - Formatador em função
     * @return EntityComboBox<T> - ComboBox criada
     */
    public static <T> EntityComboBox<T> entityComboBox(Function<T, String> formatter) {
        return new EntityComboBox<>(formatter);
    }

    /**
     * Cria lista de seleção múltipla de entidades.
     *
     * @param <T> - Tipo da entidade
     * @param formatter - Formatador em função
     * @return EntityCheckList<T> - Lista criada
     */
    public static <T> EntityCheckList<T> entityCheckList(Function<T, String> formatter) {
        return new EntityCheckList<>(formatter);
    }

    /**
     * Cria tabela.
     *
     * @return TableComponent - Tabela criada
     */
    public static TableComponent table() {
        return new TableComponent();
    }

    /**
     * Cria tabela.
     *
     * @param config - Configuração
     * @return TableComponent - Tabela criada
     */
    public static TableComponent table(
            Consumer<TableComponent> config
    ) {
        return configure(
                new TableComponent(),
                config
        );
    }

    /**
     * Cria tabela.
     *
     * @param model - Modelo da tabela
     * @return TableComponent - Tabela criada
     */
    public static <T> TableComponent table(
        BaseEntityTableModel<T> model
    ) {
        TableComponent table = new TableComponent();
        table.setModel(model);
        return table;
    }

    /**
     * Cria lista.
     *
     * @param <T> - Tipo dos itens
     * @return ListComponent<T> - Lista criada
     */
    public static <T> ListComponent<T> list() {
        return new ListComponent<>();
    }

    /**
     * Cria lista.
     *
     * @param <T> - Tipo dos itens
     * @param config - Configuração
     * @return ListComponent<T> - Lista criada
     */
    public static <T> ListComponent<T> list(
            Consumer<ListComponent<T>> config
    ) {
        return configure(
                new ListComponent<>(),
                config
        );
    }

    /**
     * Cria painel.
     *
     * @return PanelComponent - Painel criado
     */
    public static PanelComponent panel() {
        return new PanelComponent();
    }

    /**
     * Cria separador.
     *
     * @return SeparatorComponent - Separador criado
     */
    public static SeparatorComponent separator() {
        return new SeparatorComponent();
    }

    /**
     * Cria painel.
     *
     * @param config - Configuração
     * @return PanelComponent - Painel criado
     */
    public static PanelComponent panel(
            Consumer<PanelComponent> config
    ) {
        return configure(
                new PanelComponent(),
                config
        );
    }

    /**
     * Cria ScrollPane vazio.
     *
     * @return ScrollPaneComponent - ScrollPane criado
     */
    public static ScrollPaneComponent scroll() {
        return new ScrollPaneComponent();
    }

    /**
     * Cria ScrollPane com componente.
     *
     * @param component - Componente interno
     * @return ScrollPaneComponent - ScrollPane criado
     */
    public static ScrollPaneComponent scroll(
            JComponent component
    ) {
        return new ScrollPaneComponent(component);
    }

    /**
     * Cria ScrollPane com componente.
     *
     * @param component - Componente interno
     * @param config - Configuração
     * @return ScrollPaneComponent - ScrollPane criado
     */
    public static ScrollPaneComponent scroll(
            JComponent component,
            Consumer<ScrollPaneComponent> config
    ) {
        return configure(
                new ScrollPaneComponent(component),
                config
        );
    }

    /**
     * Cria ScrollPane a partir de layout.
     *
     * @param layout - Layout a ser encapsulado
     * @return ScrollPaneComponent - ScrollPane criado
     */
    public static ScrollPaneComponent scroll(
            LayoutBuilder<?> layout
    ) {
        return new ScrollPaneComponent(
                layout.build()
        );
    }

    /**
     * Cria ScrollPane a partir de layout.
     *
     * @param layout - Layout a ser encapsulado
     * @param config - Configuração
     * @return ScrollPaneComponent - ScrollPane criado
     */
    public static ScrollPaneComponent scroll(
            LayoutBuilder<?> layout,
            Consumer<ScrollPaneComponent> config
    ) {
        return configure(
                new ScrollPaneComponent(
                        layout.build()
                ),
                config
        );
    }

    /**
     * Cria barra de menu.
     *
     * @return MenuBarBuilder - Barra de menu criada
     */
    public static MenuBarBuilder menubar() {
        return new MenuBarBuilder();
    }

    /**
     * Cria menu.
     *
     * @return MenuComponent - Menu criado
     */
    public static MenuComponent menu() {
        return new MenuComponent();
    }

    /**
     * Cria menu.
     *
     * @param text - Texto do menu
     * @return MenuComponent - Menu criado
     */
    public static MenuComponent menu(String text) {
        return new MenuComponent(text);
    }

    /**
     * Cria item de menu.
     *
     * @return MenuItemComponent - Item de menu criado
     */
    public static MenuItemComponent menuItem() {
        return new MenuItemComponent();
    }

    /**
     * Cria item de menu.
     *
     * @param text - Texto do item de menu
     * @return MenuItemComponent - Item de menu criado
     */
    public static MenuItemComponent menuItem(String text) {
        return new MenuItemComponent(text);
    }

}
