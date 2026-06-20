package org.javapi.sigob.controller.listagem;

import java.util.List;

import org.javapi.sigob.controller.listagem.base.BaseListagemController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.model.service.ClienteService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroClienteScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemClienteScreen;

/**
 * Controller da listagem de clientes.
 */
public final class ListagemClienteController extends BaseListagemController<Cliente, ListagemClienteScreen> {

    /**
     * Serviço de clientes.
     */
    private final ClienteService clienteService;

    /**
     * Tela de cadastro.
     */
    private final CadastroClienteScreen cadastroScreen;

    /**
     * Construtor.
     *
     * @param screen         - Tela
     * @param cadastroScreen - Tela de cadastro
     */
    public ListagemClienteController(
            ListagemClienteScreen screen,
            CadastroClienteScreen cadastroScreen) {
        super(screen);

        this.clienteService = ServiceFactory.clientes();
        this.cadastroScreen = cadastroScreen;

        setup();
        reload();
    }

    @Override
    protected void bindSearches() {

        bindSearch(
                "id",
                () -> {
                    String text = SCREEN.searchText();

                    if (!UIValidation.notBlank(
                            text,
                            "O ID deve ser preenchido!")) {
                        return List.of();
                    }

                    Integer id = Integer.parseInt(text);

                    if (!UIValidation.condition(
                            id > 0,
                            "O ID deve ser maior que zero!")) {
                        return List.of();
                    }

                    return clienteService.findById(id)
                            .stream()
                            .toList();
                });

        bindSearch(
                "nome",
                () -> {
                    String text = SCREEN.searchText();

                    if (!UIValidation.notBlank(
                            text,
                            "O nome do cliente deve ser preenchido!")) {
                        return List.of();
                    }

                    return clienteService.findByNome(text);
                });

        bindSearch(
                "documento",
                () -> {
                    String text = SCREEN.searchText();

                    if (!UIValidation.notBlank(
                            text,
                            "O documento deve ser preenchido!")) {
                        return List.of();
                    }

                    return clienteService.findByDocumento(text);
                });
    }

    @Override
    protected List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @Override
    protected void delete(Cliente cliente) {
        clienteService.delete(cliente);
    }

    @Override
    protected void edit(Cliente cliente) {

        cadastroScreen.form().set(
                "nome",
                cliente.getNome()
            );

        cadastroScreen.form().set(
                "dataNascimento",
                cliente.getDataNascimento().toString()
            );

        cadastroScreen.form().setEntity(
                "documento",
                cliente.getDocumento()
            );

        ScreenContext.show(
                cadastroScreen.id()
            );
    }

    @Override
    protected String selectEntityMessage() {
        return "Selecione um cliente primeiro!";
    }

    @Override
    protected String deleteConfirmationMessage() {
        return "Deseja realmente excluir este cliente?";
    }

    @Override
    protected String deleteSuccessMessage() {
        return "Cliente excluído com sucesso!";
    }

    @Override
    protected String deleteErrorMessage(
            Throwable e) {
        return "Erro ao excluir cliente: "
                + e.getMessage();
    }
}
