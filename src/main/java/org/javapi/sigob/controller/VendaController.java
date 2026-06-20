package org.javapi.sigob.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.entity.Venda;
import org.javapi.sigob.model.service.ClienteService;
import org.javapi.sigob.model.service.FuncionarioService;
import org.javapi.sigob.model.service.VendaService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.dialogs.ClienteDialog;
import org.javapi.sigob.view.v2.dialogs.FuncionarioDialog;
import org.javapi.sigob.view.v2.dialogs.VendaDialog;
import org.javapi.sigob.view.v2.dialogs.base.BaseEntityDialog;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.screens.venda.VendaScreen;

/**
 * Controller de vendas.
 */
public final class VendaController {

    /**
     * Serviço de vendas.
     *
     * @see VendaService
     */
    private final VendaService vendaService;

    /**
     * Serviço de funcionários.
     *
     * @see FuncionarioService
     */
    private final FuncionarioService funcionarioService;

    /**
     * Serviço de clientes.
     *
     * @see ClienteService
     */
    private final ClienteService clienteService;

    /**
     * Controllers de vendas abertas.
     */
    private final Map<Integer, VendaEditorController> EDITORS = new HashMap<>();

    /**
     * Construtor.
     */
    public VendaController() {
        this.vendaService = ServiceFactory.vendas();
        this.funcionarioService = ServiceFactory.funcionarios();
        this.clienteService = ServiceFactory.clientes();
    }

    /**
     * Inicia nova venda.
     */
    public void iniciar() {

        Funcionario funcionario = select(
                new FuncionarioDialog(),
                funcionarioService.findAll());

        if (funcionario == null) {
            return;
        }

        Cliente cliente = select(
                new ClienteDialog(),
                clienteService.findAll());

        if (cliente == null) {
            return;
        }

        Venda venda = criarVenda(
                funcionario,
                cliente);

        abrirVenda(venda);
    }

    /**
     * Cria venda inicial.
     *
     * @param funcionario - Funcionário
     * @param cliente     - Cliente
     * @return Venda - Venda criada
     */
    private Venda criarVenda(
            Funcionario funcionario,
            Cliente cliente) {
        Venda venda = new Venda();

        venda.setFuncionario(funcionario);
        venda.setCliente(cliente);
        venda.setStatus("aberta");
        venda.setValorTotal(BigDecimal.ZERO);
        venda.setDataAbertura(
                OffsetDateTime.now());

        vendaService.save(venda);
        return venda;
    }

    /**
     * Continua venda existente.
     */
    public void continuar() {

        Venda venda = select(
                new VendaDialog(),
                vendaService.findAbertas());

        if (venda == null) {
            return;
        }

        abrirVenda(venda);
    }

    /**
     * Abre editor da venda.
     *
     * @param venda - Venda
     */
    public void abrirVenda(
            Venda venda) {

        VendaEditorController controller = EDITORS.get(venda.getId());

        if (controller == null) {

            VendaScreen screen = new VendaScreen(venda);
            ScreenContext.register(screen);

            controller = new VendaEditorController(screen);

            EDITORS.put(
                    venda.getId(),
                    controller);
        }

        ScreenContext.show(
                "venda-" + venda.getId());
    }

    /**
     * Seleciona entidade.
     *
     * @param dialog   - Dialog
     * @param entities - Entidades
     * @return T - Entidade selecionada
     */
    private <T> T select(
            BaseEntityDialog<T> dialog,
            Iterable<T> entities) {

        dialog.setEntities(
                entities);

        boolean confirmed = UIDialogs.custom(
                dialog.title(),
                dialog);

        if (!confirmed) {
            return null;
        }

        return dialog.getSelectedEntity();
    }
}
