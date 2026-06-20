package org.javapi.sigob.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Caixa;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.service.CaixaService;
import org.javapi.sigob.model.service.SaldoService;
import org.javapi.sigob.view.v2.context.CaixaContext;
import org.javapi.sigob.view.v2.context.SessionContext;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.screens.CaixaScreen;

public final class CaixaController {
    /**
     * Tela de caixa.
     */
    private final CaixaScreen SCREEN;

    /**
     * Serviço de Caixas.
     */
    private final CaixaService caixaService;

    /**
     * Serviço de Saldos.
     */
    private final SaldoService saldoService;

    /**
     * Construtor.
     * @param caixaScreen
     */
    public CaixaController(CaixaScreen caixaScreen) {
        this.SCREEN = caixaScreen;
        this.caixaService = ServiceFactory.caixas();
        this.saldoService = ServiceFactory.saldos();
        setup();
    }

    public void setup() {
        SCREEN.onAbrirCaixa(this::abrir);
        SCREEN.onFecharCaixa(this::fechar);
        SCREEN.onUpdate(this::atualizar);
    }

    public void atualizar() {
        Caixa caixa = CaixaContext.getCurrentCaixa();

        if (caixa != null) {
            SCREEN.statusLabel().setText(caixa.getStatus());
            SCREEN.saldoLabel().setText(caixa.getValorSaldo().toString());
            SCREEN.dataAberturaLabel().setText(caixa.getDataAbertura().toString());
            SCREEN.valorAberturaLabel().setText(caixa.getValorAbertura().toString());
            SCREEN.model().setEntities(
                saldoService.findByCaixaId(caixa.getId())
            );
        } else {
            SCREEN.statusLabel().setText("???");
            SCREEN.saldoLabel().setText("???");
            SCREEN.dataAberturaLabel().setText("??/??/????");
            SCREEN.valorAberturaLabel().setText("???");
            SCREEN.model().clearEntities();
        }
    }

    public void abrir() {
        Caixa caixaAtual = CaixaContext.getCurrentCaixa();

        if (caixaAtual != null) {
            UIDialogs.warn("Não é possível abrir caixa, já existe um caixa aberto!");
            return;
        }

        String valorString = UIDialogs.prompt("Informe o valor de abertura do caixa");

        if (valorString == null) {
            return;
        }

        BigDecimal valorAbertura = new BigDecimal(valorString);

        if (valorAbertura.compareTo(BigDecimal.ZERO) <= 0) {
            UIDialogs.warn("O valor de abertura deve ser maior que zero!");
            return;
        }

        Caixa novoCaixa = new Caixa();
        novoCaixa.setValorAbertura(valorAbertura);
        novoCaixa.setValorSaldo(valorAbertura);
        novoCaixa.setDataAbertura(OffsetDateTime.now());
        novoCaixa.setStatus("aberto");

        caixaService.save(novoCaixa);
        CaixaContext.setCurrentCaixa(novoCaixa);
        atualizar();
        UIDialogs.info("Caixa aberto com sucesso!");
    }

    public void fechar() {
        Caixa caixa = CaixaContext.getCurrentCaixa();
        if (caixa == null) {
            UIDialogs.warn("Não é possível fechar caixa, nenhum caixa aberto!");
            return;
        }

        String escolha = UIDialogs.option(
            "Deseja realmente fechar este caixa?",
            "Caixa",
            "Sim",
            "Não"
        );

        if (escolha == null || escolha.equals("Não")) {
            return;
        }

        caixa.setDataFechamento(OffsetDateTime.now());
        caixa.setValorFechamento(caixa.getValorSaldo());
        caixa.setStatus("fechado");
        caixaService.update(caixa);
        CaixaContext.setCurrentCaixa(null);
        atualizar();
        UIDialogs.info("Caixa fechado com sucesso!");
    }
}
