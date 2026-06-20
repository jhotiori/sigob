package org.javapi.sigob.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Caixa;
import org.javapi.sigob.model.entity.Saldo;
import org.javapi.sigob.model.service.CaixaService;
import org.javapi.sigob.model.service.SaldoService;
import org.javapi.sigob.view.v2.context.CaixaContext;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.screens.CaixaScreen;

/**
 * Controller para Saldos.
 */
public final class SaldoController {
    private final CaixaScreen caixaScreen;

    /**
     * Serviço de Saldos.
     *
     * @see SaldoService
     */
    private final SaldoService saldoService;

    /**
     * Serviço de Caixas.
     *
     * @see CaixaService
     */
    private final CaixaService caixaService;

    /**
     * Construtor.
     */
    public SaldoController() {
        this.saldoService = ServiceFactory.saldos();
        this.caixaService = ServiceFactory.caixas();
        this.caixaScreen = (CaixaScreen) ScreenContext.get("caixa");
    }

    /**
     * Realiza o deposito.
     */
    public void depositar() {
        Caixa caixa = CaixaContext.getCurrentCaixa();
        if (caixa == null) {
            UIDialogs.warn("Não é possível depositar, caixa está fechado!");
            return;
        }

        String valorString = UIDialogs.prompt(
            "Informe o valor a ser depositado"
        );

        if (valorString == null || valorString.isBlank()) {
            return;
        }

        BigDecimal valorSaldo = new BigDecimal(valorString);
        if (valorSaldo.compareTo(BigDecimal.ZERO) <= 0) {
            UIDialogs.warn("O valor a ser depositado deve ser maior que zero!");
            return;
        }

        String descricaoString = UIDialogs.prompt(
            "Informe a descrição do deposito (opcional)"
        );

        Saldo novoSaldo = createSaldo(
            valorSaldo,
            descricaoString,
            "deposito"
        );

        saldoService.save(novoSaldo);

        Caixa caixaAtual = CaixaContext.getCurrentCaixa();
        caixaAtual.setValorSaldo(caixaAtual.getValorSaldo().add(valorSaldo));
        caixaService.update(caixaAtual);
        caixaScreen.update();
    }

    /**
     * Realiza o saque.
     */
    public void sacar() {
        Caixa caixa = CaixaContext.getCurrentCaixa();
        if (caixa == null) {
            UIDialogs.warn("Não é possível sacar, caixa está fechado!");
            return;
        }

        String valorString = UIDialogs.prompt(
            "Informe o valor a ser sacado"
        );

        if (valorString == null || valorString.isBlank()) {
            return;
        }

        BigDecimal valorSaldo = new BigDecimal(valorString);
        if (valorSaldo.compareTo(BigDecimal.ZERO) <= 0) {
            UIDialogs.warn("O valor a ser sacado deve ser maior que zero!");
            return;
        }

        if (valorSaldo.compareTo(caixa.getValorSaldo()) > 0) {
            UIDialogs.warn("O valor a ser sacado deve ser menor ou igual ao saldo!");
            return;
        }

        String descricaoString = UIDialogs.prompt(
            "Informe a descrição do saque (opcional)"
        );

        Saldo novoSaldo = createSaldo(
            valorSaldo,
            descricaoString,
            "saque"
        );

        saldoService.save(novoSaldo);

        Caixa caixaAtual = CaixaContext.getCurrentCaixa();
        caixaAtual.setValorSaldo(caixaAtual.getValorSaldo().subtract(valorSaldo));
        caixaService.update(caixaAtual);
        caixaScreen.update();
    }

    /**
     * Cria um novo Saldo.
     */
    public Saldo createSaldo(BigDecimal valor, String descricao, String tipo) {
        Saldo saldo = new Saldo();
        saldo.setCaixa(CaixaContext.getCurrentCaixa());
        saldo.setDataSaldo(OffsetDateTime.now());
        saldo.setValorSaldo(valor);
        saldo.setDescricao(descricao);
        saldo.setTipo(tipo);
        return saldo;
    }
}
