package org.javapi.sigob.view.v2.screens.cadastro.base;

import java.util.List;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.components.ButtonComponent;
import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.layouts.ColumnBuilder;
import org.javapi.sigob.view.v2.framework.layouts.FormBuilder;
import org.javapi.sigob.view.v2.framework.styles.Spacing;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.framework.ui.UIForms;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIText;

public abstract class BaseCadastroScreen extends BaseScreen {

    /**
     * Botão de Criar.
     *
     * @see ButtonComponent
     */
    private final ButtonComponent CRIAR_BUTTON = UI.button("Criar");

    /**
     * Botão de Limpar.
     *
     * @see ButtonComponent
     */
    private final ButtonComponent LIMPAR_BUTTON = UI.button("Limpar");

    /**
     * Botão de Voltar.
     *
     * @see ButtonComponent
     */
    private final ButtonComponent VOLTAR_BUTTON = UI.button("Voltar");

    /**
     * Formulário da tela.
     *
     * @see FormBuilder
     */
    private final FormBuilder FORM = UIForms.create();

    /**
     * Label do titulo da tela.
     *
     * @see LabelComponent
     */
    private final LabelComponent TITLE_LABEL;

    /**
     * Label da descricao da tela.
     *
     * @see LabelComponent
     */
    private final LabelComponent DESCRIPTION_LABEL;

    /**
     * Construtor da tela base de cadastro.
     *
     * @param id - Id da tela
     */
    public BaseCadastroScreen(String id, String title) {
        super(id);
        this.TITLE_LABEL = UIText.header(title);
        this.DESCRIPTION_LABEL = UIText.subtitle(
            descriptionToHtml(description())
        );
    }

    /**
     * Obtem descricao da tela.
     *
     * @return String - Descricao
     */
    protected String description() {
        return "Nenhuma descrição foi providenciada para esta tela.";
    }

    /**
     * Converte texto para HTML.
     *
     * @param text - Texto a ser convertido
     * @return String - Texto convertido
     */
    private String descriptionToHtml(String text) {
        return "<html>%s</html>".formatted(
            text.replace("\n", "<br>")
        );
    }

    /**
     * Registra evento de criar.
     *
     * @param action - Ação executada
     */
    public void onCriar(Runnable action) {
        UIEvents.bind(CRIAR_BUTTON).onClick(action);
    }

    /**
     * Registra evento de limpar.
     *
     * @param action - Ação executada
     */
    public void onLimpar(Runnable action) {
        UIEvents.bind(LIMPAR_BUTTON).onClick(action);
    }

    /**
     * Registra evento de voltar.
     *
     * @param action - Ação executada
     */
    public void onVoltar(Runnable action) {
        UIEvents.bind(VOLTAR_BUTTON).onClick(action);
    }

    /**
     * Obtem o formulário da tela.
     *
     * @return FormBuilder - Formulário
     */
    public FormBuilder form() {
        return FORM;
    }

    /**
     * Obtem um campo do formulário.
     *
     * @param identifier - Identificador do campo
     * @return String - Valor do campo
     */
    public String value(String identifier) {
        return FORM.get(identifier);
    }

    /**
     * Obtem uma entidade do formulário.
     *
     * @param identifier - Identificador da entidade
     * @return T - Entidade
     */
    public <T> T entity(String identifier) {
        return FORM.getEntity(identifier);
    }

    /**
     * Obtem entidades selecionadas do formulário.
     *
     * @param identifier - Identificador das entidades
     * @return List<T> - Entidades selecionadas
     */
    public <T> List<T> entities(String identifier) {
        return FORM.getEntities(identifier);
    }

    /**
     * Constrói a tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected final JPanel build() {
        return UILayouts.border()
                .add(buildCenter())
                .padding(Spacing.LG)
                .build();
    }

    /**
     * Constrói os elementos do centro da tela.
     *
     * @return JPanel - Painel raiz
     */
    private JPanel buildCenter() {
        return UILayouts.column()
            .add(TITLE_LABEL)
            .gap(Spacing.SM)
            .add(DESCRIPTION_LABEL)
            .glue()
            .add(buildForm())
            .glue()
            .add(buildButtons())
            .build();
    }

    /**
     * Constrói os botões da tela.
     *
     * @return JPanel - Painel raiz
     */
    protected JPanel buildButtons() {
        return UILayouts.row()
                .add(CRIAR_BUTTON)
                .gap(Spacing.XS)
                .add(LIMPAR_BUTTON)
                .gap(Spacing.XS)
                .add(VOLTAR_BUTTON)
                .build();
    }

    /**
     * Constrói o formulário.
     *
     * @return JPanel - Painel raiz
     */
    protected JPanel buildForm() {
        return FORM.build();
    }
}
