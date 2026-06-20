package org.javapi.sigob.view.v2.dialogs.base;

import java.util.function.Function;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.components.ComboBoxComponent;
import org.javapi.sigob.view.v2.framework.components.LabelComponent;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.styles.Spacing;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIText;

/**
 * Classe base para dialogos de seleção.
 *
 * @param <T> - Tipo da seleção
 */
public abstract class BaseEntityDialog<T> extends BaseCustomDialog {
        /**
     * Label de cabeçalho
     *
     * @see LabelComponent
     */
    private final LabelComponent TITLE_LABEL;

    /**
     * ComboBox para seleção.
     *
     * @see ComboBoxComponent
     */
    protected final EntityComboBox<T> BOX;

    /**
     * Construtor de dialogo base.
     *
     * @param title - Título do dialogo
     * @param registry - Registro de entidades
     */
    protected BaseEntityDialog(
        String title,
        Function<T, String> formatter
    ) {
        super(title);
        this.BOX = new EntityComboBox<>(formatter);
        this.TITLE_LABEL = UIText.title(this.title());
    }

    /**
     * Define entidades a serem exibidas.
     *
     * @param entities - Entidades
     */
    public void setEntities(Iterable<T> entities) {
        BOX.setEntities(entities);
    }

    /**
     * Obtem entidade selecionada.
     *
     * @return T - Entidade selecionada
     */
    public T getSelectedEntity() {
        return BOX.getSelectedEntity();
    }

    /**
     * Constrói dialogo.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    public JPanel build() {
        return UILayouts.column()
                .add(buildHeader())
                .gap(Spacing.SM)
                .add(buildBody())
                .padding(Spacing.MD)
                .build();
    }

    /**
     * Constrói cabeçalho.
     *
     * @return JPanel - Painel raiz
     */
    private JPanel buildHeader() {
        return UILayouts.row()
                .add(TITLE_LABEL)
                .build();
    }

    /**
     * Constrói corpo.
     *
     * @return JPanel - Painel raiz
     */
    private JPanel buildBody() {
        return UILayouts.column()
                .add(BOX)
                .build();
    }
}
