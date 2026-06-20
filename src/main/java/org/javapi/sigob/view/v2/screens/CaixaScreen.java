package org.javapi.sigob.view.v2.screens;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.layouts.ColumnBuilder;
import org.javapi.sigob.view.v2.framework.styles.Spacing;
import org.javapi.sigob.view.v2.framework.ui.UI;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;
import org.javapi.sigob.view.v2.framework.ui.UIText;
import org.javapi.sigob.view.v2.tables.SaldoTableModel;

public final class CaixaScreen extends BaseScreen {
    /**
     * Label de status.
     */
    private final JLabel STATUS_LABEL = UIText.paragraph("???");

    /**
     * Label de funcionário.
     */
    private final JLabel VALOR_SALDO_LABEL = UIText.paragraph("R$0,00");

    /**
     * Label de valor.
     */
    private final JLabel VALOR_ABERTURA_LABEL = UIText.paragraph("R$0,00");

    /**
     * Label de data de abertura.
     */
    private final JLabel DATA_ABERTURA_LABEL = UIText.paragraph("??/??/????");

    /**
     * Botão de abrir caixa.
     */
    private final JButton ABRIR_BUTTON = UI.button("Abrir");

    /**
     * Botão de fechar caixa.
     */
    private final JButton FECHAR_BUTTON = UI.button("Fechar");

    /**
     * Tabela de saldos.
     */
    private final JTable SALDO_TABLE = UI.table();

    /**
     * Modelo da tabela de saldos.
     */
    private final SaldoTableModel SALDO_MODEL = new SaldoTableModel();

    /**
     * Construtor.
     */
    public CaixaScreen() {
        super("caixa");
        SALDO_TABLE.setModel(SALDO_MODEL);
    }

    /**
     * Define o comportamento do botão de abrir caixa.
     */
    public void onAbrirCaixa(Runnable runnable) {
        UIEvents.bind(ABRIR_BUTTON).onClick(runnable);
    }

    /**
     * Define o comportamento do botão de fechar caixa.
     */
    public void onFecharCaixa(Runnable runnable) {
        UIEvents.bind(FECHAR_BUTTON).onClick(runnable);
    }

    /**
     * Retorna a label de status.
     */
    public JLabel statusLabel() {
        return STATUS_LABEL;
    }

    /**
     * Retorna a label de funcionário.
     */
    public JLabel saldoLabel() {
        return VALOR_SALDO_LABEL;
    }

    /**
     * Retorna a label de valor de abertura.
     */
    public JLabel valorAberturaLabel() {
        return VALOR_ABERTURA_LABEL;
    }

    /**
     * Retorna a label de data de abertura.
     */
    public JLabel dataAberturaLabel() {
        return DATA_ABERTURA_LABEL;
    }

    /**
     * Retorna a tabela de saldos.
     */
    public JTable table() {
        return SALDO_TABLE;
    }

    /**
     * Retorna o modelo da tabela de saldos.
     */
    public SaldoTableModel model() {
        return SALDO_MODEL;
    }

    @Override
    protected JPanel build() {
        return UILayouts.column()
            .add(buildTitle())
            .glue()
            .add(buildInformacoesSection())
            .glue()
            .add(buildButtonsSection())
            .glue()
            .add(buildSaldosSection())
            .padding(Spacing.MD)
            .build();
    }

    private JLabel buildTitle() {
        return UIText.header("Caixa");
    }

    private JPanel buildInformacoesSection() {
        return UILayouts.column()
            .add(UIText.title("Informações"))
            .add(buildInformacoesGrid())
            .build();
    }

    private JPanel buildInformacoesGrid() {
        return UILayouts.grid(2, 2)
            .add(
                buildColumn(
                                UIText.subtitle("Status"),
                                STATUS_LABEL
                )
            )
            .add(
                buildColumn(
                                UIText.subtitle("Data de Abertura"),
                                DATA_ABERTURA_LABEL
                )
            )
            .add(
                buildColumn(
                                UIText.subtitle("Valor de Abertura"),
                                VALOR_ABERTURA_LABEL
                )
            )
                .add(
                        buildColumn(
                                UIText.subtitle("Valor de Saldo"),
                                VALOR_SALDO_LABEL
                            )
                        )
            .build();
    }

    private JPanel buildSaldosSection() {
        return UILayouts.column()
            .add(UIText.title("Saldos"))
            .add(UI.scroll(SALDO_TABLE))
            .build();
    }

    private JPanel buildButtonsSection() {
        return UILayouts.column()
            .add(UIText.title("Ações"))
            .add(buildButtonsGrid())
            .build();
    }

    private JPanel buildButtonsGrid() {
        return UILayouts.grid(1, 2)
            .add(ABRIR_BUTTON)
            .add(FECHAR_BUTTON)
            .build();
    }

    private JPanel buildColumn(JComponent ...components) {
        ColumnBuilder builder = UILayouts.column();

        for (JComponent component : components) {
            builder.add(component);
        }

        return builder.build();
    }
}
