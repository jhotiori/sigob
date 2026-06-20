package org.javapi.sigob.controller.cadastro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.javapi.sigob.controller.base.BaseCadastroController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.model.service.ClienteService;
import org.javapi.sigob.model.service.DocumentoService;
import org.javapi.sigob.view.v2.framework.components.entity.EntityComboBox;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroClienteScreen;

public final class CadastroClienteController extends BaseCadastroController<Cliente, CadastroClienteScreen> {
    /**
     * Service de Clientes
     *
     * @see ClienteService
     */
    private final ClienteService clienteService;

    /**
     * Service de Documentos
     *
     * @see DocumentoService
     */
    private final DocumentoService documentoService;

    /**
     * Construtor.
     *
     * @param screen - Tela de cadastro de clientes
     */
    public CadastroClienteController(CadastroClienteScreen screen) {
        super(screen);
        this.clienteService = ServiceFactory.clientes();
        this.documentoService = ServiceFactory.documentos();
        this.SCREEN.onUpdate(() -> {
            EntityComboBox<Documento> box = screen.box();
            box.setEntities(documentoService.findAll());
        });
        setup();
    }

    /**
     * Salva uma entidade.
     */
    @Override
    protected void save(Cliente cliente) {
        clienteService.save(cliente);
    }

    /**
     * Cria a entidade baseada na tela.
     */
    @Override
    protected Cliente entity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Cliente cliente = new Cliente();

        cliente.setNome(SCREEN.value("nome"));
        cliente.setDocumento(SCREEN.entity("documento"));
        cliente.setDataNascimento(
                LocalDate.parse(
                        SCREEN.value("nascimento"),
                        formatter
                )
        );

        return cliente;
    }

    /**
     * Validação basica dos campos na tela.
     */
    @Override
    protected boolean validate() {
        String nome = SCREEN.value("nome");
        String nascimento = SCREEN.value("nascimento");
        boolean nomeNotBlank = UIValidation.notBlank(nome, "O nome do cliente deve ser preenchido!");
        boolean nascimentoNotBlank = UIValidation.notBlank(nascimento, "A data de nascimento do cliente deve ser preenchida!");
        return nomeNotBlank && nascimentoNotBlank;
    }

    /**
     * Mensagem de sucesso na criação.
     */
    @Override
    protected String successMessage() {
        return "Cliente registrado com sucesso!";
    }

    /**
     * Mensagem de erro.
     */
    @Override
    protected String errorMessage(Throwable e) {
        return "Erro ao registrar Cliente: " + e.getMessage();
    }
}
