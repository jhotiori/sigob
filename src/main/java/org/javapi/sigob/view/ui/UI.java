package org.javapi.sigob.view.ui;

import java.awt.Image;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import org.javapi.sigob.view.builders.BorderBuilder;
import org.javapi.sigob.view.builders.CardBuilder;
import org.javapi.sigob.view.builders.ColumnBuilder;
import org.javapi.sigob.view.builders.FlowBuilder;
import org.javapi.sigob.view.builders.GridBuilder;
import org.javapi.sigob.view.builders.MenuBarBuilder;
import org.javapi.sigob.view.builders.RowBuilder;
import org.javapi.sigob.view.components.ButtonComponent;
import org.javapi.sigob.view.components.CheckBoxComponent;
import org.javapi.sigob.view.components.ComboBoxComponent;
import org.javapi.sigob.view.components.LabelComponent;
import org.javapi.sigob.view.components.ListComponent;
import org.javapi.sigob.view.components.MenuBarComponent;
import org.javapi.sigob.view.components.MenuButtonComponent;
import org.javapi.sigob.view.components.PasswordFieldComponent;
import org.javapi.sigob.view.components.ScrollComponent;
import org.javapi.sigob.view.components.TableComponent;
import org.javapi.sigob.view.components.TextAreaComponent;
import org.javapi.sigob.view.components.TextFieldComponent;

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
     * Cria ícone escalado.
     *
     * @param path - Caminho do recurso
     * @param width - Largura desejada
     * @param height - Altura desejada
     * @return ImageIcon - Ícone criado
     */
    public static ImageIcon icon(
            String path,
            int width,
            int height
    ) {
        ImageIcon icon = new ImageIcon(
                UI.class.getResource(path)
        );

        Image scaled = icon.getImage().getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(scaled);
    }

    /**
     * Cria ícone escalado.
     *
     * @param path - Caminho do recurso
     * @param size - Tamanho desejado
     * @return ImageIcon - Ícone criado
     */
    public static ImageIcon icon(
            String path,
            int size
    ) {
        return icon(path, size, size);
    }

    /**
     * Cria label vazio.
     *
     * @return LabelComponent - Label criado
     */
    public static LabelComponent label() {
        return new LabelComponent();
    }

    /**
     * Cria label vazio configurável.
     *
     * @param config - Configuração do label
     * @return LabelComponent - Label criado
     */
    public static LabelComponent label(Consumer<LabelComponent> config) {
        return build(new LabelComponent(), config);
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
     * Cria label com ícone.
     *
     * @param icon - Ícone do label
     * @return LabelComponent - Label criado
     */
    public static LabelComponent label(Icon icon) {
        return label(icon, null);
    }

    /**
     * Cria label configurável com ícone.
     *
     * @param icon - Ícone do label
     * @param config - Configuração do label
     * @return LabelComponent - Label criado
     */
    public static LabelComponent label(
            Icon icon,
            Consumer<LabelComponent> config
    ) {
        return build(new LabelComponent(icon), config);
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
     * Cria botão de menu com texto.
     *
     * @param text - Texto do botão
     * @return MenuButtonComponent - Botão de menu criado
     */
    public static MenuButtonComponent menuButton(String text) {
        return new MenuButtonComponent(text);
    }

    /**
     * Cria botão de menu configurável com texto.
     *
     * @param text - Texto do botão
     * @param config - Configuração do botão
     * @return MenuButtonComponent - Botão de menu criado
     */
    public static MenuButtonComponent menuButton(String text, Consumer<MenuButtonComponent> config) {
        return build(new MenuButtonComponent(text), config);
    }

    /**
     * Cria botão de menu configurável.
     *
     * @param config - Configuração do botão
     * @return MenuButtonComponent - Botão de menu criado
     */
    public static MenuButtonComponent menuButton(Consumer<MenuButtonComponent> config) {
        return build(new MenuButtonComponent(), config);
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
    public static <T> ComboBoxComponent<T> comboBox(
            Consumer<ComboBoxComponent<T>> config,
            T... items
    ) {
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
    public static CheckBoxComponent checkBox(
            String text,
            Consumer<CheckBoxComponent> config
    ) {
        return build(new CheckBoxComponent(text), config);
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
     * Cria lista vazia.
     *
     * @param <T> - Tipo dos itens
     * @return ListComponent<T> - Lista criada
     */
    public static <T> ListComponent<T> list() {
        return new ListComponent<>();
    }

    /**
     * Cria painel de rolagem.
     *
     * @param component - Componente alvo
     * @return ScrollComponent - Scroll criado
     */
    public static ScrollComponent scroll(JComponent component) {
        return scroll(component, null);
    }

    /**
     * Cria painel de rolagem configurável.
     *
     * @param component - Componente alvo
     * @param config - Configuração do scroll
     * @return ScrollComponent - Scroll criado
     */
    public static ScrollComponent scroll(
            JComponent component,
            Consumer<ScrollComponent> config
    ) {
        return build(new ScrollComponent(component), config);
    }

    /**
     * Cria tabela vazia.
     *
     * @return TableComponent - Tabela criada
     */
    public static TableComponent table() {
        return new TableComponent();
    }

    /**
     * Cria tabela configurável.
     *
     * @param config - Configuração da tabela
     * @return TableComponent - Tabela criada
     */
    public static TableComponent table(Consumer<TableComponent> config) {
        return build(new TableComponent(), config);
    }

    /**
     * Cria tabela com modelo.
     *
     * @param model - Modelo da tabela
     * @return TableComponent - Tabela criada
     */
    public static TableComponent table(TableModel model) {
        return new TableComponent(model);
    }

    /**
     * Cria tabela configurável com modelo.
     *
     * @param model - Modelo da tabela
     * @param config - Configuração da tabela
     * @return TableComponent - Tabela criada
     */
    public static TableComponent table(
            TableModel model,
            Consumer<TableComponent> config
    ) {
        return build(new TableComponent(model), config);
    }
}
