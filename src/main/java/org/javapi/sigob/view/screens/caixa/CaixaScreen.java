package org.javapi.sigob.view.screens.caixa;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.javapi.sigob.entity.Caixa;
import org.javapi.sigob.service.CaixaService;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.CaixaTableModel;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de gerenciamento de caixas.
 */
public final class CaixaScreen extends BaseScreen {

    /**
     * Serviço de caixas.
     */
    private final CaixaService caixaService
            = ApplicationContext.getCaixaService();

    /**
     * Modelo da tabela.
     */
    private final CaixaTableModel tableModel
            = new CaixaTableModel();

    /**
     * Tabela de caixas.
     */
    private final JTable table
            = UI.table(tableModel);

    /**
     * Botão abrir/fechar caixa.
     */
    private final JButton abrirFecharButton
            = UI.button("Abrir Caixa");

    /**
     * Botão atualizar.
     */
    private final JButton atualizarButton
            = UI.button("Atualizar");

    /**
     * Botão de saldos.
     */
    private final JButton saldosButton
            = UI.button("Saldos");

    /**
     * Cria tela de caixa.
     */
    public CaixaScreen() {
        super("caixa");

        initialize();
    }

    /**
     * Setup da tela.
     */
    @Override
    protected void setup() {

        Events.mouse(
                abrirFecharButton,
                mouse -> mouse.onClicked(
                        this::abrirOuFecharCaixa
                )
        );

        Events.mouse(
                atualizarButton,
                mouse -> mouse.onClicked(
                        this::refresh
                )
        );

        Events.mouse(
                saldosButton,
                mouse -> mouse.onClicked(
                        this::abrirTelaSaldos
                )
        );

        refresh();
    }

    /**
     * Constrói tela.
     */
    @Override
    protected JPanel build() {

        return UI.border()
                .center(buildContent())
                .padding(Spacing.XL)
                .build();
    }

    /**
     * Atualiza tela.
     */
    @Override
    public void refresh() {

        listarCaixas();

        atualizarBotao();
    }

    /**
     * Constrói conteúdo principal.
     *
     * @return JPanel
     */
    private JPanel buildContent() {

        return UI.column()
                .add(
                        UIScreen.title(
                                "Caixa"
                        ),
                        UIScreen.subtitle(
                                "Gerencie abertura e fechamento de caixas."
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Operações",
                                UIScreen.actions(
                                        abrirFecharButton,
                                        atualizarButton,
                                        saldosButton
                                )
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Caixas",
                                UI.scroll(table)
                        )
                )
                .build();
    }

    /**
     * Lista caixas.
     */
    private void listarCaixas() {

        try {

            List<Caixa> caixas
                    = caixaService.findAll();

            tableModel.setCaixas(caixas);

        } catch (Exception e) {

            Popups.error(
                    "Erro ao listar caixas:\n"
                            + e.getMessage()
            );
        }
    }

    /**
     * Atualiza texto do botão.
     */
    private void atualizarBotao() {

        try {

            boolean existeAberto
                    = caixaService.findAll()
                    .stream()
                    .anyMatch(
                            caixa -> "ABERTO"
                                    .equalsIgnoreCase(
                                            caixa.getStatus()
                                    )
                    );

            abrirFecharButton.setText(
                    existeAberto
                            ? "Fechar Caixa"
                            : "Abrir Caixa"
            );

        } catch (Exception e) {

            abrirFecharButton.setText(
                    "Abrir Caixa"
            );
        }
    }

    /**
     * Decide se abre ou fecha.
     */
    private void abrirOuFecharCaixa() {

        try {

            boolean existeAberto
                    = caixaService.findAll()
                    .stream()
                    .anyMatch(
                            caixa -> "ABERTO"
                                    .equalsIgnoreCase(
                                            caixa.getStatus()
                                    )
                    );

            if (existeAberto) {

                fecharCaixa();

            } else {

                abrirCaixa();
            }

        } catch (Exception e) {

            Popups.error(
                    e.getMessage()
            );
        }
    }

    /**
     * Abre caixa.
     *
     * ADAPTAR AO SERVICE REAL.
     */
    private void abrirCaixa() {

        try {

            boolean confirmar
                    = Popups.confirm(
                    "Deseja abrir um novo caixa?"
            );

            if (!confirmar) {
                return;
            }

            /*
             * IMPLEMENTAR
             *
             * Caixa caixa = new Caixa();
             *
             * caixa.setValorAbertura(...);
             * caixa.setValorSaldo(...);
             * caixa.setStatus("ABERTO");
             *
             * caixaService.save(caixa);
             */

            Popups.success(
                    "Caixa aberto com sucesso!"
            );

            refresh();

        } catch (Exception e) {

            Popups.error(
                    e.getMessage()
            );
        }
    }

    /**
     * Fecha caixa.
     *
     * ADAPTAR AO SERVICE REAL.
     */
    private void fecharCaixa() {

        try {

            boolean confirmar
                    = Popups.confirm(
                    "Deseja fechar o caixa atual?"
            );

            if (!confirmar) {
                return;
            }

            /*
             * IMPLEMENTAR
             *
             * Caixa aberto =
             *      caixaService.findCaixaAberto();
             *
             * aberto.setStatus("FECHADO");
             *
             * caixaService.update(aberto);
             */

            Popups.success(
                    "Caixa fechado com sucesso!"
            );

            refresh();

        } catch (Exception e) {

            Popups.error(
                    e.getMessage()
            );
        }
    }

    /**
     * Abre tela de saldos.
     */
    private void abrirTelaSaldos() {

        try {

            /*
             * ADAPTAR
             *
             * ApplicationContext
             *      .getApplicationWindow()
             *      .showScreen("saldo");
             */

            Popups.warn(
                    "Implementar navegação para tela de saldos."
            );

        } catch (Exception e) {

            Popups.error(
                    e.getMessage()
            );
        }
    }

}