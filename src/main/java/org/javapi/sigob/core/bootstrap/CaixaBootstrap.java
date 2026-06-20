package org.javapi.sigob.core.bootstrap;

import java.util.List;

import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Caixa;
import org.javapi.sigob.model.service.CaixaService;
import org.javapi.sigob.view.v2.context.CaixaContext;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;

/**
 * Responsável pela inicialização do caixa.
 */
public final class CaixaBootstrap {
    @SuppressWarnings("UnnecessaryReturnStatement")
    public static void bootstrap() {
        CaixaService caixaService = ServiceFactory.caixas();
        List<Caixa> caixasAbertos = caixaService.findAbertos();

        // Nenhum caixa aberto, nada pra fazer.
        if (caixasAbertos.isEmpty()) {
            return;
        }

        Caixa ultimoCaixa = caixasAbertos.get(0);
        String resultado = UIDialogs.option(
            "O caixa anterior (%s) permaneceu aberto.\nDeseja fechar ele?".formatted(ultimoCaixa.getDataAbertura().toString()),
            "Caixa",
            "Sim",
            "Não"
        );

        if (resultado.equals("Não")) {
            CaixaContext.setCurrentCaixa(ultimoCaixa);
            return;
        }
     }
}
