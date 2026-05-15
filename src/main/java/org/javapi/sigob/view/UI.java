package org.javapi.sigob.view;

import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javapi.sigob.view.builders.MenuBarBuilder;
import org.javapi.sigob.view.components.ButtonComponent;
import org.javapi.sigob.view.components.CheckBoxComponent;
import org.javapi.sigob.view.components.ComboBoxComponent;
import org.javapi.sigob.view.components.LabelComponent;
import org.javapi.sigob.view.components.MenuBarComponent;
import org.javapi.sigob.view.components.PasswordFieldComponent;
import org.javapi.sigob.view.components.TextAreaComponent;
import org.javapi.sigob.view.components.TextFieldComponent;
import org.javapi.sigob.view.layouts.BorderBuilder;
import org.javapi.sigob.view.layouts.CardBuilder;
import org.javapi.sigob.view.layouts.ColumnBuilder;
import org.javapi.sigob.view.layouts.FlowBuilder;
import org.javapi.sigob.view.layouts.FrameBuilder;
import org.javapi.sigob.view.layouts.GridBuilder;
import org.javapi.sigob.view.layouts.RowBuilder;

/**
 * Fábrica centralizada de componentes visuais.
 */
public final class UI {

    /**
     * Impede instanciação.
     */
    private UI() {

    }

    /**
     * Aplica configuração opcional ao componente.
     *
     * @param component - Componente alvo
     * @param config - Configuração opcional
     * @param <T> - Tipo do componente
     * @return T - Componente configurado
     */
    private static <T> T build(T component, Consumer<T> config) {
        if (config != null) {
            config.accept(component);
        }

        return component;
    }

    /**
     * Cria label vazio.
     *
     * @return LabelComponent - Label criado
     */
    public static LabelComponent label() {
        return label(null, null);
    }

    /**
     * Cria label vazio configurável.
     *
     * @param config - Configuração do label
     * @return LabelComponent - Label criado
     */
    public static LabelComponent label(Consumer<LabelComponent> config) {
        return label(null, config);
    }

    /**
     * Cria label com texto.
     *
     * @param text - Texto do label
     * @return LabelComponent - Label criado
     */
    public static LabelComponent label(String text) {
        return label(text, null);
    }

    /**
     * Cria label configurável com texto.
     *
     * @param text - Texto do label
     * @param config - Configuração do label
     * @return LabelComponent - Label criado
     */
    public static LabelComponent label(String text, Consumer<LabelComponent> config) {
        return build(new LabelComponent(text), config);
    }

    /**
     * Cria botão com texto.
     *
     * @param text - Texto do botão
     * @return ButtonComponent - Botão criado
     */
    public static ButtonComponent button(String text) {
        return button(text, null);
    }

    /**
     * Cria botão configurável com texto.
     *
     * @param text - Texto do botão
     * @param config - Configuração do botão
     * @return ButtonComponent - Botão criado
     */
    public static ButtonComponent button(String text, Consumer<ButtonComponent> config) {
        return build(new ButtonComponent(text), config);
    }

    /**
     * Cria campo de texto.
     *
     * @return TextFieldComponent - Campo criado
     */
    public static TextFieldComponent textField() {
        return textField(null, null);
    }

    /**
     * Cria campo configurável.
     *
     * @param config - Configuração do campo
     * @return TextFieldComponent - Campo criado
     */
    public static TextFieldComponent textField(Consumer<TextFieldComponent> config) {
        return textField(null, config);
    }

    /**
     * Cria campo com texto inicial.
     *
     * @param text - Texto inicial
     * @return TextFieldComponent - Campo criado
     */
    public static TextFieldComponent textField(String text) {
        return textField(text, null);
    }

    /**
     * Cria campo configurável com texto inicial.
     *
     * @param text - Texto inicial
     * @param config - Configuração do campo
     * @return TextFieldComponent - Campo criado
     */
    public static TextFieldComponent textField(String text, Consumer<TextFieldComponent> config) {
        return build(new TextFieldComponent(text), config);
    }

    /**
     * Cria campo de senha.
     *
     * @return PasswordFieldComponent - Campo criado
     */
    public static PasswordFieldComponent passwordField() {
        return passwordField(null);
    }

    /**
     * Cria campo de senha configurável.
     *
     * @param config - Configuração do campo
     * @return PasswordFieldComponent - Campo criado
     */
    public static PasswordFieldComponent passwordField(Consumer<PasswordFieldComponent> config) {
        return build(new PasswordFieldComponent(), config);
    }

    /**
     * Cria área de texto.
     *
     * @return TextAreaComponent - Área criada
     */
    public static TextAreaComponent textArea() {
        return textArea(null, null);
    }

    /**
     * Cria área configurável.
     *
     * @param config - Configuração da área
     * @return TextAreaComponent - Área criada
     */
    public static TextAreaComponent textArea(Consumer<TextAreaComponent> config) {
        return textArea(null, config);
    }

    /**
     * Cria área com texto inicial.
     *
     * @param text - Texto inicial
     * @return TextAreaComponent - Área criada
     */
    public static TextAreaComponent textArea(String text) {
        return textArea(text, null);
    }

    /**
     * Cria área configurável com texto inicial.
     *
     * @param text - Texto inicial
     * @param config - Configuração da área
     * @return TextAreaComponent - Área criada
     */
    public static TextAreaComponent textArea(String text, Consumer<TextAreaComponent> config) {
        return build(new TextAreaComponent(text), config);
    }

    /**
     * Cria ComboBox vazio.
     *
     * @param <T> - Tipo dos itens
     * @return ComboBoxComponent<T> - ComboBox criado
     */
    public static <T> ComboBoxComponent<T> comboBox() {
        return new ComboBoxComponent<>();
    }

    /**
     * Cria ComboBox configurável.
     *
     * @param config - Configuração do ComboBox
     * @param <T> - Tipo dos itens
     * @return ComboBoxComponent<T> - ComboBox criado
     */
    public static <T> ComboBoxComponent<T> comboBox(Consumer<ComboBoxComponent<T>> config) {
        return build(new ComboBoxComponent<>(), config);
    }

    /**
     * Cria ComboBox com itens.
     *
     * @param items - Itens do ComboBox
     * @param <T> - Tipo dos itens
     * @return ComboBoxComponent<T> - ComboBox criado
     */
    @SafeVarargs
    public static <T> ComboBoxComponent<T> comboBox(T... items) {
        return comboBox(null, items);
    }

    /**
     * Cria ComboBox configurável com itens.
     *
     * @param config - Configuração do ComboBox
     * @param items - Itens do ComboBox
     * @param <T> - Tipo dos itens
     * @return ComboBoxComponent<T> - ComboBox criado
     */
    @SafeVarargs
    public static <T> ComboBoxComponent<T> comboBox(Consumer<ComboBoxComponent<T>> config, T... items) {
        return build(new ComboBoxComponent<>(items), config);
    }

    /**
     * Cria CheckBox com texto.
     *
     * @param text - Texto do CheckBox
     * @return CheckBoxComponent - CheckBox criado
     */
    public static CheckBoxComponent checkBox(String text) {
        return checkBox(text, null);
    }

    /**
     * Cria CheckBox configurável com texto.
     *
     * @param text - Texto do CheckBox
     * @param config - Configuração do CheckBox
     * @return CheckBoxComponent - CheckBox criado
     */
    public static CheckBoxComponent checkBox(String text, Consumer<CheckBoxComponent> config) {
        return build(new CheckBoxComponent(text), config);
    }

    /**
     * Cria campo composto com label.
     *
     * @param label - Texto do campo
     * @param component - Componente do campo
     * @return JPanel - Campo criado
     */
    public static JPanel field(String label, JComponent component) {
        return column()
                .add(UI.label(label), component)
                .build();
    }

    /**
     * Cria campo composto com label.
     *
     * @param label - Label do Campo
     * @param component - Componente do campo
     * @return JPanel - Campo criado
     */
    public static JPanel field(JLabel label, JComponent component) {
        return column()
                .add(label, component)
                .build();
    }

    /**
     * Cria builder de barra de menus.
     *
     * @return MenuBarBuilder - Builder criado
     */
    public static MenuBarBuilder menubar() {
        return new MenuBarBuilder();
    }

    /**
     * Cria builder usando barra existente.
     *
     * @param menuBar - Barra existente
     * @return MenuBarBuilder - Builder criado
     */
    public static MenuBarBuilder menubar(MenuBarComponent menuBar) {
        return new MenuBarBuilder(menuBar);
    }

    /**
     * Cria builder de coluna.
     *
     * @return ColumnBuilder - Builder criado
     */
    public static ColumnBuilder column() {
        return new ColumnBuilder();
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     * @return ColumnBuilder - Builder criado
     */
    public static ColumnBuilder column(JPanel panel) {
        return new ColumnBuilder(panel);
    }

    /**
     * Cria builder de linha.
     *
     * @return RowBuilder - Builder criado
     */
    public static RowBuilder row() {
        return new RowBuilder();
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     * @return RowBuilder - Builder criado
     */
    public static RowBuilder row(JPanel panel) {
        return new RowBuilder(panel);
    }

    /**
     * Cria builder BorderLayout.
     *
     * @return BorderBuilder - Builder criado
     */
    public static BorderBuilder border() {
        return new BorderBuilder();
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     * @return BorderBuilder - Builder criado
     */
    public static BorderBuilder border(JPanel panel) {
        return new BorderBuilder(panel);
    }

    /**
     * Cria builder FlowLayout.
     *
     * @return FlowBuilder - Builder criado
     */
    public static FlowBuilder flow() {
        return new FlowBuilder();
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     * @return FlowBuilder - Builder criado
     */
    public static FlowBuilder flow(JPanel panel) {
        return new FlowBuilder(panel);
    }

    /**
     * Cria builder GridLayout.
     *
     * @param rows - Quantidade de linhas
     * @param cols - Quantidade de colunas
     * @return GridBuilder - Builder criado
     */
    public static GridBuilder grid(int rows, int cols) {
        return new GridBuilder(rows, cols);
    }

    /**
     * Cria builder usando painel existente.
     *
     * @param panel - Painel existente
     * @param rows - Quantidade de linhas
     * @param cols - Quantidade de colunas
     * @return GridBuilder - Builder criado
     */
    public static GridBuilder grid(JPanel panel, int rows, int cols) {
        return new GridBuilder(panel, rows, cols);
    }

    /**
     * Cria builder de frame.
     *
     * @return FrameBuilder - Builder criado
     */
    public static FrameBuilder frame() {
        return new FrameBuilder();
    }

    /**
     * Cria builder usando frame existente.
     *
     * @param frame - Frame existente
     * @return FrameBuilder - Builder criado
     */
    public static FrameBuilder frame(JFrame frame) {
        return new FrameBuilder(frame);
    }

    /**
     * Cria builder de cards.
     *
     * @return CardBuilder - Builder criado
     */
    public static CardBuilder cards() {
        return new CardBuilder();
    }

    /**
     * Cria builder de cards usando painel existente.
     *
     * @param panel - Painel existente
     * @return CardBuilder - Builder criado
     */
    public static CardBuilder cards(JPanel panel) {
        return new CardBuilder(panel);
    }
}
