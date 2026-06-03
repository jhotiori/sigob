package org.javapi.sigob.view.screens.caixa;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Saldo;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.SaldoTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de movimentações de saldo.
 */
public final class SaldoScreen extends BaseScreen {

    /**
     * Campo valor.
     */
    private final JTextField valorField =
            UI.textField(field -> field.setColumns(12));

    /**
     * Campo descrição.
     */
    private final JTextField descricaoField =
            UI.textField(field -> field.setColumns(32));

    /**
     * Tipo da movimentação.
     */
    private final JComboBox<String> tipoCombo =
            UI.comboBox(
                    "ENTRADA",
                    "SAIDA"
            );

    /**
     * Campo pesquisa.
     */
    private final JTextField pesquisaField =
            UI.textField(field -> field.setColumns(20));

    /**
     * Modelo tabela.
     */
    private final SaldoTableModel tableModel =
            new SaldoTableModel();

    /**
     * Tabela.
     */
    private final JTable table =
            UI.table(tableModel);

    /**
     * Botões.
     */
    private final JButton registrarButton =
            UI.button("Registrar");

    private final JButton limparButton =
            UI.button("Limpar");

    private final JButton pesquisarButton =
            UI.button("Pesquisar");

    private final JButton listarTodosButton =
            UI.button("Listar Todos");

    /**
     * Construtor.
     */
    public SaldoScreen() {
        super("saldo");

        initialize();
    }

    @Override
    protected void setup() {

        Events.mouse(registrarButton, mouse -> {
            mouse.onClicked(this::registrarSaldo);
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::limparFormulario);
        });

        Events.mouse(pesquisarButton, mouse -> {
            mouse.onClicked(this::pesquisar);
        });

        Events.mouse(listarTodosButton, mouse -> {
            mouse.onClicked(this::listarTodos);
        });

        listarTodos();
    }

    @Override
    protected JPanel build() {

        return UI.border()
                .center(buildContent())
                .padding(Spacing.XL)
                .build();
    }

    @Override
    public void refresh() {
        listarTodos();
    }

    /**
     * Conteúdo principal.
     */
    private JPanel buildContent() {

        return UI.column()
                .add(
                        UIScreen.title(
                                "Controle de Saldo"
                        ),
                        UIScreen.subtitle(
                                "Gerencie entradas e saídas financeiras."
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Movimentação",
                                buildCadastro()
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Histórico",
                                buildHistorico()
                        )
                )
                .build();
    }

    /**
     * Cadastro.
     */
    private JPanel buildCadastro() {

        return UI.column()
                .add(
                        UIForm.field(
                                UIForm.fieldLabel("Valor"),
                                valorField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Tipo"),
                                tipoCombo
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Descrição"),
                                descricaoField
                        )
                )
                .glue()
                .add(
                        UIScreen.actions(
                                registrarButton,
                                limparButton
                        )
                )
                .build();
    }

    /**
     * Histórico.
     */
    private JPanel buildHistorico() {

        return UI.column()
                .add(
                        UI.row()
                                .add(
                                        pesquisaField
                                )
                                .glue()
                                .add(
                                        pesquisarButton,
                                        listarTodosButton
                                )
                                .build()
                )
                .glue()
                .add(
                        UI.scroll(table)
                )
                .build();
    }

    /**
     * Registrar saldo.
     */
    private void registrarSaldo() {

        try {

            String valorTexto =
                    valorField.getText().trim();

            if (valorTexto.isBlank()) {
                Popups.warn(
                        "Informe o valor!"
                );
                return;
            }

            BigDecimal valor =
                    new BigDecimal(valorTexto);

            Saldo saldo = new Saldo();

            saldo.setValorSaldo(valor);
            saldo.setDescricao(
                    descricaoField.getText().trim()
            );
            saldo.setTipo(
                    (String) tipoCombo.getSelectedItem()
            );
            saldo.setDataSaldo(
                    OffsetDateTime.now()
            );

            ApplicationContext
                    .getSaldoService()
                    .save(saldo);

            Popups.success(
                    "Movimentação registrada!"
            );

            limparFormulario();

            listarTodos();

        } catch (Exception e) {

            Popups.error(
                    e.getMessage()
            );
        }
    }

    /**
     * Pesquisa.
     */
    private void pesquisar() {

        try {

            String texto =
                    pesquisaField.getText().trim();

            if (texto.isBlank()) {
                listarTodos();
                return;
            }

            List<Saldo> resultados =
                    ApplicationContext
                            .getSaldoService()
                            .findByTipo(texto);

            tableModel.setSaldos(
                    resultados
            );

        } catch (Exception e) {

            Popups.error(
                    e.getMessage()
            );
        }
    }

    /**
     * Lista todos.
     */
    private void listarTodos() {

        try {

            tableModel.setSaldos(
                    ApplicationContext
                            .getSaldoService()
                            .findAll()
            );

        } catch (Exception e) {

            Popups.error(
                    e.getMessage()
            );
        }
    }

    /**
     * Limpa formulário.
     */
    private void limparFormulario() {

        valorField.setText("");
        descricaoField.setText("");
        tipoCombo.setSelectedIndex(0);
    }
}